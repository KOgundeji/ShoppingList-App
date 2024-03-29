package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.MealFoodMap;

import java.util.List;

@Dao
public interface MealWithIngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPair(MealFoodMap crossRef);

    @Delete
    void deletePair(MealFoodMap crossRef);

    @Update
    void updatePair(MealFoodMap crossRef);

    @Query("DELETE FROM MealFoodMap WHERE mealId = :mealId")
    void deleteSpecificMealIngredients(Long mealId);

    @Query("DELETE FROM MealFoodMap")
    void deleteAllMealWithIngredients();

    @Transaction
    @Query("SELECT * FROM meal_table WHERE mealId = :mealId")
    MealWithIngredients getSpecificMealWithIngredients(Long mealId);

    @Transaction
    @Query("SELECT * FROM meal_table")
    LiveData<List<MealWithIngredients>> getAllMealsWithIngredients();

}
