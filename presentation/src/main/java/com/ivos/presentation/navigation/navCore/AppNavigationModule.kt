package com.ivos.presentation.navigation.navCore

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppNavigationModule {

    @Binds
    @Singleton
    fun bindAppNavigator(appNavigator: AppNavigatorImpl): AppNavigator

}
