package LinearMath;

public class Vector {
    double[] vec;
    int size;
    public Vector(double[]vec,int size){
        this.vec = vec;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public double[] getVec() {
        return vec;
    }

    public Vector Add(Vector v){
        Vector retval;
        double[] vecArr = new double[size];
        for(int i=0; i<size; i++){
            vecArr[i] = this.vec[i] + v.vec[i];
        }
        retval = new Vector(vecArr,size);
        return retval;
    }
    public double Multiply(Vector v){
        double retval = 0;
        for(int i=0; i<size;i++){
            retval += this.vec[i] * v.vec[i];
        }
        return retval;
    }
    public double GetLength(){
        double length;
        Vector v1 = this.DecreaseDimension();
        length = Math.sqrt(v1.Multiply(v1));
        return length;
    }
    public double GetAngle(Vector v){
        double CrossPro = this.Multiply(v);
        double cosAngle = CrossPro/(this.GetLength()*v.GetLength());
        double angle = Math.toDegrees(Math.acos(cosAngle));
        return angle;
    }
    public double GetAngle() {
        double r = this.GetLength();
        double div = vec[1]/r;
        double angle =Math.toDegrees(Math.asin(div));
        if(vec[1]< 0 && vec[0] < 0){
            angle =-(180 + angle);
        }
        else if(vec[0] <0){
            angle =180 - angle;
        }
        if(angle<0){
            angle+=360;
        }
        return angle;
    }

    public void Print(){
        for(int i=0; i<size;i++){
            System.out.print(this.vec[i] +", ");
        }
        System.out.println();
    }

    public Vector AddDimension(){
        double[] vecArr = new double[size+1];
        for(int i=0; i<size; i++){
            vecArr[i] = this.vec[i];
        }
        vecArr[size] = 1;
        Vector newVec = new Vector(vecArr,size+1);
        return newVec;
    }
    public Vector DecreaseDimension(){
        double[] vecArr = new double[size-1];
        for(int i=0; i<size - 1; i++){
            vecArr[i] = this.vec[i];
        }
        Vector newVec = new Vector(vecArr,size-1);
        return newVec;
    }

    public Vector minus(Vector v) {
        double[] vecArr = new double[size];
        for(int i=0; i<size - 1; i++){
            vecArr[i] = this.vec[i] - v.getVec()[i];
        }
        Vector newVec = new Vector(vecArr, size);
        return newVec;
    }
}
