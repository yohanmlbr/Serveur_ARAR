import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur implements Runnable{

    private DatagramSocket ds;
    private DatagramPacket dp;

    public Serveur(int port){
        try{
            ds = new DatagramSocket(port);
        }catch (SocketException se){
            System.out.println(se.getMessage());
        }
    }

    public void run (){
        while(true){
            try{
                    byte[] data = new byte[8192];
                    dp = new DatagramPacket(data, data.length);
                    System.out.println("SRV -- waiting...");
                    ds.receive(dp);

                    System.out.println("SRV -- received from : "+dp.getSocketAddress());
                    String debCom = new String(dp.getData(), "ascii");
                    System.out.println("SRV -- message : "+debCom);

                    if(debCom.charAt(0)=='c' && debCom.charAt(1)=='o'){
                        String idCom=""+debCom.charAt(2);
                        Com nvCom=new Com(idCom,dp);
                        new Thread(nvCom).start();
                        System.out.println("SRV -- \"co\" ok --> COM "+idCom+" created");
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}