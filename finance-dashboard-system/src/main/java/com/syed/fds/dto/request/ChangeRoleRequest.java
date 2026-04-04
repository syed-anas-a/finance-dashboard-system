package com.syed.fds.dto.request;

import com.syed.fds.enums.Role;

import jakarta.validation.constraints.NotNull;

public class ChangeRoleRequest {
	
	@NotNull(message = "role can't be empty!!")
	private Role role;
	
	public ChangeRoleRequest() {
		
	}

	public ChangeRoleRequest(@NotNull(message = "role can't be empty!!") Role role) {
		super();
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
