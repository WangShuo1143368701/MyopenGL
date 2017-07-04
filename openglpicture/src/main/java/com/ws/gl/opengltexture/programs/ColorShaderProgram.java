package com.ws.gl.opengltexture.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.ws.ijk.openglpicture.R;

import static com.ws.gl.opengltexture.programs.TextureShaderProgram.A_COLOR;
import static com.ws.gl.opengltexture.programs.TextureShaderProgram.A_POSITION;
import static com.ws.gl.opengltexture.programs.TextureShaderProgram.U_MATRIX;


/**
 * Created by WangShuo on 2016/7/5.
 */
public class ColorShaderProgram extends ShaderProgram {

  protected static final String U_MATRIX = "u_Matrix";
  protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

  protected static final String A_POSITION = "a_Position";
  protected static final String A_COLOR = "a_Color";
  protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

  private final int uMatrixLocation;

  private final int aPositionLocation;
  private final int aColorLocation;

  public ColorShaderProgram(Context context) {
    super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
    uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

    aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
    aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
  }

  @Override public void useProgram() {
    super.useProgram();
  }

  public void setUniforms(float[] matrix) {
    GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
  }

  public int getPositionAttributeLocation() {
    return aPositionLocation;
  }

  public int getColorAttributeLocation() {
    return aColorLocation;
  }
}
