import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialVendingMachine extends VendingMachine {

    private Map<String, List<Item>> customizableProducts;
    private Map<String, Integer> ingredientQuantity;

    public SpecialVendingMachine() {
        super();
        customizableProducts = new HashMap<>();
        ingredientQuantity = new HashMap<>();
    }

    public void addCustomizableProduct(String productName, List<SpecialVendingMachine.Item> choices) {
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

    private Item getItemInstance(String name) {
        Integer quantity = ingredientQuantity.get(name);
        if (quantity != null && quantity > 0) {
            ingredientQuantity.put(name, quantity - 1);
            return new Item(name, 0, 1);
        }
        return null;
    }

    public Map<String, List<Item>> getCustomizableProducts() {
        return customizableProducts;
    }

    @Override
    public void addItem(String name, double price, int quantity) {

    }

    @Override
    protected double getItemPrice(String name) {

        return 0.0;
    }

}
