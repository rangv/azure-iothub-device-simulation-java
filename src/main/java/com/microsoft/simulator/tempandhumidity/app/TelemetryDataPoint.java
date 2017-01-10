package com.microsoft.simulator.tempandhumidity.app;

import com.google.gson.Gson;

/**
 * Telemetry Data Point
 * @author ranga
 *
 */
public class TelemetryDataPoint {
	   public String deviceId;
	   public double temparature;
	   public String hostIP;

	   public String serialize() {
	     Gson gson = new Gson();
	     return gson.toJson(this);
	   }

}
