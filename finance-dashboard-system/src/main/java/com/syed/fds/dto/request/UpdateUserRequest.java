package com.syed.fds.dto.request;

import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {
	
	@NotNull(message = "Username cannot be null")
	String username;
	
	@NotNull(message = "Email cannot be null")
	String email;
	
	public UpdateUserRequest() {

	}

	public UpdateUserRequest(@NotNull(message = "Username cannot be null") String username,
			@NotNull(message = "Email cannot be null") String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
