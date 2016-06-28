package pl.com.bottega.life;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by arkadiuszarak on 11/06/2016.
 */
public class GameSwingApp {

    public static final int W = 80;
    public static final int H = 60;
    private JPanel[][] panels;
    private final GameEngine engine;

    public GameSwingApp() {
        engine = new GameEngine(W, H, 0);
        engine.tenCellsRow();
        showUi();
        startGame();
    }

    private void startGame() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                engine.nextStep();
                for (int i = 0; i < H; i++)
                    for (int j = 0; j < W; j++) {
                        color(i, j);
                    }
            }
        }).start();
    }

    private void showUi() {
        JFrame f = new JFrame("Game of life");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(H, W));
        panels = new JPanel[H][W];

        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                panels[i][j] = new JPanel();
                color(i, j);
                panels[i][j].setBorder(LineBorder.createGrayLineBorder());
                f.add(panels[i][j]);
            }
        f.setPreferredSize(new Dimension(800, 600));
        f.setVisible(true);
        f.pack();
    }

    private void color(int i, int j) {
        panels[i][j].setBackground(engine.isAlive(j, i) ? Color.BLACK : null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameSwingApp());
    }

}

