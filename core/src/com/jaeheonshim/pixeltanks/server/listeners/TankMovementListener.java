package com.jaeheonshim.pixeltanks.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.TankDriveState;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.TankDrivePacket;

import java.util.UUID;

public class TankMovementListener extends Listener {
    private TankServer tankServer;

    public TankMovementListener(TankServer tankServer) {
        this.tankServer = tankServer;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof TankDrivePacket) {
            TankDrivePacket drivePacket = ((TankDrivePacket) object);
            System.out.println(drivePacket.getDriveState());
            UUID uuid = tankServer.getIdToUuid().get(connection.getID());

            Tank tank = tankServer.getWorld().getTank(uuid);
            tank.setDriveState(drivePacket.getDriveState());

            if(drivePacket.getDriveState() == TankDriveState.FORWARD) {
                tank.setVelocity(Tank.MOVEMENT_SPEED);
            } else if(drivePacket.getDriveState() == TankDriveState.REVERSE) {
                tank.setVelocity(Tank.MOVEMENT_SPEED * -1);
            } else if(drivePacket.getDriveState() == TankDriveState.STOP) {
                tank.setVelocity(0);
            }
        }
    }
}
