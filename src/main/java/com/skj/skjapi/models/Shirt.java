//THIS ENTITY IS FOR THE API CONTROLLER
package com.skj.skjapi.models;

import javax.persistence.*;

@Entity
@Table(name = "shirts")
public class Shirt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private String size;
    @Column(name = "price")
    private String price;

    public Shirt() {
    }

    public Shirt(String name, String size, String price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public Shirt(Long id, String name, String size, String price) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
