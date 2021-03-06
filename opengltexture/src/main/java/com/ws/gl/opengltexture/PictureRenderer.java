package com.ws.gl.opengltexture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.ws.gl.opengltexture.programs.TextureShaderProgram;
import com.ws.gl.opengltexture.util.MatrixHelper;
import com.ws.gl.opengltexture.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by WangShuo on 2017/7/3.
 */

public class PictureRenderer implements GLSurfaceView.Renderer{

    private final Context context;
    private TextureShaderProgram textureProgram;
    private int texture;
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Picture picture;

    public PictureRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        picture = new Picture();
        textureProgram = new TextureShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.mipmap.pic);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width,height);

        MatrixHelper.perspectiveM(projectionMatrix,45,(float)width/(float)height,1f,10f);

        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix,0,0f,0f,-2.5f);
        //Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f);
        final float[] temp = new float[16];
        Matrix.multiplyMM(temp,0,projectionMatrix,0,modelMatrix,0);
        System.arraycopy(temp,0,projectionMatrix,0,temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(projectionMatrix,texture);
        picture.bindData(textureProgram);
        picture.draw();
    }
}
