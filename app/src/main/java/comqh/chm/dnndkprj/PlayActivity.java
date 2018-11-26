package comqh.chm.dnndkprj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import comqh.chm.dnndkprj.player.DNPlayer;

/**
 * Created by ASUS on 2018/11/14.
 */

public class PlayActivity extends RxAppCompatActivity {

    private SurfaceView surfaceView;
    private DNPlayer dnPlayer;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        surfaceView = findViewById(R.id.surfaceView);
        // 初始化
        dnPlayer = new DNPlayer();
        dnPlayer.setSurfaceView(surfaceView);


        dnPlayer.setOnErrorListener(new DNPlayer.OnErrorListener() {
            @Override
            public void onError(int error) {

            }
        });
        dnPlayer.setOnProgressListener(new DNPlayer.OnProgressListener() {

            @Override
            public void onProgress(final int progress2) {
//                if (!isTouch) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            int duration = dnPlayer.getDuration();
//                            //如果是直播
//                            if (duration != 0) {
//                                if (isSeek){
//                                    isSeek = false;
//                                    return;
//                                }
//                                //更新进度 计算比例
//                                seekBar.setProgress(progress2 * 100 / duration);
//                            }
//                        }
//                    });
//                }
            }
        });

        url = getIntent().getStringExtra("url");
        dnPlayer.setDataSource(url);
        dnPlayer.prepare();
    }
}
