package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Triangle implements Shape {

    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int x3;
    private final int y3;
    private final Color color;
    private final BasicStroke stroke;
    private final Color fillColor;
    private final boolean transparent;
    private final int group = 0;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color, BasicStroke stroke, Color fillColor,
            boolean transparent) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.color = color;
        this.stroke = stroke;
        this.fillColor = fillColor;
        this.transparent = transparent;
    }

    @Override
    public int getX1() {
        return x1;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public int getX2() {
        // Bottom-right corner for drawing
        return x2;
    }

    @Override
    public int getY2() {
        // Bottom-right corner for drawing
        return y2;
    }

    public int getX3() {
        // Bottom-right corner for drawing
        return x3;
    }

    public int getY3() {
        // Bottom-right corner for drawing
        return y3;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public BasicStroke getStroke() {
        return stroke;
    }

    @Override
    public int getGroup() {
        return group;
    }

    @Override
    public void draw(Graphics2D g) {
        int xPoints[] = { x1, x2, x3 };
        int yPoints[] = { y1, y2, y3 };
        if (fillColor != null && !transparent) {
            g.setColor(fillColor);
            g.fillPolygon(xPoints, yPoints, 3);
        }
        if (color != null) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
}