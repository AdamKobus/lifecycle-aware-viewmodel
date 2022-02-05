package com.adamkobus.android.vm.demo.di

import com.adamkobus.android.vm.demo.nav.DemoNavActionVerifier
import com.adamkobus.compose.navigation.NavActionVerifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
interface DemoBinds {

    @Binds
    @IntoSet
    fun bindsDemoNavActionVerifier(impl: DemoNavActionVerifier): NavActionVerifier
}
