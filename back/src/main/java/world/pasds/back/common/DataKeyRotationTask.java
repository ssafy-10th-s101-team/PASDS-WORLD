package world.pasds.back.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import world.pasds.back.team.service.TeamService;
import world.pasds.back.totp.service.TotpService;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataKeyRotationTask {

    private final TotpService totpService;
    private final TeamService teamService;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void rotateDataKeys() {
        // 로그 출력
        log.info("Data key Expiration Check start.");

        //데이터 키, totp 키 만료시간 체크 후 회전 로직 실행
        totpService.rotateAllDataKeys();
        teamService.rotateAllDataKeys();
    }

}
