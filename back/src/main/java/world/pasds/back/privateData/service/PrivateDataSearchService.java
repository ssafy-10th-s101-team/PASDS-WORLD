package world.pasds.back.privateData.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.privateData.entity.PrivateData;
import world.pasds.back.privateData.entity.PrivateDataDocument;
import world.pasds.back.privateData.repository.elasticsearch.PrivateDataSearchRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.repository.RoleAuthorityRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateDataSearchService {

    private final PrivateDataSearchRepository privateDataSearchRepository;
    private final ElasticsearchClient client;
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final TeamRepository teamRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    public void savePrivateData(PrivateData privateData, Long organizationId, String organizationName, Long teamId, String teamName) {
        PrivateDataDocument document = convertToDocument(privateData, organizationId, organizationName, teamId, teamName);
        applyTimestampPipeline(document);
    }

    public void updatePrivateData(PrivateData privateData) {
        PrivateDataDocument find = privateDataSearchRepository.findByPrivateDataId(privateData.getId());
        if (find != null) {
            find.setTitle(privateData.getTitle());
            privateDataSearchRepository.save(find);
        }
    }

    public void deletePrivateData(PrivateData privateData) {
        PrivateDataDocument find = privateDataSearchRepository.findByPrivateDataId(privateData.getId());
        if (find != null) {
            privateDataSearchRepository.delete(find);
        }
    }

    public void deleteAllPrivateDataByTeamId(Long teamId) {
        List<PrivateDataDocument> find = privateDataSearchRepository.findAllByTeamId(teamId);
        privateDataSearchRepository.deleteAll(find);
    }

    public List<PrivateDataDocument> search(String title, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        SearchResponse<PrivateDataDocument> response;
        try {
            response = client.search(s -> s
                            .index("private_data")
                            .query(q -> q
                                    .bool(b -> b
                                            .must(Query.of(qb -> qb
                                                    .fuzzy(fz -> fz
                                                            .field("title")
                                                            .value(title)
                                                            .fuzziness("AUTO")
                                                    )
                                            ))
                                    )
                            ),
                    PrivateDataDocument.class
            );
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }

        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(src -> {
                    if (src == null) return false;
                    // 권한 확인
                    Team team = teamRepository.findById(src.getTeamId()).orElse(null);
                    if (team == null) return false;
                    MemberRole memberRole = memberRoleRepository.findByMemberAndTeam(member, team);
                    if (memberRole == null) return false;
                    Role role = memberRole.getRole();
                    return roleAuthorityRepository.findAllByRole(role).stream()
                            .anyMatch(roleAuthority -> roleAuthority.getAuthority().getName() == AuthorityName.PRIVATE_DATA_READ);
                })
                .toList();
    }

    private void applyTimestampPipeline(PrivateDataDocument document) {
        try {
            IndexRequest<PrivateDataDocument> request = IndexRequest.of(i -> i
                    .index("private_data")
                    .pipeline("add-timestamp")
                    .document(document)
            );
            client.index(request);
        } catch (IOException e) {
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    private PrivateDataDocument convertToDocument(PrivateData privateData, Long organizationId, String organizationName, Long teamId, String teamName) {
        return PrivateDataDocument.builder()
                .privateDataId(privateData.getId())
                .title(privateData.getTitle())
                .organizationId(organizationId)
                .organizationName(organizationName)
                .teamId(teamId)
                .teamName(teamName)
                .build();
    }

    public void renameOrganization(Long organizationId, String newName) {
        List<PrivateDataDocument> privateDataDocumentList = privateDataSearchRepository.findAllByOrganizationId(organizationId)
                .stream().peek(document -> document.setOrganizationName(newName)).toList();

        privateDataSearchRepository.saveAll(privateDataDocumentList);
    }

    public void renameTeam(Long teamId, String newName) {
        List<PrivateDataDocument> privateDataDocumentList = privateDataSearchRepository.findAllByOrganizationId(teamId)
                .stream().peek(document -> document.setOrganizationName(newName)).toList();

        privateDataSearchRepository.saveAll(privateDataDocumentList);
    }
}
