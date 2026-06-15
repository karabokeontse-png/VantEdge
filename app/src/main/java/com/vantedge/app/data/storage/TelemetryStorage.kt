package com.vantedge.app.data.storage;

import android.content.Context;
import android.util.Log;
import com.vantedge.app.data.model.TelemetryRecord;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: TelemetryStorage.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@¢\u0006\u0002\u0010\nJ\u0006\u0010\u000b\u001a\u00020\u0006J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0086@¢\u0006\u0002\u0010\u000fJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/storage/TelemetryStorage;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "liveFile", "Ljava/io/File;", "getExportRecords", "", "Lcom/vantedge/app/data/model/TelemetryRecord;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLiveFileReference", "getRecentRecords", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseLine", "line", "", "readGate0Records", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class TelemetryStorage {
    private static final int GATE0_FIELD_COUNT = 9;
    private static final String GATE0_PREFIX = "GATE0";
    private static final String LOG_FILE_NAME = "telemetry.log";
    public static final int MAX_DISPLAY_RECORDS = 20;
    public static final int MAX_EXPORT_RECORDS = 500;
    private static final String TAG = "TelemetryStorage";
    private final Context context;
    private final File liveFile;
    public static final int $stable = 8;

    public TelemetryStorage(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.liveFile = new File(this.context.getFilesDir(), LOG_FILE_NAME);
    }

    public static /* synthetic */ Object getRecentRecords$default(TelemetryStorage telemetryStorage, int i, Continuation continuation, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 20;
        }
        return telemetryStorage.getRecentRecords(i, continuation);
    }

    public final Object getRecentRecords(int limit, Continuation<? super List<TelemetryRecord>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new TelemetryStorage$getRecentRecords$2(this, limit, null), continuation);
    }

    public final Object getExportRecords(Continuation<? super List<TelemetryRecord>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new TelemetryStorage$getExportRecords$2(this, null), continuation);
    }

    /* renamed from: getLiveFileReference, reason: from getter */
    public final File getLiveFile() {
        return this.liveFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0128, code lost:
    
        if (r0.exists() != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0152, code lost:
    
        r0.delete();
        android.util.Log.d(com.vantedge.app.data.storage.TelemetryStorage.TAG, "[TelemetryStorage] Snapshot deleted.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0159, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0150, code lost:
    
        if (r0.exists() == false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List<com.vantedge.app.data.model.TelemetryRecord> readGate0Records(int r23) {
        /*
            Method dump skipped, instructions count: 359
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.TelemetryStorage.readGate0Records(int):java.util.List");
    }

    private final TelemetryRecord parseLine(String line) {
        try {
            List parts = StringsKt.split$default((CharSequence) line, new String[]{"\t"}, false, 0, 6, (Object) null);
            if (parts.size() != 9) {
                Log.d(TAG, "[TelemetryStorage] Skipping malformed line (expected=9 fields, got=" + parts.size() + ")");
                return null;
            }
            return new TelemetryRecord((String) parts.get(1), (String) parts.get(2), Long.parseLong((String) parts.get(3)), Integer.parseInt((String) parts.get(4)), Integer.parseInt((String) parts.get(5)), Boolean.parseBoolean((String) parts.get(6)), (String) parts.get(7), (String) parts.get(8));
        } catch (Exception e) {
            Log.d(TAG, "[TelemetryStorage] Parse exception on line: " + e.getMessage());
            return null;
        }
    }
}
