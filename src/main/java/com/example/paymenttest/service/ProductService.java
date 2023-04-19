package com.example.paymenttest.service;

import com.example.paymenttest.dto.ProductDto;
import com.example.paymenttest.entity.Product;
import com.example.paymenttest.exeption.ProductNotExistException;
import com.example.paymenttest.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;

    public void createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        repository.save(product);
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts(){
        List<Product> allProducts = repository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: allProducts){
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, Long productId) throws Exception {
        Optional<Product> optionalProduct = repository.findById(productId);
        if(!optionalProduct.isPresent()){
            throw new Exception("product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        repository.save(product);
    }

    public Product findById(Long productId) throws ProductNotExistException{
        Optional<Product> optionalProduct = repository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("product is invalid" + productId);
        }
        return optionalProduct.get();
    }

    public void deleteProductById(Long productId){
        repository.deleteById(productId);
    }
}
