import android.content.Context
import androidx.room.Room
import com.example.myapplication.dao.FavoriteDao
import com.example.myapplication.database.AppDatabase

object DatabaseModule {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "app_database"
        ).build()
    }

    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}