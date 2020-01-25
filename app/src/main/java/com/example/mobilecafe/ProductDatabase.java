package com.example.mobilecafe;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    private static volatile ProductDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ProductDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ProductDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_db").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            databaseWriterExecutor.execute(() ->{
                ProductDao dao = INSTANCE.productDao();
                dao.deleteAll();

                Product product = new Product("Latte", "500ml");
                dao.insert(product);
                dao.insert(product);
            });
        }
    };
}
