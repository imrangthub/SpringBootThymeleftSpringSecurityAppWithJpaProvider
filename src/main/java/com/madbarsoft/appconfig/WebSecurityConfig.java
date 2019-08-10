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
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	

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
	   
	 //authorize requests		
	 		http.authorizeRequests()
	 				.antMatchers("/").permitAll()
	 				.antMatchers("/home").permitAll()
	 				.antMatchers("/auth/**").permitAll()
		     		.antMatchers("/book/**").hasRole("USER")
		       		.antMatchers("/admin/**").hasRole("ADMIN")	
	 				//.antMatchers("/user/doctor").hasAnyAuthority("ROLE_DOCTOR","ROLE_ADMIN")
	 				.anyRequest()
	 				.authenticated().and().csrf().disable();
	 		      
	 	//login and logout configuration	
	 				http.formLogin()
	 				.loginPage("/login")
	 				//.loginProcessingUrl("/authLogin")
	 				.usernameParameter("username")
	 				.passwordParameter("password")
	 				.successHandler(customAuthenticationSuccessHandler)
	 				.failureHandler(customAuthenticationFailureHandler);
	 				
	 	//logout configuration
	 				http.logout()
	 				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	 				//.deleteCookies("JSESSIONID")
	 				.logoutSuccessUrl("/home");
	 				
	 				
	 	//exception configuration				
	 				http.exceptionHandling()
	 				.accessDeniedHandler(customAccessDeniedHandler);
	 		
	 	//remember me configuration
//	 			http.rememberMe(). 
//	 			key("tel-rem-me-key").
//	 			rememberMeParameter("tel-remember-me").
//	 			rememberMeCookieName("remember-me").
//	 			tokenValiditySeconds(86400).userDetailsService(myUserDetailsService);
	 			
	 	//frame configuration		
//	 		        http.headers()
//	 		        .frameOptions()
//	 		        .sameOrigin();
   }
   
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

}
