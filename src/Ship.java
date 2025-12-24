import java.time.LocalDateTime;
import java.util.*;

public class Ship {
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
