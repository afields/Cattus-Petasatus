package com.team.catswithhats;

import com.badlogic.gdx.Gdx;

public class DesktopGPS implements GPS {
	/** Desktop implementation, we simply log invocations **/
	public void getLocation() {
		Gdx.app.log("DesktopGPS", "gps not supported for this platform");
	}

	@Override
	public double getLatitude() {
		// System.out.println("28.602307");
		return 28.606226;
	}

	@Override
	public double getLongitude() {
		// System.out.println("-81.200209");
		return -81.204715;
	}
}
