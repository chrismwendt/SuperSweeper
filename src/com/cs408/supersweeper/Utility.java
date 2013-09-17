package com.cs408.supersweeper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Utility {
    
    public static BufferedImage imageFromFilename(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static GridUnit[] flatten(GridUnit[][] objects) {
        int width = objects.length;
        if (width < 0) {
            return new GridUnit[] {};
        }

        int height = objects[0].length;
        if (height < 0) {
            return new GridUnit[] {};
        }

        GridUnit[] flattened = new GridUnit[objects.length * objects[0].length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flattened[y * width + x] = objects[x][y];
            }
        }

        return flattened;
    }
    
    public static void validateProperties(Properties _prop) {
        // this function makes sure the properties file specified is valid
        @SuppressWarnings("unused")
        double time = Double.parseDouble(_prop.getProperty("time"));
        int numMines = Integer.parseInt(_prop.getProperty("numMines"));
        int gridHeight = Integer.parseInt(_prop.getProperty("gridHeight"));
        int gridWidth = Integer.parseInt(_prop.getProperty("gridWidth"));
        int score = Integer.parseInt(_prop.getProperty("score"));

        // Throw error if either gridHeight or gridWidth <= 0
        if (gridWidth <= 0) {
            System.err.println("Invalid level properties file. GridWidth must be greater than zero.");
            System.exit(-1);
        } else if (gridHeight <= 0) {
            System.err.println("Invalid level properties file. GridHeight must be greater than zero.");
            System.exit(-1);
        }

        // Throw error if there's more bombs than grid units
        if (numMines >= gridHeight * gridWidth) {
            System.err.println("Invalid level properties file. Number of mines exceeds size of grid.");
            System.exit(-1);
        }
        
        //Throw error if there's an invalid score
        if(score < 0) {
            System.err.println("Invalid level properties file. Score is below zero.");
            System.exit(-1);
        }
    }
    
    public static void infoBox(String infoMessage, String location)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Game Over" + location, JOptionPane.INFORMATION_MESSAGE);
    }
}
