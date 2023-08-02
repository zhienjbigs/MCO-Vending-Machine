import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

public class SpecialVendingMachine extends VendingMachine { //this class is an extension of abstract class VendingMachine and inherits it

    private final Map<String, List<VendingMachine.Item>> customizableProducts; // key/value pair of String key and a VendingMachine.Item list entry value
    private final Map<String, Integer> ingredientQuantity;  //quantity of ingredient with String key for name and Integer value for quantity

    public SpecialVendingMachine() { 
        super();
        customizableProducts = new HashMap<>();
        ingredientQuantity = new HashMap<>();
    }

    public void addCustomizableProduct(String productName, List<VendingMachine.Item> choices) {
        customizableProducts.put(productName, choices); //inputs a customizableProducts key/value pair
    }

   public void prepareCustomizableProduct(String productName, List<String> chosenItems, JTextArea outputTextArea) {
    outputTextArea.append("Preparing " + productName + "...\n"); //appending for the output in GUI
    List<Item> choices = customizableProducts.get(productName); //choices object extracts the product name from customizableProducts

    if (choices == null) {// if the name is not present, statement follows.
        outputTextArea.append("Invalid product name: " + productName + "\n");
        return;
    }

    Map<String, Integer> chosenIngredientCount = new HashMap<>(); //makes new Hashmap object chosenIngredientCount

    for (String itemName : chosenItems) { //loops itename through chosenItems
        String lowercaseItemName = itemName.toLowerCase(); //converts itemName to lower case

        boolean validIngredient = false;
        for (Item choice : choices) {
            if (choice.getName().toLowerCase().equals(lowercaseItemName)) { // checks equality of choice and lowercaseItemName
                validIngredient = true;
                break;
            }
        }

        if (!validIngredient) {
            outputTextArea.append("Invalid ingredient: " + itemName + "\n");
            continue;
        }

        int ingredientCount = chosenIngredientCount.getOrDefault(lowercaseItemName, 0);
        if (ingredientCount >= 1) {
            outputTextArea.append("Duplicate ingredient: " + itemName + "\n"); //if input of ingredient is more than 1, we say this ingredient is duplicate
            continue;
        }

        chosenIngredientCount.put(lowercaseItemName, ingredientCount + 1);
        ingredientQuantity.put(lowercaseItemName, ingredientQuantity.getOrDefault(lowercaseItemName, 0) + 1);
        outputTextArea.append("Using " + itemName + " as an ingredient...\n"); //after continuing on from earlier iterative statements and as we iterate through the loop per ingredient, print this message
    }

    outputTextArea.append(productName + " Done!\n");
}

    public Map<String, List<VendingMachine.Item>> getCustomizableProducts() {
        return customizableProducts;
    }

    @Override
    public void addItem(String name, double price, int quantity) {
        // Implement the method if needed for specific behavior.
    }

    @Override
    protected double getItemPrice(String name) {
        // Implement the method if needed for specific behavior.
        return 0.0;
    }
}
