package Game15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Size extends JPanel {

    private static JButton two = new JButton("2x2");
    private static JButton three = new JButton("3x3");
    private static JButton four = new JButton("4x4");
    private static JButton five = new JButton("5x5");
    private static JButton six = new JButton("6x6");
    private static JButton seven = new JButton("7x7");
    private static JButton eight = new JButton("8x8");
    private static JButton nine = new JButton("9x9");
    private static JButton ten = new JButton("10x10");
    private static Game15 game = new Game15();


    private static JFrame f1 = new JFrame();
    public static void wind(Game15 game) {
        f1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        f1.setTitle("Size");
        f1.setResizable(false);
        f1.setLayout(new GridLayout(3,3,5,10));
        f1.setLocationRelativeTo(null);
        f1.add(two, BorderLayout.CENTER);
        addAct(two, 2);
        f1.add(three, BorderLayout.CENTER);
        addAct(three, 3);
        f1.add(four, BorderLayout.CENTER);
        addAct(four, 4);
        f1.add(five, BorderLayout.CENTER);
        addAct(five, 5);
        f1.add(six, BorderLayout.CENTER);
        addAct(six, 6);
        f1.add(seven, BorderLayout.CENTER);
        addAct(seven, 7);
        f1.add(eight, BorderLayout.CENTER);
        addAct(eight, 8);
        f1.add(nine, BorderLayout.CENTER);
        addAct(nine, 9);
        f1.add(ten, BorderLayout.CENTER);
        addAct(ten, 10);
        f1.pack();
        f1.setVisible(true);
    }

    private static void addAct(JButton button, int size){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setSize(size);
                Game15.wind(false);
                f1.dispose();
            }
        });
    }
}
