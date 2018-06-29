import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server 
{
	public static void main(String[] args) throws IOException 
	{
		while(true)
		{
	        DatagramSocket socket = new DatagramSocket(10000);
	        byte[] data = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(data, data.length);
	        System.out.println("Server Started!");
	        socket.receive(packet);
	        String info = new String(data, 0, packet.getLength());
	        System.out.println("Server" + info);
	
	        InetAddress address = packet.getAddress();
	        int port = packet.getPort();
	        byte[] data2 = "{\"type\": \"login\", \"data\": {}, \"return\": 0, \"public_key\": \"abc\"}".getBytes();
	        DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
	        socket.send(packet2);
	        socket.close();
		}
    }
}
