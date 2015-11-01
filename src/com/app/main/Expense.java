package com.app.main;

/**
 * @author Nick Mckloski.
 */
public class Expense {

    private String name;
    private String vendor;
    private String date;
    private double cost;

    public Expense(String name, String vendor, String date, double cost) {
        this.name = name;
        this.vendor = vendor;
        this.date = date;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
