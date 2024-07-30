package com.example.productslist.di

import android.app.Application
import com.example.productslist.presentation.MainActivity
import com.example.productslist.presentation.ProductItemFragment
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelsModel::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: ProductItemFragment)



    @Component.Factory
    interface ApplicationFactory {
        fun create(
           @BindsInstance application: Application
        ): ApplicationComponent
    }
}