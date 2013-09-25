package com.cs408.supersweeper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GridUnit {


    public boolean isMined = false;
    public boolean isChecked = false;
    public boolean isFlagged = false;
    public boolean isPressed = false;
    public boolean isHovered = false;
    public boolean isExposed = false;
    public boolean isExploded = false;
    public boolean isResetable = false;
    public ArrayList<GridUnit> adjacentGridUnits = new ArrayList<GridUnit>();
    private BufferedImage image;
    static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
    public static BufferedImage sample;

    static {
        GridUnit.images.put("normal", Utility.imageFromFilename("images/grid_unit.png"));
        GridUnit.images.put("empty", Utility.imageFromFilename("images/grid_unit_empty.png"));
        GridUnit.images.put("hover", Utility.imageFromFilename("images/grid_unit_hover.png"));
        GridUnit.images.put("press", Utility.imageFromFilename("images/grid_unit_click.png"));
        GridUnit.images.put("mine", Utility.imageFromFilename("images/grid_unit_mine.png"));
        GridUnit.images.put("flag", Utility.imageFromFilename("images/grid_unit_flag.png"));
        for (int i = 1; i <= 8; i++) {
            GridUnit.images.put(String.valueOf(i), Utility.imageFromFilename("images/grid_unit_" + i + ".png"));
        }
        GridUnit.sample = GridUnit.images.get("normal");
    }

    /** Constructor */
    public GridUnit() {
        image = GridUnit.images.get("normal");
    }
    
    /** Getters */
    public int adjacentMineCount() {
        int count = 0;
        for (GridUnit unit : adjacentGridUnits) {
            if (unit.isMined) {
                count++;
            }
        }
        return count;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    /** Setters */
    public void setImage(BufferedImage newImage) {
        image = newImage;
    }

    public void resetUnit() {
        isChecked = false;
        isPressed = false;
        isHovered = false;
        isExposed = false;
        if(!isFlagged)
            image = GridUnit.images.get("mine");
    }
    
    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean isMined) {
        this.isMined = isMined;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean isExposed) {
        this.isExposed = isExposed;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean isExploded) {
        this.isExploded = isExploded;
    }

    public boolean isResetable() {
        return isResetable;
    }

    public void setResetable(boolean isResetable) {
        this.isResetable = isResetable;
    }
}
