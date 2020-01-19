package com.example.mobilecafe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private LiveData<List<Product>> products;

    public ProductViewModel(@NonNull Application application){
        super(application);
        productRepository = new ProductRepository(application);
        products = productRepository.findAllProducts();
    }

    public LiveData<List<Product>> findAll(){
        return products;
    }

    public void insert(Product product){
        productRepository.insert(product);
    }
}