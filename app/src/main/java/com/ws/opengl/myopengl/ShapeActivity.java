package com.ws.opengl.myopengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ShapeActivity extends AppCompatActivity {

    private String shapeStr;
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);

        shapeStr=getIntent().getStringExtra("name");
        initView();
        initdata();
    }

    private void initView() {
        glSurfaceView= (GLSurfaceView) findViewById(R.id.opengl);

    }

    private void initdata() {
        if(shapeStr !=null) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(getRenderer(shapeStr));
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式
        }
    }
    @Override protected void onResume() {
        super.onResume();
            glSurfaceView.onResume();
    }

    @Override protected void onPause() {
        super.onPause();
            glSurfaceView.onPause();
    }

    private GLSurfaceView.Renderer getRenderer(String str){
          switch (str){
              case "Square":
                  return new GLRenderer(this);
              case "Oval":
                  return new Oval(this);
              case "Cube":
                  return new Cube(this);

              case "Cylinder":

              case "Cone":
                  return new Cone(this);
              case "Ball":
                  return new Ball(this);

              case "BallWithLight":
                  return new BallWithLight(this);

          }
        return new GLRenderer(this);
    }
}
