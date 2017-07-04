package com.ws.gl.opengltexture;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.ws.gl.opengltexture.programs.TextureShaderProgram;
import com.ws.gl.opengltexture.util.MatrixHelper;
import com.ws.gl.opengltexture.util.TextureHelper;
import com.ws.ijk.openglpicture.R;

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

    private int hChangeType;
    private int hChangeColor;
    private int hIsHalf;
    private int glHUxy;
    private Filter mFilter;

    private boolean isHalf;
    private float uXY;

    public PictureRenderer(Context context) {
        this.context = context;
    }

    public PictureRenderer(Context context, Filter filter) {
        this.context = context;
        this.mFilter=filter;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        picture = new Picture();
        textureProgram = new TextureShaderProgram(context,R.raw.half_color_vertex,R.raw.half_color_fragment);
        texture = TextureHelper.loadTexture(context, R.mipmap.pic);

        hChangeType=GLES20.glGetUniformLocation(textureProgram.getProgram(),"vChangeType");
        hChangeColor=GLES20.glGetUniformLocation(textureProgram.getProgram(),"vChangeColor");
        hIsHalf=GLES20.glGetUniformLocation(textureProgram.getProgram(),"vIsHalf");
        glHUxy=GLES20.glGetUniformLocation(textureProgram.getProgram(),"uXY");
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


        uXY=width/(float)height*3;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(projectionMatrix,texture);
        //start
        GLES20.glUniform1i(hChangeType,mFilter.getType());
        GLES20.glUniform3fv(hChangeColor,1,mFilter.data(),0);
        GLES20.glUniform1i(hIsHalf,isHalf?1:0);
        GLES20.glUniform1f(glHUxy,uXY);
        //end
        picture.bindData(textureProgram);
        picture.draw();

    }

    public void setHalf(boolean half){
        this.isHalf=half;
    }

    public void setFilter(Filter filter){
        this.mFilter=filter;
    }

    public enum Filter{

        NONE(0,new float[]{0.0f,0.0f,0.0f}),
        GRAY(1,new float[]{0.299f,0.587f,0.114f}),
        COOL(2,new float[]{0.0f,0.0f,0.1f}),
        WARM(2,new float[]{0.1f,0.1f,0.0f}),
        BLUR(3,new float[]{0.006f,0.004f,0.002f}),
        MAGN(4,new float[]{0.0f,0.0f,0.4f});


        private int vChangeType;
        private float[] data;

        Filter(int vChangeType,float[] data){
            this.vChangeType=vChangeType;
            this.data=data;
        }

        public int getType(){
            return vChangeType;
        }

        public float[] data(){
            return data;
        }

    }
}
