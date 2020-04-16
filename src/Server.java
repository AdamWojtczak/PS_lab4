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
        System.out.println("Podaj nr portu do połączenia: ");
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
                DatagramPacket packet = new DatagramPacket(arr, arr.length);
                datagramSocket.receive(packet);
                InetAddress address = packet.getAddress();
                int port2 = packet.getPort();
                packet = new DatagramPacket(arr, arr.length, address, port2);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Server otrzymał: " + received);
                datagramSocket.send(packet);
            }
            datagramSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}