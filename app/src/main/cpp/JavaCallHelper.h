//
// Created by ASUS on 2018/11/20.
//

#ifndef DNNDKPRJ_JAVACALLHELPER_H
#define DNNDKPRJ_JAVACALLHELPER_H

#include <jni.h>
class JavaCallHelper {
public:
    JavaCallHelper(JavaVM *_javaVm, JNIEnv *_env, jobject &_jobj);

    ~JavaCallHelper();

    void onError(int thread, int code);

    void onParpare(int thread);

    void onPragress(int thread,int progress);

public:

    JavaVM *javaVM;
    JNIEnv *env;
    jobject jobj;
    jmethodID jmid_error;
    jmethodID jmid_parpare;
    jmethodID jmid_pragress;
};


#endif //DNNDKPRJ_JAVACALLHELPER_H
