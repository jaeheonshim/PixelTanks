package com.jaeheonshim.pixeltanks.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.core.Bullet;
import com.jaeheonshim.pixeltanks.server.TankServer;
import com.jaeheonshim.pixeltanks.server.dto.BulletSpawnPacket;

import java.util.UUID;

public class BulletListener extends Listener {
    private TankServer tankServer;

    public BulletListener(TankServer tankServer) {
        this.tankServer = tankServer;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof BulletSpawnPacket) {
            BulletSpawnPacket spawnPacket = ((BulletSpawnPacket) object);
            Bullet bullet = new Bullet(UUID.fromString(spawnPacket.getUuid()), spawnPacket.getPosition(), spawnPacket.getRotation());
            bullet.setVelocity(spawnPacket.getVelocity());
            tankServer.getWorld().getBullets().add(bullet);

            tankServer.getServer().sendToAllExceptUDP(connection.getID(), spawnPacket);
        }
    }
}
