package com.mr_sun.logindemo.presenter;

import android.util.Log;

import com.mr_sun.logindemo.api.ILoginApi;
import com.mr_sun.logindemo.convert.SoapConverterFactory;
import com.mr_sun.logindemo.ksoap2.transport.SoapHelper;
import com.mr_sun.logindemo.model.User;
import com.mr_sun.logindemo.view.ILoginView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr_Sun on 2016/12/1.
 * http://www.jianshu.com/p/6fc8c9081c64
 */

public class ILoginPresenterImpl implements ILoginPresenter {

    private static final String TAG = "ILoginPresenterImpl";
    private String baseUrl = "http://dev.boyuyun.com.cn:9018";
    private String loginMethodName = "loginForMobile";
    private String nameSpace = "http://webservice.im.boyuyun.com/";

    private ILoginView iLoginView;
    private Retrofit retrofit;
    private ILoginApi loginApi;

    private SoapHelper soapHelper;
    private Map<String,String> soapHeaderMap;
    private String mBody;

    public ILoginPresenterImpl(ILoginView view) {
        soapHelper = SoapHelper.getInstance();
        this.iLoginView = view;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(SoapConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        loginApi = retrofit.create(ILoginApi.class);
    }

    @Override
    public void login(String username, String pwd) {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("userName", username);
        properties.put("pwd", pwd);

        List<Object> getParamters =  SoapHelper.getInstance().getParams(loginMethodName,nameSpace,properties);
        if(getParamters!=null){
            soapHeaderMap = (Map<String, String>) getParamters.get(0);
            mBody = new String((byte[]) getParamters.get(1));
        }
        loginApi.loginForMobile(soapHeaderMap, mBody)
                .subscribeOn(Schedulers.io())
                //指定回调在哪执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User responseBody) {
                        String respone = responseBody.getMessage();
                        Log.v(TAG, "respone:" + respone);
                        iLoginView.displayLoginMessage(respone);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.v(TAG, "error:" + throwable.toString());
                    }
                });

    }
}
