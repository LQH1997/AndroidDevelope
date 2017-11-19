package com.example.andrew.musicbox;

/**
 * Created by Andrew on 2017/11/15.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by West on 2015/11/10.
 */
public class MusicService extends Service {


    public final IBinder binder = new MyBinder();

    public static MediaPlayer mp = new MediaPlayer();
    public MusicService() {
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/data/melt.mp3");
            mp.prepare();
        } catch (Exception e) {
            Log.d("note","can't find song");
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder{
        MusicService getService() {
            return MusicService.this;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 101: {//播放
                    if(mp.isPlaying()){
                        mp.pause();
                    } else{
                        mp.start();
                    }
                }
                break;
                case 102: {//停止
                    if(mp != null){
                        mp.stop();
                        try{
                            mp.prepare();
                            mp.seekTo(0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                break;
                default:break;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}

