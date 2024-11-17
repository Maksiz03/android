package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.dao.FavoriteDao
import com.example.myapplication.network.DotaApi
import com.example.myapplication.network.DotaApiImpl
import com.example.myapplication.repository.MatchRepository
import com.example.myapplication.repository.MatchRepositoryImpl
import com.example.myapplication.usecase.GetMatchesByIdsUseCase
import com.example.myapplication.usecase.GetRecentMatchesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, // or just use `context`
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideDotaApi(): DotaApi {
        return DotaApiImpl()
    }

    @Provides
    @Singleton
    fun provideMatchRepository(
        favoriteDao: FavoriteDao,
        dotaApi: DotaApi
    ): MatchRepository {
        return MatchRepositoryImpl(favoriteDao, dotaApi)
    }

    @Provides
    @Singleton
    fun provideGetRecentMatchesUseCase(
        matchRepository: MatchRepository
    ): GetRecentMatchesUseCase {
        return GetRecentMatchesUseCase(matchRepository)
    }

    @Provides
    @Singleton
    fun provideGetMatchesByIdsUseCase(
        matchRepository: MatchRepository
    ): GetMatchesByIdsUseCase {
        return GetMatchesByIdsUseCase(matchRepository)
    }
}
