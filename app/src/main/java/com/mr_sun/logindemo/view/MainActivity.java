package com.mr_sun.logindemo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mr_sun.logindemo.R;
import com.mr_sun.logindemo.presenter.ILoginPresenterImpl;
import com.mr_sun.logindemo.testSoap.TestSoapActivity;

/**
 * 登录页面
 * 欢迎访问
 *  github:https://github.com/sunfengqi8023
 * csdn：http://blog.csdn.net/sfq19881224/
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,ILoginView {
    private static final String TAG = "MainActivity";

    private Button btn_login;//提交按钮
    private TextView tv_message;
    private Button btn_test;//就是一个测试

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_test = (Button)findViewById(R.id.btn_test);
        tv_message = (TextView) findViewById(R.id.tv_message);

        btn_login.setOnClickListener(this);
        btn_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                ILoginPresenterImpl impl = new ILoginPresenterImpl(this);
                impl.login("20179","6413412623232652");
                break;
            case R.id.btn_test:
                Intent intent = new Intent(this, TestSoapActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void displayLoginMessage(String message) {
        if(TextUtils.isEmpty(message)){
           tv_message.setText("什么数据也没获取到");
        }else{
            tv_message.setText(message);

        }
    }

    @Override
    public void jump2OtherActivity(Class mclass) {

    }
}
