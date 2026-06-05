package  entity;
import enums.VehicleType;

public class Vehicle {
    private String reg_no;
    private VehicleType type;

    public Vehicle(String reg_no, VehicleType type) {
        this.reg_no = reg_no;
        this.type = type;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
} 