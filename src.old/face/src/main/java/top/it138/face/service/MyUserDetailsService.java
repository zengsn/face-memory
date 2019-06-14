package top.it138.face.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import top.it138.face.dto.MyUserDetails;
import top.it138.face.entity.User;
import top.it138.face.entity.UserRole;

/**
 * Override the Spring {@link UserDetailsService}. so I can use my own service
 * to Load UserDetails
 * 
 * @author Lenovo
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService, MessageSourceAware {
	/** Logger available to subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService roleService;

	protected MessageSourceAccessor messages;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.selectByUserName(username);
		if (user == null) {
			this.logger.debug("Query returned no results for user '" + username + "'");

			throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
					new Object[] { username }, "Username {0} not found"));
		}
		MyUserDetails details = new MyUserDetails(user);
		List<GrantedAuthority> authorities = loadUserAuthorities(user.getId());
		details.setAuthorities(authorities);
		return details;
	}

	/**
	 * load authorities by UserRoleService
	 * 
	 * @param userId
	 * @return a list of GrantedAuthority objects for the user
	 */
	protected List<GrantedAuthority> loadUserAuthorities(Long userId) {
		List<UserRole> userRoles = roleService.selectByUserId(userId);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (UserRole userRole : userRoles) {
			String role = userRole.getRole();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
			authorities.add(sga);
		}

		return authorities;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		Assert.notNull(messageSource, "messageSource cannot be null");
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
