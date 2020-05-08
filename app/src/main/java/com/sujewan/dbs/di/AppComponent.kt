package com.sujewan.dbs.di

import android.app.Application
import com.sujewan.dbs.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ActivityModule::class),
    (FragmentModule::class),
    (ViewModelModule::class),
    (DatabaseModule::class),
    (NetworkModule::class)])
interface AppComponent {

    // Create an instance of the Component
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder
        @BindsInstance
        fun baseUrl(url : String) : AppComponent.Builder
        fun build(): AppComponent
    }

    fun inject(instance: MyApplication)
}