package com.example.mobilecafe;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 3, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();

    private static volatile ProductDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(" ALTER TABLE Product " + " ADD COLUMN price REAL NOT NULL DEFAULT '' ");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(" ALTER TABLE Product " + "  DROP COLUMN price");
        }
    };

    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ProductDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ProductDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_db_2").addCallback(sRoomDatabaseCallback).build();
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

                Product product = new Product("Latte", "500ml", "3.0");
                dao.insert(product);
                product = new Product("Iced Frappe", "500ml", "4.5");
                dao.insert(product);
            });
        }
    };
}
