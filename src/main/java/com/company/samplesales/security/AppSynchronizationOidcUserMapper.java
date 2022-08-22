package com.company.samplesales.security;

import com.company.samplesales.entity.User;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.UserRepository;
import io.jmix.oidc.claimsmapper.ClaimsRolesMapper;
import io.jmix.oidc.usermapper.SynchronizingOidcUserMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component("sales_AppSynchronizationOidcUserMapper")
public class AppSynchronizationOidcUserMapper extends SynchronizingOidcUserMapper<User> {

    public AppSynchronizationOidcUserMapper(UnconstrainedDataManager dataManager,
                                            UserRepository userRepository,
                                            ClaimsRolesMapper claimsRolesMapper) {
        super(dataManager, userRepository, claimsRolesMapper);

        setSynchronizeRoleAssignments(true);
    }

    @Override
    protected Class<User> getApplicationUserClass() {
        return User.class;
    }

    @Override
    protected void populateUserAttributes(OidcUser oidcUser, User jmixUser) {
        jmixUser.setUsername(oidcUser.getName());
        jmixUser.setFirstName(oidcUser.getGivenName());
        jmixUser.setLastName(oidcUser.getFamilyName());
        jmixUser.setEmail(oidcUser.getEmail());
    }
}
