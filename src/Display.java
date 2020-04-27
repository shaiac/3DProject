import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class Display {
    private MyFrame myFrame;
    private Canvas myCanvas;
    private View view;
    private String title;
    private int width, height;

    public Display(String title) throws Exception {
        this.title = title;
        this.view = new View();
        this.width = view.getResolution()[0];
        this.height = view.getResolution()[1];
        createDisplay();
    }

    private void createDisplay() throws Exception {
        myFrame = new MyFrame(title);
        myFrame.setVisible(true);
        myFrame.addClosingEvent();
        myCanvas = new MyCanvas(width,height, view);
        myFrame.add(myCanvas);
        myFrame.setSize(width,height);
        myFrame.pack();
        myFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension dim = e.getComponent().getSize();
                myCanvas.setSize(dim);
            }
        });
    }




}
