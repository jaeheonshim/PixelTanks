package com.jaeheonshim.pixeltanks.client.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.Bullet;
import com.jaeheonshim.pixeltanks.server.dto.BulletPositionPacket;
import com.jaeheonshim.pixeltanks.server.dto.BulletSpawnPacket;

import java.util.UUID;

public class BulletListener extends Listener {
    private GameScreen gameScreen;

    public BulletListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof BulletSpawnPacket) {
            BulletSpawnPacket spawnPacket = ((BulletSpawnPacket) object);
            if(!spawnPacket.isDespawn()) {
                Bullet bullet = new Bullet(UUID.fromString(spawnPacket.getUuid()), UUID.fromString(spawnPacket.getFiredBy()), spawnPacket.getPosition(), spawnPacket.getRotation());
                bullet.setVelocity(spawnPacket.getVelocity());
                gameScreen.getWorld().addBullet(bullet);
            } else {
                if(gameScreen.getWorld().getBullet(UUID.fromString(spawnPacket.getUuid())) != null)
                    gameScreen.getWorld().getBullet(UUID.fromString(spawnPacket.getUuid())).setDying(true);
//                gameScreen.getWorld().removeBullet(UUID.fromString(spawnPacket.getUuid()));
            }
        } else if(object instanceof BulletPositionPacket) {
            BulletPositionPacket bulletPositionPacket = ((BulletPositionPacket) object);

            UUID uuid = UUID.fromString(bulletPositionPacket.getUuid());
            Bullet bullet = gameScreen.getWorld().getBullet(uuid);

            if(bullet != null) {
                bullet.setPosition(bulletPositionPacket.getPosition());
            }
        }
    }
}
