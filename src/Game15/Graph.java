package Game15;


import java.util.*;

public class Graph {

    private Node[] nodeList;
    private int side = 0;

    public Node[] getNodeList() {
        return nodeList;
    }

    public Graph(int side) {
        this.makeSquareGraph(side);
    }

    public void makeSquareGraph(int side) {
        nodeList = new Node[side * side];
        for (int i = 0; i < side * side; i++) {
            Node node = new Node(i);
            nodeList[i] = node;
        }
        int count = 0;
        for (int i = 0; i < nodeList.length; i++) {
            if (count < side - 1) {
                nodeList[i].addNeighbor(nodeList[i + 1]);
                nodeList[i + 1].addNeighbor(nodeList[i]);
                count++;
            } else count = 0;
            if (i + side < nodeList.length) {
                nodeList[i].addNeighbor(nodeList[i + side]);
                nodeList[i + side].addNeighbor(nodeList[i]);
            }
        }
        this.side = side;
    }

    private void prn() {
        for (int i = 0; i < nodeList.length; i++) {
            System.out.print(nodeList[i].getKey() + " -> ");
            for (Node node : nodeList[i].getNeighbors()) {
                System.out.print(node.getKey() + " ");
            }
            System.out.println();
        }
    }

    private int heuristicFunForSquareGrph(Node start, Node goal) {
        int x1 = start.getKey() % side;
        int y1 = start.getKey() / side;
        int x2 = goal.getKey() % side;
        int y2 = goal.getKey() / side;
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private Node minFInOpen(ArrayDeque<Node> open) {
        int f = open.getFirst().getF();
        Node answer = open.getFirst();
        for (Node node : open) {
            if (node.getF() < f) {
                f = node.getF();
                answer = node;
            }
        }
        return answer;
    }

    public ArrayDeque<Node> aStar(Node start, Node goal) {
        HashSet<Node> close = new HashSet<>();
        ArrayDeque<Node> open = new ArrayDeque<>();
        open.add(start);
        HashMap<Node, Node> path = new HashMap<>();
        start.setG(0);
        start.setH(heuristicFunForSquareGrph(start, goal));
        start.setF(start.getG() + start.getH());
        while (!open.isEmpty()) {
            boolean flag;
            Node tmp = minFInOpen(open);
            if (tmp == goal) return resultPath(start, goal);
            open.remove(tmp);
            close.add(tmp);
            for (Node node : tmp.getNeighbors()) {
                if (close.contains(node)) continue;
                int tempG = tmp.getG() + 1;
                if (!open.contains(node)) {
                    open.add(node);
                    flag = true;
                } else if (tempG < node.getG()) flag = true;
                else flag = false;
                if(flag) {
                    node.setPrevNode(tmp);
                    node.setG(tempG);
                    node.setH(heuristicFunForSquareGrph(node, goal));
                    node.setF(node.getG() + node.getH());
                }
            }
        }
        return null;
    }

    private ArrayDeque<Node> resultPath(Node start, Node goal){
        ArrayDeque<Node> result = new ArrayDeque<>();
        Node tmp = goal;
        while (tmp != start) {
            result.addFirst(tmp);
            tmp = tmp.getPrevNode();
        }
        return result;
    }

    public void deleteNode(Node node) {
        for(Node n: node.getNeighbors()) {
            n.getNeighbors().remove(node);
        }
    }
    public void cancelDeleteNode(Node node) {
        for(Node n: node.getNeighbors()) {
            n.getNeighbors().add(node);
        }
    }
}
