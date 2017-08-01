package com.ws.openglvideoplayer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class VRVideoActivity extends AppCompatActivity implements SensorEventListener {

    private GLSurfaceView glSurfaceView;
    private VRVideoRenderer glRenderer;
    private SensorManager mSensorManager;
    private Sensor mRotation;
    private float[] matrix=new float[16];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors=mSensorManager.getSensorList(Sensor.TYPE_ALL);
        //todo 判断是否存在rotation vector sensor
        mRotation=mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        glSurfaceView= (GLSurfaceView) findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer=new VRVideoRenderer(this, Environment.getExternalStorageDirectory().getPath()+"/360.mp4");
        glSurfaceView.setRenderer(glRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        glRenderer.getMediaPlayer().release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        glSurfaceView.onPause();
        glRenderer.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mRotation,SensorManager.SENSOR_DELAY_GAME);
        glSurfaceView.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorManager.getRotationMatrixFromVector(matrix,sensorEvent.values);
        glRenderer.setMatrix(matrix);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
