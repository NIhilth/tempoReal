import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Sensores extends Thread {

    static DataOutputStream ostream = null;
    static DataInputStream istream = null;
    static String host = ""; //localhost / 10.72...
    static Socket socket = null;
    int port = 9090;//porta para comunicacao. Deve ser a mesma do servidor.
    String mensagemRecebida = "";
    String mensagemEnviada = "";

    Sensores(int porta) {
        this.port = porta;
        try {
            socket = new Socket("localhost", port);//conecta com o servidor.
            System.out.println("Conectado....");
            this.start();
            //comeca uma nova thread. O metodo run é executado.
            ostream = new DataOutputStream(socket.getOutputStream());
            istream = new DataInputStream(socket.getInputStream());
            Scanner console = new Scanner(System.in);

            while (true) {
                System.out.println("Mensagem: ");
                mensagemEnviada = console.nextLine();//le mensagem do console.
                ostream.writeUTF(mensagemEnviada);//manda mensagem para o servidor.
                ostream.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            try {
                mensagemRecebida = istream.readUTF();//le mensagem do servidor.
                System.out.println("Remoto: " + mensagemRecebida);
            } catch (Exception e) {

            }
        }
    }


    public static void main(String[] args) {
        new Sensores(9090);
    }
}   