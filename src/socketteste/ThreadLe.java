/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketteste;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jrpalitot
 */
public class ThreadLe extends Thread {
    private InputStream  in;
    
    public ThreadLe(InputStream in){
        this.in = in;
    }
    @Override
    public void run(){
        DataInputStream dIn = new DataInputStream(in);
        String msg = null;
        do{
            try {
                msg = dIn.readUTF();
                if (msg.contains("entrou na conversa") && !msg.contains("/~")){
                    globals.lista.add(msg.split(" ")[0]);
                    System.out.println(msg);
                }else if (msg.contains("alterado para")){
                    globals.lista.remove(msg.split(" ")[0]);
                    globals.lista.add(msg.split(" ")[3]);
                    System.out.println(msg);
                }else if (!msg.contains("send -user")){
                    System.out.println(msg);
                }else {
                    if(msg.contains("send -user "+globals.nome)){
                        System.out.println("PRIVADO" + msg);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadLe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(!msg.contains("saiu da conversa"));
    }
    
}


