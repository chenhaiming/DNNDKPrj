#include <jni.h>
#include <string>
#include "android/log.h"
#include "logger.h"
JavaVM *_vm;
extern "C"
JNIEXPORT jstring

JNICALL
Java_comqh_chm_dnndkprj_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "I m is on Jni onLoad";
    return env->NewStringUTF(hello.c_str());
}

void dynamicTest(){
    LOGE("JNI dynamicTest");
}
static const char *mClassName = "comqh/chm/dnndkprj/MainActivity";
JNINativeMethod method[] = {
        {"dynamicJavaTest","()V",(void *)dynamicTest}
};

int JNI_OnLoad(JavaVM *vm, void *re){
    //
    _vm = vm;
    // 获得JNIEnv
    JNIEnv *env = 0;
    // 小于0 失败 ，等于0 成功
    int r = vm->GetEnv((void**)&env,JNI_VERSION_1_2);
    if (r != JNI_OK){
        return -1;
    }
    //获得 class对象
    jclass jcls =  env->FindClass(mClassName);
    //注册
    env->RegisterNatives(jcls,method, sizeof(method)/ sizeof(JNINativeMethod));
    return JNI_VERSION_1_2;
}