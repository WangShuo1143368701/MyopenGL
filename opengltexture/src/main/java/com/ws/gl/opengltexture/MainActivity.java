package com.ws.gl.opengltexture;


import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ws.gl.opengltexture.wsegl.EGLBackEnvActivity;
import com.ws.gl.opengltexture.wsetc.ZipActivity;
import com.ws.gl.opengltexture.wsfbo.FBOActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GLSurfaceView glSurfaceView;

    private Button etcBtn,fboBtn,eglBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView= (GLSurfaceView) findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);

        glSurfaceView.setRenderer(new PictureRenderer(this));
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        initView();
    }

    private void initView() {
        etcBtn= (Button) findViewById(R.id.etc);
        etcBtn.setOnClickListener(this);

        fboBtn= (Button) findViewById(R.id.fbo);
        fboBtn.setOnClickListener(this);

        eglBtn= (Button) findViewById(R.id.egl);
        eglBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.etc:
                Intent intent = new Intent(MainActivity.this, ZipActivity.class);
                startActivity(intent);
                break;
            case R.id.fbo:
                Intent intent2 = new Intent(MainActivity.this, FBOActivity.class);
                startActivity(intent2);
                break;
            case R.id.egl:
                Intent intent3 = new Intent(MainActivity.this, EGLBackEnvActivity.class);
                startActivity(intent3);
                break;
        }
    }

}
