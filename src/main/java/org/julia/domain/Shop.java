package org.julia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: Миша
 * Date: 18.09.14
 */

@Entity
@Table(name = "Shop")
@NamedQuery(name = "Shop.findAll", query = "Select s from Shop s")
public class Shop implements BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotNull(message = "shop.name.size")
    @Size(min = 1, max=20, message = "shop.name.empty")
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "address")
    private String address;

    public int getVersion() {
        return version;
    }

    @Version
    @Column(name = "shops_version")
    private int version;

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    @OneToMany(mappedBy="shop")
    @JsonIgnore
    private List<Purchase> purchases;

    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
    }
    public void removePurchase(Purchase purchase){
        purchases.remove(purchase);
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (!name.equals(shop.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

