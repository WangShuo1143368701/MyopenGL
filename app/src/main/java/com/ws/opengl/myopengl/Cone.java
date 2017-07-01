/*
 *
 * Cone.java
 * 
 * Created by Wuwang on 2016/10/14
 */
package com.ws.opengl.myopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Description:圆锥
 */
public class Cone implements GLSurfaceView.Renderer {

    private int mProgram;

    private Oval oval;
    private FloatBuffer vertexBuffer;

    private final String vertexShaderCode =
            "uniform mat4 vMatrix;" +
            "varying vec4 vColor;" +
            "attribute vec4 vPosition;" +

    "void main(){" +
        "gl_Position=vMatrix*vPosition;" +
        "if(vPosition.z!=0.0){" +
           " vColor=vec4(0.0,0.0,0.0,1.0);" +
        "}else{" +
            "vColor=vec4(0.9,0.9,0.9,1.0);" +
        "}" +
    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
    "varying vec4 vColor;" +

    "void main(){" +
        "gl_FragColor=vColor;" +
    "}";

    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    private int n=360;  //切割份数
    private float height=2.0f;  //圆锥高度
    private float radius=1.0f;  //圆锥底面半径
    private float[] colors={1.0f,1.0f,1.0f,1.0f};

    private int vSize;
    private int mMatrix;
    private int mPositionHandle;
    private int mColorHandle;

    public Cone(Context context){
        oval=new Oval(context);

        float[] d=createPos();
        vSize=d.length/3;

        vertexBuffer = ByteBuffer.allocateDirect(d.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(d);
        vertexBuffer.position(0);
    }
    private float[] createPos(){
        ArrayList<Float> pos=new ArrayList<>();
        pos.add(0.0f);
        pos.add(0.0f);
        pos.add(height);
        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            pos.add((float) (radius*Math.sin(i*Math.PI/180f)));
            pos.add((float)(radius*Math.cos(i*Math.PI/180f)));
            pos.add(0.0f);
        }
        float[] d=new float[pos.size()];
        for (int i=0;i<d.length;i++){
            d[i]=pos.get(i);
        }
        return d;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f,0.5f,0.5f,1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mProgram=ShaderUtils.createProgram(vertexShaderCode,fragmentShaderCode);
        mMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        mPositionHandle=GLES20.glGetAttribLocation(mProgram,"vPosition");
        mColorHandle=GLES20.glGetUniformLocation(mProgram,"vColor");

         oval.onSurfaceCreated(gl,config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 1.0f, -10.0f, -4.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);

        GLES20.glUniformMatrix4fv(mMatrix,1,false,mMVPMatrix,0);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glUniform4fv(mColorHandle,1,colors,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,vSize);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        oval.setMatrix(mMVPMatrix);
        oval.onDrawFrame(gl);
    }
}
