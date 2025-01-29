package com.mindhub.emailMicroservice.events;

public class ProductUpdatedEvent {

    private Long id;
    private String name;
    private Integer stock;
    private Double price;

    public ProductUpdatedEvent(Long id, String name, Integer stock, Double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductUpdatedEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
