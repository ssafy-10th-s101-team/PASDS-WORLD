package world.pasds.back.role.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.role.entity.dto.request.CreateRoleRequestDto;
import world.pasds.back.role.entity.dto.request.DeleteRoleRequestDto;
import world.pasds.back.role.entity.dto.request.UpdateRoleRequestDto;
import world.pasds.back.role.entity.dto.response.GetRoleResponseDto;
import world.pasds.back.role.service.RoleService;
import world.pasds.back.team.entity.dto.request.AssignRoleRequestDto;

import java.util.List;

@RestController
@RequestMapping("/app/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{teamId}")
    public ResponseEntity<?> getRole(@PathVariable Long teamId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<GetRoleResponseDto> response = roleService.getRole(teamId, userDetails.getMemberId());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody CreateRoleRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        roleService.createRole(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRole(@RequestBody UpdateRoleRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        roleService.updateRole(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteRole(@RequestBody DeleteRoleRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        roleService.deleteRole(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        roleService.assignRole(requestDto, userDetails.getMemberId());
        return ResponseEntity.ok().build();
    }
}
