package com.example.zhubaixu.idcard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xuxu.idcard.help.IdCardGenerator;
import com.xuxu.idcard.help.IdCardValidator;

public class MainActivity extends AppCompatActivity {
    EditText et_validator;
    EditText et_card;
    Button bt_validator;
    Button bt_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_validator = (EditText) findViewById(R.id.et_validator);
        et_card = (EditText) findViewById(R.id.et_card);
        bt_validator = (Button) findViewById(R.id.bt_validator);
        bt_card = (Button) findViewById(R.id.bt_card);
        //        bt_card.setOnClickListener(new View.OnClickListener() {
        //            public void onClick(View v) {
        //                String str = new IdCardGenerator().generate();
        //                tv_card.setText("身份证："+str+"");
        //            }
        //        });
        bt_card.setOnClickListener(new MyListener());
        bt_validator.setOnClickListener(new MyListener());
    }

    private class MyListener implements View.OnClickListener {

        private static final String TAG = "MyListener";

        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            switch (v.getId()) {
                case R.id.bt_card:
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(et_card.getWindowToken(), 0);
                    //随机获取身份证号
                    String str = new IdCardGenerator().generate();
                    et_card.setText("身份证：" + str + "");
                    break;
                case R.id.bt_validator:
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(et_validator.getWindowToken(), 0);
                    //获取输入身份证号码
                    String id_card = et_validator.getText().toString().trim();
                    int len_id_card = id_card.length();
                    if (len_id_card > 18) {
                        Toast.makeText(MainActivity.this, "位数超过18", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i(TAG,"身份证号为:"+id_card+"，位数为:"+id_card.length());
                        boolean id_bool = IdCardValidator.isValidatedAllIdcard(id_card);
                        if (TextUtils.isEmpty(id_card) || id_card.equals("")) {
                            Toast.makeText(MainActivity.this, "请输入身份证号",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if (id_bool) {
                                Toast.makeText(MainActivity.this, "正确身份证号",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "错误身份证号",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            }
        }
    }
}
