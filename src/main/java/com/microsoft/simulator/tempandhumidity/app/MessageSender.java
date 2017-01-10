package com.microsoft.simulator.tempandhumidity.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.Message;

/**
 * Message Sender
 * 
 * @author ranga
 *
 */
public class MessageSender implements Runnable {
	private String deviceId = "myFirstDevice";
	public volatile boolean stopThread = false;
	private DeviceClient client;

	/**
	 * constructor
	 * @param deviceId
	 * @param client
	 * 
	 */
	public MessageSender(String deviceId, DeviceClient client) {
		this.deviceId = deviceId;
		this.client = client;
	}

	public void run() {
		try {
			double avgTemparature = 70; // m/s
			Random rand = new Random();

			while (!stopThread) {
				double currentTemparature = avgTemparature + rand.nextDouble() * 10;
				TelemetryDataPoint telemetryDataPoint = new TelemetryDataPoint();
				telemetryDataPoint.deviceId = deviceId;
				telemetryDataPoint.temparature = currentTemparature;
				try {
					telemetryDataPoint.hostIP = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String msgStr = telemetryDataPoint.serialize();
				Message msg = new Message(msgStr);
				System.out.println("Sending: " + msgStr);

				Object lockobj = new Object();
				EventCallback callback = new EventCallback();
				client.sendEventAsync(msg, callback, lockobj);

				synchronized (lockobj) {
					lockobj.wait();
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.out.println("Finished.");
		}
	}
}