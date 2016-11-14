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
                System.out.println(msg);
            } catch (IOException ex) {
                Logger.getLogger(ThreadLe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(!msg.equals("fim"));
    }
    
}
