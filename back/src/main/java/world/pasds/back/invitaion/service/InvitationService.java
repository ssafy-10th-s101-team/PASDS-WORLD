package world.pasds.back.invitaion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.service.EmailService;
import world.pasds.back.invitaion.entity.Invitation;
import world.pasds.back.invitaion.repository.InvitationRepository;
import world.pasds.back.member.entity.Member;
import world.pasds.back.organization.entity.Organization;
import world.pasds.back.team.entity.Team;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final EmailService emailService;
    private final InvitationRepository invitationRepository;

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
        emailService.sendSimpleMessage(receiverEmail,
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
        emailService.sendSimpleMessage(receiverEmail,
                "Invite to " + organization.getName() + " " + team.getName(),
                "From " + sender.getNickname() + "(" + sender.getEmail() + ")" + " invite to " + organization.getName() + " " + team.getName() + "\n" + DOMAIN + "\n" + "초대받은 이메일로 회원가입을 진행해주세요.");
    }

}
