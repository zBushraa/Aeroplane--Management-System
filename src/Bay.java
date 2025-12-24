import java.time.LocalDateTime;

public class Bay {
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
