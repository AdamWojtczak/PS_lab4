import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    static int port;

    private static int getPort() throws InputMismatchException {
        System.out.println("Enter port number (enter \"end\" to end the connection): ");
        int port = scanner.nextInt();
        scanner.nextLine();
        return port;
    }

    public static void main(String[] args) {
        boolean global_flag_end = false;
        String toBeSent = "";
        String received = "";
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            port = getPort();
            while (!global_flag_end) {
                byte[] arr = new byte[1024];
                System.out.println("What do you want to say to your server: ");
                toBeSent = scanner.nextLine();
                arr = toBeSent.getBytes();

                DatagramPacket datagramPacket = new DatagramPacket(arr, arr.length, address, port);
                datagramSocket.send(datagramPacket);
                datagramPacket = new DatagramPacket(arr, arr.length);
                datagramSocket.receive(datagramPacket);
                received = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Client received: " + received);
                if(toBeSent.equals("end")) {
                    global_flag_end = true;
                }
            }
            datagramSocket.close();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}