package org.openstack4j.openstack.sahara.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.util.ToStringHelper;
import org.openstack4j.model.sahara.JobConfigHint;
import org.openstack4j.model.sahara.JobConfigHintConfig;

/**
 * For mapping JSON response to/from java objects
 *
 * @author ekasit.kijsipongse@nectec.or.th
 * @author siwat.pru@outlook.com
 */

@JsonRootName("job_config")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaharaJobConfigHint implements JobConfigHint {

    private static final long serialVersionUID = 1L;
    @JsonProperty("configs")
    private List<SaharaJobConfigHintConfig> configs;
    @JsonProperty("args")
    private List<Object> args;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends JobConfigHintConfig> getConfigs() {
        return configs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return new ToStringHelper(this)
                .add("configs", configs)
                .add("args", args)
                .toString();
    }

}
