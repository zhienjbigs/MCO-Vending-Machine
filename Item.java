// Class for item attributes
public class Item {
    private String name; //name of the item
    private double price; //price of the item
    private int quantity; //how many of the item

    public Item(String name, double price, int quantity) { //constructors for class attributes
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    // when adding the quantity of an item, this function is used.
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
