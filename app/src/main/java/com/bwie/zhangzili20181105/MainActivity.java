package com.bwie.zhangzili20181105;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.bwie.zhangzili20181105.myview.MyView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private MyView mMyView;
    private Animator mRotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 状态栏 导航栏设
        setTop();
        //2 ToolBar设置
        setTB();
        //3 ToolBar点击事件
        setTBOnClict();
        //4 初始化自定义View
        getInitView();
    }

    /**
     * //4 初始化自定义View
     * */
    private void getInitView() {
        mMyView = findViewById(R.id.myView);
        //回調觸發
        mMyView.setOnClickMyViewListener(new MyView.OnClickMyViewListener() {
            @Override
            public void onClick(RotateAnimation rotateAnimation, View v) {
               // mRotateAnimation.setInterpolator(new LinearInterpolator());
                Toast.makeText(v.getContext(),"开始",Toast.LENGTH_SHORT).show();
               // startAnimation(mRotateAnimation);
            }
        });
    }

    /**
     * 2 // ToolBar设置
     * */
    private void setTB() {
        //初始化控件
        mToolBar = findViewById(R.id.toolbar);
        //进行设置
        mToolBar.setTitle("课时作业");
        setSupportActionBar(mToolBar);
    }

    /**
     * 1 //状态栏 导航栏设
     * */
    private void setTop() {
        //设置状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 3 //ToolBar点击事件
     * */
    private void setTBOnClict() {
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       /* */
    }

/*    private void startAnimation(Animator rotateAnimation) {

    }*/
}
