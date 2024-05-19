package world.pasds.back.common.util;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import world.pasds.back.member.entity.CustomUserDetails;

@RequiredArgsConstructor
@Service
public class LoginUserAuditorAware implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (null == authentication || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
			return Optional.empty();
		}

		return Optional.ofNullable(((CustomUserDetails) authentication.getPrincipal()).getMemberId());
	}
}
