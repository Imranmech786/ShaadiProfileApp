package com.imran.shaaditestapp.dependencies.activityBuilder;

import com.imran.shaaditestapp.activity.MainActivity;
import com.imran.shaaditestapp.dependencies.modules.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
