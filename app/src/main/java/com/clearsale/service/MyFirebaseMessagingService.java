package com.clearsale.service;

import android.util.Log;

import com.clearsale.model.Notification;
import com.clearsale.utils.NotificationUtils;
import com.clearsale.utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("karman", "message received");
        Utils.showLog (Log.DEBUG, TAG, "from " + remoteMessage.getFrom (), true);
        Utils.showLog (Log.DEBUG, TAG, "To " + remoteMessage.getTo (), true);
        Utils.showLog (Log.DEBUG, TAG, "Collapse Key " + remoteMessage.getCollapseKey (), true);
        Utils.showLog (Log.DEBUG, TAG, "Message ID " + remoteMessage.getMessageId (), true);
        Utils.showLog (Log.DEBUG, TAG, "Message Type" + remoteMessage.getMessageType (), true);
        Utils.showLog (Log.DEBUG, TAG, "Sent Time " + remoteMessage.getSentTime (), true);
        Utils.showLog (Log.DEBUG, TAG, "TTL " + remoteMessage.getTtl (), true);
        
        if (remoteMessage == null)
            return;
        if (remoteMessage.getData ().size () > 0) {
            Utils.showLog (Log.DEBUG, TAG, "Data Payload: " + remoteMessage.getData ().toString (), true);
            try {
                handleDataMessage (new JSONObject (remoteMessage.getData ().toString ()));
            } catch (Exception e) {
                Utils.showLog (Log.ERROR, TAG, "Exception: " + e.getMessage (), true);
            }
        } else {
            // Create Notification
//            Intent intent = new Intent (this, MainActivity.class);
//            intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity (this, 1410, intent, PendingIntent.FLAG_ONE_SHOT);

//            NotificationCompat.Builder notificationBuilder = new
//                    NotificationCompat.Builder (this)
//                    .setContentTitle (remoteMessage.getNotification ().getTitle ())
//                    .setContentText (remoteMessage.getNotification ().getBody ())
//                    .setSmallIcon (R.drawable.hometrust_notification_icon)
//                    .setAutoCancel (true)
//                    .setContentIntent (pendingIntent);

//            NotificationManager notificationManager = (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
//            notificationManager.notify (1410, notificationBuilder.build ());
        }
    }
    
    private void handleDataMessage (JSONObject notificationData) {
        Notification notification = new Notification();
        try {
            JSONObject data = notificationData.getJSONObject ("data");
            notification.setBackground (data.getBoolean ("is_background"));
            notification.setTitle (data.getString ("title"));
            notification.setMessage (data.getString ("message"));
            notification.setImage_url (data.getString ("image"));
            notification.setTimestamp (data.getString ("timestamp"));
            notification.setPayload (data.getJSONObject ("payload"));
    
            JSONObject payload = data.getJSONObject ("payload");

            notification.setNotification_style (payload.getInt ("notification_style"));
            notification.setNotification_type (payload.getInt ("notification_type"));
            notification.setNotification_priority (payload.getInt ("notification_priority"));
        } catch (JSONException e) {
            Utils.showLog (Log.ERROR, TAG, "JSON Exception: " + e.getMessage (), true);
        } catch (Exception e) {
            Utils.showLog (Log.ERROR, TAG, "Exception: " + e.getMessage (), true);
        }
        new NotificationUtils (getApplicationContext ()).showNotificationMessage (notification);
    }
}
