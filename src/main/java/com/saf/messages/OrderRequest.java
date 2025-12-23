package com.saf.messages;

import com.saf.core.Message;

public class OrderRequest implements Message {
    // Utilise EXACTEMENT ces noms là
    private String dishName;

    public OrderRequest() {}

    public OrderRequest(String dishName) {
        this.dishName = dishName;
    }

    // Vérifie bien que les getters correspondent : getDishName() et getCustomerName()
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    }