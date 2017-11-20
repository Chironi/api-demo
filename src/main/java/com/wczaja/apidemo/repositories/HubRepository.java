package com.wczaja.apidemo.repositories;

import com.wczaja.apidemo.entities.HubEntity;
import org.springframework.data.repository.CrudRepository;

public interface HubRepository extends CrudRepository<HubEntity, Long> {
    HubEntity findById(Long id);
}
