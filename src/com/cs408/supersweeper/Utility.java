package com.cs408.supersweeper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
                flattened[y * width + x] = objects[y][x];
            }
        }

        return flattened;
    }
}
