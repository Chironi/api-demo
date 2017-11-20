package com.wczaja.apidemo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HUB_DEVICE")
public class HubEntity extends Device implements Serializable {
    private String owner;
    private Integer maxPorts;

    public HubEntity(String owner, Integer maxPorts) {
        super();
        this.owner = owner;
        this.maxPorts = maxPorts;
    }

    public HubEntity() {
        super();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getMaxPorts() {
        return maxPorts;
    }

    public void setMaxPorts(Integer maxPorts) {
        this.maxPorts = maxPorts;
    }
}
