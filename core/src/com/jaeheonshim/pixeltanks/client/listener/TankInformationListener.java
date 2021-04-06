package com.jaeheonshim.pixeltanks.client.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.server.dto.TankInformationPacket;

import java.util.UUID;

public class TankInformationListener extends Listener {
    private GameScreen gameScreen;

    public TankInformationListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof TankInformationPacket) {
            TankInformationPacket tankInformationPacket = ((TankInformationPacket) object);
            Tank tank = gameScreen.getWorld().getTank(UUID.fromString(tankInformationPacket.getUuid()));
            if(tank == null) {
                tank = new Tank(UUID.fromString(tankInformationPacket.getUuid()));
                gameScreen.getWorld().addTank(tank);
            }

            tank.setRemotePosition(tankInformationPacket.getPosition());
            tank.setRemoteRotation(tankInformationPacket.getRotation());
            //tank.setVelocity(tankInformationPacket.getVelocity());
        }
    }
}
