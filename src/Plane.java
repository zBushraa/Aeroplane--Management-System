import java.time.LocalDateTime;
import java.util.*;

public class Plane {
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
