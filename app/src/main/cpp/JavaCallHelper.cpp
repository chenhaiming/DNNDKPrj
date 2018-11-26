//
// Created by ASUS on 2018/11/20.
//

#include "JavaCallHelper.h"
#include "macro.h"


JavaCallHelper::JavaCallHelper(JavaVM *_javaVm, JNIEnv *_env, jobject &_jobj):javaVM(_javaVm) ,env(_env) {
    jobj = env->NewGlobalRef(_jobj);
    jclass jclzz = env->GetObjectClass(_jobj);

    jmid_error = env->GetMethodID(jclzz, "onError", "(I)V");
    jmid_parpare = env->GetMethodID(jclzz, "onPrepare", "()V");
    jmid_pragress = env->GetMethodID(jclzz, "onProgress", "(I)V");
}

JavaCallHelper::~JavaCallHelper() {
    env->DeleteGlobalRef(jobj);
    jobj = 0 ;
}

void JavaCallHelper::onError(int thread, int code) {

    if (thread == THREAD_CHILD){
        if (javaVM->AttachCurrentThread(&env, 0) != JNI_OK){
            return;
        }
        env->CallVoidMethod(jobj, jmid_error, code);
    } else{
        env->CallVoidMethod(jobj, jmid_error, code);
    }
}

void JavaCallHelper::onParpare(int thread) {
    if (thread == THREAD_CHILD) {
        JNIEnv *jniEnv;
        if (javaVM->AttachCurrentThread(&jniEnv, 0) != JNI_OK) {
            return;
        }
        jniEnv->CallVoidMethod(jobj, jmid_parpare);
        javaVM->DetachCurrentThread();
    } else {
        env->CallVoidMethod(jobj, jmid_parpare);
    }
}

void JavaCallHelper::onPragress(int thread, int progress) {
    if (thread == THREAD_CHILD) {
        JNIEnv *jniEnv;
        if (javaVM->AttachCurrentThread(&jniEnv, 0) != JNI_OK) {
            return;
        }
        jniEnv->CallVoidMethod(jobj, jmid_pragress, progress);
        javaVM->DetachCurrentThread();
    } else {
        env->CallVoidMethod(jobj, jmid_pragress, progress);
    }
}