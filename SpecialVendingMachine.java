import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;

public class SpecialVendingMachine extends VendingMachine {

    private final Map<String, List<VendingMachine.Item>> customizableProducts;
    private final Map<String, Integer> ingredientQuantity;

    public SpecialVendingMachine() {
        super();
        customizableProducts = new HashMap<>();
        ingredientQuantity = new HashMap<>();
    }

    public void addCustomizableProduct(String productName, List<VendingMachine.Item> choices) {
        customizableProducts.put(productName, choices);
    }

   public void prepareCustomizableProduct(String productName, List<String> chosenItems, JTextArea outputTextArea) {
    outputTextArea.append("Preparing " + productName + "...\n");
    List<Item> choices = customizableProducts.get(productName);

    if (choices == null) {
        outputTextArea.append("Invalid product name: " + productName + "\n");
        return;
    }

    Map<String, Integer> chosenIngredientCount = new HashMap<>();

    for (String itemName : chosenItems) {
        String lowercaseItemName = itemName.toLowerCase();

        boolean validIngredient = false;
        for (Item choice : choices) {
            if (choice.getName().toLowerCase().equals(lowercaseItemName)) {
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
            outputTextArea.append("Duplicate ingredient: " + itemName + "\n");
            continue;
        }

        chosenIngredientCount.put(lowercaseItemName, ingredientCount + 1);
        ingredientQuantity.put(lowercaseItemName, ingredientQuantity.getOrDefault(lowercaseItemName, 0) + 1);
        outputTextArea.append("Using " + itemName + " as an ingredient...\n");
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
