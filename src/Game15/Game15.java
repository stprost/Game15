package Game15;


import com.sun.jmx.remote.internal.ArrayQueue;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.ArrayDeque;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.*;

import static java.lang.Thread.sleep;

class Game15 extends JPanel {

    private static int side = 4;
    private int numTiles = side * side - 1;

    private final Random rand = new Random();
    private int[] tiles = new int[numTiles + 1];
    private int tileSize;
    private int blankPos;
    private final int margin;
    private final int gridSize;
    public boolean gameOver;
    private static Dimension gameSize;


    public int getSide() {
        return side;
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getNumTiles() {
        return numTiles;
    }

    public int getBlankPos() {
        return blankPos;
    }


    public Game15() {
        gameSize = new Dimension(side * 80 + 100, side * 80 + 100);
        margin = 50;
        tileSize = 80;
        gridSize = tileSize * side;

        setPreferredSize(gameSize);
        setBackground(Color.WHITE);
        setForeground(new Color(0x6495ED));
        setFont(new Font("SansSerif", Font.BOLD, 60));
        gameOver = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOver) {
                    newGame();

                } else {

                    int ex = e.getX() - margin;
                    int ey = e.getY() - margin;

                    if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize) {
                        return;
                    }

                    int c1 = ex / tileSize;
                    int r1 = ey / tileSize;
                    int c2 = blankPos % side;
                    int r2 = blankPos / side;

                    int clickPos = r1 * side + c1;

                    int dir = 0;
                    if (c1 == c2 && Math.abs(r1 - r2) > 0) {
                        dir = (r1 - r2) > 0 ? side : -side;
                    } else if (r1 == r2 && Math.abs(c1 - c2) > 0) {
                        dir = (c1 - c2) > 0 ? 1 : -1;
                    }

                    if (dir != 0) {
                        do {
                            int newBlankPos = blankPos + dir;
                            tiles[blankPos] = tiles[newBlankPos];
                            blankPos = newBlankPos;
                        } while (blankPos != clickPos);
                        tiles[blankPos] = 0;
                    }

                    gameOver = isSolved();
                }
                repaint();
            }
        });

        newGame();
    }


    public void setSize(int side) {
        this.side = side;
        numTiles = side * side - 1;
        tiles = new int[numTiles + 1];
        gameSize = new Dimension(side * 80 + 100, side * 80 + 100);
        setPreferredSize(gameSize);
        newGame();
    }

    private void newGame() {
        do {
            reset();
            shuffle();
        } while (!isSolvable());
        gameOver = false;
    }

    private void reset() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (i + 1) % tiles.length;
        }
        blankPos = tiles.length - 1;
    }

    private void shuffle() {
        int n = numTiles;
        while (n > 1) {
            int r = rand.nextInt(n--);
            int tmp = tiles[r];
            tiles[r] = tiles[n];
            tiles[n] = tmp;
        }
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i]) {
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    private boolean isSolved() {
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

    private void drawGrid(Graphics2D g) {
        for (int i = 0; i < tiles.length; i++) {
            int r = i / side;
            int c = i % side;
            int x = margin + c * tileSize;
            int y = margin + r * tileSize;

            if (tiles[i] == 0) {
                if (gameOver) {
                    g.setColor(Color.GREEN);
                    drawCenteredString(g, "✓", x, y);
                }
                continue;
            }

            g.setColor(getForeground());
            g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.blue.darker());
            g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
            g.setColor(Color.WHITE);

            drawCenteredString(g, String.valueOf(tiles[i]), x, y);
        }
    }

    private void drawStartMessage(Graphics2D g) {
        if (gameOver) {
            g.setFont(getFont().deriveFont(Font.BOLD, 18));
            g.setColor(getForeground());
            String s = "Вы настоящий гений!";
            String s2 = "Нажмите для перезапуска.";
            int x = (getWidth() - g.getFontMetrics().stringWidth(s)) / 2;
            int y = getHeight() - 30;
            g.drawString(s, x, y);
            y = getHeight() - 10;
            x = (getWidth() - g.getFontMetrics().stringWidth(s)) / 2 - 27;
            g.drawString(s2, x, y);
        }
    }

    private void drawCenteredString(Graphics2D g, String s, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int des = fm.getDescent();

        x = x + (tileSize - fm.stringWidth(s)) / 2;
        y = y + (asc + (tileSize - (asc + des)) / 2);

        g.drawString(s, x, y);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawGrid(g);
        drawStartMessage(g);
    }


    private static JFrame f = new JFrame();
    private static JButton buttonBot;
    private static JButton buttonSize;
    private static JPanel panelLeft = new JPanel();
    private static JPanel panelButton = new JPanel();
    private static JPanel panelGame = new JPanel();
    private static Mybot mybot;
    private static Timer timer;

    public static void wind(boolean activeBot) {
        Game15 game = new Game15();
        timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Game15");
        f.setResizable(false);
        f.setLayout(new FlowLayout());
        buttonBot = new JButton("Запустить чудобота");
        buttonSize = new JButton("Поменять размер");
        panelButton.removeAll();
        panelGame.removeAll();
        panelLeft.removeAll();
        panelButton.setPreferredSize(new Dimension(200, 100));
        panelButton.setLayout(new GridLayout(2, 1, 1, 10));
        panelButton.add(buttonBot);
        panelButton.add(buttonSize);
        panelLeft.setPreferredSize(new Dimension(200, side * 80 + 100));
        panelLeft.add(panelButton);
        panelLeft.setBackground(Color.WHITE);
        f.add(panelLeft, BorderLayout.WEST);
        panelGame.add(game, BorderLayout.EAST);
        f.add(panelGame);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        mybot = new Mybot(game);
        mybot.bot();

        buttonBot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mybot = new Mybot(game);
                mybot.bot();
            }
        });
        buttonSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Size.wind(game);
            }
        });

    }

}
