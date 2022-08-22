package com.company.samplesales.security;

import io.jmix.core.security.UserRepository;
import io.jmix.security.authentication.RoleGrantedAuthority;
import io.jmix.security.model.ResourceRole;
import io.jmix.security.role.ResourceRoleRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("jmixpm_SimpleUserRepository")
public class SimpleUserRepository implements UserRepository {

    private ResourceRoleRepository resourceRoleRepository;

    public SimpleUserRepository(ResourceRoleRepository resourceRoleRepository) {
        this.resourceRoleRepository = resourceRoleRepository;
    }

    @Override
    public UserDetails getSystemUser() {
        ResourceRole role = resourceRoleRepository.getRoleByCode(FullAccessRole.CODE);
        RoleGrantedAuthority authority = RoleGrantedAuthority.ofResourceRole(role);
        return new User("system", "", Collections.singleton(authority));
    }

    @Override
    public UserDetails getAnonymousUser() {
        return new User("anonymous", "", Collections.emptyList());
    }

    @Override
    public List<? extends UserDetails> getByUsernameLike(String substring) {
        return Collections.emptyList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
