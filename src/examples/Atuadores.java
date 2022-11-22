package examples;

import java.net.*;
import java.io.*;
import java.util.Scanner;

class Atuadores extends Thread {
    static ServerSocket serverSocket = null;
    static Socket socket = null;
    static DataOutputStream ostream = null;
    static DataInputStream istream = null;
    int port;//porta para comunicacao.
    String mensagemRecebida = "";
    String mensagemEnviada = "";

    Atuadores(int porta) {
        this.port = porta;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Aguardando conexão...");
            socket = serverSocket.accept();
            //aguarda conexao com o cliente.

            System.out.println("Conexão Estabelecida.");
            ostream = new DataOutputStream(socket.getOutputStream());
            istream = new DataInputStream(socket.getInputStream());

            this.start();//inicia uma nova thread. O metodo run é executado.
            Scanner console = new Scanner(System.in);
            while (true) {
                System.out.println("Mensagem: ");
                mensagemEnviada = console.nextLine(); //le string do console
                ostream.writeUTF(mensagemEnviada);//envia string para o cliente.
                ostream.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            while (true) {
                mensagemRecebida = istream.readUTF();//le as strings do cliente
                System.out.println("Remoto: " + mensagemRecebida);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new Atuadores(9090);
    }
}