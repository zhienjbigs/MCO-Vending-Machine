import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainApp extends JFrame {
    private RegularVendingMachine rvm;
    private SpecialVendingMachine svm;

    public MainApp() {
        createUI();
        rvm = null;
        svm = null;
    }



    private void createUI() {
    setTitle("Vending Machine");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout()); 

    JTextArea outputTextArea = new JTextArea();
    outputTextArea.setEditable(false);

    
    
    
    outputTextArea.setRows(20);
    outputTextArea.setColumns(50);

    JScrollPane scrollPane = new JScrollPane(outputTextArea);
    outputTextArea.setBackground(new Color(205, 230, 245));
    scrollPane.setPreferredSize(new Dimension(600, 400));

    add(scrollPane, BorderLayout.CENTER);

        JButton createRvmButton = new JButton("Create Regular Vending Machine");
        JButton createSvmButton = new JButton("Create Special Vending Machine");
        JButton testRvmButton = new JButton("Test Regular Vending Machine");
        JButton testSvmButton = new JButton("Test Special Vending Machine");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.add(createRvmButton);
        buttonPanel.add(createSvmButton);
        buttonPanel.add(testRvmButton);
        buttonPanel.add(testSvmButton);




        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(new Color(135, 145, 158));
        createRvmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rvm = new RegularVendingMachine();
                outputTextArea.append("\nRegular vending machine created.\n");
            
                // Add items to the regular vending machine
                rvm.addItem("Fish Cake", 20.0, 10);
                rvm.addItem("Aji Tamago", 45.0, 10);
                rvm.addItem("Fried Tofu", 30.0, 10);
                rvm.addItem("Sukiyaki Bento", 210.0, 10);
                rvm.addItem("Mushroom", 10.0, 10);
                rvm.addItem("Fowl", 10.0, 10);
                rvm.addItem("Sweet Flower", 10.0, 10);
                rvm.addItem("Tofu", 30.0, 10);
                rvm.addItem("Seaweed", 20.0, 10);
                rvm.addItem("Onion", 5.0, 10);
                rvm.addItem("Rice", 40.0, 10);
                rvm.addItem("Garlic", 10.0, 10);
                rvm.addItem("Egg", 20.0, 10);
                rvm.addItem("Sunsettia", 20.0, 10);
                rvm.addItem("Orange", 20.0, 10);
            }
        });

      
createSvmButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        svm = new SpecialVendingMachine();
        outputTextArea.append("\nSpecial vending machine created.\n");

        //Customizable products and their choices
        List<SpecialVendingMachine.Item> ramenChoices = Arrays.asList(
                new SpecialVendingMachine.Item("Noodles", 15.0, 1),
                new SpecialVendingMachine.Item("Chashu Pork", 25.0, 1),
                new SpecialVendingMachine.Item("Tonkotsu Broth", 20.0, 1)
        );
        svm.addCustomizableProduct("Ramen", ramenChoices);

        List<RegularVendingMachine.Item> burgerChoices = Arrays.asList(
                new SpecialVendingMachine.Item("Bun", 10.0, 1),
                new SpecialVendingMachine.Item("Patty", 30.0, 1),
                new SpecialVendingMachine.Item("Lettuce", 5.0, 1),
                new SpecialVendingMachine.Item("Cheese", 15.0, 1),
                new SpecialVendingMachine.Item("Tomato", 8.0, 1),
                new SpecialVendingMachine.Item("Onion", 5.0, 1),
                new SpecialVendingMachine.Item("Pickles", 3.0, 1)
        );
        svm.addCustomizableProduct("Burger", burgerChoices);
    
        List<SpecialVendingMachine.Item> chickenSkewerChoices = Arrays.asList(
                new SpecialVendingMachine.Item("Mushroom", 10.0, 10),
                new SpecialVendingMachine.Item("Fowl", 10.0, 10)

        );
        svm.addCustomizableProduct("Chicken Mushroom Skewer", chickenSkewerChoices);
 
        List<SpecialVendingMachine.Item> jaxFriedRiceChoices = Arrays.asList(
                new SpecialVendingMachine.Item("Egg", 20.0, 10),
                new SpecialVendingMachine.Item("Rice", 40.0, 10),
                new SpecialVendingMachine.Item("Garlic", 10.0, 10),
                new SpecialVendingMachine.Item("Onion", 5.0, 10)
        );
        svm.addCustomizableProduct("Jax's Egg Fried Rice", jaxFriedRiceChoices);

        List<SpecialVendingMachine.Item> gpOrangeChoices = Arrays.asList(
                new SpecialVendingMachine.Item("Sunsettia", 20.0, 10),
                new SpecialVendingMachine.Item("Orange", 20.0, 10),
                new SpecialVendingMachine.Item("Sweet Flower", 10.0, 10)

        );
        svm.addCustomizableProduct("Gangplank's Orange Juice", gpOrangeChoices);
    }
});


   testRvmButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (rvm == null) {
            outputTextArea.append("\nNo regular vending machine created yet.\n");
            return;
        }

        // Display available items
        outputTextArea.append("\nAvailable Items in Regular Vending Machine\n");
        outputTextArea.append("------------------------\n");
        int itemIndex = 1;
        Map<String, VendingMachine.Slot> inventory = rvm.getInventory();
        for (String itemName : inventory.keySet()) {
            VendingMachine.Slot slot = inventory.get(itemName);
            outputTextArea.append(itemIndex + ". " + itemName + " - Price: " + slot.item.getPrice() + " - Quantity: " + slot.getQuantity() + "\n");
            itemIndex++;
        }

        // Get user input for item number
        int itemNumber;
        try {
            itemNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter the item number to order: "));
        } catch (NumberFormatException ex) {
            outputTextArea.append("\nInvalid item number. Please enter a valid number.\n");
            return;
        }

        if (itemNumber < 1 || itemNumber > inventory.size()) {
            outputTextArea.append("\nInvalid item number.\n");
            return;
        }

        String[] itemNames = inventory.keySet().toArray(new String[0]);
        String orderedItem = itemNames[itemNumber - 1];
        double price = rvm.getItemPrice(orderedItem);

        // Get user input for amount to insert
        double amountToInsert;
        try {
            amountToInsert = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount to insert (e.g., 20.0): "));
        } catch (NumberFormatException ex) {
            outputTextArea.append("\nInvalid amount. Please enter a valid number.\n");
            return;
        }

        rvm.insertMoney(amountToInsert);

        if (rvm.getMoneyInserted() >= price) {
            rvm.vend(orderedItem, outputTextArea);
            rvm.returnChange(outputTextArea);
        } else {
            outputTextArea.append("Insufficient money inserted for " + orderedItem + "\n");
            outputTextArea.append("Please insert more money or choose a different item.\n");
        }
    }
});

       testSvmButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (svm == null) {
            outputTextArea.append("\nNo special vending machine created yet.\n");
            return;
        }

        String[] productOptions = svm.getCustomizableProducts().keySet().toArray(new String[0]);
        String chosenProduct = (String) JOptionPane.showInputDialog(
                MainApp.this,
                "Choose a product to customize:",
                "Special Vending Machine",
                JOptionPane.PLAIN_MESSAGE,
                null,
                productOptions,
                productOptions[0]
        );

        if (chosenProduct != null && !chosenProduct.isEmpty()) {
            List<SpecialVendingMachine.Item> choices = svm.getCustomizableProducts().get(chosenProduct);
            StringBuilder choicesText = new StringBuilder();
            for (SpecialVendingMachine.Item choice : choices) {
                choicesText.append(choice.getName()).append(", ");
            }
            if (choicesText.length() > 0) {
                choicesText.delete(choicesText.length() - 2, choicesText.length());
            }

            String input = JOptionPane.showInputDialog(MainApp.this,
                    "Enter the chosen items separated by commas.\n" +
                            chosenProduct + " choices: " +
                            choicesText.toString(),
                    "Special Vending Machine",
                    JOptionPane.PLAIN_MESSAGE);

            if (input != null && !input.isEmpty()) {
                List<String> chosenItems = Arrays.asList(input.split(","));
                svm.prepareCustomizableProduct(chosenProduct, chosenItems, outputTextArea);
            }
        }
    }
});



        pack();
        setLocationRelativeTo(null);
    }


        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }


}