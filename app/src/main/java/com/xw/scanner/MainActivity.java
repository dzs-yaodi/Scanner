package com.xw.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.ScanActivity;
import com.google.gson.Gson;
import com.idcard.CardResp;
import com.xw.scannerlib.base.Callback;
import com.xw.scannerlib.base.Result;
import com.xw.scannerlib.base.ScannerView;
import com.xw.scannerlib.base.ViewFinder;

public class MainActivity extends AppCompatActivity {

    private ScannerView scannerView;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_scan).setOnClickListener(v -> {

            startActivityForResult(new Intent(this, ScanActivity.class), 10001);
        });

//        scannerView = findViewById(R.id.scannerView);
//        tvResult = findViewById(R.id.tv_result);
//
//        scannerView.setShouldAdjustFocusArea(true);
//        scannerView.setViewFinder(new ViewFinder(this));
////        scannerView.setViewFinder(new ViewFinder2());
//        scannerView.setSaveBmp(false);
//        scannerView.setRotateDegree90Recognition(true);
//        scannerView.setCallback(new Callback() {
//            @Override
//            public void result(Result result) {
//                tvResult.setText("识别结果：\n" + result.toString());
//                scannerView.startVibrator();
//                scannerView.restartPreviewAfterDelay(2000);
//            }
//        });
//
//        scannerView.setEnableIdCard2(true);
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


    //    @Override
//    protected void onResume() {
//        super.onResume();
//        scannerView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        scannerView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        scannerView.closeVibrator();
//        super.onDestroy();
//    }


}
