package com.phoenix.web.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import com.phoenix.data.dto.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.service.ProductService;
import com.phoenix.web.exceptions.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductRestController {


    @Autowired
    ProductService productService;



    @GetMapping()
    public ResponseEntity<?> findAllProduct(){

        List<Product> productList = productService.getAllProduct();
        return ResponseEntity.ok().body(productList);

    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto ){

        try{
            Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.ok().body(savedProduct);
        }
        catch (BusinessLogicException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody JsonPatch productPatch){

        try {
            Product updatedProduct = productService.updateProductDetails(id, productPatch);
            log.info("updated product {}", updatedProduct);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }
        catch (BusinessLogicException e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return null;
    }




}

