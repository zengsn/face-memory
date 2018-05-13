package top.it138.face.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import top.it138.face.interceptor.CaptchaAuthenticationFilter;
import top.it138.face.interceptor.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Resource(name = "myUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private LogoutSuccessHandler logoutHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)    //use my custom userDetailsService
			.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/", "/login", "/code", "/registration", "/verify", "/activateAccount", "/api/**", "/photo/show").permitAll()
			.antMatchers("/users/**", "/logs/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/changePassword").authenticated()
			.anyRequest().hasAuthority("ROLE_USER")
		.and()
			.csrf().disable()
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error=2")
			.defaultSuccessUrl("/")
			.usernameParameter("username")
			.passwordParameter("password")
			.successHandler(loginHandler())
		.and().rememberMe().tokenRepository(tokenRepository())
		   .tokenValiditySeconds(1209600)
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.logoutSuccessHandler(logoutHandler)
		.and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied")
		.and()
			.addFilterBefore(captchaAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}
	
	
	@Bean
    public JdbcTokenRepositoryImpl tokenRepository(){        
        JdbcTokenRepositoryImpl j=new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Bean
	public CaptchaAuthenticationFilter captchaAuthenticationFilter() {
		CaptchaAuthenticationFilter filter = new CaptchaAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}
	
	@Bean
	public LoginSuccessHandler loginHandler() {
		return new LoginSuccessHandler();
	}
	
}