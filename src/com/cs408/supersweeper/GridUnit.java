package com.cs408.supersweeper;

import java.awt.Graphics;
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
    public ArrayList<GridUnit> adjacentGridUnits = new ArrayList<GridUnit>();
    // TODO width and height should not be hardcoded, maybe set to the size of one of the images
    public int width = 16, height = 16;
    private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

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
    }

    /** Constructor */
    public GridUnit() {
    }

    public void draw(Graphics g) {
        BufferedImage image = null;

        if (isChecked) {
            // TODO replace grid_unit_empty with grid_unit_0
            if (isMined) {
                image = GridUnit.images.get("mine");
            } else if (adjacentMineCount() > 0) {
                image = GridUnit.images.get(String.valueOf(adjacentMineCount()));
            } else {
                image = GridUnit.images.get("empty");
            }
        } else {
            if (isFlagged) {
                image = GridUnit.images.get("flag");
            } else {
                if (isPressed) {
                    image = GridUnit.images.get("press");
                } else {
                    if (isHovered) {
                        image = GridUnit.images.get("hover");
                    } else {
                        image = GridUnit.images.get("normal");
                    }
                }
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    public int adjacentMineCount() {
        int count = 0;
        for (GridUnit unit : adjacentGridUnits) {
            if (unit.isMined) {
                count++;
            }
        }
        return count;
    }

    private void exposeMines() {
        if (isExposed) {
            return;
        }

        isExposed = true;
        if (isMined) {
            isChecked = true;
        }

        for (GridUnit unit : adjacentGridUnits) {
            if (!unit.isExposed) {
                unit.exposeMines();
            }
        }
    }

    private void check() {
        isChecked = true;

        if (adjacentMineCount() > 0) {
            return;
        }

        for (GridUnit unit : adjacentGridUnits) {
            if (!unit.isChecked) {
                unit.check();
            }
        }
    }

    public void checkPressed() {
        if (!isChecked && !isFlagged) {
            isPressed = true;
        }
    }

    public void checkReleased() {
        isPressed = false;
        if (!isFlagged) {
            if (isMined) {
                exposeMines();
            } else {
                check();
            }
        }
    }

    public void flagPressed() {
        // ignore
    }

    public void flagReleased() {
        if (!isChecked) {
            isFlagged = !isFlagged;
        }
    }
}
