package study.quizzy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(auth -> auth
	                .anyRequest().permitAll() // 모든 요청 허용
	            )
	            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
	            .formLogin(form -> form.disable()) // 폼 로그인 비활성화
	            .httpBasic(basic -> basic.disable()); // HTTP Basic 비활성화

	        return http.build();
	    }
}
