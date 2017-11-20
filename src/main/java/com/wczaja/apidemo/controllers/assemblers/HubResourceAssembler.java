package com.wczaja.apidemo.controllers.assemblers;

import com.wczaja.apidemo.controllers.HubController;
import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.resources.HubResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class HubResourceAssembler extends ResourceAssemblerSupport<HubEntity, HubResource> {

    public HubResourceAssembler() { super(HubController.class, HubResource.class); }

    @Override
    public HubResource toResource(HubEntity hubEntity) {
        HubResource hubResource = createResourceWithId(hubEntity.getId(), hubEntity);

        hubResource.setLabel(hubEntity.getLabel());
        hubResource.setLocation(hubEntity.getLocation());
        hubResource.setName(hubEntity.getName());
        hubResource.setNetworkId(hubEntity.getNetworkId());
        hubResource.setProductId(hubEntity.getProductId());
        hubResource.setZigbeeId(hubEntity.getZigbeeId());
        hubResource.setMaxPorts(hubEntity.getMaxPorts());
        hubResource.setOwner(hubEntity.getOwner());

        return hubResource;
    }
}
