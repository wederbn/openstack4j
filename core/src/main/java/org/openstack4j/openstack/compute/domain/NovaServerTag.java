package org.openstack4j.openstack.compute.domain;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.util.ToStringHelper;
import org.openstack4j.model.ModelEntity;

public class NovaServerTag implements ModelEntity {
    private static final long serialVersionUID = 1L;

    private List<String> tags = new ArrayList<String>();

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String... tags) {
        for (String tag : tags) {
            this.tags.add(tag);
        }
    }

    @Override
    public String toString() {
        return new ToStringHelper(this).add("tags", tags).toString();
    }

}
