package com.wczaja.apidemo.resources;

public class HubResource extends BaseDeviceResource {

    private String owner;
    private Integer maxPorts;

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
