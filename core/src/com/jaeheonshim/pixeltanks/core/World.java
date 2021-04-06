package com.jaeheonshim.pixeltanks.core;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Tank> tanks = new ArrayList<>();

    public World() {
        tanks.add(new Tank());
    }

    public List<Tank> getTanks() {
        return tanks;
    }
}
