package world.pasds.back.authority.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.back.authority.entity.response.GetAuthoritiesResponseDto;
import world.pasds.back.authority.service.AuthorityService;

import java.util.List;

@RestController
@RequestMapping("/app/api/authority")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("")
    public ResponseEntity<?> getAuthorities() {
        List<GetAuthoritiesResponseDto> response = authorityService.getAuthorities();
        return ResponseEntity.ok().body(response);
    }

}
