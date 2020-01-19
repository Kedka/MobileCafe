package com.example.mobilecafe;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> products;

    ProductRepository(Application application){
        ProductDatabase database = ProductDatabase.getDatabase(application);
        productDao = database.productDao();
        products = productDao.findAll();
    }

    LiveData<List<Product>> findAllProducts(){
        return products;
    }

    void insert(Product product){
        ProductDatabase.databaseWriterExecutor.execute(() -> {
            productDao.insert(product);
        });
    }
}
