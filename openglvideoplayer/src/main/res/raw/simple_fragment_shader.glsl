#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vTexCoord;
uniform samplerExternalOES sTexture;
void main() {
    //gl_FragColor=texture2D(sTexture, vTexCoord);

        vec3 centralColor = texture2D(sTexture, vTexCoord).rgb;
        gl_FragColor = vec4(0.299*centralColor.r+0.587*centralColor.g+0.114*centralColor.b);

}