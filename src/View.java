import LinearMath.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class View {
    private Vector position;
    private Vector lookAt;
    private Vector up;
    private double[] window;
    private int[] viewPort;

    public View() throws Exception {
        this.viewPort = new int[2];
        this.window = new double[4];
        this.getValuesFromFile("Resources\\ex0.vim");
    }

    private Vector createPoint(String[] str) {
        int strSize = str.length;
        double[] arrPoint = new double[strSize - 1];
        for (int j = 0; j < strSize - 1; j++) {
            arrPoint[j] = Double.parseDouble(str[j + 1]);
        }
        return new Vector(arrPoint, strSize - 1);
    }

    private void getValuesFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] str = line.split(" ");
            if (str[0].compareTo("Position") == 0) {
                this.position = createPoint(str);
            } else if (str[0].compareTo("LookAt") == 0) {
                this.lookAt = createPoint(str);
            } else if (str[0].compareTo("Up") == 0) {
                this.up = createPoint(str);
            } else if (str[0].compareTo("Window") == 0) {
                for (int i = 0; i < 4; i++) {
                    this.window[i] = Double.parseDouble((str[i + 1]));
                }
            } else if (str[0].compareTo("Viewport") == 0) {
                this.viewPort[0] = Integer.parseInt((str[1]));
                this.viewPort[1] = Integer.parseInt((str[2]));
            }
        }
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getLookAt() {
        return lookAt;
    }

    public Vector getUp() {
        return up;
    }

    public double[] getWindow() {
        return window;
    }

    public int[] getViewPort() {
        return viewPort;
    }
}
