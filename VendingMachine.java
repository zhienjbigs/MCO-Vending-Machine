    import java.util.HashMap;
    import java.util.Map;
    // abstract class whose methods and attributes will be extended to Special and Regular Vending Machines
    public abstract class VendingMachine {

        protected Map<String, Integer> inventory = new HashMap<>(); // key/value pair of a corresponding item in string with an integer value
        protected Map<Double, Integer> change = new HashMap<>(); //key/value pair of corresponding change in double and how much there is of that change
        protected double moneyInserted;
        
        public VendingMachine() {   // giving the vending machine instance a change key/value pair for an amount of change and quantity
            change.put(50.0, 10);
            change.put(20.0, 10);
            change.put(10.0, 10);
            change.put(5.0, 10);
            change.put(1.0, 10);
        }

        public abstract void addItem(String name, double price, int quantity);

        public void insertMoney(double money) { //setting moneyInserted
            this.moneyInserted += money;
        }

        public void vend(String name) { //method for vending out an item, given a name.
            Integer quantity = inventory.get(name); // finds quantity from the inventory
            if (quantity != null && quantity > 0) {
                if (moneyInserted >= getItemPrice(name)) { 
                    System.out.println("Vending " + name);
                    moneyInserted -= getItemPrice(name);
                    inventory.put(name, quantity - 1); //after doing everything else in this if statement, change key/value pair to reduce quantity by 1
                } else {
                    System.out.println("Insufficient money inserted for " + name); // paired else statement to if statement above
                }
            } else {
                System.out.println("Item unavailable: " + name); //if quantity is equal to null or not greater than 0, then this statement.
            }
        }

        public void returnChange() { //method for returning the change after paying with moneyInserted. Will also change values in change map
            System.out.println("Returning change: " + moneyInserted);
            double[] denominations = {50, 20, 10, 5, 1}; //array of denominations

            for (double denomination : denominations) { //looping through the denominations
                int numberOfDenomination = (int) (moneyInserted / denomination); //assigns int to the value of moneyInserted/denomination
                if (numberOfDenomination > 0) {
                    if (change.get(denomination) >= numberOfDenomination) { //gets the value in change map, corresponding to denomination
                        System.out.println("Returning " + numberOfDenomination + " of " + denomination);
                        moneyInserted -= numberOfDenomination * denomination; 
                        change.put(denomination, change.get(denomination) - numberOfDenomination); //reducing the key/value pair
                    } else {
                        System.out.println("Denomination " + denomination + " is not available.");
                    }
                }
            }

            if (moneyInserted > 0) {
                System.out.println("Couldn't return full change, remaining amount: " + moneyInserted); //Essentially, when moneyInserted cannot have a change due to less capacity for change of vending machine
            }
            moneyInserted = 0;
        }

        protected double getItemPrice(String name) {
          
            return 0.0;
        }

        protected Map<String, Slot> getInventory() { //maps a key/value pair of a String key to a Slot value
            Map<String, Slot> inventory = new HashMap<>();  //creates a new map instance
            for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {  // iterates through the inventory. keys are item names and integer is item quantity
                String itemName = entry.getKey();  // extracts item name from entry object
                Integer quantity = entry.getValue(); //extracts item quantity from entry object
                inventory.put(itemName, new Slot(new Item(itemName, getItemPrice(itemName), quantity), quantity)); //creating a new slot for each item and stores them in invetory map
            }
            return inventory; //returnts inventory map
        }

        protected static class Item {// represents Item in inventory
            // private attributes of static class Item
            private String name;
            private double price;
            private int quantity;

            public Item(String name, double price, int quantity) { //constructor for parameters 
                this.name = name;
                this.price = price;
                this.quantity = quantity;
            }
            //getter methods
            public String getName() {
                return name;
            }

            public double getPrice() {
                return price;
            }

            public int getQuantity() {
                return quantity;
            }
            //increments quantity 
            public void addQuantity(int quantity) {
                this.quantity += quantity;
            }
            // when there was a use of an item, reduces quantity
            public void use() {
                this.quantity--;
            }
        }
       

    public double getMoneyInserted() {
        return moneyInserted;
    }

        protected static class Slot { //represents a slot within inventory
            Item item;
            int quantity;

            public Slot(Item item, int quantity) { //constructor
                this.item = item;
                this.quantity = quantity;
            }

            public int getQuantity() { // getter method
                return quantity;
            }
        }
    }
