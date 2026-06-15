package com.vantedge.app.util;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppLogger.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\u0006\u0010\u000f\u001a\u00020\u000bJ\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007J\"\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0015J\u0016\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007J\u0016\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/vantedge/app/util/AppLogger;", "", "()V", "MAX_SIZE", "", "buffer", "Lkotlin/collections/ArrayDeque;", "", "dateFormat", "Ljava/text/SimpleDateFormat;", "add", "", "level", "tag", "message", "clear", OperatorName.SET_LINE_DASHPATTERN, "e", "throwable", "", "getLogs", "", OperatorName.SET_FLATNESS, OperatorName.SET_LINE_WIDTH, "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class AppLogger {
    private static final int MAX_SIZE = 500;
    public static final AppLogger INSTANCE = new AppLogger();
    private static final ArrayDeque<String> buffer = new ArrayDeque<>(500);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
    public static final int $stable = 8;

    private AppLogger() {
    }

    public final synchronized void d(String tag, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(message, "message");
        Log.d(tag, message);
        add("D", tag, message);
    }

    public static /* synthetic */ void e$default(AppLogger appLogger, String str, String str2, Throwable th, int i, Object obj) {
        if ((i & 4) != 0) {
            th = null;
        }
        appLogger.e(str, str2, th);
    }

    public final synchronized void e(String tag, String message, Throwable throwable) {
        String str;
        String it;
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(message, "message");
        Log.e(tag, message, throwable);
        if (throwable == null || (it = ExceptionsKt.stackTraceToString(throwable)) == null || (str = "\n" + it) == null) {
            str = "";
        }
        String extra = str;
        add(ExifInterface.LONGITUDE_EAST, tag, message + extra);
    }

    public final synchronized void i(String tag, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(message, "message");
        Log.i(tag, message);
        add("I", tag, message);
    }

    public final synchronized void w(String tag, String message) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(message, "message");
        Log.w(tag, message);
        add("W", tag, message);
    }

    private final void add(String level, String tag, String message) {
        String time = dateFormat.format(new Date());
        String line = time + " " + level + "/" + tag + ": " + message;
        if (buffer.size() >= 500) {
            buffer.removeFirst();
        }
        buffer.addLast(line);
    }

    public final synchronized List<String> getLogs() {
        return CollectionsKt.toList(buffer);
    }

    public final synchronized void clear() {
        buffer.clear();
    }
}
