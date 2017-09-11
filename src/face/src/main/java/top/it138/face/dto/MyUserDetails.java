package top.it138.face.dto;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import top.it138.face.entity.User;

public class MyUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	public MyUserDetails() {};
	
	public MyUserDetails(User user) {
		BeanUtils.copyProperties(user, this);
	}
	
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;   //account non expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;   //account non locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;  //user password non expired
	}

	@Override
	public boolean isEnabled() {
		return enabled == 1;   //1 means enable; 
	}

}
