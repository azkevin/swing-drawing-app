package Model;

public interface MoveableElement extends MyElement {
    boolean isPointInside(int xD, int yD);

    void displace(int dx, int dy);
}
