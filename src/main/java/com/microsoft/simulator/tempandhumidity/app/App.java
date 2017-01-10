package com.microsoft.simulator.tempandhumidity.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.azure.iothub.DeviceClient;
import com.microsoft.azure.iothub.IotHubClientProtocol;

/**
 * Simulator App
 * @author ranga
 *
 */
public class App {
	private static String connString = "HostName={youriothubname}.azure-devices.net;DeviceId=myFirstJavaDevice;SharedAccessKey={yourdevicekey}";
	private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
	private static String deviceId = "myDevice";

	public static void main( String[] args )
    {
        System.out.println( "Temparature Simulator" );
   	 	DeviceClient client;
		try {
			client = new DeviceClient(connString, protocol);
	        client.open();
	        MessageSender sender = new MessageSender(deviceId, client);

	        ExecutorService executor = Executors.newFixedThreadPool(1);
	        executor.execute(sender);

	        System.out.println("Press ENTER to exit.");
	        System.in.read();
	        executor.shutdownNow();
	        client.close();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
