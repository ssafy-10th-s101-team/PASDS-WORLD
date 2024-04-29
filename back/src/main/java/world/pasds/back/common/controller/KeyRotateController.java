package world.pasds.back.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public void handleMasterKeyChange(){
        log.info("masterkey-change-detected");
        totpService.refreshByMasterKey();
        teamService.refreshByMasterKey();
    }
}
