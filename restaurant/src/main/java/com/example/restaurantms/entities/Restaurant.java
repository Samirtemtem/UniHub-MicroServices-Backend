package com.example.restaurantms.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@AllArgsConstructor
public class Restaurant implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomRestaurant, menu;
    private String imageUrl;
    private String specialite;

    // Adding explicit no-argument constructor
    public Restaurant() {
        // This empty constructor is needed by JPA/Hibernate
    }

    public Restaurant(String nomRestaurant, String menu, String imageUrl,String specialite) {
        super();
        this.nomRestaurant = nomRestaurant;
        this.menu = menu;
        this.imageUrl=imageUrl;
        this.specialite=specialite;
    }

    // Manual getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
