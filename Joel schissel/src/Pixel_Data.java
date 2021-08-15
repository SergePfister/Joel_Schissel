package src;
public class Pixel_Data implements Comparable<Pixel_Data> {
    public int x;
    public int y;
    public int pixel;

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
            System.out.println("yes");
            return 0;
        } else {
            return 1;
        }
    }
    
}
