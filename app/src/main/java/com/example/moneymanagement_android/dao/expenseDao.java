package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moneymanagement_android.models.expense;

import java.util.List;

@Dao
public interface expenseDao {
    @Insert
    void insert(expense exp);

    @Update
    void update(expense exp);

    @Delete
    void delete(expense exp);

    @Query("Select * from expense")
    LiveData<List<expense>> getListExpense();

    @Query("Select * from expense Where idbudget = :id")
    LiveData<List<expense>> getAllExpensebyBudget(int id);
}