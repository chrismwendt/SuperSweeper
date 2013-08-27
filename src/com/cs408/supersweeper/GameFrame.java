package com.cs408.supersweeper;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.Canvas;

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
      
      JPanel panel = new JPanel();
      frame.getContentPane().add(panel, BorderLayout.CENTER);
      
      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
   }

}
