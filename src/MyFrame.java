/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends Frame {
    public MyFrame(String title) {
        setTitle(title);
    }

    public void addClosingEvent() {
        WindowAdapter Wa =new WindowAdapter(){
            @Override
            public void windowClosing (WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(Wa);
    }
}
