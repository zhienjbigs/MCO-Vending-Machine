import javax.swing.JTextArea;
import java.util.HashMap;
import java.util.Map;

public class RegularVendingMachine extends VendingMachine { //this class is an extension of abstract class VendingMachine and inherits it

    private Map<String, Item> items;  //map with key/value pair of String and Item

    public RegularVendingMachine() {
        super();
        items = new HashMap<>(); //new hashmap for items
    }

    @Override 
    public void addItem(String name, double price, int quantity) { //method for adding items into vending machine
        items.put(name, new Item(name, price, quantity)); //inputs a name, and creates new instance of the Item
        inventory.put(name, quantity); //puts the new instance into inventory
    }

    @Override
    protected double getItemPrice(String name) { //method for getting Item price given a string name
        Item item = items.get(name);    // looks into the map for a corresponding name 
        return (item != null) ? item.getPrice() : 0.0; //returns the value of the item in the map
    }

    public void restock(String name, int quantity, JTextArea outputTextArea) {
        Item item = items.get(name); 
        if (item != null) { // if the item exists, from items.get(name), statement follows
            item.addQuantity(quantity); // add quantity to the item, given the parameter quantity
            outputTextArea.append("Restocked " + name + ", new quantity: " + item.getQuantity() + "\n"); //restock print
        } else {
            outputTextArea.append("Item not found for restock: " + name + "\n");
        }
    }

    public void testFeatures(JTextArea outputTextArea) {
        // Display available items
        outputTextArea.append("\nAvailable Items in Regular Vending Machine\n");
        outputTextArea.append("------------------------\n");
        int itemIndex = 1; // itemIndex for testFeatures and printing out the choices
        Map<String, Slot> inventory = getInventory();
        for (String itemName : inventory.keySet()) { // iteratres through itemName to keySet in inventory
            Slot slot = inventory.get(itemName); //extracts the item name from inventory into slot
            outputTextArea.append(itemIndex + ". " + itemName + " - Price: " + slot.item.getPrice() + " - Quantity: " + slot.getQuantity() + "\n");
            itemIndex++;
        }

        // Get user input
        int itemNumber = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Enter the item number to order: "));
        String[] itemNames = inventory.keySet().toArray(new String[0]);

        if (itemNumber < 1 || itemNumber > inventory.size()) {
            outputTextArea.append("\nInvalid item number.\n");
            return;
        }

        String orderedItem = itemNames[itemNumber - 1];
        double price = getItemPrice(orderedItem);

        double amountToInsert = Double.parseDouble(javax.swing.JOptionPane.showInputDialog("Enter the amount to insert (e.g., 20.0): "));
        insertMoney(amountToInsert);
        // when the moneyInserted input is greater than the price, do statements in if statement
        if (moneyInserted >= price) {
            vend(orderedItem);
            returnChange();
        } else {
            outputTextArea.append("Insufficient money inserted for " + orderedItem + "\n");
            outputTextArea.append("Please insert more money or choose a different item.\n");
        }
    }
    // vend method for the item and also reduce quantity
    public void vend(String name, JTextArea outputTextArea) {
        Integer quantity = inventory.get(name);
        if (quantity != null && quantity > 0) {
            if (moneyInserted >= getItemPrice(name)) {
                outputTextArea.append("Vending " + name + "\n");
                moneyInserted -= getItemPrice(name);
                inventory.put(name, quantity - 1);
            } else {
                outputTextArea.append("Insufficient money inserted for " + name + "\n");
            }
        } else {
            outputTextArea.append("Item unavailable: " + name + "\n");
        }
    }
    //method for returning the change after paying with moneyInserted.
    public void returnChange(JTextArea outputTextArea) {
        outputTextArea.append("Returning change: " + moneyInserted + "\n");
        double[] denominations = {50, 20, 10, 5, 1};

        for (double denomination : denominations) {
            int numberOfDenomination = (int) (moneyInserted / denomination);
            if (numberOfDenomination > 0) {
                if (change.get(denomination) >= numberOfDenomination) {
                    outputTextArea.append("Returning " + numberOfDenomination + " of " + denomination + "\n");
                    moneyInserted -= numberOfDenomination * denomination;
                    change.put(denomination, change.get(denomination) - numberOfDenomination);
                } else {
                    outputTextArea.append("Denomination " + denomination + " is not available.\n");
                }
            }
        }

        if (moneyInserted > 0) {
            outputTextArea.append("Couldn't return full change, remaining amount: " + moneyInserted + "\n");
        }
        moneyInserted = 0;
    }

    
}
