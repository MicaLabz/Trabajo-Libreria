package com.Libreria1.app.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Libreria1.app.servicios.ClienteServicio;


	@Configuration
	@EnableWebSecurity
	@EnableGlobalMethodSecurity(prePostEnabled=true)
	public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
		
		@Autowired
		@Qualifier("clienteServicio")
		public ClienteServicio clienteServicio;
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(clienteServicio).
			passwordEncoder(new BCryptPasswordEncoder());
		}
			
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			
			.antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll().and().formLogin().loginPage("/login")
			.loginProcessingUrl("/logincheck").usernameParameter("documento").passwordParameter("clave")
			.defaultSuccessUrl("/").failureUrl("/login?error=error").permitAll().and().logout().logoutUrl("/logout")
			.logoutSuccessUrl("/login").permitAll().and().csrf().disable();
		}
	}

