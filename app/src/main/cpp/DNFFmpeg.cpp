//
// Created by ASUS on 2018/11/20.
//

#include "DNFFmpeg.h"
#include "macro.h"


void *prepareFFmpeg_(void *args){

    DNFFmpeg *fmpeg = static_cast<DNFFmpeg*>(args);
    fmpeg->prepareFFmpeg();
    return 0;
}

DNFFmpeg::DNFFmpeg(JavaCallHelper *javaCallHelper, const char *dataSource)
                :javaCallHelper(javaCallHelper){
    url = new char[strlen(dataSource)];
    strcpy(url, dataSource);
    isPlaying = false;
    duration = 0 ;
    pthread_mutex_init(&seekMutex, 0);

}

void DNFFmpeg::prepare() {
    pthread_create(&pid_prepare, NULL,prepareFFmpeg_ ,this);
}

void DNFFmpeg::prepareFFmpeg() {
    LOGE("%s",url);
    //todo 最新版本好像不用 regiest_all了
    avformat_network_init();
    // 代表一个 视频/音频 包含了视频、音频的各种信息
    formatContext = avformat_alloc_context();
    //1、打开URL
    AVDictionary *opts = NULL;
    //设置超时3秒
    av_dict_set(&opts, "timeout", "3000000", 0);
    //强制指定AVFormatContext中AVInputFormat的。这个参数一般情况下可以设置为NULL，这样FFmpeg可以自动检测AVInputFormat。
    //输入文件的封装格式
//    av_find_input_format("avi")
    int ret = avformat_open_input(&formatContext, url, NULL, &opts);
    //av_err2str(ret)
    LOGE("%s open %d  %s", url, ret, av_err2str(ret));
    if (ret != 0) {
        if (javaCallHelper) {
            javaCallHelper->onError(THREAD_CHILD, FFMPEG_CAN_NOT_OPEN_URL);
        }
        return;
    }
    //2.查找流
    if (avformat_find_stream_info(formatContext, NULL) < 0) {
        if (javaCallHelper) {
            javaCallHelper->onError(THREAD_CHILD, FFMPEG_CAN_NOT_FIND_STREAMS);
        }
        return;
    }
    //视频时长（单位：微秒us，转换为秒需要除以1000000）
    duration = formatContext->duration / 1000000;
    for (int i = 0; i < formatContext->nb_streams; ++i) {
        AVCodecParameters *codecpar = formatContext->streams[i]->codecpar;
        //找到解码器
        AVCodec *dec = avcodec_find_decoder(codecpar->codec_id);
        if (!dec) {
            if (javaCallHelper) {
                javaCallHelper->onError(THREAD_CHILD, FFMPEG_FIND_DECODER_FAIL);
            }
            return;
        }
        //创建上下文
        AVCodecContext *codecContext = avcodec_alloc_context3(dec);
        if (!codecContext) {
            if (javaCallHelper)
                javaCallHelper->onError(THREAD_CHILD, FFMPEG_ALLOC_CODEC_CONTEXT_FAIL);
            return;
        }
        //复制参数
        if (avcodec_parameters_to_context(codecContext, codecpar) < 0) {
            if (javaCallHelper)
                javaCallHelper->onError(THREAD_CHILD, FFMPEG_CODEC_CONTEXT_PARAMETERS_FAIL);
            return;
        }
        //打开解码器
        if (avcodec_open2(codecContext, dec, 0) != 0) {
            if (javaCallHelper)
                javaCallHelper->onError(THREAD_CHILD, FFMPEG_OPEN_DECODER_FAIL);
            return;
        }
        //时间基
        AVRational base = formatContext->streams[i]->time_base;

    }

    if (javaCallHelper)
        javaCallHelper->onParpare(THREAD_CHILD);

}

void DNFFmpeg::setRenderCallback(RenderFrame renderFrame) {
    this->renderFrame = renderFrame;
}

