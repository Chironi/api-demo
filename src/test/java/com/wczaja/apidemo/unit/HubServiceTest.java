package com.wczaja.apidemo.unit;

import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.repositories.HubRepository;
import com.wczaja.apidemo.resources.HubResource;
import com.wczaja.apidemo.services.HubService;
import mockit.*;

import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
@SpringBootTest
public class HubServiceTest {

    @Tested
    HubService hubService;

    @Injectable
    HubRepository mockHubRepository;

    @Test
    public void testGetAllHubs() {
        List<HubEntity> hubEntities = new ArrayList<>();
        hubEntities.add(new HubEntity());

        new Expectations() {{
            mockHubRepository.findAll();
            result = hubEntities;
        }};

        assertThat(hubService.getAllHubs()).isEqualTo(hubEntities);
    }

    @Test
    public void testGetHubById() {
        new Expectations() {{
            mockHubRepository.findById(anyLong);
            result = new HubEntity();
        }};

        assertThat(hubService.getHubById(1L).isPresent()).isTrue();
    }

    @Test
    public void testSaveHub() {
        HubResource hubResource = new HubResource();
        hubResource.setName("hub1");

        HubEntity hubEntity = new HubEntity();
        hubEntity.setName("hub1");

        new Expectations() {{
            mockHubRepository.save((HubEntity) any);
            result = hubEntity;
        }};

        assertThat(hubService.saveHub(hubResource).getName()).isEqualTo(hubResource.getName());

        new Verifications() {{
            mockHubRepository.save((HubEntity) any);
            times = 1;
        }};
    }

    @Test
    public void testUpdateHub() {
        HubResource hubResource = new HubResource();
        hubResource.setName("hub1");

        HubEntity hubEntity = new HubEntity();
        hubEntity.setName("hub1");

        new Expectations() {{
            mockHubRepository.save((HubEntity) any);
            result = hubEntity;
        }};

        Optional<HubEntity> hubEntityOptional = hubService.updateHub(1L, hubResource);
        assertThat(hubEntityOptional.isPresent()).isTrue();
        assertThat(hubEntityOptional.get().getName()).isEqualTo(hubResource.getName());

        new Verifications() {{
            mockHubRepository.save((HubEntity) any);
            times = 1;
        }};
    }

    @Test
    public void testUpdateHub_NotFound() {
        new Expectations() {{
            mockHubRepository.findById(anyLong);
            result = null;
        }};

        assertThat(hubService.updateHub(1L, new HubResource()));

        new Verifications() {{
            mockHubRepository.save((HubEntity) any);
            times = 0;
        }};
    }

    @Test
    public void testDeleteHub() {
        hubService.deleteHub(new HubEntity());

        new Verifications() {{
           mockHubRepository.delete((HubEntity) any);
           times = 1;
        }};
    }
}
