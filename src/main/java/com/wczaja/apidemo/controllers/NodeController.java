package com.wczaja.apidemo.controllers;

import com.wczaja.apidemo.controllers.assemblers.NodeResourceAssembler;
import com.wczaja.apidemo.entities.NodeEntity;
import com.wczaja.apidemo.resources.NodeResource;
import com.wczaja.apidemo.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/node")
public class NodeController {

    @Autowired
    NodeService nodeService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity getNode(@PathVariable Long id) {
        Optional<NodeEntity> nodeEntity = nodeService.getNodeById(id);
        if (nodeEntity.isPresent()) {
            NodeResourceAssembler nodeResourceAssembler = new NodeResourceAssembler();
            NodeResource nodeResource = nodeResourceAssembler.toResource(nodeEntity.get());
            return new ResponseEntity<>(nodeResource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<NodeResource>> getNodes() {
        NodeResourceAssembler nodeResourceAssembler = new NodeResourceAssembler();
        List nodeEntities = nodeService.getAllNodes();
        List<NodeResource> nodeResources = nodeResourceAssembler.toResources(nodeEntities);
        return new ResponseEntity<>(nodeResources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createNode(@RequestBody NodeResource nodeResource, UriComponentsBuilder ucBuilder) {
        NodeEntity nodeEntity = nodeService.saveNode(nodeResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/node/{id}").buildAndExpand(nodeEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateNode(@PathVariable Long id, @RequestBody NodeResource nodeResource) {
        Optional<NodeEntity> nodeEntity = nodeService.updateNode(id, nodeResource);
        if (nodeEntity.isPresent()) {
            NodeResourceAssembler nodeResourceAssembler = new NodeResourceAssembler();
            nodeResource = nodeResourceAssembler.toResource(nodeEntity.get());
            return new ResponseEntity<>(nodeResource, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteNode(@PathVariable Long id) {
        Optional<NodeEntity> nodeEntity = nodeService.getNodeById(id);
        if (nodeEntity.isPresent()) {
            nodeService.deleteNode(nodeEntity.get());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
