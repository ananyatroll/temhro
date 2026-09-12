package com.example.ui

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import java.util.Calendar

object NotificationHelper {
    const val CHANNEL_ID = "academic_motivation_channel"
    private const val CHANNEL_NAME = "Daily Academic Motivation & Study Alerts"
    private const val CHANNEL_DESC = "Timed alerts motivating you to study and track trial deadlines."
    private const val NOTIFICATION_ID = 1001

    fun createNotificationChannel(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    description = CHANNEL_DESC
                    enableLights(true)
                    enableVibration(true)
                    setShowBadge(true)
                    lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                }
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                notificationManager?.createNotificationChannel(channel)
            }
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error creating notification channel: ${t.message}", t)
        }
    }

    fun scheduleAlarmSafely(alarmManager: AlarmManager, triggerTime: Long, pendingIntent: PendingIntent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        } catch (t: Throwable) {
            try {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } catch (ex: Throwable) {
                android.util.Log.e("NotificationHelper", "Failed to schedule alarm: ${ex.message}", ex)
            }
        }
    }

    fun sendMotivationalNotification(context: Context, studentName: String, studentGoal: String) {
        try {
            createNotificationChannel(context)

            val quotes = listOf(
                "Good morning! Remember your goal: $studentGoal. Success is built through small daily focus.",
                "Rise and shine! $studentName, your potential is unlimited. Spend 15 minutes reviewing today.",
                "A brand new day to conquer your exams! Keep pushing towards $studentGoal.",
                "Believe in yourself, $studentName. High-yield topics are waiting. Notes are fully offline!"
            )
            val selectedQuote = quotes.random()

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("☀️ Morning Goal Boost")
                .setContentText(selectedQuote)
                .setStyle(NotificationCompat.BigTextStyle().bigText(selectedQuote))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(NOTIFICATION_ID, builder.build())
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error in sendMotivationalNotification: ${t.message}", t)
        }
    }

    fun sendEventReminderNotification(context: Context, eventTitle: String, subject: String, timeRemaining: String) {
        try {
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

            val text = "Upcoming $subject Assessment: $eventTitle ($timeRemaining). Open Temhiro to review your notes."
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("📅 Academic Calendar Alert")
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(eventTitle.hashCode(), builder.build())
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error in sendEventReminderNotification: ${t.message}", t)
        }
    }

    fun sendTimerCompletedNotification(context: Context, subject: String, minutes: Int) {
        try {
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
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.notify(2001, builder.build())
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error in sendTimerCompletedNotification: ${t.message}", t)
        }
    }

    fun scheduleDailyStudyReminders(context: Context) {
        try {
            createNotificationChannel(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

            // 3 daily reminders: Morning 8:00 AM, Afternoon 2:00 PM, & Evening 8:00 PM
            val reminders = listOf(
                Triple(8, 0, "☀️ Morning Study Goal: Take 15 minutes to review high-yield summaries today!"),
                Triple(14, 0, "🎯 Afternoon Focus Boost: Complete 5 practice questions to build your score!"),
                Triple(20, 0, "🌙 Evening Focus Check: Review your study tasks and maintain your streak!")
            )

            reminders.forEachIndexed { index, (hour, minute, message) ->
                val calendar = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    if (timeInMillis <= System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }

                val intent = Intent(context, FreeTrialReceiver::class.java).apply {
                    putExtra("title", "📚 Temhiro Daily Study Alert")
                    putExtra("msg", message)
                    putExtra("id", 8000 + index)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    8000 + index,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                scheduleAlarmSafely(alarmManager, calendar.timeInMillis, pendingIntent)
            }
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error scheduling daily reminders: ${t.message}", t)
        }
    }

    fun scheduleFreeTrialNotifications(context: Context, activatedAtMillis: Long) {
        try {
            createNotificationChannel(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
            val trialDurationMillis = 72L * 3600L * 1000L
            val expiresAt = activatedAtMillis + trialDurationMillis

            // Milestones: 70h, 55h, 48h, 36h, 24h, 12h, 5h, 2h, 1h, 30m, 10m remaining
            val milestones = listOf(
                Pair(70L * 3600L * 1000L, "🚀 Welcome to your 72-Hour Full Free Trial! Explore your freshman courses now."),
                Pair(55L * 3600L * 1000L, "⏰ 55 Hours Remaining on your Free Trial! Explore high-yield chapters before they lock."),
                Pair(48L * 3600L * 1000L, "⏳ 48 Hours Remaining! 2 days left of full access to Temhiro's curriculum."),
                Pair(36L * 3600L * 1000L, "⚡ 36 Hours Remaining! Halfway through your Free Trial period."),
                Pair(24L * 3600L * 1000L, "⚠️ 24 Hours Remaining! Only 1 day left on your Free Trial."),
                Pair(12L * 3600L * 1000L, "🚨 12 Hours Remaining! Upgrade to Premium for 300 ETB to keep lifetime access."),
                Pair(5L * 3600L * 1000L, "🔥 5 Hours Remaining! Your trial courses will lock soon."),
                Pair(2L * 3600L * 1000L, "⚡ 2 Hours Remaining! Don't lose access to your study summaries."),
                Pair(1L * 3600L * 1000L, "🚨 1 Hour Remaining! Final chance before your Free Trial courses lock."),
                Pair(30L * 60L * 1000L, "⚠️ 30 Minutes Remaining! Upgrade now to keep studying uninterrupted."),
                Pair(10L * 60L * 1000L, "⏰ 10 Minutes Remaining! Upgrade now for 300 ETB to retain full access.")
            )

            val now = System.currentTimeMillis()

            milestones.forEachIndexed { index, (remainingMs, messageText) ->
                val triggerTime = expiresAt - remainingMs
                if (triggerTime > now) {
                    val intent = Intent(context, FreeTrialReceiver::class.java).apply {
                        putExtra("title", "⏰ Free Trial Status Alert")
                        putExtra("msg", messageText)
                        putExtra("id", 5000 + index)
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        5000 + index,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    scheduleAlarmSafely(alarmManager, triggerTime, pendingIntent)
                }
            }
        } catch (t: Throwable) {
            android.util.Log.e("NotificationHelper", "Error scheduling free trial notifications: ${t.message}", t)
        }
    }
}
