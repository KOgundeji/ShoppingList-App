package com.kunle.shoppinglistapp.models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.data.ShoppingRepository;

import java.util.ArrayList;
import java.util.List;


//holds all of the data needed for the UI
public class ShoppingViewModel extends AndroidViewModel {

    private static ShoppingRepository repository;
    private final LiveData<List<MealWithIngredients>> allMealsWithIngredients;
    private final LiveData<List<Meal>> allMeals;
    private final LiveData<List<Food>> allFood;
    private final LiveData<List<GroceryList>> allGroceries;
    private final LiveData<List<Settings>> allSettings;


    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        repository = new ShoppingRepository(application);
        allMealsWithIngredients = repository.getAllMealsWithIngredients();
        allMeals = repository.getAllMeals();
        allFood = repository.getAllFood();
        allGroceries = repository.getAllGroceries();
        allSettings = repository.getAllSettings();
    }


    //ALL QUERIES NEED AN OBJECT!!! Therefore they can't be static

    public LiveData<List<MealWithIngredients>> getAllMealsWithIngredients() {
        return allMealsWithIngredients;
    }

    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public LiveData<List<Food>> getAllFood() {
        return allFood;
    }

    public LiveData<List<GroceryList>> getAllGroceries() {
        return allGroceries;
    }

    public LiveData<List<Settings>> getAllSettings() {
        return allSettings;
    }

    public void deleteAllFood() {
        repository.deleteAllFood();
    }

    public void deleteAllGroceries() {
        repository.deleteAllGroceries();
    }

    public void deleteAllSettings() {
        repository.deleteAllSettings();
    }

    public LiveData<Integer> checkSettingsExist(String name) {
        return repository.checkSettingsExists(name);
    }

    public LiveData<Integer> checkSetting(String name) {
        return repository.checkSetting(name);
    }

    public List<GroceryList> getFilteredGroceries(String search) {
        return repository.getFilteredGroceries(search);
    }

    public static void insertMeal(Meal meal) {
        repository.insertMeal(meal);
    }

    public static void updateMeal(Meal meal) {
        repository.updateMeal(meal);
    }

    public static void deleteMeal(Meal meal) {
        repository.deleteMeal(meal);
    }

    public static void getMeal(Integer id) {
        repository.getMeal(id);
    }


    public static void insertFood(Food food) {
        repository.insertFood(food);
    }

    public static void updateFood(Food food) {
        repository.updateFood(food);
    }

    public static void deleteFood(Food food) {
        repository.deleteFood(food);
    }

    public static void getFood(Integer id) {
        repository.getFood(id);
    }



    public static void insertGrocery(GroceryList item) {
        repository.insertGroceries(item);
    }

    public static void updateGrocery(GroceryList item) {
        repository.updateGroceries(item);
    }

    public static void deleteGrocery(GroceryList item) {
        repository.deleteGroceries(item);
    }


    public static void insertSettings(Settings settings) {
        repository.insertSetting(settings);
    }

    public static void updateSettings(Settings settings) {
        repository.updateSetting(settings);
    }

    public static void deleteSettings(Settings settings) {
        repository.deleteSetting(settings);
    }

    public static void insertPair(MealFoodMap crossRef) {
        repository.insertPair(crossRef);
    }

    public static void updatePair(MealFoodMap crossRef) {
        repository.updatePair(crossRef);
    }

    public static void deletePair(MealFoodMap crossRef) {
        repository.deletePair(crossRef);
    }

}

