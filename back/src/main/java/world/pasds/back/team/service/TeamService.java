package world.pasds.back.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.UnauthorizedException;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.exception.MemberNotFoundException;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.entity.dto.request.GetTeamsRequestDto;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;
import world.pasds.back.team.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final MemberRepository memberRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public List<GetTeamsResponseDto> getTeams(GetTeamsRequestDto requestDto, Long memberId) {
        // exception 수정 예정
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("없는 유저입니다."));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new UnauthorizedException("없는 조직입니다."));
        List<Team> findTeamList = teamRepository.findAllByOrganization(organization);

        List<GetTeamsResponseDto> response = new ArrayList<>();
        for (Team team : findTeamList) {
            MemberTeam findMemberAndTeam = memberTeamRepository.findByMemberAndTeam(member, team);

            // 내가 속해 있는 팀
            if (findMemberAndTeam != null) {
                response.add(new GetTeamsResponseDto(team.getId(), organization.getId(), team.getName()));
            }
        }

        return response;
    }
}
