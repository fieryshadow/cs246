public class Store {
   public static void main(String[] args) {
      System.out.println("Starting...");

      Computer c1 = new Computer();
      Computer c2 = new Computer();

      c1.setBrand("Apple");
      c2.setBrand("Dell");

      ship(c1);
      ship(c2);
   }

   public static void ship(ShippingItem item) {
      System.out.println("Shipping " + item.toString() +
           " -- with cost: " + item.getShippingCost());
   }
}
