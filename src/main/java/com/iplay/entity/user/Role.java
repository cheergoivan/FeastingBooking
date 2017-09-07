package com.iplay.entity.user;

public enum Role {
    ADMIN, USER, MANAGER;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
    
    public static Role getRoleFromAuthority(String authority){
    	authority = authority.contains("ROLE_")?authority.substring("ROLE_".length()):authority;
    	for(Role r:Role.values()){
    		if(r.toString().equalsIgnoreCase(authority)){
    			return r;
    		}
    	}
    	return null;
    }
}
