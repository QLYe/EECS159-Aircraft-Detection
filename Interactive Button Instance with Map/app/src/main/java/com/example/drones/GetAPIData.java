package com.example.drones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class GetAPIData{

    interface RequestUser{
        @GET("/api/users/{uid}")
        Call<UserData> getUser(@Path("uid") String uid);

        //@POST("/api/users")
        //Call<ResponsePost> postUser(@Body RequestPost requestPost);
    }

    TextView textView;

    //@Override
    String getData () {
        final String[] result = {"haha"};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RequestUser requestUser = retrofit.create(RequestUser.class);
        result[0] = "yoyo";
        requestUser.getUser("3").enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                result[0] = response.body().data.first_name;
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                textView.setText(t.getMessage());
            }

        });
        return result[0];

//        requestUser.getUser("3").enqueue(new Callback<UserData>() {
//            @Override
//            public void onResponse(Call<UserData> call, Response<UserData> response) {
//                textView.setText(response.body().data.first_name);
//            }
//
//            @Override
//            public void onFailure(Call<UserData> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });

    }
}
/*import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
public class GetAPIData {
    interface RequestUser{
        @GET("v2/point/{lat}/{lon}/{rad}")
        Call<UserData> getAircraft(
                @Path("lat") String lat,
                @Path("lon") String lon,
                @Path("rad") String rad
        );
    }
    String getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.adsb.one/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.getAircraft("37.7749", "-122.4194", "50").enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response){
                return response.body().getAC();
            }
            public void onFailure(Call<UserData> call, Throwable t){

            }

        });
    }

}*/
