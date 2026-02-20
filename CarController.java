import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
public class CarController {
    private final int delay = 50;
    private Timer timer = new Timer(delay, new TimerListener());
    CarView frame;
    // Lista med alla bilar i simuleringen
    ArrayList<AbstractCar> cars = new ArrayList<>();
    // Workshop för ENDAST Volvo
    Workshop<Volvo240> volvo240Workshop = new Workshop<>(3);
    // Måste matcha DrawPanel workshop-position
    private final int workshopX = 300;
    private final int workshopY = 300;
    public static void main(String[] args) {
        CarController cc = new CarController();
// Skapa bilar
        cc.cars.add(new Volvo240());
        cc.cars.add(new Saab95());
        cc.cars.add(new Scania());
// Startpositioner
        cc.cars.get(0).x = 0;
        cc.cars.get(0).y = 300;
        cc.cars.get(1).x = 0;
        cc.cars.get(1).y = 100;
        cc.cars.get(2).x = 0;
        cc.cars.get(2).y = 200;
// Starta View
        cc.frame = new CarView("CarSim 1.0", cc);
// Koppla bilar till DrawPanel
        cc.frame.drawPanel.setCars(cc.cars);
// Starta timer
        cc.timer.start();
    }
    //TIMER
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Iterator<AbstractCar> iterator = cars.iterator();
            while (iterator.hasNext()) {
                AbstractCar car = iterator.next();
                car.move();
                handleWallCollision(car);
                handleWorkshopCollision(car, iterator);
            }
            frame.drawPanel.repaint();
        }
    }
    //VÄGGKOLLISION
    private void handleWallCollision(AbstractCar car) {
        if (car.getX() < 0 ||
                car.getX() > frame.drawPanel.getWidth() - 100) {
            car.stopEngine();
            car.turnLeft();
            car.turnLeft();
            car.startEngine();
        }
        if (car.getY() < 0 ||
                car.getY() > frame.drawPanel.getHeight() - 100) {
            car.stopEngine();
            car.turnLeft();
            car.turnLeft();
            car.startEngine();
        }
    }
    //WORKSHOP
    private void handleWorkshopCollision(AbstractCar car,
                                         Iterator<AbstractCar> iterator) {
        if (car instanceof Volvo240 volvo) {
            double dx = volvo.getX() - workshopX;
            double dy = volvo.getY() - workshopY;
            double distance = Math.hypot(dx, dy);
            if (distance < 80) { // lite större träffyta
                volvo.stopEngine();
                volvo240Workshop.addCar(volvo);
                iterator.remove(); // Ta bort från simuleringen
            }
        }
    }
    //KNAPP-METODER 
    void gas(int amount) {
        double gas = amount / 100.0;
        for (AbstractCar car : cars) {
            car.gas(gas);
        }
    }
    void brake(int amount) {
        double brake = amount / 100.0;
        for (AbstractCar car : cars) {
            car.brake(brake);
        }
    }
    void startAllCars() {
        for (AbstractCar car : cars) {
            car.startEngine();
        }
    }
    void stopAllCars() {
        for (AbstractCar car : cars) {
            car.stopEngine();
        }
    }
    void turboOn() {
        for (AbstractCar car : cars) {
            if (car instanceof Saab95 saab) {
                saab.setTurboOn();
            }
        }
    }
    void turboOff() {
        for (AbstractCar car : cars) {
            if (car instanceof Saab95 saab) {
                saab.setTurboOff();
            }
        }
    }
    void liftBed() {
        for (AbstractCar car : cars) {
            if (car instanceof Scania scania) {
                scania.flank(10);
            }
        }
    }
    void lowerBed() {
        for (AbstractCar car : cars) {
            if (car instanceof Scania scania) {
                scania.flank(-10);
            }
        }
    }
}
