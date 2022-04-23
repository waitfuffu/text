package com.example.app13;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app13.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(viewBinding.getRoot());
        Button b1 = viewBinding.b1;
        Button b2 = viewBinding.b2;

        //获取通知管理器，用于发送通知.  服务对象
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String id = "channel_1";      //自定义设置通道id
        String description = "123";   //通道描述属性
        int importance = NotificationManager.IMPORTANCE_HIGH;//通知栏重要提示声音设定
        int NOTIFYID_1 = 0x1;
        int NOTIFYID_2 = 0x2;
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //建立通知栏通道类 ，（需要有id 重要属性）
                NotificationChannel mChannel = new NotificationChannel(id, "123", importance);
                mChannel.setDescription(description);//设置通知渠道属性
                mChannel.enableLights(true);//设置通知出现时闪灯
                mChannel.enableVibration(true); //设置通知时 震动
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(mChannel);//在notificationManager中创建该通知渠道

                //仅状态栏消息notification1
                Notification notification1 = new Notification.Builder(MainActivity.this, id) //创建 Notification对象
                        .setContentTitle("通知标题")//通知的标题
                        .setSmallIcon(R.drawable.i12)   //设置小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.i12))//设置大图标
                        .setContentText("通知内容")//通知内容
                        .setAutoCancel(true) //设置自动删除通知
                        .build();//运行

             //   notificationManager.notify(NOTIFYID_1,notification1);//通知栏 保留多条通知

                //关联Activity  notification2

                Intent intent = new Intent(MainActivity.this,Content.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                Notification notification2 = new Notification.Builder(MainActivity.this, id)
                        .setContentTitle("通知标题222222")//通知的标题
                        .setSmallIcon(R.drawable.i12)   //设置小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.i12))//设置大图标
                        .setContentText("通知内容22222")//通知内容
                        .setAutoCancel(true) //设置自动删除通知
                        .setContentIntent(pendingIntent)
                        .build();

                    notificationManager.notify(NOTIFYID_2,notification2);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notificationManager.cancel(NOTIFYID_1);//清除固定id的通知
                notificationManager.cancelAll();

            }
        });


    }
}