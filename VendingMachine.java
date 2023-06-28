/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mc01;
import java.util.*;

public abstract class VendingMachine {
    protected Map<String, Slot> inventory = new HashMap<>();
    protected Map<Double, Integer> change = new HashMap<>();
    protected double moneyInserted;

    public VendingMachine() {
        change.put(50.0, 10);
        change.put(20.0, 10);
        change.put(10.0, 10); 
        change.put(5.0, 10);
        change.put(1.0, 10);
    }

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
    double[] denominations = {50, 20, 10, 5, 1};

    for (double denomination : denominations) {
        int numberOfDenomination = (int) (moneyInserted / denomination);
        if (numberOfDenomination > 0) {
            if (change.get(denomination) >= numberOfDenomination) {
                System.out.println("Returning " + numberOfDenomination + " of " + denomination);
                moneyInserted -= numberOfDenomination * denomination;
                change.put(denomination, change.get(denomination) - numberOfDenomination);
            } else {
                // If the denomination is not available, it will skip to the next denomination
                System.out.println("Denomination " + denomination + " is not available.");
            }
        }
    }
    // If the machine can't provide full change, the remaining amount will be left in the machine
    if (moneyInserted > 0) {
        System.out.println("Couldn't return full change, remaining amount: " + moneyInserted);
    }
    moneyInserted = 0;
         }
    }

