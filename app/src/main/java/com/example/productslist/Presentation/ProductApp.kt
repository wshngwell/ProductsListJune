package com.example.productslist.presentation

import android.app.Application
import com.example.productslist.di.DaggerApplicationComponent
import dagger.Component

class ProductApp:Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)

    }
}