import java.time.LocalDateTime;
import java.util.*;

public class HarbourManager {
    private Map<String, Ship> ships;
    private Map<String, Dock> docks;
    private Queue<Ship> waitingQueue;

    public HarbourManager() {
        this.ships = new HashMap<>();
        this.docks = new HashMap<>();
        this.waitingQueue = new LinkedList<>();
        initializeDocks();
    }

    private void initializeDocks() {
        docks.put("D1", new Dock("D1", "Dock 1", 300, 15));
        docks.put("D2", new Dock("D2", "Dock 2", 350, 18));
        docks.put("D3", new Dock("D3", "Dock 3", 400, 20));
        docks.put("D4", new Dock("D4", "Dock 4", 280, 14));
    }

    public void registerShip(String shipId, String shipName, String shipType, int capacity) {
        if (ships.containsKey(shipId)) {
            System.out.println("Ship already registered!");
            return;
        }
        ships.put(shipId, new Ship(shipId, shipName, shipType, capacity));
        System.out.println("Ship " + shipName + " registered successfully!");
    }

    public boolean dockShip(String shipId) throws Exception {
        if (!ships.containsKey(shipId)) {
            throw new Exception("Ship not found!");
        }
        Ship ship = ships.get(shipId);
        
        for (Dock dock : docks.values()) {
            if (!dock.isOccupied()) {
                dock.allocate(ship);
                ship.setStatus("DOCKED");
                ship.setAssignedDock(dock.getDockId());
                ship.setArrivalTime(LocalDateTime.now());
                System.out.println("Ship " + ship.getShipName() + " docked at " + dock.getDockName());
                return true;
            }
        }
        waitingQueue.add(ship);
        System.out.println("No available dock. Ship " + ship.getShipName() + " added to waiting queue.");
        return false;
    }

    public void undockShip(String shipId) throws Exception {
        if (!ships.containsKey(shipId)) {
            throw new Exception("Ship not found!");
        }
        Ship ship = ships.get(shipId);
        String dockId = ship.getAssignedDock();
        
        if (dockId == null || !docks.containsKey(dockId)) {
            throw new Exception("Ship is not docked!");
        }
        
        Dock dock = docks.get(dockId);
        ship.unloadCargo();
        dock.deallocate();
        ship.setStatus("DEPARTED");
        ship.setDepartureTime(LocalDateTime.now());
        ship.setAssignedDock(null);
        
        System.out.println("Ship " + ship.getShipName() + " undocked from " + dock.getDockName());
        
        if (!waitingQueue.isEmpty()) {
            Ship nextShip = waitingQueue.poll();
            dockShip(nextShip.getShipId());
        }
    }

    public void loadCargo(String shipId, String cargoName, int weight) throws Exception {
        if (!ships.containsKey(shipId)) {
            throw new Exception("Ship not found!");
        }
        Ship ship = ships.get(shipId);
        ship.loadCargo(cargoName, weight);
        System.out.println("Cargo loaded: " + cargoName + " (" + weight + "kg)");
    }

    public void viewShip(String shipId) throws Exception {
        if (!ships.containsKey(shipId)) {
            throw new Exception("Ship not found!");
        }
        Ship ship = ships.get(shipId);
        System.out.println("\n--- Ship Details ---");
        System.out.println(ship);
        System.out.println("Cargo: " + ship.getCargoList());
        if (ship.getArrivalTime() != null) {
            System.out.println("Arrival: " + ship.getArrivalTime());
        }
        if (ship.getDepartureTime() != null) {
            System.out.println("Departure: " + ship.getDepartureTime());
        }
        System.out.println();
    }

    public void viewAllShips() {
        System.out.println("\n--- All Ships ---");
        if (ships.isEmpty()) {
            System.out.println("No ships registered.");
        } else {
            ships.values().forEach(System.out::println);
        }
        System.out.println();
    }

    public void viewDocks() {
        System.out.println("\n--- Dock Status ---");
        docks.values().forEach(System.out::println);
        System.out.println();
    }

    public void viewWaitingQueue() {
        System.out.println("\n--- Waiting Queue ---");
        if (waitingQueue.isEmpty()) {
            System.out.println("No ships waiting.");
        } else {
            waitingQueue.forEach(ship -> System.out.println(ship.getShipName()));
        }
        System.out.println();
    }
}
