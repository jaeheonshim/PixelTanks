package com.jaeheonshim.pixeltanks.client.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.pixeltanks.client.GameScreen;
import com.jaeheonshim.pixeltanks.core.TankDetails;

public class TankDetailsListener extends Listener {
    private GameScreen gameScreen;

    public TankDetailsListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof TankDetails) {
            TankDetails tankDetails = ((TankDetails) object);
            gameScreen.getControllingTank().setTankDetails(tankDetails);
        }
    }
}
