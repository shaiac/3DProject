import LinearMath.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class View {
    private Vector origin;
    private double direction;
    private double[] size;
    private int[] resolution;
    public View() throws Exception {
        this.size = new double[2];
        this.resolution = new int[2];
        this.getValuesFromFile("Resources\\ex0.vim");

    }
    public void getValuesFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] str = line.split(" ");
            if (str[0].compareTo("Origin") == 0) {
                int strSize = str.length;
                double[] arrPoint = new double[strSize - 1];
                for (int j = 0;j<strSize - 1;j++) {
                    arrPoint[j] = Double.parseDouble(str[j + 1]);
                }
                this.origin = new Vector(arrPoint,strSize - 1);
            } else  if (str[0].compareTo("Direction") == 0) {
                this.direction = Double.parseDouble(str[1]);
            } else if (str[0].compareTo("Size") == 0) {
                this.size[0] = Double.parseDouble(str[1]);
                this.size[1] = Double.parseDouble(str[2]);
            } else if (str[0].compareTo("Resolution") == 0) {
                this.resolution[0] = Integer.parseInt((str[1]));
                this.resolution[1] = Integer.parseInt((str[2]));
            }
        }
    }

    public double getDirection() {
        return direction;
    }

    public double[] getSize() {
        return size;
    }

    public int[] getResolution() {
        return resolution;
    }

    public Vector getOrigin() {
        return origin;
    }
}
