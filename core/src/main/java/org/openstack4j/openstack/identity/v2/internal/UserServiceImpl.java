package org.openstack4j.openstack.identity.v2.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.Apis;
import org.openstack4j.api.identity.v2.UserService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v2.Role;
import org.openstack4j.model.identity.v2.User;
import org.openstack4j.openstack.identity.v2.domain.KeystoneUser;
import org.openstack4j.openstack.identity.v2.domain.KeystoneUser.Users;
import org.openstack4j.openstack.internal.BaseOpenStackService;

/**
 * Identity User based Operations
 *
 * @author Jeremy Unruh
 */
public class UserServiceImpl extends BaseOpenStackService implements UserService {

    @Override
    public List<? extends User> list() {
        return get(Users.class, uri("/users")).execute().getList();
    }

    @Override
    public User get(String userId) {
        Objects.requireNonNull(userId);
        return get(KeystoneUser.class, uri("/users/%s", userId)).execute();
    }

    @Override
    public List<? extends User> listTenantUsers(String tenantId) {
        Objects.requireNonNull(tenantId);
        return get(Users.class, uri("/tenants/%s/users", tenantId)).execute().getList();
    }

    @Override
    public User create(String tenantId, String name, String password, String email, boolean enabled) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);
        return create(KeystoneUser.builder().name(name).tenantId(tenantId).email(email).password(password).enabled(enabled).build());
    }

    @Override
    public User create(User user) {
        Objects.requireNonNull(user);
        return post(KeystoneUser.class, uri("/users")).entity(user).execute();
    }

    @Override
    public ActionResponse delete(String userId) {
        Objects.requireNonNull(userId);
        return deleteWithResponse(uri("/users/%s", userId)).execute();
    }

    @Override
    public User enableUser(String userId, boolean enabled) {
        Objects.requireNonNull(userId);
        return put(KeystoneUser.class, uri("/users/%s/OS-KSADM/enabled", userId)).entity(KeystoneUser.builder().enabled(enabled).build()).execute();
    }

    @Override
    public User update(User user) {
        Objects.requireNonNull(user);
        return put(KeystoneUser.class, uri("/users/%s", user.getId())).entity(user).execute();
    }

    @Override
    public ActionResponse changePassword(String userId, String password) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(password);
        return put(ActionResponse.class, uri("/users/%s/OS-KSADM/password", userId)).entity(KeystoneUser.builder().id(userId).password(password).build()).execute();
    }

    @Override
    public List<? extends Role> listRoles(String userId) {
        Objects.requireNonNull(userId);
        return Apis.getIdentityV2Services().roles().listRolesForUser(userId);
    }

    @Override
    public List<? extends Role> listRoles(User user) {
        Objects.requireNonNull(user);
        return listRoles(user.getId());
    }

    @Override
    public List<? extends Role> listRolesOnTenant(String userId, String tenantId) {
        return Apis.getIdentityV2Services().roles().listRolesForUser(userId, tenantId);
    }

    @Override
    public List<? extends Role> listRolesOnCurrentTenant(User user) {
        Objects.requireNonNull(user);
        return Apis.getIdentityV2Services().roles().listRolesForUser(user.getId(), user.getTenantId());
    }

    @Override
    public User getByName(String userName) {
        Objects.requireNonNull(userName);
        return get(KeystoneUser.class, "/users").param("name", userName).execute();
    }
}
