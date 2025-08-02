package study.quizzy.auth.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import study.quizzy.auth.jwt.filter.JwtFilter;
import study.quizzy.auth.security.handler.CustomAccessDeniedHandler;
import study.quizzy.auth.security.handler.CustomAuthenticationFailureHandler;
import study.quizzy.auth.security.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 프론트엔드에서 오는 교차 출처 요청(CORS)을 이 설정에 따라 허용
		http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
				.configurationSource(corsConfigurationSource()));

		// 세션을 생성하지 않음 (stateless). JWT 같은 토큰 기반 인증 시스템에서 사용
		http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화
		// REST API 서버에서는 일반적으로 CSRF 보호가 필요 없기 때문에 끄는 것이 일반적이다
		http.csrf(config -> config.disable());

		http.authorizeHttpRequests(auth -> auth
//			    .requestMatchers("/api/challengers/**").hasAuthority("0") // 0번 역할만 허용
				.requestMatchers(HttpMethod.POST, "/api/challengers/").permitAll()
			    .anyRequest().authenticated()
			);

		// 로그인페이지 URL 을 /api/challengers/login 지정하고, 인증되지 않은 사용자가 보호된 리소스를 요청하면 이 URL로
		// 리다이렉트된다.
		http.formLogin(config -> {
			config.loginPage("/api/challengers/login");
			// 로그인 성공 시 실행될 핸들러 객체를 지정 코드
			config.successHandler(new CustomAuthenticationSuccessHandler());
			// 로그인 실패 시 실행될 핸들러 객체를 지정 코드
			config.failureHandler(new CustomAuthenticationFailureHandler());
		});

		// JWT 체크 추가
		http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(config -> {
			config.accessDeniedHandler(new CustomAccessDeniedHandler());
		});

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache- Control", "Content-Type"));
		// 자격 증명(쿠키, 인증 헤더 등)을 CORS 요청과 함께 보낼 수 있도록 허용
		configuration.setAllowCredentials(true);
		// URL 패턴에 따라 CORS 설정을 매핑할 수 있는 객체
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
