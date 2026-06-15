package com.vantedge.app.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.vantedge.app.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;

/* compiled from: DeadlineNotificationSchedular.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lcom/vantedge/app/notifications/DeadlineNotificationWorker;", "Landroidx/work/Worker;", "context", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DeadlineNotificationWorker extends Worker {
    public static final int $stable = 0;
    public static final String CHANNEL_ID = "vantedge_deadlines";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeadlineNotificationWorker(Context context, WorkerParameters params) {
        super(context, params);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        String jobTitle = getInputData().getString("jobTitle");
        if (jobTitle == null) {
            jobTitle = "Job";
        }
        String company = getInputData().getString("company");
        if (company == null) {
            company = "";
        }
        String label = getInputData().getString(AnnotatedPrivateKey.LABEL);
        if (label == null) {
            label = "Deadline reminder";
        }
        Object systemService = getApplicationContext().getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager manager = (NotificationManager) systemService;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Application Deadlines", 4);
            channel.setDescription("Reminders for job application deadlines");
            manager.createNotificationChannel(channel);
        }
        int notificationId = (int) System.currentTimeMillis();
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID).setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle(jobTitle + (StringsKt.isBlank(company) ^ true ? " at " + company : "")).setContentText(label).setPriority(1).setAutoCancel(true).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        manager.notify(notificationId, notification);
        ListenableWorker.Result success = ListenableWorker.Result.success();
        Intrinsics.checkNotNullExpressionValue(success, "success(...)");
        return success;
    }
}
