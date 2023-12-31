package org.openstack4j.openstack.networking.internal.ext;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.openstack4j.api.networking.ext.MemberService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.ext.Member;
import org.openstack4j.model.network.ext.MemberUpdate;
import org.openstack4j.openstack.compute.functions.ToActionResponseFunction;
import org.openstack4j.openstack.networking.domain.ext.NeutronMember;
import org.openstack4j.openstack.networking.domain.ext.NeutronMember.Members;
import org.openstack4j.openstack.networking.internal.BaseNetworkingServices;

/**
 * OpenStack (Neutron) Lbaas member based Operations
 *
 * @author liujunpeng
 */
public class MemberServiceImpl extends BaseNetworkingServices implements MemberService {

    /**
     * {@inheritDoc}
     */
    public List<? extends Member> list() {

        return get(Members.class, uri("/lb/members")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends Member> list(Map<String, String> filteringParams) {
        Invocation<Members> req = get(Members.class, uri("/lb/members"));
        if (filteringParams != null) {
            for (Map.Entry<String, String> entry : filteringParams.entrySet()) {
                req = req.param(entry.getKey(), entry.getValue());
            }
        }
        return req.execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    public Member get(String memberId) {
        Objects.requireNonNull(memberId);
        return get(NeutronMember.class, uri("/lb/members/%s", memberId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    public ActionResponse delete(String memberId) {
        Objects.requireNonNull(memberId);
        return ToActionResponseFunction.INSTANCE.apply(
                delete(Void.class, uri("/lb/members/%s", memberId)).executeWithResponse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member create(Member member) {
        Objects.requireNonNull(member);
        return post(NeutronMember.class, uri("/lb/members")).entity(member).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member update(String memberId, MemberUpdate member) {
        Objects.requireNonNull(memberId);
        Objects.requireNonNull(member);
        return put(NeutronMember.class, uri("/lb/members/%s", memberId)).entity(member).execute();
    }


}
