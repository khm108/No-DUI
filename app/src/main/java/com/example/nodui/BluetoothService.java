package com.example.nodui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import com.example.nodui.MainActivity;

public class BluetoothService extends Service {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private boolean running = false;

    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;
    private static final int REQUEST_ENABLE_BLUETOOTH = 2;


    private final String DEVICE_NAME = "abcd";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Serial port service ID


    @Override
    public void onCreate() {
        super.onCreate();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // 기기가 블루투스를 지원하지 않는 경우
            stopSelf();
            return;
        }





        running = true;
        connectToBluetoothDevice();

        createNotificationChannel("1", "default channel", NotificationManager.IMPORTANCE_HIGH);

        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("알람시작")
                .setContentText("알람음이 재생됩니다.")
                .setSmallIcon(R.mipmap.ic_launcher)

                .build();

        startForeground(1, notification);
    }


    void createNotificationChannel(String channelId, String channelName, int importance){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, importance));
        }
    }

    void createNotification(String channelId, int id, String title, String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setPriority(NotificationCompat.PRIORITY_HIGH)          // Head Up Display를 위해 PRIORITY_HIGH 설정
                .setSmallIcon(R.drawable.ic_launcher_foreground)        // 알림시 보여지는 아이콘. 반드시 필요
                .setContentTitle(title)
                .setContentText(text)
                //.setTimeoutAfter(1000)    // 지정한 시간 이후 알림 삭제
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(text))          // 한줄 이상의 텍스트를 모두 보여주고 싶을때 사용
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);  // 알림시 효과음, 진동 여부

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    private void connectToBluetoothDevice() {
        new Thread(() -> {
            try {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
                socket.connect();
                inputStream = socket.getInputStream();

                while (running) {
                    byte[] buffer = new byte[256];
                    int bytesRead = inputStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytesRead);

                    if (readMessage.trim().equals("1")) {
                        showNotification();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        // 푸시 알림 생성 및 표시
        MainActivity mainActivity = new MainActivity();
        mainActivity.createNotification("1", 1, "음주운전 No!", "sample text"); // MainActivity의 메소드 호출
        mainActivity.receiveData();
    }



}


