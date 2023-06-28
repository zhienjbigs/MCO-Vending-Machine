import java.util.*;

abstract class VendingMachine {
    protected Map<String, Slot> inventory = new HashMap<>();
    protected double moneyInserted;

    public abstract void addItem(String name, double price, int quantity);

    public void insertMoney(double money) {
        this.moneyInserted += money;
    }

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

    public void returnChange() {
        System.out.println("Returning change: " + moneyInserted);
        moneyInserted = 0;
    }
}

class RegularVendingMachine extends VendingMachine {

    @Override
    public void addItem(String name, double price, int quantity) {
        inventory.put(name, new Slot(new Item(name, price), quantity));
    }

    public void restock(String name, int quantity) {
        Slot slot = inventory.get(name);
        if (slot != null) {
            slot.quantity += quantity;
            System.out.println("Restocked " + name + ", new quantity: " + slot.quantity);
        } else {
            System.out.println("Item not found for restock: " + name);
        }
    }

    public void collectPayment() {
        System.out.println("Collected payment: " + moneyInserted);
        moneyInserted = 0;
    }
}

class Item {
    String name;
    double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Slot {
    Item item;
    int quantity;

    public Slot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

public class MC01 {
    public static void main(String[] args) {
        RegularVendingMachine rvm = new RegularVendingMachine();

      
        rvm.addItem("Fish Cake", 25.0, 10);
        rvm.addItem("Aji Tamago", 45.0, 10);
        rvm.addItem("Fried Tofu", 30.0, 10);

        rvm.insertMoney(200.0);
        rvm.vend("Fish Cake");
        rvm.returnChange();

        rvm.restock("Fish Cake", 10);
        rvm.collectPayment();
    }
}
