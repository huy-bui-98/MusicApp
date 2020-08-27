package com.huybui98.musicapplicationhuy.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.models.Song

/**
 * Created by huy-bui-98 on 08/25/20
 * This is class to create player notification
 */

class CreatePlayerNotification {
    companion object {
        const val CHANNEL_ID = "MusicPlayerID"
        const val CHANNEL_NAME = "Music Player"
        const val ACTION_PREVIOUS = "action_previous"
        const val ACTION_PLAY = "action_play"
        const val ACTION_NEXT = "action_next"
        var notification = Notification()
    }


    fun createNotification(context: Context, song: Song, playerButton: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            var picture = song.getPicture(context, smallIcon = false, notification = true)
            if (picture == null) {
                picture = ContextCompat.getDrawable(context, R.drawable.img_logo_music)?.toBitmap()
            }
            val mediaSessionCompat = MediaSessionCompat(context, "tag")
            // create previous button in notification
            val iconPrevious = R.drawable.ic_previous_notification
            val intentPrevious =
                Intent(
                    context,
                    NotificationBroadcastReceiver::class.java
                ).setAction(ACTION_PREVIOUS)
            val pendingIntentPrevious = PendingIntent.getBroadcast(
                context,
                0,
                intentPrevious,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // create play button in notification
            val intentPlay =
                Intent(context, NotificationBroadcastReceiver::class.java).setAction(ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(
                context,
                0,
                intentPlay,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // create next button in notification
            val iconNext = R.drawable.ic_next_notification
            val intentNext =
                Intent(context, NotificationBroadcastReceiver::class.java).setAction(ACTION_NEXT)
            val pendingIntentNext = PendingIntent.getBroadcast(
                context,
                0,
                intentNext,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // create intent to application when click on notification
            val notifyIntent = Intent(context, MusicActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_notification)
                .setLargeIcon(picture)
                .setContentTitle(song.nameSong)
                .setContentText(song.singer)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setContentIntent(notifyPendingIntent)
                .addAction(iconPrevious, ACTION_PREVIOUS, pendingIntentPrevious)
                .addAction(playerButton, ACTION_PLAY, pendingIntentPlay)
                .addAction(iconNext, ACTION_NEXT, pendingIntentNext)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.sessionToken)
                )
                .setPriority(NotificationCompat.PRIORITY_LOW).build()
            notification.flags = Notification.FLAG_NO_CLEAR
            notificationManagerCompat.notify(1, notification)
        }
    }
}
