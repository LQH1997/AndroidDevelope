package com.example.andrew.musicbox;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity  {
    boolean isStarted = false;
    private MusicService musicService;
    private SeekBar seekBar;
    private TextView musicStatus, musicTime, totalTime;
    private Button btnPlayOrPause, btnStop, btnQuit;
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private ImageView MusicPic;
    private ObjectAnimator animator;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };
    private void bindServiceConnection() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }
    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            musicTime.setText(time.format(musicService.mp.getCurrentPosition()));
            totalTime.setText(time.format(musicService.mp.getDuration()));
            seekBar.setProgress(musicService.mp.getCurrentPosition());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicService.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicService = new MusicService();
        bindServiceConnection();
        MusicPic = (ImageView) findViewById(R.id.MusicPic);
        animator = ObjectAnimator.ofFloat(MusicPic, "rotation",  0, 360);
        seekBar = (SeekBar)this.findViewById(R.id.MusicSeekBar);
        seekBar.setProgress(musicService.mp.getCurrentPosition());
        seekBar.setMax(musicService.mp.getDuration());


        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(5000);

        musicStatus = (TextView)this.findViewById(R.id.MusicStatus);
        musicTime = (TextView)this.findViewById(R.id.MusicTime);
        totalTime = (TextView)this.findViewById(R.id.totalTime);
        btnPlayOrPause = (Button)this.findViewById(R.id.BtnPlayorPause);
        btnPlayOrPause.setText("PLAY");
        btnPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.playOrPause();
                if(btnPlayOrPause.getText().equals("PLAY")) {
                    Toast.makeText(getApplicationContext(),"Start Playing", Toast.LENGTH_SHORT).show();
                    musicStatus.setText("Playing");
                    btnPlayOrPause.setText("PAUSE");
                    if(isStarted) {
                        animator.resume();
                    } else {
                        animator.start();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Music Paused",Toast.LENGTH_SHORT).show();
                    musicStatus.setText("Paused");
                    btnPlayOrPause.setText("PLAY");
                    animator.pause();
                    isStarted = true;
                }
            }
        });
        btnStop = (Button) findViewById(R.id.BtnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicService.stop();
                seekBar.setProgress(0);
                animator.cancel();
                isStarted = false;
                btnPlayOrPause.setText("PLAY");
            }
        });
        btnQuit = (Button) findViewById(R.id.BtnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }

    @Override
    protected void onResume() {
        if(musicService.mp.isPlaying()) {
            musicStatus.setText(getResources().getString(R.string.playing));
        } else {
            musicStatus.setText(getResources().getString(R.string.pause));
        }

        seekBar.setProgress(musicService.mp.getCurrentPosition());
        seekBar.setMax(musicService.mp.getDuration());
        handler.post(runnable);
        super.onResume();
        Log.d("hint", "handler post runnable");
    }

    @Override
    public void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }

}

