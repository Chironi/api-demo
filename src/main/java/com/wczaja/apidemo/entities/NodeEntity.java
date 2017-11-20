package com.wczaja.apidemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="NODE_DEVICE")
public class NodeEntity extends Device implements Serializable {
    private String type;
    @Column(name="node_group")
    private String group;

    @ManyToOne
    private HubEntity hub;

    public NodeEntity(String type, String group, HubEntity hub) {
        super();
        this.type = type;
        this.group = group;
        this.hub = hub;
    }

    public NodeEntity() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public HubEntity getHub() {
        return hub;
    }

    public void setHub(HubEntity hub) {
        this.hub = hub;
    }
}
