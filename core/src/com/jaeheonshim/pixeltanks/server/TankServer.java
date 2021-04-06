package com.jaeheonshim.pixeltanks.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.jaeheonshim.pixeltanks.core.Tank;
import com.jaeheonshim.pixeltanks.core.TankDriveState;
import com.jaeheonshim.pixeltanks.core.TankRotationState;
import com.jaeheonshim.pixeltanks.core.World;
import com.jaeheonshim.pixeltanks.server.dto.ConnectionResponse;
import com.jaeheonshim.pixeltanks.server.dto.TankDrivePacket;
import com.jaeheonshim.pixeltanks.server.dto.TankInformationPacket;
import com.jaeheonshim.pixeltanks.server.dto.TankRotationPacket;
import com.jaeheonshim.pixeltanks.server.listeners.TankMovementListener;
import com.jaeheonshim.pixeltanks.server.listeners.ConnectionListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TankServer {
    private Map<Integer, UUID> idToUuid = new HashMap<>();

    private Server server;
    private World world;

    public static final int TPS = 20;
    private long lastTickTime = -1;

    private ScheduledExecutorService serverTicker;

    public TankServer() throws IOException {
        server = new Server();
        server.start();
        server.bind(4200, 4201);

        initServer();

        world = new World();

        serverTicker = Executors.newScheduledThreadPool(4);
        serverTicker.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(lastTickTime == -1) {
                    lastTickTime = System.nanoTime();
                }
                tick(1f / TPS);
                lastTickTime = System.nanoTime();
            }
        }, 0, (long) ((1f / TPS) * 1000), TimeUnit.MILLISECONDS);

    }

    public void tick(float delta) {
        world.update(delta);
        for(Tank tank : world.getTanks()) {
            server.sendToAllUDP(new TankInformationPacket(tank.getUuid().toString(), tank.getPosition(), tank.getRotation(), tank.getVelocity()));
        }
    }

    private void initServer() {
        Kryo kryo = server.getKryo();
        registerClasses(kryo);

        server.addListener(new ConnectionListener(this));
        server.addListener(new TankMovementListener(this));
    }

    public static void registerClasses(Kryo kryo) {
        kryo.register(TankInformationPacket.class);
        kryo.register(Vector2.class);

        kryo.register(ConnectionResponse.class);
        kryo.register(UUID.class);

        kryo.register(TankDriveState.class);
        kryo.register(TankDrivePacket.class);

        kryo.register(TankRotationPacket.class);
        kryo.register(TankRotationState.class);
    }

    public World getWorld() {
        return world;
    }

    public Map<Integer, UUID> getIdToUuid() {
        return idToUuid;
    }
}
