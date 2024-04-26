package world.pasds.back.privateData.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.privateData.entity.dto.request.CreatePrivateDataRequestDto;
import world.pasds.back.privateData.service.PrivateDataService;
import world.pasds.back.team.entity.dto.request.GetPrivateDataListRequestDto;
import world.pasds.back.team.entity.dto.request.GetPrivateDataRequestDto;
import world.pasds.back.team.entity.dto.response.GetPrivateDataListResponseDto;
import world.pasds.back.team.entity.dto.response.GetPrivateDataResponseDto;

import java.util.List;

@RestController
@RequestMapping("/app/api/data")
@RequiredArgsConstructor
public class PrivateDataController {

    private final PrivateDataService privateDataService;

    @GetMapping("/list")
    public ResponseEntity<?> getPrivateDataList(@RequestBody GetPrivateDataListRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetPrivateDataListResponseDto> response = privateDataService.getPrivateDataList(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getPrivateData(@RequestBody GetPrivateDataRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        GetPrivateDataResponseDto response = privateDataService.getPrivateData(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPrivateData(@RequestBody CreatePrivateDataRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        privateDataService.createPrivateData(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
