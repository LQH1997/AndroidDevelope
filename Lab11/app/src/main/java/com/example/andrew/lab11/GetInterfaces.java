package com.example.andrew.lab11;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Andrew on 2017/12/13.
 */

public class GetInterfaces {
    public interface GetUser {
        @GET("users/{user}")
        Call<ResponseBody> listRepos(@Path("user") String user);
    }

    public interface GetUserRepo {
        @GET("users/{user}/repos")
        Call<ResponseBody> listRepos(@Path("user") String user);
    }
}
