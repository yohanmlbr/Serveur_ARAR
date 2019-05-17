import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Com implements Runnable {
    DatagramPacket dp = null;
    String name;

    public Com(String name, DatagramPacket dp){
        this.dp=dp;
        this.name=name;
    }

    public int countCharInString(String s){
        int r=0;
        byte[] buf = s.getBytes();
        for(int i=0;i<buf.length;i++){
            if(buf[i]!=0)
                r++;
        }
        return r;
    }

    @Override
    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket();
            String nv = "Bienvenue nouveau client : "+dp.getSocketAddress();
            DatagramPacket dpback = new DatagramPacket(nv.getBytes("ascii"),nv.getBytes().length,dp.getAddress(),dp.getPort());
            ds.send(dpback);

            while (true){
                byte [] data = new byte[8192];
                dp = new DatagramPacket(data, data.length);
                ds.receive(dp);
                String tmp_msg = new String(dp.getData(), "ascii");
                if(tmp_msg.charAt(0)=='d' && tmp_msg.charAt(1)=='e' && countCharInString(tmp_msg)==2){
                    break;
                }
                String msg="SRV auto response for msg -> "+tmp_msg;
                DatagramPacket dpresponse=new DatagramPacket(msg.getBytes("ascii"),msg.getBytes().length,dp.getAddress(),dp.getPort());
                System.out.println("COM "+name+" -- auto msg sent back to : "+dp.getSocketAddress());
                ds.send(dpresponse);
            }
            System.out.println("COM "+name+" -- \"de\" ok --> disconnection");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
