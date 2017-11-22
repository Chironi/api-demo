package com.wczaja.apidemo.unit;

import com.wczaja.apidemo.entities.NodeEntity;
import com.wczaja.apidemo.repositories.NodeRepository;
import com.wczaja.apidemo.resources.NodeResource;
import com.wczaja.apidemo.services.HubService;
import com.wczaja.apidemo.services.NodeService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
@SpringBootTest
public class NodeServiceTest {

    @Tested
    NodeService nodeService;

    @Injectable
    NodeRepository mockNodeRepository;

    @Injectable
    HubService mockHubService;

    @Test
    public void testGetAllNodes() {
        List<NodeEntity> nodeEntities = new ArrayList<>();
        nodeEntities.add(new NodeEntity());

        new Expectations() {{
            mockNodeRepository.findAll();
            result = nodeEntities;
        }};

        assertThat(nodeService.getAllNodes()).isEqualTo(nodeEntities);
    }

    @Test
    public void testGetNodeById() {
        new Expectations() {{
            mockNodeRepository.findById(anyLong);
            result = new NodeEntity();
        }};

        assertThat(nodeService.getNodeById(1L).isPresent()).isTrue();
    }

    @Test
    public void testSaveNode() {
        NodeResource nodeResource = new NodeResource();
        nodeResource.setName("node1");

        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName("node1");

        new Expectations() {{
            mockNodeRepository.save((NodeEntity) any);
            result = nodeEntity;
        }};

        assertThat(nodeService.saveNode(nodeResource).getName()).isEqualTo(nodeResource.getName());

        new Verifications() {{
            mockNodeRepository.save((NodeEntity) any);
            times = 1;
        }};
    }

    @Test
    public void testUpdateNode() {
        NodeResource nodeResource = new NodeResource();
        nodeResource.setName("node1");

        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setName("node1");

        new Expectations() {{
            mockNodeRepository.save((NodeEntity) any);
            result = nodeEntity;
        }};

        Optional<NodeEntity> nodeEntityOptional = nodeService.updateNode(1L, nodeResource);
        assertThat(nodeEntityOptional.isPresent()).isTrue();
        assertThat(nodeEntityOptional.get().getName()).isEqualTo(nodeResource.getName());

        new Verifications() {{
            mockNodeRepository.save((NodeEntity) any);
            times = 1;
        }};
    }

    @Test
    public void testUpdateNode_NotFound() {
        new Expectations() {{
            mockNodeRepository.findById(anyLong);
            result = null;
        }};

        assertThat(nodeService.updateNode(1L, new NodeResource()));

        new Verifications() {{
            mockNodeRepository.save((NodeEntity) any);
            times = 0;
        }};
    }

    @Test
    public void testDeleteNode() {
        nodeService.deleteNode(new NodeEntity());

        new Verifications() {{
            mockNodeRepository.delete((NodeEntity) any);
            times = 1;
        }};
    }
}
