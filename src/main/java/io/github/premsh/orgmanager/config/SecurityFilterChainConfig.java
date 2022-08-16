package io.github.premsh.orgmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfig {
    @Bean
    public SecurityFilterChain filterChainConfig(HttpSecurity http) throws Exception{
        http
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()

                    .antMatchers("/**").hasAnyAuthority("SUPERADMIN")

                    .antMatchers(
                            "/org/**"
                    ).hasAnyAuthority("ADMIN")

                    .antMatchers(
                            "/org/**/department"
                    ).hasAnyAuthority("EMPLOYEE_MANAGER")

                    .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
