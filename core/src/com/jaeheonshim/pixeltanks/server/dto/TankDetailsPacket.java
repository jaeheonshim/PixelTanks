package com.jaeheonshim.pixeltanks.server.dto;

import com.jaeheonshim.pixeltanks.core.TankDetails;

public class TankDetailsPacket {
    private String uuid;
    private TankDetails tankDetails;

    public TankDetailsPacket() {
    }

    public TankDetailsPacket(String uuid, TankDetails tankDetails) {
        this.uuid = uuid;
        this.tankDetails = tankDetails;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TankDetails getTankDetails() {
        return tankDetails;
    }

    public void setTankDetails(TankDetails tankDetails) {
        this.tankDetails = tankDetails;
    }
}
