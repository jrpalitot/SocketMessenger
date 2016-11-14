/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketteste;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author jrpalitot
 */
public class SocketTeste extends Thread{
    
    public static void main(String[] args) throws IOException  {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Scanner enviar_mensagem = new Scanner (System.in);
        String nome;
        String opcao;
        String mensagem;
        
        System.out.println("Qual seu nome? ");
        nome = enviar_mensagem.nextLine();
        System.out.println("--------------------------------");
        System.out.println("Você deseja: \n 1 - Entrar numa sala existente \n 2 - Criar uma sala ");
        opcao = enviar_mensagem.nextLine();
        System.out.println("--------------------------------");
        
        
        if (opcao.equals("1")){
            System.out.println("Qual IP da sala que deseja conectar?");
            opcao = enviar_mensagem.nextLine();
            Socket socket = new Socket(opcao, 6500);
        }else if (opcao.equals("2")){
            System.out.println("Criando sala... \nSala criada! \n\n-----------------------");
            ServerSocket serverSocket = new ServerSocket(6500);
            Socket socket = serverSocket.accept();
        }else{
            System.out.println("Opção inválida..");
            System.exit(0);
        }
        ThreadLe tLe = new ThreadLe(socket.getInputStream());
        tLe.start();
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        
        do{
            mensagem = enviar_mensagem.nextLine();
            sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            if (mensagem.startsWith("@>")){
                dataOutputStream.writeUTF(">> ["+nome+"] às "+sdf+" : "+ mensagem);
            }else{
                dataOutputStream.writeUTF("["+nome+"] às "+sdf+" : "+ mensagem);
            }
        } while(true);
    }
    
    
}
