package com.phoenix.data.dto;

import com.phoenix.data.models.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDto {

    private String name;
    private String description;
    private double price;
    private int quantity;
    //private String imageUrl;
    private MultipartFile image;

}

