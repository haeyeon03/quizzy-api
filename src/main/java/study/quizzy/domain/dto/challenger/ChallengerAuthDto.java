package study.quizzy.domain.dto.challenger;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChallengerAuthDto implements UserDetails {
	private String challengerId;
	private String nickname;
	private String password;
	private String provider;
	private String email;


	public ChallengerAuthDto(String challengerId) {
		this.challengerId = challengerId;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.challengerId;
	}

}
