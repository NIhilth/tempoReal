import java.io.*;
import java.net.*;

class UDPClientThread extends Thread{

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader( System.in));

    //cria um objeto IP/UPD
    DatagramSocket clientSocket;
    String servidor = "localhost";
    int porta = 9090;

    //transforma um nome em IP
    InetAddress IPAddress;

    //cria os buffers de envio e recebimento
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    public UDPClientThread(){
        try {
            clientSocket = new DatagramSocket();

            IPAddress = InetAddress.getByName(servidor);

            while (true) {
                System.out.println("Digite o texto a ser enviado ao servidor: ");
                String sentence = inFromUser.readLine();
                sendData = sentence.getBytes();

                //cria o pacote UDP a ser enviado
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);

                System.out.println("Enviando pacote UDP para " + servidor + ":" + porta);

                //envia o pacote UDP
                clientSocket.send(sendPacket);

                //cria um datagrama para guardar a resposta
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);;

                //aguarda o recebimento da resposta
                clientSocket.receive(receivePacket);
                System.out.println("Pacote UDP recebido...");
                String modifiedSentence = new String(receivePacket.getData());
                System.out.println("Texto recebido do servidor:" + modifiedSentence);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        new UDPClientThread();


//        clientSocket.close();
//        System.out.println("Socket cliente fechado!");
    }
}