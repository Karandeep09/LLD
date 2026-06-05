package enums;

public enum VehicleType {
    Car, 
    Bike;

    public SpotType toSpotType() {
        switch (this) {
            case Car:
                return SpotType.CarSpot;
            case Bike:
                return SpotType.BikeSpot;
            default:
                throw new IllegalArgumentException("Unsupported vehicle type: " + this);
        }
    }
}
