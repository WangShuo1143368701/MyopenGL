package com.ws.gl.opengltexture.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.ws.gl.opengltexture.util.ShaderHelper;
import com.ws.gl.opengltexture.util.TextResourceReader;


/**
 * Created by WangShuo on 2016/7/5.
 */
public class ShaderProgram {


  protected final int program;

  protected ShaderProgram(Context context, int vertexShaderResourceId,
      int fragmentShaderResourceId) {
    program = ShaderHelper.buildProgram(//
        TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
        TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
  }

  protected ShaderProgram(String vertexShaderResource,
                          String fragmentShaderResource) {
    program = ShaderHelper.buildProgram(vertexShaderResource,fragmentShaderResource);
  }

  public void useProgram() {
    GLES20.glUseProgram(program);
  }

  public int getProgram() {
   return program;
  }

}
