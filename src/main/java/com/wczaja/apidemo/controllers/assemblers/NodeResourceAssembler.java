package com.wczaja.apidemo.controllers.assemblers;

import com.wczaja.apidemo.controllers.HubController;
import com.wczaja.apidemo.controllers.NodeController;
import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.entities.NodeEntity;
import com.wczaja.apidemo.resources.NodeResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class NodeResourceAssembler extends ResourceAssemblerSupport<NodeEntity, NodeResource> {

    public NodeResourceAssembler() {
        super(NodeController.class, NodeResource.class);
    }

    @Override
    public NodeResource toResource(NodeEntity nodeEntity) {
        NodeResource nodeResource = createResourceWithId(nodeEntity.getId(), nodeEntity);

        nodeResource.setLabel(nodeEntity.getLabel());
        nodeResource.setLocation(nodeEntity.getLocation());
        nodeResource.setName(nodeEntity.getName());
        nodeResource.setNetworkId(nodeEntity.getNetworkId());
        nodeResource.setProductId(nodeEntity.getProductId());
        nodeResource.setZigbeeId(nodeEntity.getZigbeeId());
        nodeResource.setGroup(nodeEntity.getGroup());
        nodeResource.setType(nodeEntity.getType());
        HubEntity relatedHubEntity = nodeEntity.getHub();
        if (null != relatedHubEntity) {
            nodeResource.setHubId(nodeEntity.getHub().getId());

            Link hubLink = linkTo(methodOn(HubController.class).getHub(relatedHubEntity.getId())).withRel("hub");
            nodeResource.add(hubLink);
        }

        return  nodeResource;
    }
}
