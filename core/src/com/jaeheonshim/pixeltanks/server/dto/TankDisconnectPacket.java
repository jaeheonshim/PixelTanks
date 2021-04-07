package com.jaeheonshim.pixeltanks.server.dto;

import java.util.UUID;

public class TankDisconnectPacket {
    private String disconnectUUID;

    public TankDisconnectPacket() {
    }

    public TankDisconnectPacket(String disconnectUUID) {
        this.disconnectUUID = disconnectUUID;
    }

    public String getDisconnectUUID() {
        return disconnectUUID;
    }

    public void setDisconnectUUID(String disconnectUUID) {
        this.disconnectUUID = disconnectUUID;
    }
}
