package com.madbarsoft.appconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(customAuthenticationProvider);
		auth.eraseCredentials(false);
		
	}

	 @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	      .antMatchers("/css/**")
	      .antMatchers("/js/**")
	      .antMatchers("/loginform/**")
	      .antMatchers("/vendor/**");
	  }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http
	         .authorizeRequests()
	     		 .antMatchers("/book/**").hasRole("USER")
	       		 .antMatchers("/admin/**").hasRole("ADMIN")	
	             .antMatchers("/", "/home").permitAll()
	             .antMatchers("/login").permitAll()
	             .anyRequest().authenticated()
	             .and()
            .formLogin()
	             .loginPage("/login")
	             .defaultSuccessUrl("/home")
	             .permitAll()
	             .and()
	         .logout() 
	             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	             .logoutSuccessUrl("/home")
		         .permitAll()
	             .and()
	         .exceptionHandling().accessDeniedPage("/403");
   }
   

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

}
