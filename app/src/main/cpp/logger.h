//
// Created by Administrator on 2018/10/15.
//
#include "android/log.h"
#ifndef DNNDKPRJ_LOG_H
#define DNNDKPRJ_LOG_H

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "chm", __VA_ARGS__);

#endif //DNNDKPRJ_LOG_H
