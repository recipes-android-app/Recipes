package com.example.recipes.localdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.recipes.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Query("DELETE FROM User")
    void delete();
}
