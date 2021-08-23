package src;
public class Pixel_Data implements Comparable<Pixel_Data> {
    private int x;
    private int y;
    private int pixel;

    public Pixel_Data(int x, int y, int pixel) {
        this.x = x;
        this.y = y;
        this.pixel = pixel;
    }
    @Override
    public String toString() {

        return "X-Cord = "+x+" / Y-Cord = "+y + " / Pixel Wert = "+ pixel;
    }
    

    @Override
    public int compareTo(Pixel_Data o) {
        if ( o.pixel == pixel) {
            return 0;
        } else {
            return 1;
        }
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
}
