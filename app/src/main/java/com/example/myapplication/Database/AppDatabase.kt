package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dao.FavoriteDao
import com.example.myapplication.entities.FavoriteItem

@Database(entities = [FavoriteItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}