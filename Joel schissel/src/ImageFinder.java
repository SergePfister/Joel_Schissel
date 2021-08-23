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

    // Überprüft ob das refPic im Screenshot enthalten ist
    public Pixel_Data checker(String path, int inaccuracy) throws InterruptedException, IOException, AWTException {

        // Laden des Referenz Bildes
        BufferedImage refPic;
        Path file = Paths.get(path);
        refPic = ImageIO.read(file.toFile());
        // Erzeugen des Screenshot zum vergleichen
        BufferedImage screenShot;
        Robot robot = new Robot();
        screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

        // Speichert den gemachten Screenshot ab
        // Path file1 = Paths.get("Joel schissel/Images/Screenshots.jpg");
        // ImageIO.write(screenShot, "JPG", file1.toFile());

        // Erzeugen un Fülleen der Arrays mit Pixel daten
        int[] screenArray = null;
        int[] refArray = null;
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

        // Erzeugen der Pixel_Data Objekte
        screenObject = pixler(screenArray, screenShot.getWidth(), screenShot.getHeight());
        refObject = pixler(refArray, refPic.getWidth(), refPic.getHeight());

        // inaccuracy in Prozent des RefPic Dumme Idee viel zu grosse Zahl Aufwand wird viel zugross
        //int fehlerInProzent = (refObject.size() / 100) * inaccuracy;


        // Start der Logik um das RefPic im Screenshot zu suchen und den Mittelpunkt des
        // RefPics fals geefunden zu returnen
        for (int x = 0; x < screenObject.size() - (screenShot.getWidth() * (refPic.getHeight() - 1)); x++) {

            int fehlerInt = 0;
            int refX = 0;
            int yOffSet = 0;
            int count = 0;

            while (fehlerInt <= inaccuracy) {
                fehlerInt += screenObject.get(x + refX + (yOffSet * (screenShot.getWidth() - refPic.getWidth())))
                        .compareTo(refObject.get(refX));
                refX++;
                if (refX % refPic.getWidth() == 0) {
                    yOffSet++;
                }
                count++;
                if (count >= refObject.size() - 1) {
                    screenObject.get(x).setX(screenObject.get(x).getX() + (refPic.getWidth() / 2));
                    screenObject.get(x).setY(screenObject.get(x).getY() + (refPic.getHeight() / 2));
                    return screenObject.get(x);
                }
            }
        }
        return null;
    }

    public Boolean klick(String path, int inaccuracy) throws Exception {
        Pixel_Data a = this.checker(path, inaccuracy);
        if (!(a == null)) {
            int x = a.getX();
            int y = a.getY();
            int mask = InputEvent.BUTTON1_DOWN_MASK;
            Robot bot = new Robot();
            bot.mouseMove(x, y);
            bot.mousePress(mask);
            bot.mouseRelease(mask);
            return true;
        } else {
            return false;
        }
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
