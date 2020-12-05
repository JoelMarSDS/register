package com.cash.register.domain.entity.dto;

import org.springframework.security.core.GrantedAuthority;

public class RoleDTO implements GrantedAuthority {

    private Character profile;
    private Character status;

    public Character getProfile() {
        return profile;
    }

    public void setProfile(Character profile) {
        this.profile = profile;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @Override
    public String getAuthority() {
        return this.getRoleConvert();
    }

    private String getRoleConvert(){
        if (profile.toString().equalsIgnoreCase("A")){
            return "ADMIN";
        }
        if (profile.toString().equalsIgnoreCase("O")){
            return "USER";
        }
        return null;
    }
}
