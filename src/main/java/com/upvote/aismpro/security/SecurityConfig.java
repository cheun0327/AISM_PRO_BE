package com.upvote.aismpro.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;


    // 스프링 시큐리티 룰을 무시하게 하는 Url 규칙(여기 등록하면 규칙 적용하지 않음)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/vendor/**")
                .antMatchers("/js/**")
                .antMatchers("/favicon*/**")
                .antMatchers("/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf 비활성
        http.csrf().disable()

                // exception handling에 custom handler 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // security는 session 사용이 default -> session 비활성
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                // swagger 설정
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()

                // 회원가입, 로그인 api는 토큰 없어도 허용
                .antMatchers("/login/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/token/**").permitAll()

                .antMatchers("/library/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/playlist/**").permitAll()
                .antMatchers(HttpMethod.GET, "/song/playlist/insert").authenticated()
                .antMatchers(HttpMethod.GET, "/song/**").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/sell/**").permitAll()

                .antMatchers("/playlist/validate/**").authenticated()
                .anyRequest().authenticated()       // 나머지 api 모두 인증 필요

                .and()
                .cors()

                // JWTFilter을 addFilterBefore로 등록했던 JWTSecurityConfig 클래스 적용
                .and()
                .apply(new JWTSecurityConfig(tokenProvider));

    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://141.164.62.192");
        configuration.addAllowedOrigin("http://141.164.62.192:80");
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
