import java.time.LocalDateTime;

public class Dock {
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
