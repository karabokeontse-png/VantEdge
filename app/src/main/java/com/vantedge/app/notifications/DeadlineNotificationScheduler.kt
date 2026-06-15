package com.vantedge.app.notifications;

import android.content.Context;
import androidx.compose.material3.CalendarModelKt;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;

/* compiled from: DeadlineNotificationSchedular.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0002J.\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\b¨\u0006\u000f"}, d2 = {"Lcom/vantedge/app/notifications/DeadlineNotificationScheduler;", "", "()V", "cancel", "", "context", "Landroid/content/Context;", "recordId", "", "sameDayAt9AM", "deadlineMs", "schedule", "jobTitle", "", "company", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DeadlineNotificationScheduler {
    public static final int $stable = 0;
    public static final DeadlineNotificationScheduler INSTANCE = new DeadlineNotificationScheduler();

    private DeadlineNotificationScheduler() {
    }

    public final void schedule(Context context, long recordId, String jobTitle, String company, long deadlineMs) {
        List reminders;
        String jobTitle2 = jobTitle;
        String company2 = company;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(jobTitle2, "jobTitle");
        Intrinsics.checkNotNullParameter(company2, "company");
        WorkManager workManager = WorkManager.getInstance(context);
        Intrinsics.checkNotNullExpressionValue(workManager, "getInstance(...)");
        long now = System.currentTimeMillis();
        String tag = "deadline_" + recordId;
        List reminders2 = CollectionsKt.listOf((Object[]) new Triple[]{new Triple(Long.valueOf(deadlineMs - 259200000), "Application closes in 3 days", "3-day"), new Triple(Long.valueOf(deadlineMs - CalendarModelKt.MillisecondsIn24Hours), "Application closes tomorrow!", "1-day"), new Triple(Long.valueOf(sameDayAt9AM(deadlineMs)), "Application closes today!", "same-day")});
        List $this$forEach$iv = reminders2;
        for (Object element$iv : $this$forEach$iv) {
            Triple triple = (Triple) element$iv;
            long triggerMs = ((Number) triple.component1()).longValue();
            String message = (String) triple.component2();
            String suffix = (String) triple.component3();
            Iterable $this$forEach$iv2 = $this$forEach$iv;
            long delayMs = triggerMs - now;
            if (delayMs > 0) {
                reminders = reminders2;
                Data data = new Data.Builder().putString("jobTitle", jobTitle2).putString("company", company2).putString(AnnotatedPrivateKey.LABEL, message).build();
                Intrinsics.checkNotNullExpressionValue(data, "build(...)");
                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DeadlineNotificationWorker.class).setInitialDelay(delayMs, TimeUnit.MILLISECONDS).setInputData(data).addTag(tag).addTag(tag + "_" + suffix).build();
                workManager.enqueue(request);
            } else {
                reminders = reminders2;
            }
            jobTitle2 = jobTitle;
            company2 = company;
            $this$forEach$iv = $this$forEach$iv2;
            reminders2 = reminders;
        }
    }

    public final void cancel(Context context, long recordId) {
        Intrinsics.checkNotNullParameter(context, "context");
        WorkManager.getInstance(context).cancelAllWorkByTag("deadline_" + recordId);
    }

    private final long sameDayAt9AM(long deadlineMs) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(deadlineMs);
        cal.set(11, 9);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTimeInMillis();
    }
}
