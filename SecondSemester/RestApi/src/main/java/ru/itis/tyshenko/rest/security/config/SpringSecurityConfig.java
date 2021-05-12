package ru.itis.tyshenko.rest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.itis.tyshenko.rest.security.token.AccessTokenAuthFilter;
import ru.itis.tyshenko.rest.security.token.AccessTokenAuthenticationProvider;
import ru.itis.tyshenko.rest.security.token.RefreshTokenAuthFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessTokenAuthFilter accessTokenAuthFilter;

    @Autowired
    private RefreshTokenAuthFilter refreshTokenAuthFilter;

    @Autowired
    private AccessTokenAuthenticationProvider accessTokenAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(accessTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(refreshTokenAuthFilter, AccessTokenAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/user/**").hasAuthority("ADMIN")
                .antMatchers("/update/**").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(accessTokenAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
