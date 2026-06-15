package com.vantedge.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import androidx.core.content.FileProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: LogDumper.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/vantedge/app/util/LogDumper;", "", "()V", "TAG", "", "dumpAndShare", "", "context", "Landroid/content/Context;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class LogDumper {
    public static final int $stable = 0;
    public static final LogDumper INSTANCE = new LogDumper();
    private static final String TAG = "LogDumper";

    private LogDumper() {
    }

    public final void dumpAndShare(Context context) {
        String pid;
        Intrinsics.checkNotNullParameter(context, "context");
        File logFile = new File(context.getCacheDir(), "vantedge_full_log.txt");
        try {
            List appLogs = AppLogger.INSTANCE.getLogs();
            if (!appLogs.isEmpty()) {
                pid = CollectionsKt.joinToString$default(appLogs, "\n", null, null, 0, null, null, 62, null);
            } else {
                String pid2 = String.valueOf(Process.myPid());
                String[] cmd = {"logcat", "-d", "-v", "threadtime", "--pid=" + pid2, "-t", "2000"};
                Process process = Runtime.getRuntime().exec(cmd);
                InputStream inputStream = process.getInputStream();
                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
                BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
                try {
                    BufferedReader it = bufferedReader;
                    String output = TextStreamsKt.readText(it);
                    CloseableKt.closeFinally(bufferedReader, null);
                    InputStream errorStream = process.getErrorStream();
                    Intrinsics.checkNotNullExpressionValue(errorStream, "getErrorStream(...)");
                    Reader inputStreamReader2 = new InputStreamReader(errorStream, Charsets.UTF_8);
                    bufferedReader = inputStreamReader2 instanceof BufferedReader ? (BufferedReader) inputStreamReader2 : new BufferedReader(inputStreamReader2, 8192);
                    try {
                        BufferedReader it2 = bufferedReader;
                        String errors = TextStreamsKt.readText(it2);
                        CloseableKt.closeFinally(bufferedReader, null);
                        process.waitFor();
                        if (StringsKt.isBlank(output) && (!StringsKt.isBlank(errors))) {
                            pid = "Logcat failed:\n" + errors;
                        } else {
                            pid = output;
                        }
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                } finally {
                }
            }
            String output2 = pid;
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            StringBuilder $this$dumpAndShare_u24lambda_u242 = new StringBuilder();
            StringBuilder append = $this$dumpAndShare_u24lambda_u242.append("=== VANTEDGE FULL LOG DUMP ===");
            Intrinsics.checkNotNullExpressionValue(append, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append.append('\n'), "append(...)");
            StringBuilder append2 = $this$dumpAndShare_u24lambda_u242.append("Timestamp: " + timestamp);
            Intrinsics.checkNotNullExpressionValue(append2, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append2.append('\n'), "append(...)");
            StringBuilder append3 = $this$dumpAndShare_u24lambda_u242.append("Device: " + Build.MANUFACTURER + " " + Build.MODEL);
            Intrinsics.checkNotNullExpressionValue(append3, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append3.append('\n'), "append(...)");
            StringBuilder append4 = $this$dumpAndShare_u24lambda_u242.append("Android: " + Build.VERSION.SDK_INT);
            Intrinsics.checkNotNullExpressionValue(append4, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append4.append('\n'), "append(...)");
            StringBuilder append5 = $this$dumpAndShare_u24lambda_u242.append("App logs in buffer: " + AppLogger.INSTANCE.getLogs().size());
            Intrinsics.checkNotNullExpressionValue(append5, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append5.append('\n'), "append(...)");
            StringBuilder append6 = $this$dumpAndShare_u24lambda_u242.append("====================================");
            Intrinsics.checkNotNullExpressionValue(append6, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append6.append('\n'), "append(...)");
            Intrinsics.checkNotNullExpressionValue($this$dumpAndShare_u24lambda_u242.append('\n'), "append(...)");
            if (!StringsKt.isBlank(output2)) {
                StringBuilder append7 = $this$dumpAndShare_u24lambda_u242.append(output2);
                Intrinsics.checkNotNullExpressionValue(append7, "append(...)");
                Intrinsics.checkNotNullExpressionValue(append7.append('\n'), "append(...)");
            } else {
                StringBuilder append8 = $this$dumpAndShare_u24lambda_u242.append("=== NO LOGS CAPTURED ===");
                Intrinsics.checkNotNullExpressionValue(append8, "append(...)");
                Intrinsics.checkNotNullExpressionValue(append8.append('\n'), "append(...)");
                StringBuilder append9 = $this$dumpAndShare_u24lambda_u242.append("Log buffer is empty and logcat returned nothing.");
                Intrinsics.checkNotNullExpressionValue(append9, "append(...)");
                Intrinsics.checkNotNullExpressionValue(append9.append('\n'), "append(...)");
                StringBuilder append10 = $this$dumpAndShare_u24lambda_u242.append("This usually means the device blocks logcat access.");
                Intrinsics.checkNotNullExpressionValue(append10, "append(...)");
                Intrinsics.checkNotNullExpressionValue(append10.append('\n'), "append(...)");
                StringBuilder append11 = $this$dumpAndShare_u24lambda_u242.append("Fix: Replace Log.d() calls with AppLogger.d() in critical paths.");
                Intrinsics.checkNotNullExpressionValue(append11, "append(...)");
                Intrinsics.checkNotNullExpressionValue(append11.append('\n'), "append(...)");
            }
            String finalText = $this$dumpAndShare_u24lambda_u242.toString();
            Intrinsics.checkNotNullExpressionValue(finalText, "toString(...)");
            FilesKt.writeText$default(logFile, finalText, null, 2, null);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", logFile);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.putExtra("android.intent.extra.SUBJECT", "VantEdge Full Logs");
            intent.addFlags(1);
            context.startActivity(Intent.createChooser(intent, "Share VantEdge Logs"));
        } catch (Exception e) {
            Log.e(TAG, "Dump failed: " + e.getMessage());
            try {
                File errorFile = new File(context.getCacheDir(), "vantedge_dump_error.txt");
                FilesKt.writeText$default(errorFile, "Log dump failed: " + e.getMessage() + "\n\n" + ExceptionsKt.stackTraceToString(e), null, 2, null);
                Uri uri2 = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", errorFile);
                Intent $this$dumpAndShare_u24lambda_u244 = new Intent("android.intent.action.SEND");
                $this$dumpAndShare_u24lambda_u244.setType("text/plain");
                $this$dumpAndShare_u24lambda_u244.putExtra("android.intent.extra.STREAM", uri2);
                context.startActivity(Intent.createChooser($this$dumpAndShare_u24lambda_u244, "Share Error"));
            } catch (Exception e2) {
            }
        }
    }
}
