package com.cs408.supersweeper;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.Canvas;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class GameFrame
{

   private JFrame frame;
   private  JPanel gamePanel;

   /**
    * Launch the application.
    */
   public static void main(String[] args)
   {
      EventQueue.invokeLater(new Runnable()
      {
         public void run()
         {
            try
            {
               GameFrame window = new GameFrame();
               window.frame.setVisible(true);
               GameState gs = new GameState(window.getGamePanel());
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      
      
   }

   /**
    * Create the application.
    */
   public GameFrame()
   {
      initialize();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize()
   {
      frame = new JFrame();
      frame.setBounds(100, 100, 450, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BorderLayout(0, 0));
      
      gamePanel = new JPanel();
      frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
      
      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
      
      JMenu mnFile = new JMenu("File");
      menuBar.add(mnFile);
      
      JMenuItem mntmNewGame = new JMenuItem("New Game");
      mnFile.add(mntmNewGame);
      
      JMenuItem mntmLoginlogout = new JMenuItem("Login/Logout");
      mnFile.add(mntmLoginlogout);
      
      JMenuItem mntmMainMenu = new JMenuItem("Main Menu");
      mnFile.add(mntmMainMenu);
      
      JMenuItem mntmExit = new JMenuItem("Exit");
      mnFile.add(mntmExit);
   }

   private JPanel getGamePanel() {
	   return this.gamePanel;
   }
 
}
