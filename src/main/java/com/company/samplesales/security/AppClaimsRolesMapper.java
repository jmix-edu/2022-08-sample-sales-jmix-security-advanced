package com.company.samplesales.security;

import io.jmix.oidc.OidcProperties;
import io.jmix.oidc.claimsmapper.BaseClaimsRolesMapper;
import io.jmix.security.role.ResourceRoleRepository;
import io.jmix.security.role.RowLevelRoleRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("sales_AppClaimsRolesMapper")
public class AppClaimsRolesMapper extends BaseClaimsRolesMapper {

    private OidcProperties oidcProperties;

    public AppClaimsRolesMapper(ResourceRoleRepository resourceRoleRepository,
                                RowLevelRoleRepository rowLevelRoleRepository,
                                OidcProperties oidcProperties) {
        super(resourceRoleRepository, rowLevelRoleRepository);
        this.oidcProperties = oidcProperties;
    }

    @Override
    protected Collection<String> getResourceRolesCodes(Map<String, Object> claims) {
        return getCodesFrom(claims, "jmix_resource$");
    }

    @Override
    protected Collection<String> getRowLevelRoleCodes(Map<String, Object> claims) {
        return getCodesFrom(claims, "jmix_row_level$");
    }

    private Collection<String> getCodesFrom(Map<String, Object> claims, String rolePrefix) {
        Object claimRoles = claims.get(oidcProperties.getDefaultClaimsRolesMapper().getRolesClaimName());
        if (claimRoles instanceof Collection) {
            Collection<String> roles = (Collection<String>) claimRoles;

            List<String> resultRoles = new ArrayList<>();

            for (String role : roles) {
                if (!role.startsWith(rolePrefix)) {
                    continue;
                }
                String code = role.split("\\$")[1];
                resultRoles.add(code);
            }

            return resultRoles;
        } else {
            return Collections.emptyList();
        }
    }
}
