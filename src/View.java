import LinearMath.Matrix;
import LinearMath.Transformation3D;
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
    private Transformation3D transformation;
    private Matrix VM1;
    private Matrix VM2;
    public double windowHieght;
    public double windowWidth;

    public View() {
        this.viewPort = new int[2];
        this.window = new double[4];
        this.transformation = new Transformation3D();
        this.getValuesFromFile("Resources\\ex0.vim");
        createVM1();
        createVM2();
    }

    private Vector createPoint(String[] str) {
        int strSize = str.length;
        double[] arrPoint = new double[strSize - 1];
        for (int j = 0; j < strSize - 1; j++) {
            arrPoint[j] = Double.parseDouble(str[j + 1]);
        }
        return new Vector(arrPoint, strSize - 1);
    }

    private void getValuesFromFile(String filePath) {
        File file = new File(filePath);
        try {
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
        } catch (Exception e) {
            System.out.println("Error reading from File");
        }
    }

    private void createVM2() {
        this.windowHieght = window[3] - window[2];
        this.windowWidth = window[1] - window[0];
        double viewWidth = (double) this.viewPort[0];
        double viewHeight = (double) this.viewPort[1];
        Matrix scale = transformation.scale(viewWidth/this.windowWidth, viewHeight/this.windowHieght,0);
        Matrix t2 = transformation.translate(viewWidth / 2, viewHeight / 2, 0);
        this.VM2 = t2.Multiply(scale);
    }

    public Matrix getVM2() {
        return this.VM2;
    }


    private void createVM1() {
        Vector L =  this.lookAt.AddDimension();
        Vector P= this.position.AddDimension();
        Vector V = this.up.AddDimension();
        Vector Zv = P.minus(L);
        Zv = Zv.normal();
        Vector Xv = V.crossPruduct(Zv);
        Xv = Xv.normal();
        Vector Yv = Xv.crossPruduct(Zv);
        double[][] arrayR = {{Xv.getVec()[0],Xv.getVec()[1],Xv.getVec()[2], 0},
                {Yv.getVec()[0],Yv.getVec()[1],Yv.getVec()[2], 0},
                {Zv.getVec()[0],Zv.getVec()[1],Zv.getVec()[2], 0},
                {0,0,0,1}};
        Matrix R = new Matrix(arrayR,arrayR.length);
        Matrix T = transformation.translate(-(P.getVec()[0]),-(P.getVec()[1]),-(P.getVec()[2]));
        //VM1
        this.VM1 = R.Multiply(T);
    }

    public Matrix getVM1() {
        return this.VM1;
    }

    public Vector getLookAt() {
        return lookAt;
    }

    public Vector getPosition() {
        return position;
    }

    public Transformation3D getTransformation() {
        return this.transformation;
    }
    public double[] getWindow() {
        return window;
    }

    public int[] getViewPort() {
        return viewPort;
    }


}
