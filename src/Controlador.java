import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class Controlador extends JFrame implements Runnable {

    private JLabel valorTa, valorT, valorTi, valorNo, valorH;
    private JPanel caldeiraPanel;
    DatagramSocket clientSocket;
    String servidor = "localhost";
    int porta = 9090;
    InetAddress IPAddress;
    byte[] sendData = new byte[1024], receiveData = new byte[1024];

    public Controlador() {
        criarComponentes();
    }

    private void atualizarValor(String comando, DatagramSocket clientSocket, InetAddress IPAddress, JLabel labelValor) {
        try {
            Long horarioAtual = new Date().getTime();
            sendData = comando.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);
            labelValor.setText(new String(receivePacket.getData()));

            Long horarioMudanca = new Date().getTime();
            gravarArquivo(horarioMudanca, horarioMudanca - horarioAtual );

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void criarComponentes() {
        setContentPane(caldeiraPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void run() {
        if (!isVisible()) {
            setVisible(true);
            try {
                clientSocket = new DatagramSocket();

                IPAddress = InetAddress.getByName(servidor);

                while (true) {
                    atualizarValor("sta0", clientSocket, IPAddress, valorTa);
                    atualizarValor("st-0", clientSocket, IPAddress, valorT);
                    atualizarValor("sti0", clientSocket, IPAddress, valorTi);
                    atualizarValor("sno0", clientSocket, IPAddress, valorNo);
                    atualizarValor("sh-0", clientSocket, IPAddress, valorH);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "A janela já está aberta");
        }
    }

    public void gravarArquivo(Long horaAtual, Long tempoDeResposta){
        File arquivo = new File("C:\\Users\\joao_hm_silva\\Documents\\Arquivo\\gravacao.txt");
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            Date dataAtual = new Date(horaAtual);

            bw.write("Tempo de resposta as "+ dataAtual +" :"+tempoDeResposta);
            bw.newLine();

            bw.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
