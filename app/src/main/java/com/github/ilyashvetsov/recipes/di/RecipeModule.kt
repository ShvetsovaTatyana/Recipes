package com.github.ilyashvetsov.recipes.di

import android.content.Context
import androidx.room.Room
import com.github.ilyashvetsov.recipes.data.CategoriesDao
import com.github.ilyashvetsov.recipes.data.RecipeApiService
import com.github.ilyashvetsov.recipes.data.RecipeDao
import com.github.ilyashvetsov.recipes.data.RecipeDataBase
import com.github.ilyashvetsov.recipes.ui.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Provides
    fun providesDataBase(@ApplicationContext context: Context): RecipeDataBase =
        Room.databaseBuilder(
            context,
            RecipeDataBase::class.java,
            "app_database"
        ).build()

    @Provides
    fun provideCategoriesDao(recipeDataBase: RecipeDataBase): CategoriesDao =
        recipeDataBase.categoriesDao()

    @Provides
    fun provideRecipeDao(recipeDataBase: RecipeDataBase): RecipeDao =
        recipeDataBase.recipeDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }
}