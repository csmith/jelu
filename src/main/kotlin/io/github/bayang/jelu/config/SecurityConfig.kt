package io.github.bayang.jelu.config

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .logout { it -> it.logoutUrl("/api/logout")
                                .invalidateHttpSession(true)
            }
            .csrf{ it.disable() }
            .cors().disable()
            .authorizeRequests {
                it.antMatchers(
                    "/api/token","/api/setup/status"
                ).permitAll()
                it.mvcMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN", "INITIAL_SETUP")
                it.antMatchers(
                    "/api/users**",
                ).hasRole("ADMIN")
                it.antMatchers(
                    "/api/**",
                ).hasRole("USER")
            }
            .httpBasic()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

    }

}