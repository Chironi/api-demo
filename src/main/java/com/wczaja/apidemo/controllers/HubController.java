package com.wczaja.apidemo.controllers;

import com.wczaja.apidemo.controllers.assemblers.HubResourceAssembler;
import com.wczaja.apidemo.entities.HubEntity;
import com.wczaja.apidemo.resources.HubResource;
import com.wczaja.apidemo.services.HubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api/hub")
public class HubController {

    @Autowired
    HubService hubService;

    @RequestMapping(value = "{id}", method = GET)
    public ResponseEntity getHub(@PathVariable Long id) {
        Optional<HubEntity> hubEntity = hubService.getHubById(id);
        if (hubEntity.isPresent()) {
            HubResourceAssembler hubResourceAssembler = new HubResourceAssembler();
            HubResource hubResource = hubResourceAssembler.toResource(hubEntity.get());
            return new ResponseEntity<>(hubResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<HubResource>> getHubs() {
        HubResourceAssembler hubResourceAssembler = new HubResourceAssembler();
        List hubEntities = hubService.getAllHubs();
        List<HubResource> hubResources =  hubResourceAssembler.toResources(hubEntities);
        return new ResponseEntity<>(hubResources, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createHub(@RequestBody HubResource hubResource, UriComponentsBuilder ucBuilder) {
        HubEntity hubEntity = hubService.saveHub(hubResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/hub/{id}").buildAndExpand(hubEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = PUT)
    public ResponseEntity updateHub(@PathVariable Long id, @RequestBody HubResource hubResource) {
        Optional<HubEntity> hubEntity = hubService.updateHub(id, hubResource);
        if (hubEntity.isPresent()) {
            HubResourceAssembler hubResourceAssembler = new HubResourceAssembler();
            hubResource = hubResourceAssembler.toResource(hubEntity.get());
            return new ResponseEntity<>(hubResource, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity deleteHub(@PathVariable Long id) {
        Optional<HubEntity> hubEntity = hubService.getHubById(id);
        if (hubEntity.isPresent()) {
            hubService.deleteHub(hubEntity.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
