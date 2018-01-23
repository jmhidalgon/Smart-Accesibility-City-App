package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.firebaseservice;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.MainActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

public class SACAPPFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "NOTICIAS";

    public static String getInstance() {
        SACAPPControl.firebaseToken = FirebaseInstanceId.getInstance().getToken();
        return FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        SACAPPControl.firebaseToken = FirebaseInstanceId.getInstance().getToken();
    }
}
