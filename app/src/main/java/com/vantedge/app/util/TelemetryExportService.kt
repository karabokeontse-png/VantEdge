package com.vantedge.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.core.content.FileProvider;
import com.vantedge.app.data.storage.TelemetryStorage;
import java.io.File;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;

/* compiled from: TelemetryExportService.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bH\u0086@¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\tJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000e\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/vantedge/app/util/TelemetryExportService;", "", "context", "Landroid/content/Context;", "telemetryStorage", "Lcom/vantedge/app/data/storage/TelemetryStorage;", "(Landroid/content/Context;Lcom/vantedge/app/data/storage/TelemetryStorage;)V", "createExportFile", "Lkotlin/Pair;", "Ljava/io/File;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getShareIntent", "Landroid/content/Intent;", "exportFile", "getShareUri", "Landroid/net/Uri;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class TelemetryExportService {
    private static final String EXPORT_FILE_PREFIX = "vantedge_telemetry_";
    private static final String EXPORT_FILE_SUFFIX = ".log";
    private static final String FILE_PROVIDER_AUTHORITY_SUFFIX = ".fileprovider";
    private static final String GATE0_PREFIX = "GATE0";
    private static final int SCHEMA_VERSION = 1;
    private static final String TAG = "TelemetryExportService";
    private final Context context;
    private final TelemetryStorage telemetryStorage;
    public static final int $stable = 8;

    public TelemetryExportService(Context context, TelemetryStorage telemetryStorage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(telemetryStorage, "telemetryStorage");
        this.context = context;
        this.telemetryStorage = telemetryStorage;
    }

    public final Object createExportFile(Continuation<? super Pair<? extends File, String>> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new TelemetryExportService$createExportFile$2(this, null), continuation);
    }

    public final Uri getShareUri(File exportFile) {
        Intrinsics.checkNotNullParameter(exportFile, "exportFile");
        try {
            return FileProvider.getUriForFile(this.context, this.context.getPackageName() + FILE_PROVIDER_AUTHORITY_SUFFIX, exportFile);
        } catch (Exception e) {
            Log.e(TAG, "[TelemetryExportService] FileProvider URI failed — FileProvider not registered in AndroidManifest yet (Step C pending). Error: " + e.getMessage());
            return null;
        }
    }

    public final Intent getShareIntent(File exportFile) {
        Intrinsics.checkNotNullParameter(exportFile, "exportFile");
        Uri uri = getShareUri(exportFile);
        if (uri == null) {
            return null;
        }
        Intent $this$getShareIntent_u24lambda_u240 = new Intent("android.intent.action.SEND");
        $this$getShareIntent_u24lambda_u240.setType("text/plain");
        $this$getShareIntent_u24lambda_u240.putExtra("android.intent.extra.STREAM", uri);
        $this$getShareIntent_u24lambda_u240.putExtra("android.intent.extra.SUBJECT", "VantEdge Telemetry Export");
        $this$getShareIntent_u24lambda_u240.addFlags(1);
        return $this$getShareIntent_u24lambda_u240;
    }
}
