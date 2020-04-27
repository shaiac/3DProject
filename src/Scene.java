import LinearMath.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Scene {
    private List<Vector> vertexList;
    private List<Edge> edgesList;

    public Scene() throws Exception{
        this.edgesList = new ArrayList<>();
        this.vertexList = new ArrayList<>();
        initializeLists("Resources\\ex0.scn");
    }
    public void initializeLists(String filePath) throws Exception{
        List<Vector> pointsIndex = new ArrayList<>();
        int lineNumber = 0, edgesNum, vertexNum = 0, i;
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            if (lineNumber == 0) {
                vertexNum = Integer.parseInt(line);
                lineNumber++;
                continue;
            } else if (lineNumber == 1 + vertexNum) {
                edgesNum = Integer.parseInt(line);
                line = br.readLine();
                lineNumber++;
                for (i = 0; i < edgesNum; i++) {
                    String[] edges = line.split(" ");
                    edgesList.add(new Edge(Integer.parseInt(edges[0]),Integer.parseInt(edges[1])));
                    line = br.readLine();
                    lineNumber++;
                }
                continue;
            }
            for (i = 0; i < vertexNum; i++) {
                String[] point = line.split(" ");
                double[] arrPoint = new double[point.length + 1];
                for (int j = 0;j<point.length;j++) {
                    arrPoint[j] = Double.parseDouble(point[j]);
                }
                arrPoint[point.length] = 1;
                Vector vertex = new Vector(arrPoint,arrPoint.length);
                //Point pt = new Point(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
                this.vertexList.add(vertex);
                if (i + 1 == vertexNum) {
                    continue;
                }
                line = br.readLine();
                lineNumber++;
            }
            lineNumber++;
        }
    }
    public List<Edge> getEdgesList() {
        return edgesList;
    }

    public List<Vector> getVertexList() {
        return vertexList;
    }
}


