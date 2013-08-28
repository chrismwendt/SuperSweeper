package com.cs408.supersweeper;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class GameFrame
{

   private JFrame frame;

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
 * @throws IOException 
    */
   public GameFrame() throws IOException
   {
      initialize();
   }

   /**
    * Initialize the contents of the frame.
 * @throws IOException 
    */
   private void initialize() throws IOException
   {
      frame = new JFrame();
      frame.setBounds(100, 100, 450, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BorderLayout(0, 0));
      
      GamePanel gp = new GamePanel("001.properties"); //load first level right away...
      frame.getContentPane().add(gp, BorderLayout.CENTER);
      
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
 
}
