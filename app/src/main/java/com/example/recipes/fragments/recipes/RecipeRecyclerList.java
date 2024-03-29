package com.example.recipes.fragments.recipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recipes.R;
import com.example.recipes.dto.Recipe;
import com.example.recipes.utils.ImageHelper;
import java.util.List;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeName;
    ImageButton deleteRecipeBtn;
    ImageButton editRecipeBtn;
    List<Recipe> recipes;
    Boolean hasAccess;
    ImageView recipeImage;
    Recipe clickedRecipe;

    public RecipeViewHolder(@NonNull View itemView, RecipeRecyclerAdapter.OnItemClickListener listener, RecipeRecyclerAdapter.OnDeleteButtonClickListener listenerDelete, RecipeRecyclerAdapter.OnEditButtonClickListener listenerEdit, List<Recipe> recipes, Boolean hasAccess) {
        super(itemView);
        this.recipes = recipes;
        this.hasAccess = hasAccess;
        recipeName = itemView.findViewById(R.id.recipe_list_recipe_name);
        deleteRecipeBtn = itemView.findViewById(R.id.recipe_list_recipe_delete_btn);
        editRecipeBtn = itemView.findViewById(R.id.recipe_list_recipe_edit_btn);
        recipeImage = itemView.findViewById(R.id.recipe_list_recipe_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(getRecipeByPosition());
            }
        });

        editRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerEdit.onItemClick(getRecipeByPosition());
            }
        });

        deleteRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerDelete.onItemClick(getRecipeByPosition());
            }
        });

    }

    public void bind(Recipe recipe, int pos) {
        recipeName.setText(recipe.name);
        int recipeActionsStatus = hasAccess ? View.VISIBLE : View.GONE;
        deleteRecipeBtn.setVisibility(recipeActionsStatus);
        editRecipeBtn.setVisibility(recipeActionsStatus);
        ImageHelper.insertImageByUrl(recipe, recipeImage);
        this.clickedRecipe= recipe;
    }

    private String getRecipeByPosition() {
        return this.clickedRecipe.id;
    }
}

class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    OnItemClickListener listener;
    OnEditButtonClickListener listenerEdit;
    OnDeleteButtonClickListener listenerDelete;

    public static interface OnItemClickListener {
        void onItemClick(String recipeId);
    }

    public static interface OnEditButtonClickListener {
        void onItemClick(String recipeId);
    }

    public static interface OnDeleteButtonClickListener {
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

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    void setOnEditButtonClickListener(OnEditButtonClickListener listenerEdit) {
        this.listenerEdit = listenerEdit;
    }

    void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listenerDelete) {
        this.listenerDelete = listenerDelete;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_list_recipe, parent, false);
        return new RecipeViewHolder(view, listener, listenerDelete, listenerEdit, recipes, hasAccess);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = this.recipes.get(position);
        holder.bind(recipe, position);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }
}
