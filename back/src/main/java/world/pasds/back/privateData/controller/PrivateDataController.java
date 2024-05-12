package world.pasds.back.privateData.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.privateData.entity.PrivateDataDocument;
import world.pasds.back.privateData.entity.dto.request.*;
import world.pasds.back.privateData.service.PrivateDataSearchService;
import world.pasds.back.privateData.service.PrivateDataService;
import world.pasds.back.privateData.entity.dto.response.GetPrivateDataListResponseDto;
import world.pasds.back.privateData.entity.dto.response.GetPrivateDataResponseDto;

import java.util.List;

@RestController
@RequestMapping("/app/api/data")
@RequiredArgsConstructor
public class PrivateDataController {

    private final PrivateDataService privateDataService;
    private final PrivateDataSearchService privateDataSearchService;

    @GetMapping("/search/{organizationId}")
    public ResponseEntity<?> searchData(@PathVariable(name = "organizationId") Long organizationId, @RequestParam String title, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PrivateDataDocument> response = privateDataSearchService.search(organizationId, title, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/{teamId}/{offset}")
    public ResponseEntity<?> getPrivateDataList(@PathVariable(name = "teamId") Long teamId, @PathVariable(name = "offset") int offset,  @AuthenticationPrincipal CustomUserDetails userDetails) {
        GetPrivateDataListResponseDto response = privateDataService.getPrivateDataList(teamId, offset, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{teamId}/{privateDataId}")
    public ResponseEntity<?> getPrivateData(@PathVariable(name = "teamId") Long teamId, @PathVariable(name = "privateDataId") Long privateDataId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        GetPrivateDataResponseDto response = privateDataService.getPrivateData(teamId, privateDataId, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPrivateData(@RequestBody CreatePrivateDataRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        privateDataService.createPrivateData(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePrivateData(@RequestBody UpdatePrivateDataRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        privateDataService.updatePrivateData(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-role")
    public ResponseEntity<?> updatePrivateDataRole(@RequestBody UpdatePrivateDataRoleRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        privateDataService.updatePrivateDataRole(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePrivateData(@RequestBody DeletePrivateDataRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        privateDataService.deletePrivateData(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
