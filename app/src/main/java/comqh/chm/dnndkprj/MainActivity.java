package comqh.chm.dnndkprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chm.live.LiveManager;
import com.chm.live.list.LiveList;
import com.chm.live.room.Room;
import com.chm.live.room.Videoinfo;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends RxAppCompatActivity  implements LiveAdapter.OnItemClickListener{

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private LiveAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new LiveAdapter(this);
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        initDate();
    }

    private void initDate() {

        LiveManager.getInstance()
                .getLiveList("lol")
                .compose(this.<LiveList>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<LiveList>() {
                    @Override
                    public void onNext(LiveList liveList) {
                        mAdapter.setItems(liveList.getData().getItems());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onItemClick(String id) {
        LiveManager.getInstance()
                .getLiveRoom(id)
                .compose(this.<Room>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Room>() {
                    @Override
                    public void onNext(Room room) {
                        Videoinfo info = room.getData().getInfo().getVideoinfo();
                        String[] plflags = info.getPlflag().split("_");
                        String room_key = info.getRoom_key();
                        String sign = info.getSign();
                        String ts = info.getTs();
                        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                        String v = "3";
                        if (null != plflags && plflags.length > 0) {
                            v = plflags[plflags.length - 1];
                        }
                        intent.putExtra("url", "http://pl" + v + ".live" +
                                ".panda.tv/live_panda/" + room_key
                                + "_mid" +
                                ".flv?sign=" + sign +
                                "&time=" + ts);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}