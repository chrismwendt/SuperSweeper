package com.cs408.supersweeper;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Powerup extends JButton {


	private static final long serialVersionUID = 1L;
	String name;
	int price;

	
	/** Constructor */
	   public Powerup(String name, int price)
	   {
		   super(name + ":\n" + price + " points");
		   this.name = name;
		   this.price = price;
	   }
	   
	   /** Getters */	   
	   public int getPrice()
	   {
		   return price;
	   }
	   
	   public String getName()
	   {
		   return name;
	   }
	   
}
