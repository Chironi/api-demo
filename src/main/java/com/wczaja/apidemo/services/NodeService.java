package com.wczaja.apidemo.services;

import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.entities.NodeEntity;
import com.wczaja.apidemo.repositories.NodeRepository;
import com.wczaja.apidemo.resources.NodeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service of CRUD methods for Nodes
 */
@Service
public class NodeService {

    @Autowired
    HubService hubService;

    @Autowired
    NodeRepository nodeRepository;

    private static final Logger log = LoggerFactory.getLogger(NodeService.class);

    /**
     * Gets a List of all NodeEntities from the NodeRepository
     *
     * @return List of all NodeEntities
     */
    public List<NodeEntity> getAllNodes() {
        return (List<NodeEntity>) nodeRepository.findAll();
    }

    /**
     * Gets a NodeEntity by id from the NodeRepository
     *
     * @param id Id of the NodeEntity to retrieve
     * @return Optional of the retrieved NodeEntity
     */
    public Optional<NodeEntity> getNodeById(Long id) {
        return Optional.ofNullable(nodeRepository.findById(id));
    }

    /**
     * Creates a new NodeEntity provided a NodeResource
     *
     * @param nodeResource A NodeResource containing the parameters for the new NodeEntity
     * @return The newly created NodeEntity
     */
    public NodeEntity saveNode(NodeResource nodeResource) {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setGroup(nodeResource.getGroup());
        nodeEntity.setType(nodeResource.getType());
        nodeEntity.setLabel(nodeResource.getLabel());
        nodeEntity.setLocation(nodeResource.getLocation());
        nodeEntity.setName(nodeResource.getName());
        nodeEntity.setNetworkId(nodeResource.getNetworkId());
        nodeEntity.setZigbeeId(nodeResource.getZigbeeId());
        nodeEntity.setProductId(nodeResource.getProductId());
        Optional<HubEntity> relatedHubEntity = hubService.getHubById(nodeResource.getHubId());
        if (relatedHubEntity.isPresent()) {
            nodeEntity.setHub(relatedHubEntity.get());
        }

        return nodeRepository.save(nodeEntity);
    }

    /**
     * Updates an existing NodeEntity with the parameters of the input NodeResource
     *
     * @param id Id of the existing NodeEntity to update
     * @param nodeResource NodeResource containing updates parameters
     * @return Optional of the updates NodeEntity, or empty Optional if no existing NodeEntity for input id
     */
    public Optional<NodeEntity> updateNode(Long id, NodeResource nodeResource) {
        Optional<NodeEntity> nodeEntityOptional = getNodeById(id);
        if (nodeEntityOptional.isPresent()) {
            NodeEntity nodeEntity = nodeEntityOptional.get();
            nodeEntity.setGroup(nodeResource.getGroup());
            nodeEntity.setType(nodeResource.getType());
            nodeEntity.setName(nodeResource.getName());
            nodeEntity.setLabel(nodeResource.getLabel());
            nodeEntity.setNetworkId(nodeResource.getNetworkId());
            nodeEntity.setZigbeeId(nodeResource.getZigbeeId());
            nodeEntity.setLocation(nodeResource.getLocation());
            nodeEntity.setProductId(nodeResource.getProductId());

            log.info("Updating Node with id " + id);
            return Optional.of(nodeRepository.save(nodeEntity));
        } else {
            log.info("NodeService#updateNode could not find Node with id " + id);
            return Optional.empty();
        }
    }

    /**
     * Deletes the input NodeEntity
     *
     * @param nodeEntity The NodeEntity to delete
     */
    public void deleteNode(NodeEntity nodeEntity) {
        log.info("Deleting Node with id " + nodeEntity.getId());
        nodeRepository.delete(nodeEntity);
    }
}
