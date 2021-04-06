package com.jaeheonshim.pixeltanks.client.listener;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.server.dto.ConnectionResponse;

import java.util.UUID;

public class ConnectionResponseListener extends Listener {
    private GameScreen gameScreen;

    public ConnectionResponseListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof ConnectionResponse) {
            ConnectionResponse connectionResponse = ((ConnectionResponse) object);
            Tank tank = new Tank(UUID.fromString(connectionResponse.getAssignedUUID()));
            gameScreen.getWorld().addTank(tank);
            gameScreen.setControllingTank(tank);
            Gdx.app.log("GameScreen", "Successfully established connection with server!");
        }
    }
}
