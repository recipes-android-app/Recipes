package com.example.recipes.database;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.recipes.database.interfaces.IRecipesDBHandler;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

import java.util.LinkedList;
import java.util.List;

public class RecipesFirebaseHandler implements IRecipesDBHandler{
    public FirebaseFirestore db;

    public RecipesFirebaseHandler(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllRecipes(RecipeModel.GetAllRecipesListener callback) {
        db.collection(Recipe.COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Recipe st = Recipe.fromJson(json.getData());
                                list.add(st);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void getRecipesOfUser(String userId, RecipeModel.GetAllRecipesListener callback) {
        db.collection(Recipe.COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Recipe> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Recipe st = Recipe.fromJson(json.getData());
                                list.add(st);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }
    public void addRecipe(Recipe recipe, ModelClient.Listener<Void> listener) {
        db.collection(Recipe.COLLECTION).document(recipe.getId()).set(recipe.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

}
