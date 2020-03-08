package edu.egg.depelos;

import edu.egg.depelos.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(10)
public class Security extends WebSecurityConfigurerAdapter{ 

    @Autowired
	public UsuarioServicio usuarioServicio;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
            System.out.println("=======================================================");
		http.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/login", "/register", "/alvaro", "/home") //los recursos de esta carpeta los puede acceder cualquier usuario
                        //sin la necesidad de tener usuario
				.permitAll()
			.and().formLogin()
                        
                        //ahora viene la configuarion del metodo login
				.loginPage("/login") //aca es donde se encuentra el login, en el controlador hay una get mapping con /login
					.loginProcessingUrl("/logincheck") //url para autenticar usuario
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/success")
					.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout")
					.permitAll().and().csrf().disable();
	}

}
