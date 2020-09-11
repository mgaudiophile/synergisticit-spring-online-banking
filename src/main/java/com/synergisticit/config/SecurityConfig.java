package com.synergisticit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Autowired
	AccessDeniedHandler accessDeniedHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeRequests()
					.antMatchers("/", "/home", "/css/**", "/img/**", "/api/**").permitAll()
					.antMatchers("/customer").hasAnyRole("Customer", "Admin")
					.antMatchers("/admin").hasRole("Admin")
					.anyRequest().authenticated()
					.and()
					.formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll()
					.and()
					.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
					.and()
					.logout().permitAll()
					.and()
					.httpBasic()
					.and()
					.csrf()
					.disable();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
