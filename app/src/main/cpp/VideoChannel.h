//
// Created by ASUS on 2018/11/26.
//

#ifndef DNNDKPRJ_VIDEOCHANNEL_H
#define DNNDKPRJ_VIDEOCHANNEL_H

#include <stdint.h>
#include <libavutil/rational.h>
#include <libavcodec/avcodec.h>
#include "JavaCallHelper.h"

typedef void (*RenderFrame)(uint8_t *, int, int, int);


class VideoChannel {
    VideoChannel(int id,JavaCallHelper *javaCallHelper, AVCodecContext *avCodecContext, AVRational base, int fps);
    virtual ~VideoChannel();

    virtual void play();

    virtual void stop();

    void decodePacket();

    void synchronizeFrame();

    void setRenderCallback(RenderFrame renderFrame);

public:
//    AudioChannel *audioChannel = 0;
private:
    int fps;
    pthread_t pid_video_play;
    pthread_t pid_synchronize;
    RenderFrame renderFrame;
};


#endif //DNNDKPRJ_VIDEOCHANNEL_H
