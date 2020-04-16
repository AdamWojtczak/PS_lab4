import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server {
    static Scanner scanner = new Scanner(System.in);
    static int port;

    private static int getPort() throws InputMismatchException {
        System.out.println("Insert port number: ");
        int port = scanner.nextInt();
        scanner.nextLine();
        return port;
    }

    public static void main(String[] args) {
        boolean global_flag_end = false;
        port = getPort();
        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);
            while(!global_flag_end) {
                byte[] arr = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(arr, arr.length);
                datagramSocket.receive(datagramPacket);
                InetAddress address = datagramPacket.getAddress();
                int port2 = datagramPacket.getPort();
                datagramPacket = new DatagramPacket(arr, arr.length, address, port2);
                String received = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Server received: " + received);
                datagramSocket.send(datagramPacket);
            }
            datagramSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}