package Game15;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static void prn(ArrayDeque<Node> nodeList){
        for(Node node: nodeList){
            System.out.print(node.getKey()+" -> ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.makeSquareGraph(4);
        prn(graph.aStar(graph.getNodeList()[8], graph.getNodeList()[3]));
    }
}
