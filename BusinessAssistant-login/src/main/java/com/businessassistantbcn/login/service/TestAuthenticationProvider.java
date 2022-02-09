package com.businessassistantbcn.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class TestAuthenticationProvider extends DaoAuthenticationProvider {
	
	public static final List<GrantedAuthority> AUTHORITIES;
	
	static {
		AUTHORITIES = AuthorityUtils.commaSeparatedStringToAuthorityList(""
				+ "ROLE_ADMIN,"
				+ "ROLE_USER");
				// Aquí se pueden añadir más credenciales
	}
	
	private static List<TestUser> testUsers = new ArrayList<>();
	
	@Getter
	@AllArgsConstructor
	private static class TestUser {
		
		private String email;
		private String password;
		private List<String> roles;
		
		public boolean match(String name) {
			return email.equals(name);
		}
		
	}
	
	@Autowired
	public TestAuthenticationProvider(LoginService loginService) {
		testUsers.add(new TestUser("jvicente@gmail.com", "56589pp05s", List.of("ROLE_ADMIN")));
		// Aquí se pueden añadir más usuarios a 'testUsers'
		super.setUserDetailsService(loginService);
	}
	
	public static Optional<User> findByUserName(String username) {
		Function<TestUser, User> conversion = user -> {
				List<String> auth = user.getRoles();
				return new User(user.getEmail(), user.getPassword(),
						auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		};
		
		return testUsers.stream().filter(u -> u.match(username)).map(conversion).findFirst();
	}
	
}
