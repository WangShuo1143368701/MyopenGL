attribute vec4 aPosition;//顶点位置
attribute vec4 aTexCoord;//S T 纹理坐标
varying vec2 vTexCoord;
uniform mat4 uMatrix;
uniform mat4 uSTMatrix;

uniform mat4 uViewMatrix;
uniform mat4 uModelMatrix;
uniform mat4 uRotateMatrix;

void main() {
    vTexCoord = (uSTMatrix * aTexCoord).xy;
    gl_Position = uMatrix*uRotateMatrix*uViewMatrix*uModelMatrix*aPosition;
}







