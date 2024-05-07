package world.pasds.back.authority.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.pasds.back.authority.entity.Authority;
import world.pasds.back.authority.entity.AuthorityName;
import world.pasds.back.authority.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        List<Authority> authorityList = authorityRepository.findAll();
        if (authorityList.isEmpty()) {
            List<Authority> authorities = new ArrayList<>();
            for (AuthorityName authorityName : AuthorityName.values()) {
                authorities.add(Authority.builder().name(authorityName).build());
            }
            authorityRepository.saveAll(authorities);
        }
    }
}
