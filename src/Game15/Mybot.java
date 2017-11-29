package Game15;

import java.util.ArrayDeque;

public class Mybot {
    public Game15 game = new Game15();


    private int[] tiles = game.getTiles();
    private int blankPos = game.getBlankPos();
    private int side = game.getSide();
    private int numTiles = game.getNumTiles();

    private Graph graph = new Graph(side);

    public Mybot() {
    }
    
    public void bot() {
        for (int i = 0; i < (side - 3) * side + side; i++) {
            if (i % side == side - 2) {
                moveTileTo(i + 1, i + 1);
            } else if (i % side == side - 1) {
                if (findNode(i + 1) == i - 1 + side || findNode(i + 1) == i - 1) {
                    moveTileTo(i + 1, i - 2 + side);
                    moveTileTo(i, i);
                }
                graph.deleteNode(graph.getNodeList()[i]);
                moveTileTo(i + 1, i + side);
                graph.deleteNode(graph.getNodeList()[i + side]);
                graph.cancelDeleteNode(graph.getNodeList()[i]);
                moveTileTo(i, i - 1);
                graph.deleteNode(graph.getNodeList()[i - 1]);
                graph.cancelDeleteNode(graph.getNodeList()[i + side]);
                moveTileTo(i + 1, i);
                graph.deleteNode(graph.getNodeList()[i]);
            } else {
                moveTileTo(i + 1, i);
                graph.deleteNode(graph.getNodeList()[i]);
            }
        }
        for (int i = (side - 3) * side + side; i < (side - 3) * side + side * 2 - 1; i++) {
            if (i != (side - 3) * side + side * 2 - 2) {
                moveTileTo(i + 1 + side, i);
                if (findNode(i + 1) == i + side || findNode(i + 1) == i + 1 + side) {
                    moveTileTo(i + 1, i + 2 + side);
                }
                moveTileTo(i + 1 + side, i);
                graph.deleteNode(graph.getNodeList()[i]);
                moveTileTo(i + 1, i + 1);
                graph.deleteNode(graph.getNodeList()[i + 1]);
                graph.cancelDeleteNode(graph.getNodeList()[i]);
                moveTileTo(i + 1 + side, i + side);
                graph.cancelDeleteNode(graph.getNodeList()[i + 1]);
                moveTileTo(i + 1, i);
            } else {
                moveTileTo(side * side - 1, side * side - 2);
                moveTileTo(side * side - 1 - side, side * side - 2 - side);
                moveTileTo(side * side - side, side * side - 1 - side);
            }
        }
    }

    private void moveTileTo(int start, int goal) {
        Node nodeStart = graph.getNodeList()[findNode(start)];
        Node nodeGoal = graph.getNodeList()[goal];
        Node blankNode = graph.getNodeList()[blankPos];
        ArrayDeque<Node> mainPath = graph.aStar(nodeStart, nodeGoal);
        for (Node mainNode : mainPath) {
            graph.deleteNode(nodeStart);
            ArrayDeque<Node> localPath = graph.aStar(blankNode, mainNode);
            for (Node localNode : localPath) {
                changeWithBlank(localNode.getKey());
            }
            graph.cancelDeleteNode(nodeStart);
            changeWithBlank(nodeStart.getKey());
            nodeStart = graph.getNodeList()[findNode(start)];
            blankNode = graph.getNodeList()[blankPos];
        }
    }

    private int findNode(int k) {
        int r = -1;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == k) r = i;
        }
        return r;
    }

    public void changeWithBlank(int a) {
        int newBlankPos = a;
        sleep(0);
        tiles[blankPos] = tiles[newBlankPos];
        blankPos = newBlankPos;
        tiles[blankPos] = 0;
        game.repaint();
    }


    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isSolved() {
        if (tiles[tiles.length - 1] != 0) {
            return false;
        }
        for (int i = numTiles - 1; i >= 0; i--) {
            if (tiles[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void reload() {
        game = new Game15();
        graph = new Graph(side);
        tiles = game.getTiles();
        blankPos = game.getBlankPos();
    }
}
