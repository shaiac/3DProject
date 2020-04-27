public class Edge {
    private int pointIndex0;
    private int pointIndex1;

    /**
     * Construct a line given the starting point and the ending point.
     * @param pointIndex0 the index of point 0
     * @param pointIndex1   the index of point 1
     */
    public Edge(int pointIndex0, int pointIndex1) {
        this.pointIndex0 = pointIndex0;
        this.pointIndex1 = pointIndex1;
    }

    public int getpointIndex0() {
        return this.pointIndex0;
    }


    public int getpointIndex1() {
        return this.pointIndex1;
    }
}
