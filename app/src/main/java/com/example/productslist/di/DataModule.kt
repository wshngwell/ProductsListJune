package com.example.productslist.di

import android.app.Application
import com.example.productslist.data.database.AppDataBase
import com.example.productslist.data.database.AppDataBaseDao
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
companion object{
    @Provides
    fun providesAppDataBaseDao(application: Application): AppDataBaseDao {
        return AppDataBase.createInstance(application).getDao()
    }
}

}