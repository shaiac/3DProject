/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class MyCanvas extends Canvas implements MouseListener,  MouseMotionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private int viewHeight;
    private int viewWidth;
    private Boolean clipingFlag;
    private Scene scene;
    private View view;
    private Clipping clipping;
    private Transformation3D transformation;
    private String transType;
    private Vector pressedPoint;
    private ChangeListener listener;
    private char axis;
    private Matrix VM1;
    private Matrix VM2;
    private Matrix AT;
    private Matrix CT;
    private Matrix TT;
    private Matrix Proj;
    public MyCanvas(int width, int height, View view) {
        this.scene = new Scene();
        this.view = view;
        this.transformation = view.getTransformation();
        this.viewHeight = height;
        this.viewWidth = width;
        clipingFlag = false;
        this.axis = 'z';
        InitializeMatrices();
        createClipping();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public void InitializeMatrices() {
        this.AT = new Matrix(4);
        AT.toIdentityMatrix();
        this.CT = new Matrix(4);
        CT.toIdentityMatrix();
        this.TT = new Matrix(4);
        TT.toIdentityMatrix();
        this.Proj = new Matrix(4);
        Proj.toIdentityMatrix();
        this.VM1 = view.getVM1();
        this.VM2 = view.getVM2();
    }

    public void createClipping() {
        List<Edge> boundaryEdgesList = new ArrayList<>();
        List<Vector> cVertexList = new ArrayList<>();
        double[] upperLeft = {20, 20,1};
        Vector p0 = new Vector(upperLeft,3);
        cVertexList.add(p0);
        double[] upperRight = {viewWidth - 40,20,1};
        Vector p1 = new Vector(upperRight,3);
        cVertexList.add(p1);
        double[] downRight = {viewWidth - 40,viewHeight - 60,1};
        Vector p2 = new Vector(downRight,3);
        cVertexList.add(p2);
        double[] downLeft = {20,viewHeight - 60,1};
        Vector p3 = new Vector(downLeft,3);
        cVertexList.add(p3);
        cVertexList.add(p3);
        Edge edge0 = new Edge(0,1);
        boundaryEdgesList.add(edge0);
        Edge edge1 = new Edge(1,2);
        boundaryEdgesList.add(edge1);
        Edge edge2 = new Edge(2,3);
        boundaryEdgesList.add(edge2);
        Edge edge3 = new Edge(3,0);
        boundaryEdgesList.add(edge3);
        this.clipping = new Clipping(boundaryEdgesList, cVertexList);
    }

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }


    public void changeSize (Dimension dim) {
        this.setSize(dim);
        viewHeight = dim.height;
        viewWidth = dim.width;
        createClipping();
        view.createVM2(viewWidth, viewHeight);
        this.VM2 = view.getVM2();
    }
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,viewWidth,viewHeight);
        //setSize(this.viewWidth, this.viewHeight);
        this.TT = VM2.Multiply(Proj.Multiply(CT.Multiply(AT.Multiply(VM1))));
        this.draw(this.scene.getEdgesList(), this.UpdateVertex(this.scene.getVertexList(), this.TT));
    }

    private void drawBackground(Graphics g) {
        g.setFont(new Font("Forte", Font.BOLD, 20));
        g.setColor(Color.red);
        g.drawString("C - Toggle Clip on/off.", 20, 20);
        g.drawString("R - Resets To Original Position.", 20, 40);
        g.drawString("Q - Quit.", 20, 60);
    }

    public void draw(List<Edge> edges, List<Vector> vertex) {
        Graphics g = getGraphics();
        int edgesNum = edges.size();
        int i;
        g.setColor(Color.yellow);
        g.drawRect(20,20,viewWidth - 60,viewHeight - 80);
        g.setColor(Color.green);
        for (i = 0; i < edgesNum; i++) {
            Vector ver0 = vertex.get(edges.get(i).getpointIndex0());
            Vector ver1 = vertex.get(edges.get(i).getpointIndex1());
            if (clipingFlag) {
                List<Vector> line = new ArrayList<>();
                    line.add(ver0);
                    line.add(ver1);
                line = clipping.clip(line);
                if (line != null) {
                    g.drawLine((int) line.get(0).getVec()[0], (int) line.get(0).getVec()[1],
                            (int) line.get(1).getVec()[0], (int)line.get(1).getVec()[1]);
                }

            } else {
                g.drawLine((int) ver0.getVec()[0], (int) ver0.getVec()[1],
                        (int) ver1.getVec()[0], (int) ver1.getVec()[1]);
            }
        }

    }

    private List<Vector> UpdateVertex(List<Vector> vertex, Matrix TT){
        List<Vector> updatedVertex = new ArrayList<>();
        int i;
        int vertexNum = vertex.size();
        for (i = 0; i < vertexNum; i++) {
            updatedVertex.add(TT.Multiply(vertex.get(i)));
        }
        return updatedVertex;
    }

    public void transformationType(int x, int y) {
        int width = this.getWidth();
        int height = this.getHeight();
        Boolean yPosition = (y <= height /3 || y >= (2 * height) / 3);
        if (x < width /3) {
            if (yPosition) {
                this.transType = "Rotate";
            } else {
                this.transType = "Scale";
            }

        } else if (x > (2 * width) / 3) {
            if (yPosition) {
                this.transType = "Rotate";
            } else {
                this.transType = "Scale";
            }
        } else {
            if (yPosition) {
                this.transType = "Scale";
            } else {
                this.transType = "Translate";
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        this.transformationType(e.getX(),e.getY());
        this.pressedPoint = new Vector(new double[]{e.getX(),e.getY(), 1}, 3);
    }

    public void mouseReleased(MouseEvent e) {
        AT = CT.Multiply(AT);
        CT.toIdentityMatrix();
        this.repaint();
    }
    //change to 3D
    public void mouseDragged(MouseEvent e) {
        if (transType.equals("Translate")) {
            CT = transformation.translate((e.getX() - pressedPoint.getVec()[0])*this.view.windowWidth/this.viewWidth,
                    (e.getY() - pressedPoint.getVec()[1])*this.view.windowHieght/this.viewHeight, 0);


        } else {
            double arr[] = {viewWidth/2,viewHeight/2,1};
            Vector center = new Vector(arr, 3);
            Matrix transLookat = transformation.translate(0,0,view.getPosition().getVec()[2] - view.getLookAt().getVec()[2]);
            Matrix transLookatBack = transformation.translate(0,0,-(view.getPosition().getVec()[2] - view.getLookAt().getVec()[2]));
            Matrix transCenter = transformation.translate(viewWidth/2, viewHeight/2, 0);
            Matrix transBack = transformation.translate(-viewWidth/2, -viewHeight/2, 0);
            Vector destination = new Vector(new double[]{e.getX(),e.getY(), 1}, 3);
            if (transType.equals(("Scale"))) {

                double SF = destination.minus(center).GetLength() /
                        pressedPoint.minus(center).GetLength();
                Matrix scale = transformation.scale(SF, SF, SF);
                CT = transLookatBack.Multiply(scale).Multiply(transLookat);
            } else {
                double angle1 = destination.minus(center).GetAngle();
                double angle2 = pressedPoint.minus(center).GetAngle();
                Matrix rotate = transformation.rotate(-(angle1-angle2),this.axis);
                CT = transLookatBack.Multiply(rotate).Multiply(transLookat);
                //CT = rotate;
            }
        }
        //TT = VM2.Multiply(CT.Multiply(AT.Multiply(VM1)));
        this.repaint();
    }

    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'c' || e.getKeyChar() == 'C') {
            clipingFlag = !clipingFlag;
        } else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            InitializeMatrices();
        } else if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') {
            System.exit(0);
        } else if (e.getKeyChar() == 'x' || e.getKeyChar() == 'X') {
            this.axis = 'x';
        } else if (e.getKeyChar() == 'y' || e.getKeyChar() == 'Y') {
            this.axis = 'y';
        } else if (e.getKeyChar() == 'z' || e.getKeyChar() == 'Z') {
            this.axis = 'z';
        } else if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L') {
            this.scene = new Scene();
            this.view = new View();
            this.listener.onChangeHappened(view);
            InitializeMatrices();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}