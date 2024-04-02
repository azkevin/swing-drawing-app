package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Square implements Shape {

    private int x1;
    private int y1;
    private final int side;
    private final Color color;
    private final BasicStroke stroke;
    private Color fillColor;
    private boolean transparent;
    private final int group = 0;

    public Square(int x1, int y1, int side, Color color, BasicStroke stroke, Color fillColor,
            boolean transparent) {
        this.x1 = x1;
        this.y1 = y1;
        this.side = side;
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
        return x1 + side;
    }

    @Override
    public int getY2() {
        // Bottom-right corner for drawing
        return y1 + side;
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
    public boolean isPointInside(int xD, int yD) {
        return (xD >= this.x1 && xD <= (this.x1 + side) && yD >= this.y1 && yD <= (this.y1 + side));
    }

    @Override
    public void displace(int dx, int dy) {
        this.x1 += dx;
        this.y1 += dy;
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
            g.fillRect(x1, y1, side, side);
        }
        if (color != null) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawRect(x1, y1, side, side);
        }
    }
}