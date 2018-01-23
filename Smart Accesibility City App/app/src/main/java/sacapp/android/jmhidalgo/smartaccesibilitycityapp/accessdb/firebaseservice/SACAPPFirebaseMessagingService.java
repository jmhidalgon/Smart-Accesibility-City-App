package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.firebaseservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.MainEntityActivity;

public class SACAPPFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: " + from);

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Notificación: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Notificación: " + remoteMessage.getData().toString());
        }

        showNotification();
    }

    private void showNotification(){
        Intent intent = new Intent(this, MainEntityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.saapp_menu)
                .setContentTitle("Titulo")
                .setContentText("Texto")
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
