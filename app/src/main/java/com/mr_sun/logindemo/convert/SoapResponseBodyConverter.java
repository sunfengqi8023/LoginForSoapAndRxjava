package com.mr_sun.logindemo.convert;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.mr_sun.logindemo.ksoap2.SoapEnvelope;
import com.mr_sun.logindemo.ksoap2.serialization.SoapObject;
import com.mr_sun.logindemo.ksoap2.transport.SoapHelper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Copyright (c) 2016. 东华博育云有限公司 Inc. All rights reserved.
 * com.mr_sun.logindemo.convert
 * 作者：Created by sfq on 2016/12/2.
 * 联系方式：
 * 功能描述：
 * 修改：无
 */
public final class SoapResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private static final String TAG = "SoapResponseBodyConvert";

    private final TypeAdapter<T> adapter;
    private final Gson gson;

    SoapResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        //value 参考soap的返回，截取其中的字符串json
        SoapEnvelope soapEnvelope = SoapHelper.getInstance().getSoapEnvelope();
        SoapHelper.getInstance().parseResponse(soapEnvelope,value.byteStream());
        SoapObject resultsRequestSOAP = (SoapObject) soapEnvelope.bodyIn;
        Object obj = (Object) resultsRequestSOAP.getProperty(0);
        Log.e(TAG, "ResponseBodyToStringConverterFactory : " + obj.toString());
        try {
            return adapter.fromJson(obj.toString());
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }finally {
            value.close();
        }
    }
}