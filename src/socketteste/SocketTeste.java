/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketteste;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author jrpalitot
 */
public class SocketTeste extends Thread{
    
    public static void main(String[] args) throws IOException  {
        SimpleDateFormat sdf;
        Scanner enviar_mensagem = new Scanner (System.in);
        String opcao;
        String mensagem;
        DataOutputStream dataOutputStream;
        Calendar data_atual;
        
        
        System.out.println("Qual seu nome? ");
        globals.nome = enviar_mensagem.nextLine();
        globals.lista.add(globals.nome);
        System.out.println("--------------------------------");
        System.out.println("Você deseja: \n 1 - Entrar numa sala existente \n 2 - Criar uma sala ");
        opcao = enviar_mensagem.nextLine();
        System.out.println("--------------------------------");
        
        
        switch (opcao) {
            case "1":
                {
                    System.out.println("Qual IP da sala que deseja conectar?");
                    mensagem = enviar_mensagem.nextLine();
                    Socket socket = new Socket(InetAddress.getByName(mensagem), 6500);
                    ThreadLe tLe = new ThreadLe(socket.getInputStream());
                    tLe.start();
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(globals.nome + " entrou na conversa");
                    
                   do{
                        data_atual = Calendar.getInstance();
                        mensagem = enviar_mensagem.nextLine();
                        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        if (mensagem.startsWith("send -user ")){
                            dataOutputStream.writeUTF("//~"+globals.nome+": " + mensagem + " - "+sdf.format(data_atual.getTime()));
                        }else if (mensagem.startsWith("bye")){
                            dataOutputStream.writeUTF(globals.nome+" saiu da conversa.");
                            socket.close();
                            System.exit(0);
                        }else if (mensagem.startsWith("list")){
                            System.out.println(globals.lista.toString());
                        }else if (mensagem.startsWith("rename")){
                            dataOutputStream.writeUTF(globals.nome+" alterado para " + mensagem.split(" ")[1]);
                            globals.lista.remove(globals.nome);
                            globals.nome = mensagem.split(" ")[1];
                            globals.lista.add(globals.nome);
                        }else{
                            dataOutputStream.writeUTF("/~"+globals.nome+": " + mensagem + " - "+sdf.format(data_atual.getTime()));
                        }
                    } while(true);
                    
//                    break;
                }
            case "2":
                {
                    System.out.println("Sala criada! \n----------------------- \n");
                    ServerSocket serverSocket = new ServerSocket(6500);
                    Socket socket = serverSocket.accept();
                    ThreadLe tLe = new ThreadLe(socket.getInputStream());
                    tLe.start();
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(globals.nome + " entrou na conversa");
                    
                    do{
                        data_atual = Calendar.getInstance();
                        mensagem = enviar_mensagem.nextLine();
                        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                        if (mensagem.startsWith("send -user ")){
                            dataOutputStream.writeUTF("//~"+globals.nome+": " + mensagem + " - "+sdf.format(data_atual.getTime()));
                        }else if (mensagem.startsWith("bye")){
                            dataOutputStream.writeUTF(globals.nome+" saiu da conversa.");
                            socket.close();
                            System.exit(0);
                        }else if (mensagem.startsWith("list")){
                            System.out.println(globals.lista.toString());
                        }else{
                            dataOutputStream.writeUTF("/~"+globals.nome+": " + mensagem + " - "+sdf.format(data_atual.getTime()));
                        }
                    } while(true);
                    
//                    break;
                }
            default:
                System.out.println("Opção inválida..");
                System.exit(0);
        }
        
    }
    
    
}
