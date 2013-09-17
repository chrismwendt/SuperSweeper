package com.cs408.supersweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameFrame implements ActionListener {

    private JFrame frame;
    private JMenuItem mntmRestartLevel;
    private JMenuItem mntmLevelSelect;
    private JMenuItem mntmExit;
    private JLabel lblScore;
    
    private LevelSelectPanel lsp;
    private GamePanel gp;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new GameFrame();

    }

    /**
     * Create the application.
     * 
     * @throws IOException
     */
    public GameFrame() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @throws IOException
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("SuperSweeper!");
        frame.setIconImage(GridUnit.images.get("mine"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        
        lsp = new LevelSelectPanel(this);
        frame.getContentPane().add(lsp, c);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        menuBar.setLayout(new BorderLayout());
        
        JMenu mnOptions = new JMenu("Options");
        menuBar.add(mnOptions, BorderLayout.WEST);
        
        mntmLevelSelect = new JMenuItem("Level Select");
        mnOptions.add(mntmLevelSelect);
        mntmLevelSelect.addActionListener(this);

        mntmRestartLevel = new JMenuItem("RestartLevel");
        mnOptions.add(mntmRestartLevel);
        mntmRestartLevel.addActionListener(this);

        mntmExit = new JMenuItem("Exit");
        mnOptions.add(mntmExit);
        
        lblScore = new JLabel("Score: 0 ");
        lblScore.setHorizontalAlignment(SwingConstants.RIGHT);
        menuBar.add(lblScore, BorderLayout.EAST);
        mntmExit.addActionListener(this);

        //Resize
        resizeFrame();
        
        //Move to center of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();

        if (action == mntmRestartLevel) {
            if(gp != null) {
                gp.restartLevel();
            }

        }  else if (action == mntmLevelSelect) {
            gotoLevelSelect();

        } else if (action == mntmExit) {
            frame.dispose();
            System.exit(0);
        }
    }

    public void startLevel(String propertiesFile) {  
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        
        //Remove levelSelectPanel
        frame.remove(lsp);
        
        //Add appropriate GamePanel
        gp = new GamePanel(propertiesFile);
        frame.getContentPane().add(gp, c);
        
        resizeFrame();
    }
    
    public void gotoLevelSelect(){
        if(gp == null) {
            return;
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        
        //Remove gamePanel
        frame.remove(gp);
        
        //Add new LevelSelectPanel
        frame.getContentPane().add(lsp, c); 

        resizeFrame();
    }
    
    /** Internal Methods */
    private void resizeFrame() {
        //Tried using setMax/Min Size but SetMaximumSize is broken (known Bug)
        //So i just made it not resizeable >.>
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
