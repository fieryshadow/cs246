public class Computer implements ShippingItem {
   private String brand;

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public double getShippingCost() {
      return 130;
   }

   public String toString() {
      return "a(n) " + this.brand + "computer";
   }
}
