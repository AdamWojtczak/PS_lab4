import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    static int port;

    private static int getPort() throws InputMismatchException {
        System.out.println("Podaj nr portu do połączenia: (aby zakoncyzc polaczenie wyslij komunikat \"end\")");
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
                System.out.println("podaj co przesłac do servera: ");
                toBeSent = scanner.nextLine();
                arr = toBeSent.getBytes();

                DatagramPacket packet = new DatagramPacket(arr, arr.length, address, port);
                datagramSocket.send(packet);
                packet = new DatagramPacket(arr, arr.length);
                datagramSocket.receive(packet);
                received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Klient otrzymał: " + received);
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