package com.jaeheonshim.pixeltanks.client.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.server.dto.TankConnectPacket;
import com.jaeheonshim.pixeltanks.server.dto.TankDisconnectPacket;

import java.util.UUID;

public class TankConnectionListener extends Listener {
    private GameScreen gameScreen;

    public TankConnectionListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof TankConnectPacket) {
            TankConnectPacket connectPacket = ((TankConnectPacket) object);
            gameScreen.getWorld().addTank(new Tank(UUID.fromString(connectPacket.getUuid()), connectPacket.getName()));
        } else if(object instanceof TankDisconnectPacket) {
            TankDisconnectPacket disconnectPacket = ((TankDisconnectPacket) object);
            gameScreen.getWorld().removeTank(UUID.fromString(disconnectPacket.getDisconnectUUID()));
        }
    }
}
