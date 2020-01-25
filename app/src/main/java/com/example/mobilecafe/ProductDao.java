package com.example.mobilecafe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Query("SELECT * FROM product ORDER BY id")
    public LiveData<List<Product>> findAll();

    @Query("DELETE FROM product")
    public void deleteAll();
}
