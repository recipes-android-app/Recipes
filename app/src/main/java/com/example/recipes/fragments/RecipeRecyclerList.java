package com.example.recipes.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.models.Recipe;
import java.util.List;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeName;
    TextView recipeOwner;
    ImageButton deleteRecipeBtn;
    ImageButton editRecipeBtn;
    List<Recipe> recipes;
    Boolean hasAccess;

    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener,RecipeRecyclerAdapter.OnEditButtonClickListener listenerEdit, List<Recipe> recipes, Boolean hasAccess) {
        super(itemView);
        this.recipes = recipes;
        this.hasAccess = hasAccess;
        recipeName = itemView.findViewById(R.id.recipe_list_recipe_name);
        recipeOwner = itemView.findViewById(R.id.recipe_list_recipe_owner);
        deleteRecipeBtn = itemView.findViewById(R.id.recipe_list_recipe_delete_btn);
        editRecipeBtn = itemView.findViewById(R.id.recipe_list_recipe_edit_btn);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                Recipe recipe = recipes.get(pos);
                listener.onItemClick(recipe.id);
            }
        });

        editRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                Recipe recipe = recipes.get(pos);
                listenerEdit.onItemClick(recipe.id);
            }
        });
    }

    public void bind(Recipe recipe, int pos) {
        recipeName.setText(recipe.name);
//        recipeOwner.setText(recipe.owner);
        int RecipeActionsStatus = hasAccess ? View.VISIBLE : View.GONE;
        deleteRecipeBtn.setVisibility(RecipeActionsStatus);
        editRecipeBtn.setVisibility(RecipeActionsStatus);
    }
}

class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    OnItemClickListener listener;
    OnEditButtonClickListener listenerEdit;

    public static interface OnItemClickListener {
        void onItemClick(String recipeId);
    }

    public static interface OnEditButtonClickListener {
        void onItemClick(String recipeId);
    }

    LayoutInflater inflater;
    List<Recipe> recipes;
    Boolean hasAccess;

    public RecipeRecyclerAdapter(LayoutInflater inflater, List<Recipe> recipes, Boolean hasAccess) {
        this.inflater = inflater;
        this.recipes = recipes;
        this.hasAccess = hasAccess;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    void setOnEditButtonClickListener(OnEditButtonClickListener listenerEdit) {
        this.listenerEdit = listenerEdit;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_list_recipe, parent, false);
        return new RecipeViewHolder(view, listener,listenerEdit, recipes, hasAccess);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe, position);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }
}
