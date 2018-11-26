//
// Created by ASUS on 2018/11/20.
//

#ifndef DNNDKPRJ_DNFFMPEG_H
#define DNNDKPRJ_DNFFMPEG_H

#include <pthread.h>
#include <android/native_window.h>
#include "JavaCallHelper.h"
#include "VideoChannel.h"


extern "C"{
#include <libavformat/avformat.h>
#include <libavutil/time.h>
};


class DNFFmpeg {
    friend void * async_stop(void* args);

public:
    DNFFmpeg(JavaCallHelper *javaCallHelper, const char *dataSource);

    ~DNFFmpeg();
    void prepare();

    void prepareFFmpeg();

    void start();

    void play();

    void setRenderCallback(RenderFrame renderFrame);

    void stop();

    int getDuration() {
        return duration;
    }

    void seek(int i);


private:
    char *url;
    JavaCallHelper *javaCallHelper;

    pthread_t pid_prepare;
    pthread_t pid_play;
    pthread_t pid_stop;

    pthread_mutex_t seekMutex;
    AVFormatContext *formatContext = 0;

    int duration;

    RenderFrame renderFrame;

//    AudioChannel *audioChannel = 0;
//    VideoChannel *videoChannel = 0;

    bool isPlaying;
    bool isSeek = 0;
};


#endif //DNNDKPRJ_DNFFMPEG_H
