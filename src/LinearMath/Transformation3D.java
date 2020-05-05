/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package LinearMath;

public class Transformation3D {
    public Matrix translate(double deltaX, double deltaY, double deltaZ) {
            double[][] array = {{1, 0 , 0, deltaX} , {0, 1, 0, deltaY} , {0, 0 , 1, deltaZ} , {0,0,0,1} };
        Matrix matrix = new Matrix(array, array.length);
        return matrix;
    }
    public Matrix scale(double a, double b, double c) {
        double[][] array = {{a, 0 , 0, 0} , {0, b, 0, 0} , {0, 0, c, 0}, {0, 0, 0 ,1} };
        Matrix matrix = new Matrix(array, array.length);
        return matrix;
    }
    public Matrix rotate(double angle, char axis) {
        double radians, cos, sin;
        Matrix matrix = new Matrix(4);
        double[][] array;
        radians = Math.toRadians(angle);
        cos = Math.cos(radians);
        sin = Math.sin(radians);
        switch (axis) {
            case 'z':
                double[][] arrayZ = {{cos, -sin, 0, 0}, {sin, cos, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayZ, arrayZ.length);
                break;
            case 'x':
                double[][] arrayX = {{1, 0, 0, 0}, {0, cos, -sin, 0}, {0, sin, cos, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayX, arrayX.length);
                break;
            case 'y':
                double[][] arrayY = {{cos, 0, -sin, 0}, {0, 1, 0, 0}, {sin, 0, cos, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayY, arrayY.length);
        }
        return matrix;
    }
}
