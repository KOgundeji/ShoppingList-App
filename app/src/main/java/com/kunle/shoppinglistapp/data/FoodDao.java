package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Food;
import java.util.List;

@Dao
public interface FoodDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Update
    void updateFood(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllFood();

    @Query("SELECT * FROM food_table WHERE name = :name LIMIT 1")
    Food getFood(String name);

    @Query("SELECT * FROM food_table WHERE grocery_list")
    LiveData<List<Food>> getAllGroceries();

    @Query("SELECT * FROM food_table")
    LiveData<List<Food>> getAllFood();
}
