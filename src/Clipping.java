import LinearMath.Vector;

import java.util.ArrayList;
import java.util.List;

public class Clipping {
    private List<Edge> boundaryEdgesList;
    private List<Vector> vertexList;

    public Clipping(List<Edge> boundaryEdgesList, List<Vector> vertexList) {
        this.boundaryEdgesList = boundaryEdgesList;
        this.vertexList = vertexList;
    }

    public double findMax(List<Double> list) {
        double max = 0;
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i) > max)
                max = list.get(i);
        }
        return max;
    }

    public double findMin(List<Double> list) {
        double min = 1;
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i) < min)
                min = list.get(i);
        }
        return min;
    }

    public List<Vector> clip(List<Vector> line){
        int edgesNum = boundaryEdgesList.size();
        int i;
        List<Vector> newLine = new ArrayList<>();
        Vector[]  normals = new Vector[edgesNum];
        Vector[]  P0_PEi = new Vector[edgesNum];

        //Create the normal vectors
        for(i =0 ; i<edgesNum; i++){
            double[] norm= new double[line.get(0).getSize()];
            norm[1] = vertexList.get((i+1)%edgesNum).getVec()[0] - vertexList.get(i).getVec()[0];
            norm[0] = vertexList.get(i).getVec()[1] - vertexList.get((i+1)%edgesNum).getVec()[1];
            norm[2] = 1;
            normals[i] = new Vector(norm,3);
        }
        //calculate the delta vector
        Vector delta = line.get(1).minus(line.get(0));
        //choose PEi vector and calc dist from v0
        for(i = 0; i<edgesNum;i++){
            P0_PEi[i] = (vertexList.get(i).minus(line.get(0)));
        }

        //numerator and denomerator
        double[] numer = new double[edgesNum];
        double[] denom = new double[edgesNum];

        for(i=0; i<edgesNum; i++){
            numer [i] = normals[i].Multiply(P0_PEi[i]);
            denom[i] = normals[i].Multiply(delta);
        }
        // find the t and check pE and pL
        double p;
        List<Double> pE = new ArrayList<>();
        List<Double> pL = new ArrayList<>();
        for(i = 0; i < edgesNum; i++) {
            if(denom[i] != 0){
                p = numer[i] / denom[i];
                if (denom[i] > 0) {
                    pE.add(p);
                } else {
                    pL.add(p);
                }
            }

        }
        double minPL = findMin(pL);
        double maxPE = findMax(pE);
        if (maxPE > minPL) {
            return null;
        }
        double[] newPt0 = new double[2];
        double[] newPt1 = new double[2];
        //create the new line
        newPt0[0] = line.get(0).getVec()[0] + delta.getVec()[0] * maxPE;
        newPt0[1] = line.get(0).getVec()[1] + delta.getVec()[1] * maxPE;
        newPt1[0] = line.get(0).getVec()[0] + delta.getVec()[0] * minPL;
        newPt1[1] = line.get(0).getVec()[1] + delta.getVec()[1] * minPL;
        newLine.add(new Vector(newPt0,2));
        newLine.add(new Vector(newPt1,2));
        return newLine;
    }
                                                   
}
