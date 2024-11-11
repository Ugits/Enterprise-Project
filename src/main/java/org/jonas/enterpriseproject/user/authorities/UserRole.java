package org.jonas.enterpriseproject.user.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.jonas.enterpriseproject.user.authorities.UserPermission.*;

public enum UserRole {

    GUEST(GET),
    USER(GET, POST),
    ADMIN(GET, POST, DELETE);

    private final List<String> permissions;

    UserRole(UserPermission... permissionList) {
        this.permissions = Arrays.stream(permissionList)
                .map(UserPermission::getPermission)
                .toList();
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        simpleGrantedAuthorityList.addAll(getPermissions().stream().map(SimpleGrantedAuthority::new)
                .toList());

        return simpleGrantedAuthorityList;
    }
}
