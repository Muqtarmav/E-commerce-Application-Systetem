package com.phoenix.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    //@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantityAdded;

//    public void setQuantityAdded(Integer quantityAdded){
//        if (product.getQuantity() >= quantityAdded)
//            this.quantityAdded = quantityAdded;
//        else{
//            this.quantityAdded = 0;
//        }
//        this.product = product;
//    }


    public Item(Product product, int quantityAdded) {
        if (quantityAdded <= product.getQuantity())
            this.quantityAdded = quantityAdded;
        else{
                this.quantityAdded = 0;
            }
            this.product = product;
        }



}
