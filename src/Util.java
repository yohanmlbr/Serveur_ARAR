import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public static ArrayList<Integer> scan(int min, int max){
        ArrayList<Integer> res = new ArrayList<>();
        for (int port = min; port <= max; port++) {
            try {
                DatagramSocket server = new DatagramSocket(port);
                server.close();
                res.add(port);
            } catch (SocketException ex) {
                System.out.println("There is a server on port " + port);
            }
        }
        return res;
    }

    public static String ecrireMessage (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un message :");
        String str = sc.nextLine();
        System.out.println();
        //sc.close();
        return str;
    }
}