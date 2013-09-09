package com.cs408.supersweeper;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameFrame implements ActionListener {
    private JFrame frame;
    private JMenuItem mntmNewGame;
    private JMenuItem mntmLoginlogout;
    private JMenuItem mntmMainMenu;
    private JMenuItem mntmExit;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameFrame window = new GameFrame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * 
     * @throws IOException
     */
    public GameFrame() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @throws IOException
     */
    private void initialize() throws IOException {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel statusLabel = new JLabel();
        c.gridy = 0;
        frame.getContentPane().add(statusLabel, c);

        GamePanel gp = new GamePanel("001.properties", statusLabel); // load first level right away...
        c.gridy = 1;
        frame.getContentPane().add(gp, c);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmNewGame = new JMenuItem("New Game");
        mnFile.add(mntmNewGame);
        mntmNewGame.addActionListener(this);

        mntmLoginlogout = new JMenuItem("Login/Logout");
        mnFile.add(mntmLoginlogout);
        mntmLoginlogout.addActionListener(this);

        mntmMainMenu = new JMenuItem("Main Menu");
        mnFile.add(mntmMainMenu);
        mntmMainMenu.addActionListener(this);

        mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);
        mntmExit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();

        // TODO: add implementation to menu items
        if (action == mntmNewGame) {

        } else if (action == mntmLoginlogout) {

        } else if (action == mntmMainMenu) {

        } else if (action == mntmExit) {
            frame.dispose();
            System.exit(0);
        }
    }
}
