package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle implements Shape {

    private int x;
    private int y;
    private final int radius;
    private final Color color;
    private final BasicStroke stroke;
    private Color fillColor;
    private boolean transparent;
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
    public Color getFillColor() {
        if (transparent)
            return null;
        return fillColor;
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
    public boolean isPointInside(int xD, int yD) {
        if ((Math.sqrt((xD - (this.x + this.radius)) * (xD - (this.x + this.radius))
                + (yD - (this.y + this.radius)) * (yD - (this.y + this.radius)))) < this.radius) {
            return true;
        }
        return false;
    }

    @Override
    public void displace(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public void fill(Color fillColor) {
        this.fillColor = fillColor;
        this.transparent = false;
    }

    @Override
    public void draw(Graphics2D g) {
        if (fillColor != null && !transparent) {
            g.setColor(fillColor);
            g.fillOval(x, y, 2 * radius, 2 * radius);
        }
        if (color != null) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawOval(x, y, 2 * radius, 2 * radius);
        }
    }
}
