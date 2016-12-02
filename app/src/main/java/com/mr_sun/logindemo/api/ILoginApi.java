package com.mr_sun.logindemo.api;

import com.mr_sun.logindemo.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Mr_Sun on 2016/12/1.
 */

public interface ILoginApi {

    @POST("/services/loginService")
    rx.Observable<User> loginForMobile(@HeaderMap Map<String,String> headerMap, @Body String body);
    
}
