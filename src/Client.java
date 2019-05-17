import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client implements Runnable {

    private DatagramSocket ds;
    private DatagramPacket dp;
    private boolean bot;
    private String name;

    public Client(String name, boolean bot){
        try{
            ds = new DatagramSocket();
            this.name=name;
            this.bot=bot;
        }catch (SocketException se){
            System.out.println(se.getMessage());
        }
    }

    public void sendCoToServeur(){
        try{
            String co="co"+name;
            DatagramPacket dpco = new DatagramPacket(co.getBytes("ascii"), co.getBytes().length, InetAddress.getByName("localhost"), 8000);
            ds.send(dpco);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try{
            ds=new DatagramSocket();
            byte[] data = new byte[8192];
            dp = new DatagramPacket(data, data.length);
            sendCoToServeur();
            while (true) {
                ds.receive(dp);
                String msg = new String(dp.getData(), "ascii");
                System.out.println("CLIENT "+name+" -- received from COM "+name+" : " + dp.getSocketAddress());
                System.out.println("CLIENT "+name+" -- message : "+msg);
                if(bot==false){
                    System.out.println("Le CLIENT "+name+" peut répondre,");
                    msg=Util.ecrireMessage();
                }
                else{
                    msg="Je suis le bot "+name+" donc je reponds, time : "+System.currentTimeMillis();
                }
                DatagramPacket dpresponse=new DatagramPacket(msg.getBytes("ascii"),msg.getBytes().length,dp.getAddress(),dp.getPort());
                ds.send(dpresponse);
                System.out.println("CLIENT "+name+" -- message sent to : "+dp.getSocketAddress());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
