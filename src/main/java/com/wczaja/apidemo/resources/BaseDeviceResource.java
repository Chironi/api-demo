package com.wczaja.apidemo.resources;

import org.springframework.hateoas.ResourceSupport;

public class BaseDeviceResource extends ResourceSupport {
    private String name;
    private String label;
    private String location;
    private String zigbeeId;
    private String networkId;
    private String productId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZigbeeId() {
        return zigbeeId;
    }

    public void setZigbeeId(String zigbeeId) {
        this.zigbeeId = zigbeeId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
