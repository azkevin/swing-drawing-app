package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle implements Shape {

    private final int x;
    private final int y;
    private final int radius;
    private final Color color;
    private final BasicStroke stroke;
    private final Color fillColor;
    private final boolean transparent;
    private final int group = 0;

    public Circle(int x, int y, int radius, Color color, BasicStroke stroke, Color fillColor, boolean transparent) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.stroke = stroke;
        this.fillColor = fillColor;
        this.transparent = transparent;
    }

    @Override
    public int getX1() {
        return x - radius; // Top-left corner for drawing
    }

    @Override
    public int getY1() {
        return y - radius;
    }

    @Override
    public int getX2() {
        return x + radius; // Bottom-right corner for drawing
    }

    @Override
    public int getY2() {
        return y + radius;
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
        if (fillColor != null && !transparent) {
            g.setColor(fillColor);
            g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
        if (color != null) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
        }
    }
}
