package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChangeMealIngredientsAdapter extends RecyclerView.Adapter<ChangeMealIngredientsAdapter.MealViewHolder> {

    private final Context context;
    private final List<Meal> mealList;
    private boolean visible = false;
    private List<Meal> delete_list;
    private LifecycleOwner owner;

    public ChangeMealIngredientsAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
        delete_list = new ArrayList<>();
    }

    public ChangeMealIngredientsAdapter(Context context, List<Meal> mealList, LifecycleOwner owner) {
        this(context, mealList);
        this.owner = owner;
        delete_list = new ArrayList<>();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.inner_cardview, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if (visible) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        String name = mealList.get(position).getName();
        holder.item.setText(name);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public List<Meal> getDelete_list() {
        return delete_list;
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;
        private final CheckBox checkBox;
        private List<Food> currentFoodList;
        private EditFoodFromEditMealAdapter editFoodFromEditMealAdapter;
        private long mealId;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            ImageView clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        delete_list.add(mealList.get(getAdapterPosition()));
                    } else if (!checkBox.isChecked()) {
                        delete_list.remove(mealList.get(getAdapterPosition()));
                    }
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View new_view = inflater.inflate(R.layout.edit_meal_ingredients, null);

                    RecyclerView mealIngredientsRecycler = new_view.findViewById(R.id.modify_meal_recycler);
                    EditText mealName = new_view.findViewById(R.id.mealName);
                    ImageView add = new_view.findViewById(R.id.modify_meal_ingredient_add);
                    ImageView delete = new_view.findViewById(R.id.modify_meal_ingredient_delete);
                    ImageView cancel = new_view.findViewById(R.id.modify_meal_ingredient_back);
                    ImageView final_delete = new_view.findViewById(R.id.modify_trash_can);
                    ImageView final_cancel = new_view.findViewById(R.id.modify_trash_can_cancel);
                    LinearLayout button_bar = new_view.findViewById(R.id.modify_meal_button_bar);
                    LinearLayout final_delete_layout = new_view.findViewById(R.id.modify_trash_can_layout);

                    mealName.setText(mealList.get(getAdapterPosition()).getName());
                    builder.setView(new_view);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    ShoppingViewModel.mainMealsWithIngredientsList.observe(owner, new Observer<List<MealWithIngredients>>() {
                        @Override
                        public void onChanged(List<MealWithIngredients> mealWithIngredients) {
                            currentFoodList = mealWithIngredients.get(position).getFoodList();
                            mealId = mealWithIngredients.get(position).getMeal().getMealId();

                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            editFoodFromEditMealAdapter = new EditFoodFromEditMealAdapter(context, currentFoodList);
                            mealIngredientsRecycler.setHasFixedSize(true);
                            mealIngredientsRecycler.setLayoutManager(layoutManager);
                            mealIngredientsRecycler.setAdapter(editFoodFromEditMealAdapter);
                        }
                    });


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builderAdd = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View new_view = inflater.inflate(R.layout.add_ingredients, null);

                            TextInputEditText ingredient = new_view.findViewById(R.id.add_new_ingredient_for_meal);
                            TextInputEditText quantity = new_view.findViewById(R.id.add_new_quantity_for_meal);
                            AutoCompleteTextView dropdown = new_view.findViewById(R.id.add_new_category_for_meal);
                            LinearLayout confirm = new_view.findViewById(R.id.grocery_edit_confirm);
                            LinearLayout back = new_view.findViewById(R.id.grocery_edit_back);

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(context, R.layout.category_list_items,
                                            ShoppingViewModel.getFoodCategories());
                            dropdown.setAdapter(adapter);

                            final String[] selectedItem = {"Uncategorized"};

                            dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectedItem[0] = adapterView.getItemAtPosition(i).toString();
                                }
                            });

                            builderAdd.setView(new_view);
                            AlertDialog dialog = builderAdd.create();

                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String created_name = String.valueOf(ingredient.getText()).trim();
                                    String created_quantity = String.valueOf(quantity.getText()).trim();
                                    String created_category = selectedItem[0];

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Food preExistingFood = ShoppingViewModel.getFood(created_name);
                                            Food newFood;
                                            long foodId;

                                            if (Objects.nonNull(preExistingFood)) {
                                                boolean preExisting_inGroceryList = preExistingFood.isInGroceryList();
                                                newFood = new Food(created_name, created_quantity, created_category, preExisting_inGroceryList);
                                                if (preExistingFood.getQuantity().equals(created_quantity)) {
                                                    foodId = preExistingFood.getFoodId();
                                                    newFood.setFoodId(foodId);
                                                    ShoppingViewModel.updateFood(newFood);
                                                } else {
                                                    foodId = ShoppingViewModel.insertFood(newFood);
                                                }
                                            } else {
                                                newFood = new Food(created_name, created_quantity, created_category, false);
                                                foodId = ShoppingViewModel.insertFood(newFood);
                                            }
                                            ShoppingViewModel.insertPair(new MealFoodMap(mealId,foodId));
                                        }
                                    }).start();

                                    dialog.dismiss();
                                }
                            });

                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //shows new linear layout and lets that choose what to delete
                            button_bar.setVisibility(View.GONE);
                            final_delete_layout.setVisibility(View.VISIBLE);
                            editFoodFromEditMealAdapter.setVisibility(true);
                            editFoodFromEditMealAdapter.notifyDataSetChanged();

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    final_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            for (Long foodId : editFoodFromEditMealAdapter.getClass_Delete_list()) {
                                ShoppingViewModel.deletePair(new MealFoodMap(mealId,foodId));
                            }

                            button_bar.setVisibility(View.VISIBLE);
                            final_delete_layout.setVisibility(View.GONE);
                            editFoodFromEditMealAdapter.setVisibility(false);
                            notifyDataSetChanged();

                        }
                    });

                    final_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            button_bar.setVisibility(View.VISIBLE);
                            final_delete_layout.setVisibility(View.GONE);
                            editFoodFromEditMealAdapter.setVisibility(false);
                            editFoodFromEditMealAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }
}

