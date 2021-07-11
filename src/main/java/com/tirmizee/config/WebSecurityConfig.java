package com.tirmizee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tirmizee.config.filter.XSSFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	XSSFilter xssFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http
//			.headers()
//				.xssProtection()
//				.and()
//				.and();
	
		http
			.headers()
				.contentSecurityPolicy("script-src 'self'")
				.and().and()
			.addFilterAfter(xssFilter, BasicAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/xss");
	}
	
	

}
