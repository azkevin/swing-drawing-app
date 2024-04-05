package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Triangle implements ClosedShape {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x3;
    private int y3;
    private final Color color;
    private final BasicStroke stroke;
    private Color fillColor;
    private boolean transparent;
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
        return x2;
    }

    @Override
    public int getY2() {
        return y2;
    }

    public int getX3() {
        return x3;
    }

    public int getY3() {
        return y3;
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

    private double area(int x1, int y1, int x2, int y2,
            int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) +
                x3 * (y1 - y2)) / 2.0);
    }

    @Override
    public boolean isPointInside(int xD, int yD) {
        double A = area(this.x1, this.y1, this.x2, this.y2, this.x3, this.y3);

        double A1 = area(xD, yD, this.x2, this.y2, this.x3, this.y3);

        double A2 = area(this.x1, this.y1, xD, yD, this.x3, this.y3);

        double A3 = area(this.x1, this.y1, this.x2, this.y2, xD, yD);

        return (A == A1 + A2 + A3);
    }

    @Override
    public void displace(int dx, int dy) {
        this.x1 += dx;
        this.x2 += dx;
        this.x3 += dx;
        this.y1 += dy;
        this.y2 += dy;
        this.y3 += dy;
    }

    @Override
    public void fill(Color fillColor) {
        this.fillColor = fillColor;
        this.transparent = false;
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