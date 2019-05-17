public class Main {

    public static void main (String args[]){
        try {
            new Thread(new Serveur(8000)).start();

            new Thread(new Client("1",false)).start();
            //Thread.sleep(5000);
            /*new Thread(new Client("2",true)).start();
            new Thread(new Client("3",true)).start();
            new Thread(new Client("4",true)).start();*/

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
