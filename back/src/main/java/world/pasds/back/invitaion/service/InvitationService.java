package world.pasds.back.invitaion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.service.EmailService;
import world.pasds.back.invitaion.entity.Invitation;
import world.pasds.back.invitaion.entity.dto.request.AcceptOrganizationInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.request.AcceptTeamInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.response.RejectOrganizationInviteRequestDto;
import world.pasds.back.invitaion.entity.dto.response.RejectTeamInviteRequestDto;
import world.pasds.back.invitaion.repository.InvitationRepository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.entity.MemberOrganization;
import world.pasds.back.member.entity.MemberRole;
import world.pasds.back.member.entity.MemberTeam;
import world.pasds.back.member.repository.MemberOrganizationRepository;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.member.repository.MemberRoleRepository;
import world.pasds.back.member.repository.MemberTeamRepository;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.organization.repository.OrganizationRepository;
import world.pasds.back.role.entity.Role;
import world.pasds.back.role.repository.RoleRepository;
import world.pasds.back.team.entity.Team;
import world.pasds.back.team.repository.TeamRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final EmailService emailService;
    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final OrganizationRepository organizationRepository;
    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;

    private final String DOMAIN = "https://k10s101.p.ssafy.io";

    @Transactional
    public void inviteMemberToOrganization(Organization organization, Member sender, String receiverEmail) {
        Invitation invitation = Invitation.builder()
                .invitedBy(sender)
                .invitedMemberEmail(receiverEmail)
                .expiredAt(LocalDateTime.now().plusDays(3))
                .organization(organization)
                .build();
        invitationRepository.save(invitation);
        emailService.sendMessage(receiverEmail,
                "Invite to " + organization.getName(),
                "From " + sender.getNickname() + "(" + sender.getEmail() + ")" + " invite to " + organization.getName() + "\n" + DOMAIN);
    }

    @Transactional
    public void inviteMemberToTeam(Organization organization, Team team, Member sender, String receiverEmail) {
        Invitation invitation = Invitation.builder()
                .invitedBy(sender)
                .invitedMemberEmail(receiverEmail)
                .expiredAt(LocalDateTime.now().plusDays(3))
                .organization(organization)
                .team(team)
                .build();
        invitationRepository.save(invitation);
        emailService.sendMessage(receiverEmail,
                "Invite to " + organization.getName() + " " + team.getName(),
                "From " + sender.getNickname() + "(" + sender.getEmail() + ")" + " invite to " + organization.getName() + " " + team.getName() + "\n" + DOMAIN + "\n" + "초대받은 이메일로 회원가입을 진행해주세요.");
    }

    @Transactional
    public void acceptOrganizationInvite(AcceptOrganizationInviteRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        Invitation invitation = invitationRepository.findOrganizationInvitationByInvitedMemberEmailAndOrganizationOrderByCreatedAt(member.getEmail(), organization);

        // 조직 초대가 유효한 경우
        if (invitation != null && invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
            MemberOrganization memberOrganization = MemberOrganization.builder()
                    .member(member)
                    .organization(organization)
                    .build();
            memberOrganizationRepository.save(memberOrganization);
            invitationRepository.delete(invitation);

            /**
             * Todo sender에게 수락 알림 보내기
             */
        } else {
            /**
             * Todo sender에게 초대 만료 알림 보내기
             */
        }
    }

    @Transactional
    public void rejectOrganizationInvite(RejectOrganizationInviteRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));

        Invitation invitation = invitationRepository.findOrganizationInvitationByInvitedMemberEmailAndOrganizationOrderByCreatedAt(member.getEmail(), organization);

        // 조직 초대가 유효한 경우
        if (invitation != null && invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
            invitationRepository.delete(invitation);

            /**
             * Todo sender에게 거절 알림 보내기
             */
        } else {
            /**
             * Todo sender에게 초대 만료 알림 보내기
             */
        }
    }

    @Transactional
    public void acceptTeamInvite(AcceptTeamInviteRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));
        Role role = roleRepository.findById(requestDto.getRoleId()).orElseThrow(() -> new BusinessException(ExceptionCode.ROLE_NOT_FOUND));

        Invitation invitation = invitationRepository.findTeamInvitationByInvitedMemberEmailAndOrganizationAndTeamOrderByCreatedAt(member.getEmail(), organization, team);

        // 팀 초대가 유효한 경우
        if (invitation != null && invitation.getExpiredAt().isBefore(LocalDateTime.now())) {

            MemberOrganization findMemberAndOrganization = memberOrganizationRepository.findByMemberAndOrganization(member, organization);
            // 우리 조직원인 경우
            if (findMemberAndOrganization != null) {
                MemberTeam memberTeam = MemberTeam.builder()
                        .member(member)
                        .team(team)
                        .build();
                memberTeamRepository.save(memberTeam);

                MemberRole memberRole = MemberRole.builder()
                        .member(member)
                        .role(role)
                        .build();
                memberRoleRepository.save(memberRole);
            }

            invitationRepository.delete(invitation);

            /**
             * Todo sender에게 수락 알림 보내기
             */
        } else {
            /**
             * Todo sender에게 초대 만료 알림 보내기
             */
        }
    }

    @Transactional
    public void rejectTeamInvite(RejectTeamInviteRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Organization organization = organizationRepository.findById(requestDto.getOrganizationId()).orElseThrow(() -> new BusinessException(ExceptionCode.ORGANIZATION_NOT_FOUND));
        Team team = teamRepository.findById(requestDto.getTeamId()).orElseThrow(() -> new BusinessException(ExceptionCode.TEAM_NOT_FOUND));

        Invitation invitation = invitationRepository.findTeamInvitationByInvitedMemberEmailAndOrganizationAndTeamOrderByCreatedAt(member.getEmail(), organization, team);

        if (invitation != null && invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
            invitationRepository.delete(invitation);

            /**
             * Todo sender에게 거절 알림 보내기
             */
        } else {
            /**
             * Todo sender에게 초대 만료 알림 보내기
             */
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkInvitation(Member member, String email) {
        List<Invitation> invitationList = invitationRepository.findAllByInvitedMemberEmail(email);

        for (Invitation invitation : invitationList) {
            if (invitation != null && invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
                Organization organization = invitation.getOrganization();
                if (organization != null) {
                    MemberOrganization memberOrganization = MemberOrganization.builder()
                            .member(member)
                            .organization(organization)
                            .build();
                    memberOrganizationRepository.save(memberOrganization);
                    Team team = invitation.getTeam();
                    Role role = invitation.getRole();
                    if (team != null && role != null) {
                        MemberTeam memberTeam = MemberTeam.builder()
                                .member(member)
                                .team(team)
                                .build();
                        memberTeamRepository.save(memberTeam);

                        MemberRole memberRole = MemberRole.builder()
                                .member(member)
                                .role(role)
                                .build();
                        memberRoleRepository.save(memberRole);
                    }
                }

                invitationRepository.delete(invitation);
            }
        }
    }
}
