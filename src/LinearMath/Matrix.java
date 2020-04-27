package LinearMath;

public class Matrix {
    double[][] mat;
    int size;
    public Matrix(int size) {
        this.mat = new double[size][size];
        this.size = size;
    }

    public Matrix (double[][] matrix, int matSize){
        mat = matrix;
        size = matSize;
    }

    public double[][] getMat(){
        return mat;
    }
    public int getSize(){
        return size;
    }
    public Matrix Multiply(Matrix m){
        Matrix ans;
        double[][] ans2Arr = new double[size][size];
        for( int i = 0; i<size; i++){
            for(int j=0;j<size;j++){
                double sum = 0;
                for (int k = 0; k<size; k++){
                    sum += mat[i][k] * m.mat[k][j];
                }
                ans2Arr[i][j] = sum;
            }
        }
        ans = new Matrix(ans2Arr,size);
        return ans;
    }
    public Matrix Add(Matrix m){
        Matrix ans;
        double[][] ans2Arr = new double[size][size];
        for(int i=0;i<size;i++){
            for(int j=0; j<size;j++){
                ans2Arr[i][j] = this.mat[i][j] + m.mat[i][j];
            }
        }
        ans = new Matrix(ans2Arr,size);
        return ans;
    }
    public Vector Multiply(Vector v){
        Vector ans;
        double[] ans2Arr = new double[size];
        for( int i = 0; i<size; i++){
            double sum = 0;
            for(int j=0;j<size;j++){
                sum+= v.vec[j]*this.mat[i][j];
            }
            ans2Arr[i] = sum;
        }
        ans = new Vector(ans2Arr,size);
        return ans;
    }

    public void toIdentityMatrix() {
        for( int i = 0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    mat[i][j] = 1;
                } else {
                    mat[i][j] = 0;
                }
            }
        }
    }

}
