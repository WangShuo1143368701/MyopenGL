package com.ws.opengl.myopengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 搭建最基本的 OpenGL Android 程序
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    private Button SquareBtn,OvalBtn,CubeBtn,CylinderBtn,ConeBtn,BallBtn,BallWithLightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initdata(new GLRenderer(this));
    }

    private void initView() {
        glSurfaceView= (GLSurfaceView) findViewById(R.id.opengl);
        SquareBtn = (Button) findViewById(R.id.Square);
        OvalBtn = (Button) findViewById(R.id.Oval);
        CubeBtn = (Button) findViewById(R.id.Cube);
        CylinderBtn = (Button) findViewById(R.id.Cylinder);
        ConeBtn = (Button) findViewById(R.id.Cone);
        BallBtn = (Button) findViewById(R.id.Ball);
        BallWithLightBtn = (Button) findViewById(R.id.BallWithLight);

        SquareBtn.setOnClickListener(this);
        OvalBtn.setOnClickListener(this);
        CubeBtn.setOnClickListener(this);
        CylinderBtn.setOnClickListener(this);
        ConeBtn.setOnClickListener(this);
        BallBtn.setOnClickListener(this);
        BallWithLightBtn.setOnClickListener(this);
    }
    private void initdata(GLSurfaceView.Renderer renderer) {
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;//是否支持OpenGL2.0
        if (supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(renderer);
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式
            rendererSet = true;
        } else {
            Toast.makeText(this, "Not support OpenGL2.0", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override protected void onResume() {
        super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.Square://矩形
                StartActivity("Square");
                break;
            case  R.id.Oval://圆形
                StartActivity("Oval");
                break;
            case  R.id.Cube://正方体
                StartActivity("Cube");
                break;
            case  R.id.Cylinder://圆柱
                StartActivity("Cylinder");
                break;
            case  R.id.Cone://圆锥
                StartActivity("Cone");
                break;
            case  R.id.Ball://球体
                StartActivity("Ball");
                break;
            case  R.id.BallWithLight://带光源的球
                StartActivity("BallWithLight");
                break;
            default:
                break;
        }
    }

    private void StartActivity(String str) {
        Intent intent = new Intent(MainActivity.this,ShapeActivity.class);
        intent.putExtra("name",str);
        startActivity(intent);
    }
}
