import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
public class DrawPanel extends JPanel {
    private ArrayList<AbstractCar> cars;
    private BufferedImage volvoImage;
    private BufferedImage saabImage;
    private BufferedImage scaniaImage;
    private BufferedImage workshopImage;
    private final Point workshopPoint = new Point(300, 300);
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        try {
            volvoImage = ImageIO.read(
                    DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            saabImage = ImageIO.read(
                    DrawPanel.class.getResourceAsStream("pics/Saab95.jpg"));
            scaniaImage = ImageIO.read(
                    DrawPanel.class.getResourceAsStream("pics/Scania.jpg"));
            workshopImage = ImageIO.read(
                    DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setCars(ArrayList<AbstractCar> cars) {
        this.cars = cars;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cars != null) {
            for (AbstractCar car : cars) {
                BufferedImage image = null;
                switch (car.getModelName()) {
                    case "Volvo240" -> image = volvoImage;
                    case "Saab95" -> image = saabImage;
                    case "Scania" -> image = scaniaImage;
                }
                if (image != null) {
                    g.drawImage(image,
                            (int) car.getX(),
                            (int) car.getY(),
                            null);
                }
            }
        }
// Rita workshop
        g.drawImage(workshopImage,
                workshopPoint.x,
                workshopPoint.y,
                null);
    }
}
