package org.openstack4j.openstack.identity.v3.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.AuthStore;
import org.openstack4j.model.identity.AuthVersion;
import org.openstack4j.model.identity.v3.Authentication;
import org.openstack4j.openstack.common.Auth.Type;
import org.openstack4j.openstack.common.BasicResourceEntity;
import org.openstack4j.openstack.common.IdResourceEntity;

/**
 * Represents an v3 Auth object.
 */
@JsonRootName("auth")
public class KeystoneAuth implements Authentication, AuthStore {

    private static final long serialVersionUID = 1L;
    @JsonProperty
    private AuthIdentity identity;
    private AuthScope scope;
    @JsonIgnore
    private transient Type type;

    public KeystoneAuth() {
        super();
    }

    public KeystoneAuth(String tokenId) {
        this.identity = AuthIdentity.createTokenType(tokenId);
        this.type = Type.TOKEN;
    }

    public KeystoneAuth(String tokenId, AuthScope scope) {
        this.identity = AuthIdentity.createTokenType(tokenId);
        this.scope = scope;
        this.type = Type.TOKEN;
    }

    public KeystoneAuth(String userId, String password) {
        this(userId, password, null, null, Type.CREDENTIALS);
    }

    public KeystoneAuth(String userOrApplicationId, String passcode, Identifier domain, AuthScope scope, Type type) {
        this.type = type;
        this.scope = scope;

        if (this.type == Type.APPLICATION_CREDENTIALS) {
            this.identity = AuthIdentity.createApplicationType(userOrApplicationId, passcode);
        } else {
            this.identity = AuthIdentity.createCredentialType(userOrApplicationId, passcode, domain);

        }
    }

    public KeystoneAuth(AuthScope scope, Type type) {
        this.scope = scope;
        this.type = type;
    }

    protected KeystoneAuth(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return identity.getPassword().getUser().getName();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return identity.getPassword().getUser().getPassword();
    }

    @Override
    @JsonIgnore
    public String getId() {
        return identity.getPassword().getUser().getDomain().getId();
    }

    @Override
    @JsonIgnore
    public String getName() {
        return identity.getPassword().getUser().getDomain().getName();
    }

    @JsonIgnore
    @Override
    public AuthVersion getVersion() {
        return AuthVersion.V3;
    }

    public static final class AuthIdentity implements Identity, Serializable {

        private static final long serialVersionUID = 1L;

        private AuthPassword password;
        private AuthToken token;
        private List<String> methods = Lists.newArrayList();
        @JsonProperty("application_credential")
        private ApplicationCredentials applicationCredentials;

        static AuthIdentity createTokenType(String tokenId) {
            AuthIdentity identity = new AuthIdentity();
            identity.methods.add("token");
            identity.token = new AuthToken(tokenId);
            return identity;
        }

        static AuthIdentity createCredentialType(String username, String password) {
            return createCredentialType(username, password, null);
        }

        static AuthIdentity createCredentialType(String username, String password, Identifier domain) {
            AuthIdentity identity = new AuthIdentity();
            identity.password = new AuthPassword(username, password, domain);
            identity.methods.add("password");
            return identity;
        }

        static AuthIdentity createApplicationType(String userOrApplicationId, String passcode) {
            AuthIdentity identity = new AuthIdentity();
            identity.applicationCredentials = new ApplicationCredentials(userOrApplicationId, passcode);
            identity.methods.add("application_credential");
            return identity;
        }

        @Override
        public Password getPassword() {
            return password;
        }

        @Override
        public Token getToken() {
            return token;
        }

        @Override
        public List<String> getMethods() {
            return methods;
        }

        public ApplicationCredentials getApplicationCredentials() {
            return applicationCredentials;
        }

        public void setApplicationCredentials(ApplicationCredentials applicationCredentials) {
            this.applicationCredentials = applicationCredentials;
        }

        public static final class AuthToken implements Token, Serializable {

            private static final long serialVersionUID = 1L;

            @JsonProperty
            private String id;

            AuthToken() {
            }

            AuthToken(String id) {
                this.id = id;
            }

            @Override
            public String getId() {
                return id;
            }
        }

        public static final class AuthPassword implements Password, Serializable {

            private static final long serialVersionUID = 1L;

            private AuthUser user;

            public AuthPassword() {
            }

            public AuthPassword(String username, String password, Identifier domain) {
                this.user = new AuthUser(username, password, domain);
            }

            @Override
            public User getUser() {
                return user;
            }

            public static final class AuthUser extends BasicResourceEntity implements User {

                private static final long serialVersionUID = 1L;

                private AuthDomain domain;
                private String password;

                public AuthUser() {
                }

                public AuthUser(String username, String password, Identifier domainIdentifier) {
                    this.password = password;
                    if (domainIdentifier != null) {
                        domain = new AuthDomain();
                        if (domainIdentifier.isTypeID())
                            domain.setId(domainIdentifier.getId());
                        else
                            domain.setName(domainIdentifier.getId());
                        setName(username);
                    } else
                        setId(username);
                }

                @Override
                public Domain getDomain() {
                    return domain;
                }

                @Override
                public String getPassword() {
                    return password;
                }

                public static final class AuthDomain extends BasicResourceEntity implements Domain {

                    private static final long serialVersionUID = 1L;

                }
            }
        }

        public static final class ApplicationCredentials implements Serializable {

            private static final long serialVersionUID = 1L;

            private String id;
            private String secret;

            public ApplicationCredentials() {
            }

            public ApplicationCredentials(String id, String secret) {
                this.id = id;
                this.secret = secret;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

    public static final class AuthScope implements Scope, Serializable {

        private static final long serialVersionUID = 1L;

        @JsonProperty("project")
        private ScopeProject project;

        @JsonProperty("domain")
        private AuthDomain domain;

        @JsonProperty("OS-TRUST:trust")
        private ScopeTrust trust;

        public AuthScope(ScopeProject project) {
            this.project = project;
        }

        public AuthScope(AuthDomain domain) {
            this.domain = domain;
        }

        public AuthScope(ScopeTrust trust) {
            this.trust = trust;
        }

        public static AuthScope project(Identifier project, Identifier domain) {
            return new AuthScope(ScopeProject.create(project, domain));
        }

        public static AuthScope project(Identifier project) {
            return new AuthScope(ScopeProject.create(project));
        }

        public static AuthScope domain(Identifier domain) {
            Objects.requireNonNull(domain, "Domain Scope: domain identifier or name cannot be null");
            return new AuthScope(new AuthDomain(domain));
        }

        public static AuthScope trust(String id) {
            Objects.requireNonNull(id, "Trust Scope: trust id cannot be null");
            return new AuthScope(new ScopeTrust(id));
        }

        @Override
        public Project getProject() {
            return project;
        }

        @Override
        public Domain getDomain() {
            return domain;
        }

        public static final class ScopeProject extends BasicResourceEntity implements Project {

            private static final long serialVersionUID = 1L;
            private AuthDomain domain;
            @JsonProperty
            private String id;
            @JsonProperty
            private String name;

            public static ScopeProject create(Identifier project) {
                Objects.requireNonNull(project, "Project Scope: project identifier or name cannot be null");

                ScopeProject scope = new ScopeProject();

                if (project.isTypeID()) {
                    scope.id = project.getId();
                }
                return scope;

            }

            public static ScopeProject create(Identifier project, Identifier domain) {
                Objects.requireNonNull(project, "Project Scope: project identifier or name cannot be null");
                Objects.requireNonNull(domain, "Project Scope: domain identifier or name cannot be null");

                ScopeProject scope = new ScopeProject();
                scope.domain = new AuthDomain(domain);
                if (project.isTypeID()) {
                    scope.id = project.getId();
                } else {
                    scope.name = project.getId();
                }
                return scope;
            }

            @Override
            public Domain getDomain() {
                return domain;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getName() {
                return name;
            }
        }

        public static final class AuthDomain extends BasicResourceEntity implements Domain {

            private static final long serialVersionUID = 1L;

            @JsonProperty
            private String id;
            @JsonProperty
            private String name;

            public AuthDomain(Identifier domain) {
                if (domain.isTypeID())
                    this.id = domain.getId();
                else
                    this.name = domain.getId();
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getName() {
                return name;
            }
        }

        public static final class ScopeTrust extends IdResourceEntity {

            private static final long serialVersionUID = 1L;

            public ScopeTrust(String id) {
                this.setId(id);
            }

        }

    }
}
