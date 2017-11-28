package Game15;

import java.util.ArrayList;

public class Node {

    private int key;
    private ArrayList<Node> neighbors = new ArrayList<>();
    private int g;
    private int h;
    private int f;
    private Node prevNode;

    public Node getPrevNode() {
        return prevNode;
    }

    public int getKey() {
        return key;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node(int key) {
        this.key = key;
    }

    public void addNeighbor(Node node) {
        neighbors.add(node);
    }


}
