package com.ws.gl.opengltexture.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by WangShuo on 2016/7/5.
 */
public class TextureHelper {

  private final static String TAG = "TextureHelper";

  public static int loadTexture(Context context,int resourceId){
    final int[] textureObjectIds = new int[1];
    GLES20.glGenTextures(1,textureObjectIds,0);//创建纹理对象 把生成的ID存在textureObjectIds
    if(textureObjectIds[0] == 0 ){
      if(LoggerConfig.ON){
        Log.w(TAG,"Could not generate a new OpenGL texture object.");
      }
      return 0;
    }
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;

    final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId,options);

    if(bitmap==null){
      if(LoggerConfig.ON){
        Log.w(TAG,"Resource ID "+resourceId+" could not be decoded");
      }
      GLES20.glDeleteTextures(1,textureObjectIds,0);
      return 0;
    }
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureObjectIds[0]);//告诉openGL后面的纹理调用用id为textureObjectIds的
    /*我们使用 glTexParameteri 来设置每个过滤器，GL_TEXTURE_MIN_FILTER 代表缩小的情况，GL_TEXTURE_MAG_FILTER 代表放大的情况。
    对于缩小的情况，我们选择 GL_LINEAR_MIPMAP_LINEAR ，告诉 OpenGL 使用三线性过滤，
    对于放大的情况，我们设置放大器为 GL_LINEAR ，告诉 OpenGL 使用双线性过滤。*/
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR_MIPMAP_LINEAR);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
    //加载位图到 OpenGL 中，并把它复制到当前绑定的纹理对象
    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
    /*生成 MIP 贴图。完成这些动作后，我们需要解除与这个纹理的绑定，以免用其他方法以外的改变这个纹理*/
    GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

    bitmap.recycle();
  //解除与这个纹理的绑定
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    return textureObjectIds[0];
  }

 /* //生成纹理
            GLES20.glGenTextures(1,texture,0);
  //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
  //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
  //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
  //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
  //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
  //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);*/
}
