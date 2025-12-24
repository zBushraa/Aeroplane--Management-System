import java.time.LocalDateTime;
import java.util.*;

class Ship {
    private String shipId;
    private String shipName;
    private String shipType;
    private int capacity;
    private int currentCargo;
    private String status;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String assignedDock;
    private List<String> cargoList;

    public Ship(String shipId, String shipName, String shipType, int capacity) {
        this.shipId = shipId;
        this.shipName = shipName;
        this.shipType = shipType;
        this.capacity = capacity;
        this.currentCargo = 0;
        this.status = "AT_SEA";
        this.cargoList = new ArrayList<>();
    }

    public void loadCargo(String cargoName, int weight) throws Exception {
        if (currentCargo + weight > capacity) {
            throw new Exception("Cargo capacity exceeded!");
        }
        cargoList.add(cargoName + " (" + weight + "kg)");
        currentCargo += weight;
    }

    public void unloadCargo() {
        cargoList.clear();
        currentCargo = 0;
    }

    public String getShipId() { return shipId; }
    public String getShipName() { return shipName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAssignedDock() { return assignedDock; }
    public void setAssignedDock(String dockId) { this.assignedDock = dockId; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime time) { this.arrivalTime = time; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime time) { this.departureTime = time; }
    public int getCurrentCargo() { return currentCargo; }
    public int getCapacity() { return capacity; }
    public List<String> getCargoList() { return new ArrayList<>(cargoList); }

    @Override
    public String toString() {
        return String.format("Ship{id='%s', name='%s', type='%s', status='%s', dock='%s', cargo=%d/%d}", 
            shipId, shipName, shipType, status, assignedDock, currentCargo, capacity);
    }
}

class Plane {
    private String planeId;
    private String planeName;
    private String airline;
    private int capacity;
    private int passengers;
    private String status;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String assignedBay;
    private List<String> cargoList;

    public Plane(String planeId, String planeName, String airline, int capacity) {
        this.planeId = planeId;
        this.planeName = planeName;
        this.airline = airline;
        this.capacity = capacity;
        this.passengers = 0;
        this.status = "IN_AIR";
        this.cargoList = new ArrayList<>();
    }

    public void boardPassengers(int count) throws Exception {
        if (passengers + count > capacity) {
            throw new Exception("Passenger capacity exceeded!");
        }
        passengers += count;
    }

    public void deboardPassengers(int count) throws Exception {
        if (passengers - count < 0) {
            throw new Exception("Cannot deboard more passengers than onboard!");
        }
        passengers -= count;
    }

    public void loadCargo(String cargoName) {
        cargoList.add(cargoName);
    }

    public void unloadCargo() {
        cargoList.clear();
    }

    public String getPlaneId() { return planeId; }
    public String getPlaneName() { return planeName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAssignedBay() { return assignedBay; }
    public void setAssignedBay(String bayId) { this.assignedBay = bayId; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime time) { this.arrivalTime = time; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime time) { this.departureTime = time; }
    public int getPassengers() { return passengers; }
    public int getCapacity() { return capacity; }
    public List<String> getCargoList() { return new ArrayList<>(cargoList); }

    @Override
    public String toString() {
        return String.format("Plane{id='%s', name='%s', airline='%s', status='%s', bay='%s', passengers=%d/%d}", 
            planeId, planeName, airline, status, assignedBay, passengers, capacity);
    }
}

class Dock {
    private String dockId;
    private String dockName;
    private int length;
    private int depth;
    private boolean isOccupied;
    private Ship occupyingShip;
    private LocalDateTime occupiedSince;

    public Dock(String dockId, String dockName, int length, int depth) {
        this.dockId = dockId;
        this.dockName = dockName;
        this.length = length;
        this.depth = depth;
        this.isOccupied = false;
        this.occupyingShip = null;
    }

    public boolean allocate(Ship ship) throws Exception {
        if (isOccupied) {
            throw new Exception("Dock " + dockId + " is already occupied!");
        }
        this.occupyingShip = ship;
        this.isOccupied = true;
        this.occupiedSince = LocalDateTime.now();
        return true;
    }

    public void deallocate() {
        this.occupyingShip = null;
        this.isOccupied = false;
        this.occupiedSince = null;
    }

    public String getDockId() { return dockId; }
    public String getDockName() { return dockName; }
    public boolean isOccupied() { return isOccupied; }
    public Ship getOccupyingShip() { return occupyingShip; }
    public LocalDateTime getOccupiedSince() { return occupiedSince; }
    public int getLength() { return length; }
    public int getDepth() { return depth; }

    @Override
    public String toString() {
        String shipInfo = isOccupied ? occupyingShip.getShipName() : "VACANT";
        return String.format("Dock{id='%s', name='%s', status='%s', ship='%s'}", 
            dockId, dockName, isOccupied ? "OCCUPIED" : "VACANT", shipInfo);
    }
}

class Bay {
    private String bayId;
    private String bayName;
    private String type;
    private boolean isOccupied;
    private Plane occupyingPlane;
    private LocalDateTime occupiedSince;

    public Bay(String bayId, String bayName, String type) {
        this.bayId = bayId;
        this.bayName = bayName;
        this.type = type;
        this.isOccupied = false;
        this.occupyingPlane = null;
    }

    public boolean allocate(Plane plane) throws Exception {
        if (isOccupied) {
            throw new Exception("Bay " + bayId + " is already occupied!");
        }
        this.occupyingPlane = plane;
        this.isOccupied = true;
        this.occupiedSince = LocalDateTime.now();
        return true;
    }

    public void deallocate() {
        this.occupyingPlane = null;
        this.isOccupied = false;
        this.occupiedSince = null;
    }

    public String getBayId() { return bayId; }
    public String getBayName() { return bayName; }
    public String getType() { return type; }
    public boolean isOccupied() { return isOccupied; }
    public Plane getOccupyingPlane() { return occupyingPlane; }
    public LocalDateTime getOccupiedSince() { return occupiedSince; }

    @Override
    public String toString() {
        String planeInfo = isOccupied ? occupyingPlane.getPlaneName() : "VACANT";
        return String.format("Bay{id='%s', name='%s', type='%s', status='%s', plane='%s'}", 
            bayId, bayName, type, isOccupied ? "OCCUPIED" : "VACANT", planeInfo);
    }
}

class HarbourManager {
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

class AirportManager {
    private Map<String, Plane> planes;
    private Map<String, Bay> bays;
    private Queue<Plane> waitingQueue;

    public AirportManager() {
        this.planes = new HashMap<>();
        this.bays = new HashMap<>();
        this.waitingQueue = new LinkedList<>();
        initializeBays();
    }

    private void initializeBays() {
        bays.put("B1", new Bay("B1", "Bay 1", "International"));
        bays.put("B2", new Bay("B2", "Bay 2", "Domestic"));
        bays.put("B3", new Bay("B3", "Bay 3", "Cargo"));
        bays.put("B4", new Bay("B4", "Bay 4", "International"));
        bays.put("B5", new Bay("B5", "Bay 5", "Domestic"));
    }

    public void registerPlane(String planeId, String planeName, String airline, int capacity) {
        if (planes.containsKey(planeId)) {
            System.out.println("Plane already registered!");
            return;
        }
        planes.put(planeId, new Plane(planeId, planeName, airline, capacity));
        System.out.println("Plane " + planeName + " registered successfully!");
    }

    public boolean parkPlane(String planeId) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        
        for (Bay bay : bays.values()) {
            if (!bay.isOccupied()) {
                bay.allocate(plane);
                plane.setStatus("PARKED");
                plane.setAssignedBay(bay.getBayId());
                plane.setArrivalTime(LocalDateTime.now());
                System.out.println("Plane " + plane.getPlaneName() + " parked at " + bay.getBayName());
                return true;
            }
        }
        waitingQueue.add(plane);
        System.out.println("No available bay. Plane " + plane.getPlaneName() + " added to waiting queue.");
        return false;
    }

    public void departurePlane(String planeId) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        String bayId = plane.getAssignedBay();
        
        if (bayId == null || !bays.containsKey(bayId)) {
            throw new Exception("Plane is not parked!");
        }
        
        Bay bay = bays.get(bayId);
        plane.unloadCargo();
        bay.deallocate();
        plane.setStatus("IN_AIR");
        plane.setDepartureTime(LocalDateTime.now());
        plane.setAssignedBay(null);
        
        System.out.println("Plane " + plane.getPlaneName() + " departed from " + bay.getBayName());
        
        if (!waitingQueue.isEmpty()) {
            Plane nextPlane = waitingQueue.poll();
            parkPlane(nextPlane.getPlaneId());
        }
    }

    public void boardPassengers(String planeId, int count) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        plane.boardPassengers(count);
        System.out.println(count + " passengers boarded. Total: " + plane.getPassengers() + "/" + plane.getCapacity());
    }

    public void deboardPassengers(String planeId, int count) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        plane.deboardPassengers(count);
        System.out.println(count + " passengers deboarded. Total: " + plane.getPassengers() + "/" + plane.getCapacity());
    }

    public void loadCargo(String planeId, String cargoName) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        plane.loadCargo(cargoName);
        System.out.println("Cargo loaded: " + cargoName);
    }

    public void viewPlane(String planeId) throws Exception {
        if (!planes.containsKey(planeId)) {
            throw new Exception("Plane not found!");
        }
        Plane plane = planes.get(planeId);
        System.out.println("\n--- Plane Details ---");
        System.out.println(plane);
        System.out.println("Cargo: " + plane.getCargoList());
        if (plane.getArrivalTime() != null) {
            System.out.println("Arrival: " + plane.getArrivalTime());
        }
        if (plane.getDepartureTime() != null) {
            System.out.println("Departure: " + plane.getDepartureTime());
        }
        System.out.println();
    }

    public void viewAllPlanes() {
        System.out.println("\n--- All Planes ---");
        if (planes.isEmpty()) {
            System.out.println("No planes registered.");
        } else {
            planes.values().forEach(System.out::println);
        }
        System.out.println();
    }

    public void viewBays() {
        System.out.println("\n--- Bay Status ---");
        bays.values().forEach(System.out::println);
        System.out.println();
    }

    public void viewWaitingQueue() {
        System.out.println("\n--- Waiting Queue ---");
        if (waitingQueue.isEmpty()) {
            System.out.println("No planes waiting.");
        } else {
            waitingQueue.forEach(plane -> System.out.println(plane.getPlaneName()));
        }
        System.out.println();
    }
}

public class App {
    private static HarbourManager harbourManager;
    private static AirportManager airportManager;
    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        harbourManager = new HarbourManager();
        airportManager = new AirportManager();
        scanner = new Scanner(System.in);

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   HARBOUR & AIRPORT MANAGEMENT SYSTEM   ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    shipManagementMenu();
                    break;
                case "2":
                    planeManagementMenu();
                    break;
                case "3":
                    running = false;
                    System.out.println("\nThank you for using the Harbour & Airport Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Ship Management");
        System.out.println("2. Plane Management");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void shipManagementMenu() throws Exception {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Ship Management ---");
            System.out.println("1. Register Ship");
            System.out.println("2. Dock Ship");
            System.out.println("3. Undock Ship");
            System.out.println("4. Load Cargo");
            System.out.println("5. View Ship Details");
            System.out.println("6. View All Ships");
            System.out.println("7. View Dock Status");
            System.out.println("8. View Waiting Queue");
            System.out.println("9. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    registerShip();
                    break;
                case "2":
                    dockShip();
                    break;
                case "3":
                    undockShip();
                    break;
                case "4":
                    loadCargo();
                    break;
                case "5":
                    viewShipDetails();
                    break;
                case "6":
                    harbourManager.viewAllShips();
                    break;
                case "7":
                    harbourManager.viewDocks();
                    break;
                case "8":
                    harbourManager.viewWaitingQueue();
                    break;
                case "9":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void planeManagementMenu() throws Exception {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- Plane Management ---");
            System.out.println("1. Register Plane");
            System.out.println("2. Park Plane");
            System.out.println("3. Departure Plane");
            System.out.println("4. Board Passengers");
            System.out.println("5. Deboard Passengers");
            System.out.println("6. Load Cargo");
            System.out.println("7. View Plane Details");
            System.out.println("8. View All Planes");
            System.out.println("9. View Bay Status");
            System.out.println("10. View Waiting Queue");
            System.out.println("11. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    registerPlane();
                    break;
                case "2":
                    parkPlane();
                    break;
                case "3":
                    departurePlane();
                    break;
                case "4":
                    boardPassengers();
                    break;
                case "5":
                    deboardPassengers();
                    break;
                case "6":
                    loadPlaneCargo();
                    break;
                case "7":
                    viewPlaneDetails();
                    break;
                case "8":
                    airportManager.viewAllPlanes();
                    break;
                case "9":
                    airportManager.viewBays();
                    break;
                case "10":
                    airportManager.viewWaitingQueue();
                    break;
                case "11":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerShip() {
        System.out.print("Enter Ship ID: ");
        String shipId = scanner.nextLine().trim();
        System.out.print("Enter Ship Name: ");
        String shipName = scanner.nextLine().trim();
        System.out.print("Enter Ship Type (e.g., Cargo, Passenger, Container): ");
        String shipType = scanner.nextLine().trim();
        System.out.print("Enter Capacity (kg): ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());
        
        harbourManager.registerShip(shipId, shipName, shipType, capacity);
    }

    private static void dockShip() throws Exception {
        System.out.print("Enter Ship ID to dock: ");
        String shipId = scanner.nextLine().trim();
        harbourManager.dockShip(shipId);
    }

    private static void undockShip() throws Exception {
        System.out.print("Enter Ship ID to undock: ");
        String shipId = scanner.nextLine().trim();
        harbourManager.undockShip(shipId);
    }

    private static void loadCargo() throws Exception {
        System.out.print("Enter Ship ID: ");
        String shipId = scanner.nextLine().trim();
        System.out.print("Enter Cargo Name: ");
        String cargoName = scanner.nextLine().trim();
        System.out.print("Enter Cargo Weight (kg): ");
        int weight = Integer.parseInt(scanner.nextLine().trim());
        
        harbourManager.loadCargo(shipId, cargoName, weight);
    }

    private static void viewShipDetails() throws Exception {
        System.out.print("Enter Ship ID: ");
        String shipId = scanner.nextLine().trim();
        harbourManager.viewShip(shipId);
    }

    private static void registerPlane() {
        System.out.print("Enter Plane ID: ");
        String planeId = scanner.nextLine().trim();
        System.out.print("Enter Plane Name: ");
        String planeName = scanner.nextLine().trim();
        System.out.print("Enter Airline: ");
        String airline = scanner.nextLine().trim();
        System.out.print("Enter Passenger Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine().trim());
        
        airportManager.registerPlane(planeId, planeName, airline, capacity);
    }

    private static void parkPlane() throws Exception {
        System.out.print("Enter Plane ID to park: ");
        String planeId = scanner.nextLine().trim();
        airportManager.parkPlane(planeId);
    }

    private static void departurePlane() throws Exception {
        System.out.print("Enter Plane ID for departure: ");
        String planeId = scanner.nextLine().trim();
        airportManager.departurePlane(planeId);
    }

    private static void boardPassengers() throws Exception {
        System.out.print("Enter Plane ID: ");
        String planeId = scanner.nextLine().trim();
        System.out.print("Enter number of passengers to board: ");
        int count = Integer.parseInt(scanner.nextLine().trim());
        
        airportManager.boardPassengers(planeId, count);
    }

    private static void deboardPassengers() throws Exception {
        System.out.print("Enter Plane ID: ");
        String planeId = scanner.nextLine().trim();
        System.out.print("Enter number of passengers to deboard: ");
        int count = Integer.parseInt(scanner.nextLine().trim());
        
        airportManager.deboardPassengers(planeId, count);
    }

    private static void loadPlaneCargo() throws Exception {
        System.out.print("Enter Plane ID: ");
        String planeId = scanner.nextLine().trim();
        System.out.print("Enter Cargo Name: ");
        String cargoName = scanner.nextLine().trim();
        
        airportManager.loadCargo(planeId, cargoName);
    }

    private static void viewPlaneDetails() throws Exception {
        System.out.print("Enter Plane ID: ");
        String planeId = scanner.nextLine().trim();
        airportManager.viewPlane(planeId);
    }
}
