package entity;

public class ParkingSpot {
   private String spot_id;
   private boolean isFree;
    public ParkingSpot(String spot_id) {
     this.spot_id = spot_id;
     this.isFree = true;
    }
   public boolean isFree() {
    return isFree;
   }
   public String getId() {
    return spot_id;
   }
   public synchronized boolean reserve () {
    if(isFree){
     isFree = false;
     return true;
    } 
    return false;
   }

   public synchronized boolean release () {
     if(isFree) return false; 
     isFree = true;
     return true; 
   }
}
