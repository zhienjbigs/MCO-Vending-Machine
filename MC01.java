/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mc01;


import java.util.*;

public class MC01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RegularVendingMachine rvm = null;

        while (true) {
           System.out.println("Menu:\n1. Create Vending Machine\n2. Test Vending Machine\n3. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    rvm = new RegularVendingMachine();
                    System.out.println("Vending machine created.");
                    break;
                case 2:
                    if (rvm == null) {
                        System.out.println("No vending machine created yet.");
                        break;
                    }
                    // Test vending machine (you can add more test cases)
                    rvm.addItem("Fish Cake", 25.0, 10);
                    rvm.addItem("Aji Tamago", 45.0, 10);
                    rvm.addItem("Fried Tofu", 30.0, 10);
                    rvm.insertMoney(200.0);
                    rvm.vend("Fish Cake");
                    rvm.returnChange();
                    break;
                case 3:
                    System.out.println("Exiting.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
