package cn.edu.fjnu.towide.security;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

public class JdbcManagerDetailsManager extends JdbcUserDetailsManager {

	@Override
	public String getUsersByUsernameQuery() {
		return super.getUsersByUsernameQuery();
	}

}
