package com.sensorberg.notificationssdksampleapp

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.sensorberg.notifications.sdk.AbstractActionReceiver
import com.sensorberg.notifications.sdk.Action
import com.sensorberg.notifications.sdk.Conversion
import timber.log.Timber

class ActionReceiver : AbstractActionReceiver() {
    override fun onAction(context: Context, action: Action) {

        Timber.i("Action received by the application. $action")

        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("action", action)
            val pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = createNotification(context, pending, action)

            App.sdk.setConversion(action, Conversion.Ignored)
            notificationManager.notify(action.instanceId.hashCode(), notification)
        } else {
            Timber.w("Notifications are disabled")
            App.sdk.setConversion(action, Conversion.NotificationDisabled)
        }
    }

    private fun createNotification(context: Context, pending: PendingIntent, action: Action): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initChannels(context)
        }
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pending)
                .setContentTitle(action.subject)
                .setContentText(action.body)
                .setSmallIcon(R.drawable.ic_notification)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true)
                .setShowWhen(true)
                .build()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NotificationChannelIdFromSdkSample"

        @TargetApi(Build.VERSION_CODES.O)
        private fun initChannels(context: Context) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification SDK", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Notification SDK"
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}