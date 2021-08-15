package src;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.PixelGrabber;
import javax.imageio.ImageIO;

public class ImageFinder {

    public static void main(String[] args) throws Exception {
        ImageFinder a = new ImageFinder();
        System.out.println(a.checker());
    }

    public Pixel_Data checker() throws InterruptedException, IOException, AWTException {
        int lul = 0;
        int[] screenArray = null;
        int[] refArray = null;
        
        BufferedImage refPic;
        File file = new File("C:\\Users\\Serge\\Desktop\\Joel_Schissel\\Joel schissel\\Capture.PNG");
        refPic = ImageIO.read(file);

        BufferedImage screenShot;
        File file1 = new File("C:\\Users\\Serge\\Desktop\\Joel_Schissel\\Joel schissel\\Screenshots" + "jpg");
        Robot robot = new Robot();
        screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", file1);

        screenArray = new int[screenShot.getWidth() * screenShot.getHeight()];
        refArray = new int[refPic.getWidth() * refPic.getHeight()];
        List<Pixel_Data> screenObject = new ArrayList<>();
        List<Pixel_Data> refObject = new ArrayList<>();

        PixelGrabber a = new PixelGrabber(screenShot, 0, 0, screenShot.getWidth(), screenShot.getHeight(), screenArray,
                0, screenShot.getWidth());

        if (a.grabPixels(0))
            ;

        PixelGrabber b = new PixelGrabber(refPic, 0, 0, refPic.getWidth(), refPic.getHeight(), refArray, 0,
                refPic.getWidth());

        if (b.grabPixels(0))
            ;

        screenObject = pixler(screenArray, screenShot.getWidth(), screenShot.getHeight());
        refObject = pixler(refArray, refPic.getWidth(), refPic.getHeight());

        for (int x = 0; x < screenObject.size(); x++) {

            int fehlerInt = 0;
            int refX = 0;
            int yOffSet = 0;
            int count = 0;

            while (fehlerInt == 0) {
                fehlerInt += screenObject.get(x + (yOffSet * (screenShot.getWidth() - refPic.getWidth())))
                        .compareTo(refObject.get(refX));
                // Test var
                lul += screenObject.get(x + (yOffSet * (screenShot.getWidth() - refPic.getWidth())))
                        .compareTo(refObject.get(refX));

                refX++;
                if (refX % refPic.getWidth() == 0) {
                    yOffSet++;
                }
                count++;

                if (count == refObject.size() - 1) {
                    System.out.println("true ...");
                    return screenObject.get(x + (yOffSet * (screenShot.getWidth() - refPic.getWidth())));
                }
            }
        }
        System.out.println("false ..." + lul);

        return null;
    }

    public List<Pixel_Data> pixler(int[] a, int w, int h) {
        List<Pixel_Data> k = new ArrayList<>();
        int i = 0;
        int t = 0;
        for (int pixel : a) {
            k.add(new Pixel_Data(i % w, t, pixel));
            i++;
            if (i % w == 0) {
                t++;
            }
        }
        return k;
    }
}
