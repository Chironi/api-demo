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

@Service
public class NodeService {

    @Autowired
    HubService hubService;

    @Autowired
    NodeRepository nodeRepository;

    private static final Logger log = LoggerFactory.getLogger(NodeService.class);

    public List<NodeEntity> getAllNodes() {
        return (List<NodeEntity>) nodeRepository.findAll();
    }

    public Optional<NodeEntity> getNodeById(Long id) {
        return Optional.ofNullable(nodeRepository.findById(id));
    }

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

    public void deleteNode(NodeEntity nodeEntity) {
        log.info("Deleting Node with id " + nodeEntity.getId());
        nodeRepository.delete(nodeEntity);
    }
}
