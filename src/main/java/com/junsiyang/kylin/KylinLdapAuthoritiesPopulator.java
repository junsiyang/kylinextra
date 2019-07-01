package com.junsiyang.kylin;

import java.util.Set;

import com.google.common.collect.Sets;
import org.springframework.ldap.core.ContextSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.util.StringUtils;

public class KylinLdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

    public final static String ROLE_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_MODELER = "ROLE_MODELER";
    public final static String ROLE_ANALYST = "ROLE_ANALYST";


    private SimpleGrantedAuthority adminRoleAsAuthority;

    private SimpleGrantedAuthority modelerRoleAsAuthority;

    private SimpleGrantedAuthority analystRoleAsAuthority;

    /**
     * Constructor for group search scenarios. <tt>userRoleAttributes</tt> may still be
     * set as a property.
     *
     * @param contextSource   supplies the contexts used to search for user roles.
     * @param groupSearchBase if this is an empty string the search will be performed from
     */
    public KylinLdapAuthoritiesPopulator(ContextSource contextSource,
                                         String groupSearchBase, String rolesString) {
        super(contextSource, groupSearchBase);

        setConvertToUpperCase(false);
        setRolePrefix("");

        String[] roles = StringUtils.split(rolesString, ",");

        this.adminRoleAsAuthority = new SimpleGrantedAuthority(roles[0]);

        if (roles.length > 1) {
            this.modelerRoleAsAuthority = new SimpleGrantedAuthority(roles[1]);
        }

        if (roles.length > 2) {
            this.modelerRoleAsAuthority = new SimpleGrantedAuthority(roles[2]);
        }
    }

    @Override
    public Set<GrantedAuthority> getGroupMembershipRoles(String userDn, String username) {
        Set<GrantedAuthority> authorities = super.getGroupMembershipRoles(userDn, username);
        Set<GrantedAuthority> userAuthorities = Sets.newHashSet(authorities);
        if (authorities.contains(adminRoleAsAuthority)) {
            userAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        }

        if (authorities.contains(modelerRoleAsAuthority)){
            userAuthorities.add(new SimpleGrantedAuthority(ROLE_MODELER));
        }

        if (authorities.contains(analystRoleAsAuthority)){
            userAuthorities.add(new SimpleGrantedAuthority(ROLE_ANALYST));
        }

        return userAuthorities;
    }
}
