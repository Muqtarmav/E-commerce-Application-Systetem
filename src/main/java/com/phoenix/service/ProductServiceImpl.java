package com.phoenix.service;

import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.phoenix.data.dto.ProductDto;
import com.phoenix.data.models.Product;
import com.phoenix.data.repository.ProductRepository;
import com.phoenix.service.cloud.CloudinaryService;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
            @Qualifier("cloudinary-service")
    CloudinaryService cloudinaryService;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long productId) throws ProductDoesNotExistException {
        if (productId == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        Optional<Product> queryResult = productRepository.findById(productId);
        if (queryResult.isPresent()) {
            return queryResult.get();
        }

        throw new ProductDoesNotExistException("Product with ID : " + productId + ": does not exists");
    }


    @Override
    public Product createProduct(ProductDto productDto) throws BusinessLogicException {

        if (productDto == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Product> query = productRepository.findByName(productDto.getName());
        if (query.isPresent()) {
            throw new BusinessLogicException("Product with name" + productDto.getName() + "already exists");
        }

            Product product = new Product();

        try {
            if (productDto.getImage() != null) {
                Map<?, ?> getUpload = cloudinaryService.upload(productDto.getImage().getBytes(),
                        ObjectUtils.asMap("public_id", "inventory/" + productDto.getImage().getOriginalFilename()));
                product.setImageUrl(getUpload.get("url").toString());
            }
        }

        catch (IOException e){
            e.printStackTrace();
        }


        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());

        return productRepository.save(product);
    }



    private Product saveOrUpdate(Product product) throws BusinessLogicException {
        if (product == null) {
            throw new BusinessLogicException("product cannot be null");
        }
        return productRepository.save(product);

    }



    @Override
    public Product updateProductDetails(Long ProductId, JsonPatch productPatch) throws BusinessLogicException {
        Optional<Product> productQuery = productRepository.findById(ProductId);
        if (productQuery.isEmpty()) {
            throw new BusinessLogicException("Product with Id" + ProductId + " does not exist");
        }
        Product targetProduct = productQuery.get();
        try {
            targetProduct = applyPatchToProduct(productPatch, targetProduct);
            return saveOrUpdate(targetProduct);

        } catch (JsonPatchException | JsonProcessingException | BusinessLogicException je) {
            throw new BusinessLogicException("update failed");
        }
    }



    private Product applyPatchToProduct(JsonPatch productPatch, Product targetProduct) throws BusinessLogicException, JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = productPatch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));

        return objectMapper.treeToValue(patched, Product.class);
    }

}




