package world.pasds.back.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.team.service.TeamService;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequestMapping("/app/api/key-rotate")
@RequiredArgsConstructor
@Slf4j
public class KeyRotateController {

    private final TotpService totpService;
    private final TeamService teamService;

    @PostMapping("/handle-masterkey-change")
    public ResponseEntity<Void> handleMasterKeyChange(){
        log.info("masterkey-change-detected");
        totpService.refreshByMasterKey();
        teamService.refreshByMasterKey();
        return ResponseEntity.ok().build();
    }
}
