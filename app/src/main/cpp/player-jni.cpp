#include <jni.h>
#include <string>
#include "DNFFmpeg.h"
#include "macro.h"

JavaVM *javaVM = NULL;
DNFFmpeg *ffmpeg = 0;
extern "C"{
#include <libavutil/imgutils.h>

void renderFrame(uint8_t *date, int linesize, int w, int h){


}

JNIEXPORT void JNICALL
Java_comqh_chm_dnndkprj_player_DNPlayer_native_1prepare(JNIEnv *env, jobject instance,
                                                        jstring dataSource_) {
    const char *dataSource = env->GetStringUTFChars(dataSource_, 0);
    JavaCallHelper *javaCallHelper = new JavaCallHelper(javaVM, env, instance);
    ffmpeg = new DNFFmpeg(javaCallHelper, dataSource);
    ffmpeg->setRenderCallback(renderFrame);
    ffmpeg->prepare();
    env->ReleaseStringUTFChars(dataSource_, dataSource);
}


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    javaVM = vm;
//    av_log_set_level(AV_LOG_INFO);
//    av_log_set_callback(callback);
    return JNI_VERSION_1_4;
}
}