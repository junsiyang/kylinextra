# kylin extra project

This project provides some customized extensions for Apache Kylin.

- KylinLdapAuthoritiesPopulator


## KylinLdapAuthoritiesPopulator
#### Why we need it:

There are three build-in roles of Kylin: "ADMIN", "MODELER", "ANALYST". Meanwhile Kylin supports LDAP authentication but only "ADMIN" role can be configured with a LDAP group name.(kylin.security.acl.admin-role)

In some situation only one role is not enough. So KylinLdapAuthoritiesPopulator can be used to expand the capacity. 

PS: Apache Ranger can provide more flexible acl control on Kylin, but nowadays we are nowhere near that time. If 
necessary we use ranger instead of this in the future. 

#### How to use:

1. run mvn package and get kylinextra.jar
2. add `kylinextra.jar` on  `$KYLIN_HOME/tomcat/webapps/kylin/WEB-INF/lib`
3. edit `$KYLIN_HOME/tomcat/webapps/kylin/WEB-INF/classes/kylinSecurity.xml` and replace `org.apache.kylin.rest.security.LDAPAuthoritiesPopulator` with `com.junsiyang.kylin.KylinLdapAuthoritiesPopulator`
5. edit `$KYLIN_HOME/conf/kylin.properties`
6. fill in the corresponding value into `kylin.security.acl.admin-role={LDAP_GROUP_NAME1},{LDAP_GROUP_NAME2},
{LDAP_GROUP_NAME3}` (<mark>make sure the sequence of LDAP group names!</mark>)

```
{LDAP_GROUP_NAME1}: the LDAP group name for the build-in role "ROLE_ADMIN" of Kylin
{LDAP_GROUP_NAME2}: the LDAP group name for the build-in role "ROLE_MODELER" of Kylin
{LDAP_GROUP_NAME3}: the LDAP group name for the build-in role "ROLE_ANALYST" of Kylin
```
