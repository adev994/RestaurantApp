package com.restaurant.management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/js/**","/css/**","/img/**","/libs/**","/","/about-us").permitAll()
                .antMatchers("/add-user").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .failureUrl("/login?error=true").defaultSuccessUrl("/food")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true).permitAll();

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10,new SecureRandom());
    }
}
