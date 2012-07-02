/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package viewer;

/**
 *
 * @author testi
 */

import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import yunikorn.core.packet.*;
import yunikorn.yukon.event.*;
public class StreamListener {
private MediaStreamObservable observable;
InputStream source;
ViewerMIDlet parent;

    public MediaStreamObservable getObservable() {
        return observable;
    }

private class ReadThread implements Runnable
{
private InputStream stream;

        public ReadThread(InputStream stream) {
            this.stream = stream;
        }
    
    
    public void run() {
        try {
        observable.readStream(stream);
        } catch (Throwable ex)
        {
        Form modifyForm = parent.getForm();
        //modifyForm.append(ex.toString()); //Modifications to the form
        parent.onError(ex, modifyForm);
        }
}
    }


    public StreamListener(ViewerMIDlet parent) {
    observable = new SilentYukonPacketObservable();
    source = null;
    this.parent = parent;
    }
    synchronized public void start() throws IOException
    {
        
        //ServerSocketConnection serverSocket = (ServerSocketConnection)Connector.open("socket://:42803");
        //SocketConnection conn = (SocketConnection)serverSocket.acceptAndOpen();
        HttpConnection conn = (HttpConnection)Connector.open("http://liandri.mine.nu:3023/stream/localhost");
        
        
        
        /*byte[] read = new byte[20];
        
        conn.openDataInputStream().read(read);
        StringBuffer bytesString = new StringBuffer();
        for (int i = 0; i < read.length;i++)
        {
        bytesString.append(read[i] + " ");
        }
        parent.getForm().append(bytesString.toString());*/
        
        
       //new ReadThread(conn.openInputStream()).run();
        new Thread(new ReadThread(conn.openInputStream())).start();

        

    }
    synchronized public void stop() throws IOException
    {
    if (source != null) {
    source.close(); //Uuuuuh, TODO, is InputStream threadsafe (by contract)?
    }
    }
    



}
