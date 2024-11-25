package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.entities.FavoriteItem

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(item: FavoriteItem)

    // Add this method for bulk insertion
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(items: List<FavoriteItem>)

    @Query("DELETE FROM favorites WHERE matchId = :matchId")
    suspend fun deleteFavorite(matchId: Long)

    @Query("SELECT * FROM favorites WHERE matchId IN (:matchIds)")
    suspend fun getMatchesByIds(matchIds: List<Long>): List<FavoriteItem>
}