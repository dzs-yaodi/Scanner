package com;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.xw.scannerlib.R;
import com.xw.scannerlib.base.Callback;
import com.xw.scannerlib.base.Result;
import com.xw.scannerlib.base.ScannerView;
import com.xw.scannerlib.base.ViewFinder;

public class ScanActivity extends AppCompatActivity {


    private ScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_layout);

        scannerView = findViewById(R.id.scannerView);
        initTitleColorBar(this,scannerView);

        scannerView.setShouldAdjustFocusArea(true);
        scannerView.setViewFinder(new ViewFinder(this));
//        scannerView.setViewFinder(new ViewFinder2());
        scannerView.setSaveBmp(false);
        scannerView.setRotateDegree90Recognition(true);
        scannerView.setCallback(new Callback() {
            @Override
            public void result(Result result) {
//                tvResult.setText("识别结果：\n" + result.toString());
                scannerView.startVibrator();
                scannerView.restartPreviewAfterDelay(2000);

                Intent intent = new Intent();
                intent.putExtra("idcard",result.data);
                setResult(-1,intent);
                finish();
            }
        });

        scannerView.setEnableIdCard2(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.onPause();
    }

    @Override
    protected void onDestroy() {
        scannerView.closeVibrator();
        super.onDestroy();
    }


    /**
     * 初始化状态栏，适配沉浸式
     */
    public static void initTitleColorBar(Activity activity, View bgView) {
        //设置沉浸式状态栏
        translucentStatusBar(activity, true);
        //计算出状态栏高度，并设置view留出对应位置
        int height = (int) getStatusBarHeight(activity);
        bgView.setPadding(0, height, 0, 0);
    }


    @SuppressLint("NewApi")
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        //view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.requestApplyInsets(mChildView);
        }
    }


    /**
     * 获取状态栏高度。
     *
     * @param context context
     * @return 返回获取的状态栏高度
     */
    public static float getStatusBarHeight(Context context) {
        float result = 0;
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimension(resourceId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
