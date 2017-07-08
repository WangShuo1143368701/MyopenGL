package com.ws.gl.opengltexture;


import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ws.ijk.openglpicture.R;

public class Main2Activity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    private boolean isHalf=false;
    private PictureRenderer mRenderer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView= (GLSurfaceView) findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);

        mRenderer=new PictureRenderer(this);
        glSurfaceView.setRenderer(mRenderer);
        mRenderer.setFilter(PictureRenderer.Filter.NONE);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mDeal:
                isHalf=!isHalf;
                if(isHalf){
                    item.setTitle("处理一半");
                }else{
                    item.setTitle("全部处理");
                }
                //mRenderer.refresh();
                break;
            case R.id.mDefault:
                mRenderer.setFilter(PictureRenderer.Filter.NONE);
                break;
            case R.id.mGray:
                mRenderer.setFilter(PictureRenderer.Filter.GRAY);
                break;
            case R.id.mCool:
                mRenderer.setFilter(PictureRenderer.Filter.COOL);
                break;
            case R.id.mWarm:
                mRenderer.setFilter(PictureRenderer.Filter.WARM);
                break;
            case R.id.mBlur:
                mRenderer.setFilter(PictureRenderer.Filter.BLUR);
                break;
            case R.id.mMagn:
                mRenderer.setFilter(PictureRenderer.Filter.MAGN);
                break;
            case R.id.beauty:
                Intent intent = new Intent(Main2Activity.this,BeautyActivity.class);
                startActivity(intent);
                return true;
        }
        mRenderer.setHalf(isHalf);
        glSurfaceView.requestRender();
        return super.onOptionsItemSelected(item);
    }
}
