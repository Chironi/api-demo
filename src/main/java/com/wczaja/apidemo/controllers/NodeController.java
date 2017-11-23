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

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST Controller for Node HATEAOS resource
 */
@RestController
@RequestMapping(value = "/api/nodes")
public class NodeController {

    @Autowired
    NodeService nodeService;

    /**
     * Endpoint to get Node resource by Id
     *
     * @param id Id of the Node to retrieve
     * @return ResponseEntity containing the Node resource
     */
    @RequestMapping(value = "{id}", method = GET)
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

    /**
     * Endpoint to get list of Node resources
     *
     * @return ResponseEntity containing list of all Node resources
     */
    @RequestMapping(method = GET)
    public ResponseEntity getNodes() {
        NodeResourceAssembler nodeResourceAssembler = new NodeResourceAssembler();
        List nodeEntities = nodeService.getAllNodes();
        List<NodeResource> nodeResources = nodeResourceAssembler.toResources(nodeEntities);
        return new ResponseEntity<>(nodeResources, HttpStatus.OK);
    }

    /**
     * Endpoint to create a new NodeEntity from the provided NodeResource
     *
     * @param nodeResource The NodeResource to create a NodeEntity with
     * @param ucBuilder UriComponentsBuilder used for building the location header path
     * @return ResponseEntity containing header with location of newly created NodeResource
     */
    @RequestMapping(method = POST)
    public ResponseEntity createNode(@RequestBody NodeResource nodeResource, UriComponentsBuilder ucBuilder) {
        NodeEntity nodeEntity = nodeService.saveNode(nodeResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/nodes/{id}").buildAndExpand(nodeEntity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing NodeEntity from the provided NodeResource
     *
     * @param id Id of the existing NodeEntity to update
     * @param nodeResource NodeResource containing updated attributes
     * @return ResponseEntity containing the updated NodeResource
     */
    @RequestMapping(value = "{id}", method = PUT)
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

    /**
     * Endpoint to delete an existing NodeEntity
     *
     * @param id Id of the NodeEntity to delete
     * @return ResponseEntity containing the HttpStatus
     */
    @RequestMapping(value = "{id}", method = DELETE)
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
