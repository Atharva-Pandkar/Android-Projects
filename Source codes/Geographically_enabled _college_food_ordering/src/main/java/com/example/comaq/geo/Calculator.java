package com.example.comaq.geo;

public class Calculator {
    int sum=1, final_Total;

    public int getFinal_Total() {
        return final_Total;
    }

    public void setFinal_Total(int final_Total) {
        this.final_Total = final_Total;
    }

    public int add(int Price, int quantity) {
        sum = Price * quantity;
        return sum;
    }
}
