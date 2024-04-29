package world.pasds.back.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.privateData.service.PrivateDataService;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequestMapping("/app/api/key-rotate")
@RequiredArgsConstructor
public class KeyRotateController {

    private final TotpService totpService;
    private final PrivateDataService privateDataService;

    @PostMapping("/handle-masterkey-change")
    public void handleMasterKeyChange(){

        totpService.refreshByMasterKey();
        privateDataService.refreshByMasterKey();
    }
}
