package com.xw.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.ScanActivity;
import com.google.gson.Gson;
import com.idcard.CardResp;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_scan).setOnClickListener(v -> {

            startActivityForResult(new Intent(this, ScanActivity.class), 10001);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10001 && resultCode == -1) {

            String result = data.getStringExtra("idcard");
            if (!TextUtils.isEmpty(result)) {
                CardResp resp = new Gson().fromJson(result, CardResp.class);

                if (resp != null) {
                    Toast.makeText(this, "身份证号码:" + resp.getCardNumber(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
