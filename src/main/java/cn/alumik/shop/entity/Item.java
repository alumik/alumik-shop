package cn.alumik.shop.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
