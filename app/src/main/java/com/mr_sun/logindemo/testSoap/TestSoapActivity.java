package com.mr_sun.logindemo.testSoap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mr_sun.logindemo.R;
import com.mr_sun.logindemo.ksoap2.SoapEnvelope;
import com.mr_sun.logindemo.ksoap2.serialization.MarshalBase64;
import com.mr_sun.logindemo.ksoap2.serialization.SoapObject;
import com.mr_sun.logindemo.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 只是一个测试的activityt，用来测试引入的soap源码是否能够正常运行
 */
public class TestSoapActivity extends AppCompatActivity {
    private static final String TAG = "TestSoapActivity";

    // 登录注册的nameSpace
    public static String loginPoint = "http://dev.boyuyun.com.cn:9018/services/loginService";

    public static String nameSpace = "http://webservice.im.boyuyun.com/";

    private TextView tv_mesage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_soap);

        tv_mesage = (TextView)findViewById(R.id.tv_mesage);

        login("20150007@7779", "5airhqhfiooajsdo622", new WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                final Object obj = result.getProperty(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_mesage.setText(obj.toString());
                    }
                });
            }

            @Override
            public void callBackFailed(String error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_mesage.setText("访问报错了呢！！！");
                        }
                    });
            }
        });

    }




    /**
     * 回调函数接口声明
     */
    public interface WebServiceCallBack {
        public void callBack(SoapObject result);

        public void callBackFailed(String error);
    }

    //webservices 接口访问方法
    public static void webservicesHelp(final String method, String nameSpace, String endPoint, HashMap<String, Object> properties,
                                       final WebServiceCallBack webServiceCallBack) {

        SoapObject rpc = new SoapObject(nameSpace, method);
        if (properties != null) {
            for (Iterator<Map.Entry<String, Object>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> entry = it.next();
                rpc.addProperty(entry.getKey(), entry.getValue());
            }
        }

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        new MarshalBase64().register(envelope);
        //添加的额外的认证头部
        //envelope.headerOut = getHeader(nameSpace);
        envelope.bodyOut = rpc;
        envelope.dotNet = false;
        envelope.setOutputSoapObject(rpc);

       /* //重新设置了超时时间为15s
        final HttpTransportSE transport = new HttpTransportSE(endPoint, 15000);

        Runnable runable = new Runnable() {
            @Override
            public void run() {
                SoapObject resultsRequestSOAP = null;
                try {
                    //不为空就报类型转换错误""
                    transport.call("", envelope);

                    if (envelope.getResponse() != null) {
                        resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    webServiceCallBack.callBack(resultsRequestSOAP);
                }

            }
        };
        new Thread(runable).start();*/

    }


    public static void login(String username, String password, WebServiceCallBack webServiceCallBack) {
        String endPoint;
        endPoint = loginPoint;
        String methodName = "loginForMobile";

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("userName", username);
        properties.put("pwd", password);
        webservicesHelp(methodName, nameSpace, endPoint, properties, webServiceCallBack);
    }
}
