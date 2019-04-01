package com.imran.shaaditestapp.application;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.imran.shaaditestapp.dependencies.component.ApplicationComponent;
import com.imran.shaaditestapp.dependencies.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent appComponent = DaggerApplicationComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}