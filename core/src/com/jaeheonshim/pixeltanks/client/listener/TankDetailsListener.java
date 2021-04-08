package com.jaeheonshim.pixeltanks.client.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.TankDetails;
import com.jaeheonshim.pixeltanks.server.dto.TankDetailsPacket;

import java.util.UUID;

public class TankDetailsListener extends Listener {
    private GameScreen gameScreen;

    public TankDetailsListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof TankDetailsPacket) {
            TankDetailsPacket detailsPacket = ((TankDetailsPacket) object);
            TankDetails previousDetails = gameScreen.getWorld().getTank(UUID.fromString(detailsPacket.getUuid())).getTankDetails();
            gameScreen.getWorld().getTank(UUID.fromString(detailsPacket.getUuid())).setTankDetails(detailsPacket.getTankDetails());

            if(detailsPacket.getTankDetails().getHp() < previousDetails.getHp()) {
                gameScreen.getWorld().getTank(UUID.fromString(detailsPacket.getUuid())).getTankDetails().setTakingDamage(true);
            }
        }
    }
}
