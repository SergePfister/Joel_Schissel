package src;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.PixelGrabber;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class ImageFinder {

    public static void main(String[] args) throws Exception {
        ImageFinder a = new ImageFinder();

        a.Runner(a.checker());
    }

    public void Runner(Pixel_Data data) throws Exception{
        if ( data==null){
            System.out.println("Bild nicht gefunden");
        }else{
            klick(data.x, data.y);
            System.out.println(data);
        }
    }

    public Pixel_Data checker() throws InterruptedException, IOException, AWTException {
        int[] screenArray = null;
        int[] refArray = null;

        BufferedImage refPic;
        Path file = Paths.get("Joel schissel/Images/Capture.PNG");
        refPic = ImageIO.read(file.toFile());

        BufferedImage screenShot;
        Path file1 = Paths.get("Joel schissel/Images/Screenshots.jpg");
        Robot robot = new Robot();
        screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(screenShot, "JPG", file1.toFile());

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

        for (int x = 0; x < screenObject.size()-(screenShot.getWidth()*(refPic.getHeight()-1)); x++) {

            int fehlerInt = 0;
            int refX = 0;
            int yOffSet = 0;
            int count = 0;

            while (fehlerInt <= 100) {
                fehlerInt += screenObject.get(x + refX + (yOffSet * (screenShot.getWidth() - refPic.getWidth())))
                        .compareTo(refObject.get(refX));              
                refX++;
                if (refX % refPic.getWidth() == 0) {
                    yOffSet++;
                }
                count++;
                if (count >= refObject.size()-1) {
                    System.out.println("true ...");
                    screenObject.get(x).setX(screenObject.get(x).x+(refPic.getWidth()/2));
                    screenObject.get(x).setY(screenObject.get(x).y+(refPic.getHeight()/2));
                    return screenObject.get(x);
                }
            }
        }
        return null;
    }
    public void klick(int xPosition,int yPosition)throws Exception{

        int x = xPosition;
        int y = yPosition;
        int mask = InputEvent.BUTTON1_DOWN_MASK;
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(mask);     
        bot.mouseRelease(mask);        
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
