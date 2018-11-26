package comqh.chm.dnndkprj.player;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ASUS on 2018/11/14.
 */

public class DNPlayer implements SurfaceHolder.Callback {




    private String dataSource ;
    private SurfaceHolder surfaceHolder ;
    private OnPrepareListener onPrepareListener;
    private OnErrorListener onErrorListener;
    private OnProgressListener onProgressListener;


    public void setOnPrepareListener(OnPrepareListener onPrepareListener) {
        this.onPrepareListener = onPrepareListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        if (null != surfaceHolder){
            surfaceHolder.removeCallback(this);
        }
        this.surfaceHolder = surfaceView.getHolder();
        // 更新 WindowManager

        this.surfaceHolder.addCallback(this);
    }


    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public interface OnPrepareListener {
        void onPrepare();
    }

    public interface OnErrorListener {
        void onError(int error);
    }

    public interface OnProgressListener {
        void onProgress(int progress);
    }


    /**
     * method
     */

    public void prepare(){
        native_prepare(dataSource);
    }


    public void onError(int errorCode) {
//        stop();
        if (null != onErrorListener) {
            onErrorListener.onError(errorCode);
        }
    }

    public void onPrepare() {
        if (null != onPrepareListener) {
            onPrepareListener.onPrepare();
        }
    }

    /**
     * native 回调给java 播放进去的
     * @param progress
     */
    public void onProgress(int progress) {
        if (null != onProgressListener) {
            onProgressListener.onProgress(progress);
        }
    }


    /**
     * native
     */
    static {
        System.loadLibrary("player");
    }


    private native void native_prepare(String dataSource);

}
