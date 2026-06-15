package com.vantedge.app.util;

import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.vantedge.app.data.model.TelemetryRecord;
import com.vantedge.app.data.model.UserDecisionEvent;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* compiled from: TelemetryCollector.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0012H\u0002J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\u001aJ\u0006\u0010\u001b\u001a\u00020\u0015J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\rJ\u000e\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\tJ\f\u0010#\u001a\u00020\u0012*\u00020\rH\u0002J\f\u0010#\u001a\u00020\u0012*\u00020\tH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/vantedge/app/util/TelemetryCollector;", "", "context", "Landroid/content/Context;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Landroid/content/Context;Lkotlinx/coroutines/CoroutineDispatcher;)V", "decisionLog", "Lkotlin/collections/ArrayDeque;", "Lcom/vantedge/app/data/model/UserDecisionEvent;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "queue", "Lcom/vantedge/app/data/model/TelemetryRecord;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "seenHashes", "Ljava/util/LinkedHashSet;", "", "Lkotlin/collections/LinkedHashSet;", "telemetryFile", "Ljava/io/File;", "appendToFile", "", "line", "getDecisionEvents", "", "getLogFile", "getRecordsForCalibration", "limit", "", "record", "telemetry", "recordDecision", NotificationCompat.CATEGORY_EVENT, "toLogLine", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class TelemetryCollector {
    private static final String LOG_FILE_NAME = "telemetry.log";
    private static final int MAX_QUEUE_SIZE = 100;
    private static final String TAG = "TelemetryCollector";
    private final ArrayDeque<UserDecisionEvent> decisionLog;
    private final ReentrantLock lock;
    private final ArrayDeque<TelemetryRecord> queue;
    private final CoroutineScope scope;
    private final LinkedHashSet<String> seenHashes;
    private final File telemetryFile;
    public static final int $stable = 8;

    public TelemetryCollector(Context context, CoroutineDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.telemetryFile = new File(context.getFilesDir(), LOG_FILE_NAME);
        this.lock = new ReentrantLock();
        this.scope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(dispatcher));
        this.seenHashes = new LinkedHashSet<>(100);
        this.queue = new ArrayDeque<>(100);
        this.decisionLog = new ArrayDeque<>();
    }

    public /* synthetic */ TelemetryCollector(Context context, CoroutineDispatcher coroutineDispatcher, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? Dispatchers.getIO() : coroutineDispatcher);
    }

    public final void record(TelemetryRecord telemetry) {
        Intrinsics.checkNotNullParameter(telemetry, "telemetry");
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new TelemetryCollector$record$1(this, telemetry, null), 3, null);
    }

    public final void recordDecision(UserDecisionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new TelemetryCollector$recordDecision$1(this, event, null), 3, null);
    }

    public final List<TelemetryRecord> getRecordsForCalibration(int limit) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int safeLimit = Math.min(limit, this.queue.size());
            return CollectionsKt.takeLast(this.queue, safeLimit);
        } finally {
            reentrantLock.unlock();
        }
    }

    public final List<UserDecisionEvent> getDecisionEvents() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return CollectionsKt.toList(this.decisionLog);
        } finally {
            reentrantLock.unlock();
        }
    }

    /* renamed from: getLogFile, reason: from getter */
    public final File getTelemetryFile() {
        return this.telemetryFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void appendToFile(String line) {
        try {
            FilesKt.appendText(this.telemetryFile, line + "\n", Charsets.UTF_8);
        } catch (Exception e) {
            Log.e(TAG, "[TelemetryCollector] File write failed: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toLogLine(TelemetryRecord $this$toLogLine) {
        return "GATE0\t" + $this$toLogLine.getDocumentHash() + "\t" + $this$toLogLine.getSessionId() + "\t" + $this$toLogLine.getTimestampMs() + "\t" + $this$toLogLine.getGate0Score() + "\t" + $this$toLogLine.getGate0Threshold() + "\t" + $this$toLogLine.getGate0Accepted() + "\t" + $this$toLogLine.getGate0Reason() + "\t" + $this$toLogLine.getExtractionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toLogLine(UserDecisionEvent $this$toLogLine) {
        return "DECISION\t" + $this$toLogLine.getDocumentHash() + "\t" + $this$toLogLine.getSessionId() + "\t" + $this$toLogLine.getTimestampMs() + "\t" + $this$toLogLine.getDecisionType();
    }
}
