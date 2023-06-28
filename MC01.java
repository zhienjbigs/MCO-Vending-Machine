import java.util.*;

abstract class VendingMachine {
    // map to store the inventory of items
    protected Map<String, Slot> inventory = new HashMap<>();
    // variable to keep track of the inserted money
    protected double moneyInserted;

    // abstract method to add an item to the machine
    public abstract void addItem(String name, double price, int quantity);

    // method to insert money into the vending machine
    public void insertMoney(double money) {
        this.moneyInserted += money;
    }

    // method to vend an item
    public void vend(String name) {
        Slot slot = inventory.get(name);
        if (slot != null && slot.quantity > 0) {
            if (moneyInserted >= slot.item.price) {
                System.out.println("Vending " + name);
                moneyInserted -= slot.item.price;
                slot.quantity--;
            } else {
                System.out.println("Insufficient money inserted for " + name);
            }
        } else {
            System.out.println("Item unavailable: " + name);
        }
    }

    // method to return the change
    public void returnChange() {
        System.out.println("Returning change: " + moneyInserted);
        moneyInserted = 0;
    }
}

class RegularVendingMachine extends VendingMachine {
    // method to add an item to the regular vending machine
    @Override
    public void addItem(String name, double price, int quantity) {
        inventory.put(name, new Slot(new Item(name, price), quantity));
    }

    // method to restock an item
    public void restock(String name, int quantity) {
        Slot slot = inventory.get(name);
        if (slot != null) {
            slot.quantity += quantity;
            System.out.println("Restocked " + name + ", new quantity: " + slot.quantity);
        } else {
            System.out.println("Item not found for restock: " + name);
        }
    }

    // method to collect the payment
    public void collectPayment() {
        System.out.println("Collected payment: " + moneyInserted);
        moneyInserted = 0;
    }
}

class Item {
    String name;
    double price;

    // constructor for the Item class
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Slot {
    Item item;
    int quantity;

    // constructor for the Slot class
    public Slot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

public class MC01 {
    public static void main(String[] args) {
        // creating a regular vending machine
        RegularVendingMachine rvm = new RegularVendingMachine();

        // adding items to the machine
        rvm.addItem("Fish Cake", 25.0, 10);
        rvm.addItem("Aji Tamago", 45.0, 10);
        rvm.addItem("Fried Tofu", 30.0, 10);

        // inserting money and vend an item
        rvm.insertMoney(200.0);
        rvm.vend("Fish Cake");
        rvm.returnChange();

        // restocking the vending machine and collecting payment
        rvm.restock("Fish Cake", 10);
        rvm.collectPayment();
    }
}
