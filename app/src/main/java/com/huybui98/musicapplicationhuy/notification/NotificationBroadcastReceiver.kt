package com.huybui98.musicapplicationhuy.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by huy-bui-98 on 08/25/20
 * This is BroadcastReceiver class for notification
 */

open class NotificationBroadcastReceiver: BroadcastReceiver() {
    companion object{
        internal const val ACTION_KEY = "player_music"
        internal const val ACTION_NAME = "action_name"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent(ACTION_KEY).putExtra(ACTION_NAME,intent?.action))
    }
}
