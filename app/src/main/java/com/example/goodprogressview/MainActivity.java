package com.example.goodprogressview;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    GoodProgressView good_progress_view1;
    GoodProgressView good_progress_view2;

    int progressValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_main);
        good_progress_view1 = (GoodProgressView)findViewById(R.id.good_progress_view1);
        good_progress_view2 = (GoodProgressView)findViewById(R.id.good_progress_view2);

        //第一个进度条使用默认进度颜色，第二个指定颜色（随机生成）
        good_progress_view2.setColors(randomColors());

        timer.schedule(task, 1000, 1000); // 1s后执行task,经过1s再次执行
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.i("log","handler : progressValue="+progressValue);

                //通知view，进度值有变化
                good_progress_view1.setProgressValue(progressValue*2);
                good_progress_view1.postInvalidate();

                good_progress_view2.setProgressValue(progressValue);
                good_progress_view2.postInvalidate();

                progressValue+=1;
                if(progressValue>100){
                    timer.cancel();
                }
            }
            super.handleMessage(msg);
        };
    };

    private int[] randomColors() {
        int[] colors=new int[2];

        Random random = new Random();
        int r,g,b;
        for(int i=0;i<2;i++){
            r=random.nextInt(256);
            g=random.nextInt(256);
            b=random.nextInt(256);
            colors[i]= Color.argb(255, r, g, b);
            Log.i("customView","log: colors["+i+"]="+Integer.toHexString(colors[i]));
        }

        return colors;
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

}
