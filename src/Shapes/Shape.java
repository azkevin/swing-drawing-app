package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public interface Shape {
    int getX1();

    int getY1();

    int getX2(); // optional for shapes without width/height

    int getY2(); // optional for shapes without width/height

    Color getColor();

    BasicStroke getStroke();

    int getGroup();

    boolean isPointInside(int xD, int yD);

    void displace(int dx, int dy);

    void draw(Graphics2D g); // Let each shape implementation define how to draw itself
}
