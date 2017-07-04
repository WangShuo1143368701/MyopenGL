package com.ws.gl.opengltexture.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.ws.gl.opengltexture.util.ShaderHelper;
import com.ws.gl.opengltexture.util.TextResourceReader;


/**
 * Created by WangShuo on 2016/7/5.
 */
public class ShaderProgram {

  protected static final String U_MATRIX = "u_Matrix";
  protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

  protected static final String A_POSITION = "a_Position";
  protected static final String A_COLOR = "a_Color";
  protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

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
