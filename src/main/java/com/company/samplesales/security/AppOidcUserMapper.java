package com.company.samplesales.security;

import com.google.common.base.Strings;
import io.jmix.oidc.claimsmapper.ClaimsRolesMapper;
import io.jmix.oidc.usermapper.BaseOidcUserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("sales_AppOidcUserMapper")
public class AppOidcUserMapper extends BaseOidcUserMapper<AppJmixOidcUser> {

    private ClaimsRolesMapper claimsRolesMapper;

    public AppOidcUserMapper(ClaimsRolesMapper claimsRolesMapper) {
        this.claimsRolesMapper = claimsRolesMapper;
    }

    @Override
    protected AppJmixOidcUser initJmixUser(OidcUser oidcUser) {
        return new AppJmixOidcUser();
    }

    @Override
    protected void populateUserAttributes(OidcUser oidcUser, AppJmixOidcUser jmixUser) {
        String fullName = oidcUser.getUserInfo().getFullName();
        jmixUser.setFormattedName(Strings.isNullOrEmpty(fullName)
                ? oidcUser.getPreferredUsername()
                : fullName + "[" + oidcUser.getPreferredUsername() + "]");
    }

    @Override
    protected void populateUserAuthorities(OidcUser oidcUser, AppJmixOidcUser jmixUser) {
        Collection<? extends GrantedAuthority> authorities =
                claimsRolesMapper.toGrantedAuthorities(oidcUser.getClaims());
        jmixUser.setAuthorities(authorities);
    }
}
