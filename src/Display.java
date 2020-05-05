/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class Display implements ChangeListener{
    private MyFrame myFrame;
    private Canvas myCanvas;
    private View view;
    private String title;
    private int width, height;

    public Display(String title) {
        this.title = title;
        this.view = new View();
        this.width = view.getViewPort()[0];
        this.height = view.getViewPort()[1];
        createDisplay();
    }

    private void createDisplay() {
        myFrame = new MyFrame(title);
        myFrame.setVisible(true);
        myFrame.addClosingEvent();
        myCanvas = new MyCanvas(width,height, view);
        ((MyCanvas) myCanvas).setChangeListener(this);
        myFrame.add(myCanvas);
        myFrame.setSize(width,height);
        //myFrame.pack();
        myFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension dim = e.getComponent().getSize();
                ((MyCanvas) myCanvas).changeSize(dim);
            }
        });
    }

    public void onChangeHappened(View view) {
        this.view = view;
        this.width = view.getViewPort()[0];
        this.height = view.getViewPort()[1];
        createDisplay();
    }


}
