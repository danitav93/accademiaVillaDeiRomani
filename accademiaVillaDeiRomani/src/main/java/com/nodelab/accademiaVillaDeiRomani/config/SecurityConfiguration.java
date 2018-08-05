package com.nodelab.accademiaVillaDeiRomani.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.Privileges;
import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);

	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.
			authorizeRequests()
				.antMatchers(Urls.pathSeparator).denyAll() 
				.antMatchers(Urls.loginPath).permitAll()
				.antMatchers(Urls.registrationPath).permitAll()
				.antMatchers(Urls.baseAuthenticatedUserPath+Urls.pathSeparator+Urls.adminSeparator+Urls.pathSeparator+"**").hasAnyAuthority(Ruoli.ruolo_amministratore,Ruoli.ruolo_super_amministratore)
				.antMatchers(Urls.baseAuthenticatedUserPath+Urls.pathSeparator+Urls.studentSeparator+Urls.pathSeparator+"**").authenticated()
				.antMatchers(Urls.updatePassword,Urls.openUpdatePasswordView).hasAuthority(Privileges.CHANGE_PASSWORD_PRIVILEGE)
			.and()
			.formLogin()
					.loginProcessingUrl(Urls.loginPath)
					.loginPage(Urls.loginPath)
					.failureUrl(Urls.loginPath+"?error=true")
					.defaultSuccessUrl(Urls.submitLoginPath)
					.usernameParameter("matricola")
					.passwordParameter("password")
			.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl(Urls.loginPath)
			.and()
			.exceptionHandling()
				.accessDeniedPage("/access-denied");

	}

	

	@Override
	public void configure(WebSecurity web) throws Exception {

	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");

	}
	
}
