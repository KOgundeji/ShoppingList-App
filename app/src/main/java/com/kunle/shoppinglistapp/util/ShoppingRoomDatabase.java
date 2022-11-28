package com.kunle.shoppinglistapp.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.kunle.shoppinglistapp.data.FoodDao;
import com.kunle.shoppinglistapp.data.MealDao;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This is creating the actual RoomDatabase, which is comprised of the Entities, DAO, and SQLite to form our main database

@Database(entities = {Food.class, Meal.class}, version = 1, exportSchema = false)
public abstract class ShoppingRoomDatabase extends RoomDatabase {

    public abstract FoodDao foodDao();

    public abstract MealDao mealDao();

    public static final int NUMBER_OF_THREADS = 4; //not sure why we chose this #

    private static volatile ShoppingRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //the executor is going to write things into the database, but not through the main thread
    //this will create a new thread for us to use

    public static ShoppingRoomDatabase getDatabase(final Context context) {
        if (context == null) {
            synchronized (ShoppingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShoppingRoomDatabase.class, "shopping_database")
//                            .addCallback(sRoomDatabaseCallback) //may just delete this part
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    //I think the only reason this is here is to create something when the database first runs
//    private static final RoomDatabase.Callback sRoomDatabaseCallback =
//            new RoomDatabase.Callback() {
//                @Override
//                public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                    super.onCreate(db);
//                    databaseWriteExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            FoodDao foodDao = INSTANCE.foodDao();
//                            foodDao.deleteAll(); //to start fresh
//
//                            Food food = new Food();
//                            foodDao.insert(food);
//                        }
//                    });
//                }
//
//            };
}
