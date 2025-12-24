import java.time.LocalDateTime;
import java.util.*;

public class AirportManager {
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
