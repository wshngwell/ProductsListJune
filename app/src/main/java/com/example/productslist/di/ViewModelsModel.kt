package com.example.productslist.di

import androidx.lifecycle.ViewModel
import com.example.productslist.presentation.MainViewModel
import com.example.productslist.presentation.ProductItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModel {


    @Binds
    @IntoMap
    @VIewModelKey(MainViewModel::class)
    fun bindsMainViewModel(mainViewModel: MainViewModel):ViewModel


    @Binds
    @IntoMap
    @VIewModelKey(ProductItemViewModel::class)
    fun bindsItemViewModel(productItemViewModel: ProductItemViewModel):ViewModel
}