package com.adamkobus.android.vm.demo.di

import com.adamkobus.android.vm.demo.nav.DemoNavActionVerifier
import com.adamkobus.compose.navigation.ComposeNavigation
import com.adamkobus.compose.navigation.NavActionVerifier
import com.adamkobus.compose.navigation.NavigationConsumer
import com.adamkobus.compose.navigation.NavigationStateSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module(includes = [NavigationModuleBinds::class])
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun providesNavigationConsumer(): NavigationConsumer {
        return ComposeNavigation.getNavigationConsumer()
    }

    @Provides
    fun provideNavigationStateSource(): NavigationStateSource =
        ComposeNavigation.getNavigationStateSource()
}

@InstallIn(SingletonComponent::class)
@Module
internal interface NavigationModuleBinds {

    @Binds
    @IntoSet
    fun bindDefaultNavActionVerifier(impl: DemoNavActionVerifier): NavActionVerifier
}
