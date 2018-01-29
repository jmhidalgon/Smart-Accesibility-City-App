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

import java.util.Map;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.DetailsActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.MainEntityActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.UserDetailActivity;

public class SACAPPFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTICIAS";
    private String notificationTitle;
    private String notificationContent;
    private String userId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        /*Log.d(TAG, "Mensaje recibido de: " + from);
        */
        Map<String, String> messageData;

        if(remoteMessage.getNotification() != null){
            messageData = remoteMessage.getData();
            notificationTitle = messageData.get("title");
            userId = messageData.get("userId");
            notificationContent = messageData.get("notificationContent");
        }

        showNotification();
    }

    private void showNotification(){

        if(userId == null){
            return;
        }

        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("userId", userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.saapp_menu)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
