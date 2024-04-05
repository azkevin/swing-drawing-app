package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageShape implements MoveableElement {

    private int x1;
    private int y1;
    private final BufferedImage imageBuffered;
    private final int width;
    private final int height;
    private final int group = 0;

    // (x1, y1, selectedFile, width, height)
    public ImageShape(int x1, int y1, File selectedFile, int width, int height) {
        this.x1 = x1;
        this.y1 = y1;

        try {
            this.imageBuffered = ImageIO.read(selectedFile);
            if (this.imageBuffered != null) {
                this.width = (width <= 0) ? this.imageBuffered.getWidth() : width;
                this.height = (height <= 0) ? this.imageBuffered.getHeight() : height;
            } else {
                this.width = 0;
                this.height = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to load image from file.");
        }
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
    public int getGroup() {
        return group;
    }

    @Override
    public boolean isPointInside(int x, int y) {
        return (x >= x1 && x <= (x1 + width) && y >= y1 && y <= (y1 + height));
    }

    @Override
    public void displace(int dx, int dy) {
        this.x1 += dx;
        this.y1 += dy;
    }

    @Override
    public void draw(Graphics2D g) {
        if (imageBuffered != null) {
            g.drawImage(imageBuffered, x1, y1, width, height, null);
        }
    }
}
