package org.julia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: Миша
 * Date: 18.09.14
 */

@Entity
@Table(name = "Goods")
public class Goods implements BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotNull(message = "goods.name.size")
    @Size(min = 1, max=20, message = "goods.name.empty")
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;
    @Column(name = "description")
    private String description;

    @Version
    @Column(name = "goods_version")
    private int version;

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "goods")
    @JsonIgnore
    private List<Purchase> purchases;

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (!name.equals(goods.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getVersion() {
        return version;
    }
}