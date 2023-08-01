    import java.util.HashMap;
    import java.util.Map;

    public abstract class VendingMachine {

        protected Map<String, Integer> inventory = new HashMap<>();
        protected Map<Double, Integer> change = new HashMap<>();
        protected double moneyInserted;
        
        public VendingMachine() {
            change.put(50.0, 10);
            change.put(20.0, 10);
            change.put(10.0, 10);
            change.put(5.0, 10);
            change.put(1.0, 10);
        }

        public abstract void addItem(String name, double price, int quantity);

        public void insertMoney(double money) {
            this.moneyInserted += money;
        }

        public void vend(String name) {
            Integer quantity = inventory.get(name);
            if (quantity != null && quantity > 0) {
                if (moneyInserted >= getItemPrice(name)) {
                    System.out.println("Vending " + name);
                    moneyInserted -= getItemPrice(name);
                    inventory.put(name, quantity - 1);
                } else {
                    System.out.println("Insufficient money inserted for " + name);
                }
            } else {
                System.out.println("Item unavailable: " + name);
            }
        }

        public void returnChange() {
            System.out.println("Returning change: " + moneyInserted);
            double[] denominations = {50, 20, 10, 5, 1};

            for (double denomination : denominations) {
                int numberOfDenomination = (int) (moneyInserted / denomination);
                if (numberOfDenomination > 0) {
                    if (change.get(denomination) >= numberOfDenomination) {
                        System.out.println("Returning " + numberOfDenomination + " of " + denomination);
                        moneyInserted -= numberOfDenomination * denomination;
                        change.put(denomination, change.get(denomination) - numberOfDenomination);
                    } else {
                        System.out.println("Denomination " + denomination + " is not available.");
                    }
                }
            }

            if (moneyInserted > 0) {
                System.out.println("Couldn't return full change, remaining amount: " + moneyInserted);
            }
            moneyInserted = 0;
        }

        protected double getItemPrice(String name) {
          
            return 0.0;
        }

        protected Map<String, Slot> getInventory() {
            Map<String, Slot> inventory = new HashMap<>();
            for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {
                String itemName = entry.getKey();
                Integer quantity = entry.getValue();
                inventory.put(itemName, new Slot(new Item(itemName, getItemPrice(itemName), quantity), quantity));
            }
            return inventory;
        }

        protected static class Item {
            private String name;
            private double price;
            private int quantity;

            public Item(String name, double price, int quantity) {
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }

            public String getName() {
                return name;
            }

            public double getPrice() {
                return price;
            }

            public int getQuantity() {
                return quantity;
            }

            public void addQuantity(int quantity) {
                this.quantity += quantity;
            }

            public void use() {
                this.quantity--;
            }
        }
       

    public double getMoneyInserted() {
        return moneyInserted;
    }

        protected static class Slot {
            Item item;
            int quantity;

            public Slot(Item item, int quantity) {
                this.item = item;
                this.quantity = quantity;
            }

            public int getQuantity() {
                return quantity;
            }
        }
    }
