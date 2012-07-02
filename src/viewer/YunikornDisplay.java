/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package viewer;
import javax.microedition.lcdui.*;
//import javax.microedition.midlet.*;
import yunikorn.core.packet.*;

/**
 *
 * @author testi
 */
public class YunikornDisplay extends Canvas implements MediaStreamListener {
VideoFrame currentImage;
ViewerMIDlet parent;
long frameCount;
    public void onAudioSequence(AudioSequence arg0) {
    //Nothing
    }

    public void onVideoFrame(VideoFrame arg0) {
        //System.out.println("receiving");
        currentImage = arg0;

        repaint();
        
        //instead of repaint
        //parent.getForm().deleteAll();
        //parent.getForm().append("Framenumber: " + frameCount++);
    }

    public void onEndOfStream() {
    }

    public YunikornDisplay(ViewerMIDlet parent) {
        currentImage = null;
        //this.setFullScreenMode(true);
        this.parent = parent;
        frameCount = 0;
    }

    protected void keyPressed(int arg0) {
        parent.commandAction(parent.getExitCommand(), parent.getForm());
    }

    protected void paint(Graphics arg0) {
        try {
        if (currentImage != null) {
        
        int[] buffer =currentImage.getARGBBuffer();
            arg0.drawRGB(buffer, 0,currentImage.getWidth() , 0,0, currentImage.getWidth(), currentImage.getHeight(), false);
        
        }
        } catch (Exception ex)
        {
        parent.onError(ex, parent.getForm());
        parent.commandAction(parent.getExitCommand(), parent.getForm());
        }
    }

}
