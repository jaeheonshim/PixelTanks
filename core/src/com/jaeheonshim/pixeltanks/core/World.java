package com.jaeheonshim.pixeltanks.core;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Tank> tanks = new ArrayList<>();

    public List<Tank> getTanks() {
        return tanks;
    }

    public void update(float delta) {
        for(Tank tank : tanks) {
            tank.update(delta);
        }
    }
}
