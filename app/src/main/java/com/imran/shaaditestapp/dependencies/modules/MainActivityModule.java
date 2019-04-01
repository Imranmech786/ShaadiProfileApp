package com.imran.shaaditestapp.dependencies.modules;

import com.imran.shaaditestapp.retrofit.APIInterface;
import com.imran.shaaditestapp.viewModel.MainActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainActivityViewModel mainActivityViewModel(APIInterface apiInterface) {
        return new MainActivityViewModel(apiInterface);
    }
}
