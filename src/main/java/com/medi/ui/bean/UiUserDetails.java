package com.medi.ui.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class UiUserDetails implements UserDetails, OAuth2User, Cloneable{


	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String password;
	private String name;
	private Boolean enabled;
	private Boolean locked;
	private String roles;
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private HashMap<String, Object> attributes;

	public UiUserDetails(String username) {
		this.username = username;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (roles != null) {
			authorities.add(new SimpleGrantedAuthority(roles));
		}

		return authorities;
	}	
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setAttributes(Map<String, Object> att) {
		if (att != null) {
			this.attributes = new HashMap<>();
			this.attributes.put("name", att.get("name"));
			this.attributes.put("username", att.get("username"));
		}
	}
	
	@Override
	public Map<String, Object> getAttributes() {

		Map<String, Object> copy = new HashMap<>();
		if (this.attributes == null) {

			this.attributes = new HashMap<>();
			this.attributes.put("name", this.getName());
			copy.put("name", this.getName());
			this.attributes.put("username", this.getUsername());
			copy.put("username", this.getUsername());
		} else {

			for (Map.Entry<String, Object> s : this.attributes.entrySet()) {
				copy.put(s.getKey(), s.getValue());
			}

		}

		return copy;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public int hashCode() {
		return Objects.hash(name, username);
	}

	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		UiUserDetails other = (UiUserDetails) object;
		return Objects.equals(name, other.name)
				&& Objects.equals(username, other.username);
	}

	public Object clone() {
		UiUserDetails copy = null;

		try {
			copy = (UiUserDetails) super.clone();
		} catch (CloneNotSupportedException c) {
			c.printStackTrace();
		}

		if (attributes != null) {
			copy.attributes.put("name", this.attributes.get("name"));
			copy.attributes.put("username", this.attributes.get("username"));
		}

		return copy;

	}

}
