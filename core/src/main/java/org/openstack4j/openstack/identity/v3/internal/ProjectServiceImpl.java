package org.openstack4j.openstack.identity.v3.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.identity.v3.ProjectService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.identity.v3.Project;
import org.openstack4j.openstack.identity.v3.domain.KeystoneProject;
import org.openstack4j.openstack.identity.v3.domain.KeystoneProject.Projects;

import static org.openstack4j.core.transport.ClientConstants.PATH_PROJECTS;

public class ProjectServiceImpl extends BaseIdentityServices implements ProjectService {

    @Override
    public Project create(Project project) {
        Objects.requireNonNull(project);
        return post(KeystoneProject.class, PATH_PROJECTS).entity(project).execute();
    }

    @Override
    public Project create(String domainId, String name, String description, boolean enabled) {
        Objects.requireNonNull(domainId);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(enabled);
        return create(KeystoneProject.builder().domainId(domainId).name(name).description(description).enabled(enabled).build());
    }

    @Override
    public Project get(String projectId) {
        Objects.requireNonNull(projectId);
        return get(KeystoneProject.class, PATH_PROJECTS, "/", projectId).execute();
    }

    @Override
    public List<? extends Project> getByName(String projectName) {
        Objects.requireNonNull(projectName);
        return get(Projects.class, uri(PATH_PROJECTS)).param("name", projectName).execute().getList();
    }

    @Override
    public Project getByName(String projectName, String domainId) {
        Objects.requireNonNull(projectName);
        Objects.requireNonNull(domainId);
        return get(Projects.class, uri(PATH_PROJECTS)).param("name", projectName).param("domain_id", domainId).execute().first();
    }

    @Override
    public Project update(Project project) {
        Objects.requireNonNull(project);
        return patch(KeystoneProject.class, PATH_PROJECTS, "/", project.getId()).entity(project).execute();
    }

    @Override
    public ActionResponse delete(String projectId) {
        Objects.requireNonNull(projectId);
        return deleteWithResponse(PATH_PROJECTS, "/", projectId).execute();
    }

    @Override
    public List<? extends Project> list() {
        return get(Projects.class, uri(PATH_PROJECTS)).execute().getList();
    }

}
