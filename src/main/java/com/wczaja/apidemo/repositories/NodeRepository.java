package com.wczaja.apidemo.repositories;

import com.wczaja.apidemo.entities.NodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository<NodeEntity, Long> {
    NodeEntity findById(Long id);
}
