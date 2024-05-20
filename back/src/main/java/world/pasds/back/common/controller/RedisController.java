package world.pasds.back.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.common.service.KeyService;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final KeyService keyService;

    @GetMapping("/testGenerate")
    public String setValue() {
        System.out.println("controller 동작 완료. jwt secret key 발급확인");
        String key = keyService.getJwtSecretKey();
        return key;
    }

}
