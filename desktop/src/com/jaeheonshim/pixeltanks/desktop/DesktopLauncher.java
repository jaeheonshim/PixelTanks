package com.jaeheonshim.pixeltanks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaeheonshim.pixeltanks.PixelTanks;
import com.jaeheonshim.pixeltanks.server.TankServer;

import java.io.IOException;

public class DesktopLauncher {
	private static TankServer tankServer;

	public static void main (String[] arg) throws IOException {
		if(arg.length > 0 && arg[0].equalsIgnoreCase("-server")) {
			tankServer = new TankServer();
		} else {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			new LwjglApplication(new PixelTanks(), config);
		}
	}
}
