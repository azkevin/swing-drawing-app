package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle implements ClosedShape {

    protected int x1;
    protected int y1;
    protected final int width;
    protected final int height;
    protected final Color color;
    protected final BasicStroke stroke;
    protected Color fillColor;
    protected boolean transparent;
    protected final int group = 0;

    public Rectangle(int x1, int y1, int side, Color color, BasicStroke stroke, Color fillColor,
            boolean transparent) {
        this.x1 = x1;
        this.y1 = y1;
        width = side;
        height = side;
        this.color = color;
        this.stroke = stroke;
        this.fillColor = fillColor;
        this.transparent = transparent;
    }

    public Rectangle(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke, Color fillColor,
            boolean transparent) {
        this.x1 = x1;
        this.y1 = y1;
        width = Math.abs(x2 - x1);
        height = Math.abs(y2 - y1);
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
        return x1 + width;
    }

    @Override
    public int getY2() {
        return y1 + height;
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
        return (xD >= this.x1 && xD <= (this.x1 + width) && yD >= this.y1 && yD <= (this.y1 + height));
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
            g.fillRect(x1, y1, width, height);
        }
        if (color != null) {
            g.setColor(color);
            g.setStroke(stroke);
            g.drawRect(x1, y1, width, height);
        }
    }
}