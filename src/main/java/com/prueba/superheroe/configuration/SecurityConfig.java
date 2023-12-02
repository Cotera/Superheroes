package com.prueba.superheroe.configuration;

import com.prueba.superheroe.authentication.JwtAuthenticationFilter;
import com.prueba.superheroe.authentication.JwtAuthorizationFilter;
import com.prueba.superheroe.authentication.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider)) // Filtro para gestionar la autenticación del usuario mediante el uso de tokens JWT.
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtTokenProvider, userDetailsService)) // Filtro para gestionar la autorización basada en tokens JWT.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll() // Rutas públicas
                .antMatchers( "/h2-console/**").permitAll()
                .antMatchers( "/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
            .headers().frameOptions().disable() // Necesario para que funcione el iframe de la consola H2
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
            .csrf().disable().httpBasic(); // Autenticación básica*/
    }

    /*
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }*/
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}