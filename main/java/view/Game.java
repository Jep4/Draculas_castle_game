package view;

import model.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


public class Game {

    public static JFrame window;
    private JPanel startScreen;
    private BufferedImage myPicture;
    private static Board board;
    JLabel picLabel;
    public static Sound background = new Sound();

    public Game() {
        setStartScreen();

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void endScreen(String score, String time) {
        JPanel endScreen = new JPanel();

        URL url = Game.class.getResource("/start.gif");
        Icon icon = new ImageIcon(url);
        JLabel backgroundScreen = new JLabel(icon);
        backgroundScreen.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel TotalScore = new JLabel("Score: ");
        TotalScore.setFont(new Font("Helvetica", Font.PLAIN, 64));
        TotalScore.setForeground(Color.WHITE);

        JLabel TotalTime = new JLabel("Time: ");
        TotalTime.setFont(new Font("Helvetica", Font.PLAIN, 64));
        TotalTime.setForeground(Color.WHITE);

        JLabel sc = new JLabel(score);
        sc.setFont(new Font("Century Gothic", Font.PLAIN, 64));
        sc.setForeground(Color.WHITE);

        JLabel tm = new JLabel(time);
        tm.setFont(sc.getFont().deriveFont(64f));
        tm.setForeground(Color.WHITE);

        ImageIcon restart = new ImageIcon("src/main/resources/Restart.png");
        Image restartImage = restart.getImage().getScaledInstance(150, 40,  java.awt.Image.SCALE_SMOOTH);
        restart = new ImageIcon(restartImage);

        JLabel restartButton = new JLabel(restart);

      //  restartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                window.remove(endScreen);
                window.repaint();
                window.revalidate();
                board.resetBoard();
            }
        });

        c.gridy = 0;
        c.gridx = 0;
        backgroundScreen.add(TotalScore, c);
        c.gridy = 0;
        c.gridx = 1;
        backgroundScreen.add(sc, c);
        c.gridy = 1;
        c.gridx = 0;
        backgroundScreen.add(TotalTime, c);
        c.gridy = 1;
        c.gridx = 1;
        backgroundScreen.add(tm, c);
        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(100, 125, 0, 0);
        backgroundScreen.add(restartButton, c);


        endScreen.add(backgroundScreen);
        endScreen.setBackground(Color.black);
        endScreen.setPreferredSize(new Dimension(1280, 704));


        window.add(endScreen);
        window.setVisible(true);

        background.stop();
        background.setFile(6);
        background.play();
        background.loop();
    }

    private void setStartScreen() {
        try {
            myPicture = ImageIO.read(getClass().getResource("/vampire hunter.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(myPicture);

        picLabel = new JLabel(icon);
        picLabel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        icon = new ImageIcon("src/main/resources/start.png");
        JLabel startButton = new JLabel(icon);

        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                board = new Board();
                window.remove(startScreen);
                window.addKeyListener(board);
                window.add(board);
                window.repaint();
                window.revalidate();
            }
        });

        startButton.setBorder(BorderFactory.createEmptyBorder());
        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(200, 0, 0, 0);
        picLabel.add(startButton, c);

        startScreen = new JPanel();
        startScreen.add(picLabel);

        startScreen.setBackground(Color.black);
        startScreen.setPreferredSize(new Dimension(1280, 704));

        window = new JFrame("Dracula's Curse");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(startScreen);
        window.setBackground(Color.black);

        background.setFile(1);
        background.play();
        background.loop();
    }
}
