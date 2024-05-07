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
            authorities.add(Authority.builder().name(AuthorityName.PRIVATE_DATA_CREATE).build());
            authorities.add(Authority.builder().name(AuthorityName.PRIVATE_DATA_READ).build());
            authorities.add(Authority.builder().name(AuthorityName.PRIVATE_DATA_UPDATE).build());
            authorities.add(Authority.builder().name(AuthorityName.PRIVATE_DATA_DELETE).build());
            authorities.add(Authority.builder().name(AuthorityName.PRIVATE_DATA_ROLE_UPDATE).build());
            authorities.add(Authority.builder().name(AuthorityName.ROLE_CREATE).build());
            authorities.add(Authority.builder().name(AuthorityName.ROLE_READ).build());
            authorities.add(Authority.builder().name(AuthorityName.ROLE_UPDATE).build());
            authorities.add(Authority.builder().name(AuthorityName.ROLE_DELETE).build());
            authorities.add(Authority.builder().name(AuthorityName.TEAM_UPDATE).build());
            authorities.add(Authority.builder().name(AuthorityName.TEAM_DELETE).build());
            authorities.add(Authority.builder().name(AuthorityName.TEAM_INVITE).build());
            authorities.add(Authority.builder().name(AuthorityName.TEAM_REMOVE).build());
            authorityRepository.saveAll(authorities);
        }
    }
}
