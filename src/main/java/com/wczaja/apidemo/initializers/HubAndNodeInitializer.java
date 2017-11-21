package com.wczaja.apidemo.initializers;

import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.entities.NodeEntity;
import com.wczaja.apidemo.repositories.HubRepository;
import com.wczaja.apidemo.repositories.NodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HubAndNodeInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(HubAndNodeInitializer.class);

    @Autowired
    private HubRepository hubRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public void run(String... strings) throws Exception {
        log.info("-- Initializing Hub data --");

        HubEntity hubEntity1 = new HubEntity();
        hubEntity1.setProductId(UUID.randomUUID().toString());
        hubEntity1.setZigbeeId("0x0000");
        hubEntity1.setNetworkId("192.168.1.1");
        hubEntity1.setLocation("Living Room");
        hubEntity1.setName("Samsung Super Hub");
        hubEntity1.setLabel("Downstairs Hub");
        hubEntity1.setOwner("Will Czaja");
        hubEntity1.setMaxPorts(32);

        hubRepository.save(hubEntity1);

        log.info("-- Initializing Node data --");

        NodeEntity nodeEntity1 = new NodeEntity();
        nodeEntity1.setProductId(UUID.randomUUID().toString());
        nodeEntity1.setZigbeeId("0x0001");
        nodeEntity1.setNetworkId("192.168.1.2");
        nodeEntity1.setLocation("Living Room");
        nodeEntity1.setName("Motorola Door Sensor");
        nodeEntity1.setLabel("Front Door Sensor");
        nodeEntity1.setType("magnetic contact breaker");
        nodeEntity1.setGroup("Living Room");
        nodeEntity1.setHub(hubEntity1);

        NodeEntity nodeEntity2 = new NodeEntity();
        nodeEntity2.setProductId(UUID.randomUUID().toString());
        nodeEntity2.setZigbeeId("0x0002");
        nodeEntity2.setNetworkId("192.168.1.3");
        nodeEntity2.setLocation("Living Room");
        nodeEntity2.setName("SmartThings Light Switch");
        nodeEntity2.setLabel("Ambient Wall Light");
        nodeEntity2.setType("LED light strip");
        nodeEntity2.setGroup("Living Room");
        nodeEntity2.setHub(hubEntity1);

        NodeEntity nodeEntity3 = new NodeEntity();
        nodeEntity3.setProductId(UUID.randomUUID().toString());
        nodeEntity3.setZigbeeId("0x0003");
        nodeEntity3.setNetworkId("192.168.1.4");
        nodeEntity3.setLocation("Backyard");
        nodeEntity3.setName("Motorola Motion Sensor");
        nodeEntity3.setLabel("Backyard Motion Sensor");
        nodeEntity3.setType("Infrared");
        nodeEntity3.setGroup("Outside");

        nodeRepository.save(nodeEntity1);
        nodeRepository.save(nodeEntity2);
        nodeRepository.save(nodeEntity3);
    }
}
