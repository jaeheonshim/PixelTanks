package com.jaeheonshim.pixeltanks.server.dto;

import com.jaeheonshim.pixeltanks.core.TankRotationState;

public class TankRotationPacket {
    private TankRotationState rotationState;

    public TankRotationPacket() {
    }

    public TankRotationPacket(TankRotationState rotationState) {
        this.rotationState = rotationState;
    }

    public TankRotationState getRotationState() {
        return rotationState;
    }

    public void setRotationState(TankRotationState rotationState) {
        this.rotationState = rotationState;
    }
}
