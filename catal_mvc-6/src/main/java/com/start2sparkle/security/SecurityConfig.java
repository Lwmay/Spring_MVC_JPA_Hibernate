package com.start2sparkle.security;

import javax.sql.DataSource;

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
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// Pour utiliser JDBC auth :
	@Autowired
	private DataSource dataSource;
	
	// On redefinit deux méthodes
	
	// Defini où sont les utilisateurs : database ou fichier ou annuaire ou ...
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		// Pour faire un test tout d'abord, les utilisateurs sont stockés en mémoire.
		// auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");			// noop : no password encoder. Le mot de passe reste en clair.
		// auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
		
		// Pour l'encoder le mot de passe :
		BCryptPasswordEncoder bcpe = getBCPE();
		//System.out.println(bcpe.encode("1234"));     -> Juste pour voir en clair le mdp à mettre dans la db.
		
		
		/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   
		 * Avec inMemoryAuthentication :
		 * 
		auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234")).roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("user").password(bcpe.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());
		>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   
		* */
		
		/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 	
		 * Avec JDBC authentification :
		 * 
		 * Il faut tout d'abord aller dans la base de donnée et ajouter 3 tables : 
		 * >> users avec 3 champs : username, password, active.
		 * >> roles avec 1 champs
		 * >> users_roles 2 champs
		 */
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
			.authoritiesByUsernameQuery("select username as pricipal, roles as roles from users_roles where username=?")
			.rolePrefix("ROLE_")
			.passwordEncoder(getBCPE());
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.formLogin();				// On dit que l'on a besoin d'un formulaire d'authentification.
										// attention par default, spring suppose que pour les ressources il ne faut pas d'authenfication
										// Pour instanrer pour toutes les ressources on fait :
		// http.authorizeRequests().anyRequest().authenticated(); 	// C'est une règle : toutes les requettes nécessite une authentification.
		
		// On spécifie ici les adresses qui ont besoin d'authentification :
		// http.authorizeRequests().antMatchers("/save","/delete","/edit","formProduit").hasRole("ADMIN");
		
		// Mais ce n'est pas parfait si on a bcp d'url
		// On fait alors :
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");  	// Toutes les url contenant /admin/* sont accessible avec role ADMIN
		http.authorizeRequests().antMatchers("/user/*").hasRole("USER");  	// Toutes les url contenant /admin/* sont accessible avec role USER
	}
	
	@Bean									// Une fois que l'obet est créé, il est placé dans le context spring.
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
	
}
