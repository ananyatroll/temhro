package com.example.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "academic_motivation_channel"
    private const val CHANNEL_NAME = "Daily Academic Motivation"
    private const val CHANNEL_DESC = "Morning alerts motivating you to crush your curriculum goals."
    private const val NOTIFICATION_ID = 1001

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendMotivationalNotification(context: Context, studentName: String, studentGoal: String) {
        createNotificationChannel(context)

        val quotes = listOf(
            "Good morning! Remember your goal: $studentGoal. Success is a collection of small daily efforts.",
            "Rise and shine! $studentName, your potential is unlimited. Spend 15 minutes of focus today.",
            "A brand new day to conquer your exams! Keep pushing towards $studentGoal.",
            "Believe in yourself, $studentName. High-yield topics are waiting. Standard notes are fully offline!"
        )
        val selectedQuote = quotes.random()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // System fallback icon
            .setContentTitle("☀️ Morning Goal Boost")
            .setContentText(selectedQuote)
            .setStyle(NotificationCompat.BigTextStyle().bigText(selectedQuote))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        try {
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun sendEventReminderNotification(context: Context, eventTitle: String, subject: String, timeRemaining: String) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            eventTitle.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val text = "Upcoming $subject Assessment: $eventTitle ($timeRemaining). Open Tamhero to review your notes."
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("📅 Academic Calendar Alert")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        try {
            notificationManager.notify(eventTitle.hashCode(), builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun sendTimerCompletedNotification(context: Context, subject: String, minutes: Int) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            2001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val text = "Great job! You completed a $minutes-minute deep focus session in $subject. Take a 5-minute break!"
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("⏱️ Study Session Completed!")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        try {
            notificationManager.notify(2001, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun scheduleFreeTrialNotifications(context: Context, activatedAtMillis: Long) {
        createNotificationChannel(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? android.app.AlarmManager ?: return
        val trialDurationMillis = 72L * 3600L * 1000L
        val expiresAt = activatedAtMillis + trialDurationMillis

        // Milestones: 55h, 48h, 36h, 24h, 12h, 5h, 1h, 10m remaining
        val milestones = listOf(
            Pair(55L * 3600L * 1000L, "⏰ 55 Hours Remaining on your Free Trial! Explore your course before it locks."),
            Pair(48L * 3600L * 1000L, "⏳ 48 Hours Remaining! 2 days left to try Tamhero's high-yield content."),
            Pair(36L * 3600L * 1000L, "⚡ 36 Hours Remaining! Halfway through your Free Trial period."),
            Pair(24L * 3600L * 1000L, "⚠️ 24 Hours Remaining! Only 1 day left on your Free Trial."),
            Pair(12L * 3600L * 1000L, "🚨 12 Hours Remaining! Upgrade to Premium for 300 ETB to keep unlimited access."),
            Pair(5L * 3600L * 1000L, "🔥 5 Hours Remaining! Your trial course will lock soon."),
            Pair(1L * 3600L * 1000L, "🚨 1 Hour Remaining! Final chance before your Free Trial course locks."),
            Pair(10L * 60L * 1000L, "⏰ 10 Minutes Remaining! Upgrade now for 300 ETB to retain full access.")
        )

        val now = System.currentTimeMillis()

        milestones.forEachIndexed { index, (remainingMs, messageText) ->
            val triggerTime = expiresAt - remainingMs
            if (triggerTime > now) {
                val intent = Intent(context, FreeTrialReceiver::class.java).apply {
                    putExtra("msg", messageText)
                    putExtra("id", 5000 + index)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    5000 + index,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    } else {
                        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
