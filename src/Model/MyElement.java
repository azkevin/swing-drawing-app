package Model;

import java.awt.Graphics2D;

public interface MyElement {
    int getX1();

    int getY1();

    int getX2();

    int getY2();

    int getGroup();

    void draw(Graphics2D g); // Let each shape implementation define how to draw itself
}
