package com.jaeheonshim.pixeltanks.server.dto;

import com.jaeheonshim.pixeltanks.core.TankDriveState;

public class TankDrivePacket {
    private TankDriveState driveState; // 1 for forward, -1 for backwards, 0 for stop;

    public TankDrivePacket() {
    }

    public TankDrivePacket(TankDriveState driveState) {
        this.driveState = driveState;
    }

    public TankDriveState getDriveState() {
        return driveState;
    }
}
