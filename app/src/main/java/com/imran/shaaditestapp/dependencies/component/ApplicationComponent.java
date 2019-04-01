package com.imran.shaaditestapp.dependencies.component;

import android.app.Application;

import com.imran.shaaditestapp.application.BaseApplication;
import com.imran.shaaditestapp.dependencies.activityBuilder.ActivityBuilder;
import com.imran.shaaditestapp.dependencies.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBuilder.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {


    void inject(BaseApplication myApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
