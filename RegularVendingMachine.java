import javax.swing.JTextArea;
import java.util.HashMap;
import java.util.Map;

public class RegularVendingMachine extends VendingMachine {

    private Map<String, Item> items;

    public RegularVendingMachine() {
        super();
        items = new HashMap<>();
    }

    @Override 
    public void addItem(String name, double price, int quantity) {
        items.put(name, new Item(name, price, quantity));
        inventory.put(name, quantity);
    }

    @Override
    protected double getItemPrice(String name) {
        Item item = items.get(name);
        return (item != null) ? item.getPrice() : 0.0;
    }

    public void restock(String name, int quantity, JTextArea outputTextArea) {
        Item item = items.get(name);
        if (item != null) {
            item.addQuantity(quantity);
            outputTextArea.append("Restocked " + name + ", new quantity: " + item.getQuantity() + "\n");
        } else {
            outputTextArea.append("Item not found for restock: " + name + "\n");
        }
    }

    public void testFeatures(JTextArea outputTextArea) {
        // Display available items
        outputTextArea.append("\nAvailable Items in Regular Vending Machine\n");
        outputTextArea.append("------------------------\n");
        int itemIndex = 1;
        Map<String, Slot> inventory = getInventory();
        for (String itemName : inventory.keySet()) {
            Slot slot = inventory.get(itemName);
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

        if (moneyInserted >= price) {
            vend(orderedItem);
            returnChange();
        } else {
            outputTextArea.append("Insufficient money inserted for " + orderedItem + "\n");
            outputTextArea.append("Please insert more money or choose a different item.\n");
        }
    }
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
