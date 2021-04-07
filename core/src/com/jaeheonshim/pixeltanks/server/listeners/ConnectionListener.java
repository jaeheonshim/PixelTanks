package com.jaeheonshim.pixeltanks.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.ConnectionResponse;
import com.jaeheonshim.pixeltanks.server.dto.TankDisconnectPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConnectionListener extends Listener {
    private TankServer tankServer;

    public ConnectionListener(TankServer tankServer) {
        this.tankServer = tankServer;
    }

    @Override
    public void connected(Connection connection) {
        UUID uuid = UUID.randomUUID();
        tankServer.getIdToUuid().put(connection.getID(), uuid);
        Log.info("Player connected, assigning UUID: " + uuid);

        ConnectionResponse response = new ConnectionResponse(uuid.toString());

        Tank newTank = new Tank(uuid);
        tankServer.getWorld().addTank(newTank);

        connection.sendTCP(response);
    }

    @Override
    public void disconnected(Connection connection) {
        UUID uuid = tankServer.getIdToUuid().get(connection.getID());
        if(uuid != null) {
            Log.info("Player disconnected, removing tank with UUID " + uuid);
            tankServer.getWorld().removeTank(uuid);
            tankServer.getIdToUuid().remove(connection.getID());
            tankServer.getServer().sendToAllTCP(new TankDisconnectPacket(uuid.toString()));
        }
    }
}
