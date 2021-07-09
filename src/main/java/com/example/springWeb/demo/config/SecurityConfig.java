package com.example.springWeb.demo.config;

import com.example.springWeb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/registration","/mainPage", "/search", "/sort").not().fullyAuthenticated()
                .and()
                .authorizeRequests().antMatchers("/userPage/**").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/mainAdmin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().and().exceptionHandling().accessDeniedPage("/error")
                .and()
                .authorizeRequests().and().formLogin()// Submit URL of login page.
                .loginPage("/login")
                .defaultSuccessUrl("/successLogin")
                .failureUrl ("/login?error=true")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
