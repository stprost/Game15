package Game15;

import javax.swing.*;
import java.awt.*;
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
        Mybot mybot = new Mybot();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Fifteen Puzzle");
        f.setResizable(false);
        f.add(mybot.game, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        boolean a = mybot.isSolved();
        mybot.bot();
        a = mybot.isSolved();
    }
}
