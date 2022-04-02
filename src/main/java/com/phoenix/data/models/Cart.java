package com.phoenix.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER )
    private List<Item> itemList;


    private Double totalPrice;

    public void addItem(Item item){
        if (itemList == null){
            itemList = new ArrayList<>();
        }

        itemList.add(item);
    }
}