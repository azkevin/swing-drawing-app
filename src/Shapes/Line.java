package Shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Line implements Shape, Cloneable {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private final Color color;
    private final BasicStroke stroke;
    private final int group = 0;

    public Line(int x1, int y1, int x2, int y2, Color color, BasicStroke stroke) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.stroke = stroke;
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
        float m = (float) (this.y2 - this.y1) / (this.x2 - this.x1);
        float c = this.y1 - m * this.x1;
        return yD == m * xD + c;
    }

    @Override
    public void displace(int dx, int dy) {
        this.x1 += dx;
        this.x2 += dx;
        this.y1 += dy;
        this.y2 += dy;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(stroke);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public Line clone() {
        try {
            return (Line) super.clone();
        } catch (CloneNotSupportedException e) {
            // Should never happen because Line implements Cloneable
            throw new InternalError(e);
        }
    }
}
