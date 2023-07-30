import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

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
            }
        });

      
createSvmButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        svm = new SpecialVendingMachine();
        outputTextArea.append("\nSpecial vending machine created.\n");

        //Customizable products 
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
    }
});


        testRvmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rvm == null) {
                    outputTextArea.append("\nNo regular vending machine created yet.\n");
                    return;
                }
                rvm.testFeatures(outputTextArea);
            }
        });

        testSvmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (svm == null) {
                    outputTextArea.append("\nNo special vending machine created yet.\n");
                    return;
                }

                String input = JOptionPane.showInputDialog(MainApp.this,
                        "Enter the chosen items separated by commas.\n" +
                                "Ramen = Noodles,Chashu Pork,Tonkotsu Broth\n" +
                                "Burger = Bun,Patty,Lettuce,Cheese,Tomato,Onion,Pickles",
                        "Special Vending Machine", JOptionPane.PLAIN_MESSAGE);

                if (input != null && !input.isEmpty()) {
                    List<String> chosenItems = Arrays.asList(input.split(","));
                    String productName = svm.getCustomizableProducts().keySet().toArray(new String[0])[0];
                    svm.prepareCustomizableProduct(productName, chosenItems, outputTextArea);
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