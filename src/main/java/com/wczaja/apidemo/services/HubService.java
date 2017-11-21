package com.wczaja.apidemo.services;

import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.repositories.HubRepository;
import com.wczaja.apidemo.resources.HubResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HubService {

    @Autowired
    HubRepository hubRepository;

    private static final Logger log = LoggerFactory.getLogger(HubService.class);

    public List<HubEntity> getAllHubs() {
        return (List<HubEntity>) hubRepository.findAll();
    }

    public Optional<HubEntity> getHubById(Long id) {
        return Optional.ofNullable(hubRepository.findById(id));
    }

    public HubEntity saveHub(HubResource hubResource) {
        HubEntity hubEntity = new HubEntity();
        hubEntity.setMaxPorts(hubResource.getMaxPorts());
        hubEntity.setOwner(hubResource.getOwner());
        hubEntity.setName(hubResource.getName());
        hubEntity.setLabel(hubResource.getLabel());
        hubEntity.setNetworkId(hubResource.getNetworkId());
        hubEntity.setZigbeeId(hubResource.getZigbeeId());
        hubEntity.setLocation(hubResource.getLocation());
        hubEntity.setProductId(hubResource.getProductId());

        return hubRepository.save(hubEntity);
    }

    public Optional<HubEntity> updateHub(Long id, HubResource hubResource) {
        Optional<HubEntity> hubEntityOptional = getHubById(id);
        if (hubEntityOptional.isPresent()) {
            HubEntity hubEntity = hubEntityOptional.get();
            hubEntity.setMaxPorts(hubResource.getMaxPorts());
            hubEntity.setOwner(hubResource.getOwner());
            hubEntity.setName(hubResource.getName());
            hubEntity.setLabel(hubResource.getLabel());
            hubEntity.setNetworkId(hubResource.getNetworkId());
            hubEntity.setZigbeeId(hubResource.getZigbeeId());
            hubEntity.setLocation(hubResource.getLocation());
            hubEntity.setProductId(hubResource.getProductId());

            log.info("Updating Hub with id " + id);
            return Optional.of(hubRepository.save(hubEntity));
        } else {
            log.info("HubService#updateHub could not find Hub with id " + id);
            return Optional.empty();
        }
    }

    public void deleteHub(HubEntity hubEntity) {
        log.info("Deleting Hub with id " + hubEntity.getId());
        hubRepository.delete(hubEntity);
    }
}
