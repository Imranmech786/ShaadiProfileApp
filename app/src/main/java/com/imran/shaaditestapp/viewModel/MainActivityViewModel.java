package com.imran.shaaditestapp.viewModel;

import android.arch.lifecycle.ViewModel;

import com.imran.shaaditestapp.model.JsonResponse;
import com.imran.shaaditestapp.model.Result;
import com.imran.shaaditestapp.retrofit.APIInterface;
import com.imran.shaaditestapp.utils.StateLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {

    private APIInterface apiInterface;

    private StateLiveData<List<Result>> jsonResponseLiveData = new StateLiveData<>();

    public MainActivityViewModel(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public StateLiveData<List<Result>> getJsonResponseLiveData() {
        return jsonResponseLiveData;
    }

    public void getListFromApi(int count) {
        apiInterface.getList(count)
                .enqueue(new Callback<JsonResponse>() {
                    @Override
                    public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Result> resultList = response.body().getResults();
                            jsonResponseLiveData.postSuccess(resultList);
                        } else {
                            jsonResponseLiveData.postComplete();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponse> call, Throwable t) {
                        jsonResponseLiveData.postError(t);
                    }
                });
    }
}
