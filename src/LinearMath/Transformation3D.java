package LinearMath;

public class Transformation3D {
    public Matrix translate(double deltaX, double deltaY) {
            double[][] array = {{1, 0 , deltaX} , {0, 1, deltaY} , {0, 0 ,1} };
        Matrix matrix = new Matrix(array, array.length);
        return matrix;
    }
    public Matrix scale(double a, double b, double c) {
        double[][] array = {{a, 0 , 0, 0} , {0, b, 0, 0} , {0, 0, c, 1}, {0, 0, 0 ,1} };
        Matrix matrix = new Matrix(array, array.length);
        return matrix;
    }
    public Matrix rotate(double angle, String axis) {
        double radians, cos, sin;
        Matrix matrix = new Matrix(4);
        double[][] array;
        switch (axis) {
            case "z":
                radians = Math.toRadians(angle);
                cos = Math.cos(radians);
                sin = Math.sin(radians);
                double[][] arrayZ = {{cos, sin, 0, 0}, {-sin, cos, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayZ, arrayZ.length);
                break;
            case "x":
                radians = Math.toRadians(angle);
                cos = Math.cos(radians);
                sin = Math.sin(radians);
                double[][] arrayX = {{cos, sin, 0, 0}, {-sin, cos, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayX, arrayX.length);
            case "y":
                radians = Math.toRadians(angle);
                cos = Math.cos(radians);
                sin = Math.sin(radians);
                double[][] arrayY = {{cos, sin, 0, 0}, {-sin, cos, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
                matrix = new Matrix(arrayY, arrayY.length);
        }
        return matrix;
    }
}
