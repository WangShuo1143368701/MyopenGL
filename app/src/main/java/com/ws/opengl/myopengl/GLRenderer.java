package com.ws.opengl.myopengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Shuo.Wang on 2017/3/14.
 */

public class GLRenderer implements  GLSurfaceView.Renderer {

    private Context context;
    private int programId;

    private static final String A_POSITION = "aPosition";
    private int aPositionLocation;

    private final float[] projectionMatrix=new float[16];
    private static final String U_Matrix = "uMatrix";
    private int uMatrixLocation;

    private FloatBuffer vertexBuffer;
    private final float[] vertexData = {
            1f,-1f,0f,
            -1f,-1f,0f,
            1f,1f,0f,
            -1f,1f,0f
    };

    public GLRenderer(Context context) {
        this.context = context;

        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);//清屏

        String vertexShader = ShaderUtils.readRawTextFile(context, R.raw.simple_vertex_shader);
        String fragmentShader= ShaderUtils.readRawTextFile(context, R.raw.simple_fragment_shader);
        programId=ShaderUtils.createProgram(vertexShader,fragmentShader);
        aPositionLocation= GLES20.glGetAttribLocation(programId,A_POSITION);
        uMatrixLocation= GLES20.glGetUniformLocation(programId,U_Matrix);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio=width>height?
                (float)width/height:
                (float)height/width;
        if (width>height){
            Matrix.orthoM(projectionMatrix,0,-ratio,ratio,-1f,1f,-1f,1f);
        }else Matrix.orthoM(projectionMatrix,0,-1f,1f,-ratio,ratio,-1f,1f);
     //  Matrix.orthoM()  左右(x)下上(y)近远(z)
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);//清空颜色缓冲区和深度缓冲区
        GLES20.glUseProgram(programId);

        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,projectionMatrix,0);


        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glVertexAttribPointer(aPositionLocation, 3, GLES20.GL_FLOAT, false,
                12, vertexBuffer);//一个float 4个字节 上面的3乘以4共十二
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

    }


}
