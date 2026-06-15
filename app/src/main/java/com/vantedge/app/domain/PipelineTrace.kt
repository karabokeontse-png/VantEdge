package com.vantedge.app.domain;

import android.util.Log;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.bouncycastle.i18n.ErrorBundle;

/* compiled from: PipelineTrace.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\nJ$\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\nJ\"\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010J,\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0014\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00010\nJ6\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00192\u0006\u0010\u001a\u001a\u00020\u0004J \u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0004J2\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u001f2\b\b\u0002\u0010 \u001a\u00020\u001f2\b\b\u0002\u0010!\u001a\u00020\u001fJ\u0016\u0010\"\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/vantedge/app/domain/PipelineTrace;", "", "()V", "TAG", "", "dataQuality", "", "stage", "issue", ErrorBundle.DETAIL_ENTRY, "", "entry", "context", "error", "reason", "throwable", "", "exit", "durationMs", "", ErrorBundle.SUMMARY_ENTRY, "validateEnum", "fieldName", "value", "allowed", "", "fallback", "validateNonBlank", "", "validateScore", "scoreName", "", "min", "max", "warn", "message", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class PipelineTrace {
    public static final int $stable = 0;
    public static final PipelineTrace INSTANCE = new PipelineTrace();
    private static final String TAG = "PipelineTrace";

    private PipelineTrace() {
    }

    public final void entry(String stage, Map<String, ? extends Object> context) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(context, "context");
        String ctx = CollectionsKt.joinToString$default(context.entrySet(), " ", null, null, 0, null, new Function1<Map.Entry<? extends String, ? extends Object>, CharSequence>() { // from class: com.vantedge.app.domain.PipelineTrace$entry$ctx$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Map.Entry<String, ? extends Object> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ((Object) it.getKey()) + "=" + it.getValue();
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Map.Entry<? extends String, ? extends Object> entry) {
                return invoke2((Map.Entry<String, ? extends Object>) entry);
            }
        }, 30, null);
        Log.i(TAG, "[PIPELINE] ENTRY " + stage + " " + ctx);
    }

    public final void exit(String stage, long durationMs, Map<String, ? extends Object> summary) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(summary, "summary");
        String summ = CollectionsKt.joinToString$default(summary.entrySet(), " ", null, null, 0, null, new Function1<Map.Entry<? extends String, ? extends Object>, CharSequence>() { // from class: com.vantedge.app.domain.PipelineTrace$exit$summ$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Map.Entry<String, ? extends Object> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ((Object) it.getKey()) + "=" + it.getValue();
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Map.Entry<? extends String, ? extends Object> entry) {
                return invoke2((Map.Entry<String, ? extends Object>) entry);
            }
        }, 30, null);
        Log.i(TAG, "[PIPELINE] EXIT " + stage + " duration=" + durationMs + "ms " + summ);
    }

    public static /* synthetic */ void error$default(PipelineTrace pipelineTrace, String str, String str2, Throwable th, int i, Object obj) {
        if ((i & 4) != 0) {
            th = null;
        }
        pipelineTrace.error(str, str2, th);
    }

    public final void error(String stage, String reason, Throwable throwable) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(reason, "reason");
        if (throwable != null) {
            Log.e(TAG, "[PIPELINE] ERROR " + stage + " " + reason, throwable);
        } else {
            Log.e(TAG, "[PIPELINE] ERROR " + stage + " " + reason);
        }
    }

    public final void warn(String stage, String message) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(message, "message");
        Log.w(TAG, "[PIPELINE] WARN " + stage + " " + message);
    }

    public final void dataQuality(String stage, String issue, Map<String, ? extends Object> details) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(issue, "issue");
        Intrinsics.checkNotNullParameter(details, "details");
        String det = CollectionsKt.joinToString$default(details.entrySet(), " ", null, null, 0, null, new Function1<Map.Entry<? extends String, ? extends Object>, CharSequence>() { // from class: com.vantedge.app.domain.PipelineTrace$dataQuality$det$1
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Map.Entry<String, ? extends Object> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ((Object) it.getKey()) + "=" + it.getValue();
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Map.Entry<? extends String, ? extends Object> entry) {
                return invoke2((Map.Entry<String, ? extends Object>) entry);
            }
        }, 30, null);
        Log.w(TAG, "[PIPELINE] DATA_QUALITY " + stage + " " + issue + " " + det);
    }

    public static /* synthetic */ boolean validateScore$default(PipelineTrace pipelineTrace, String str, String str2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            i2 = 0;
        }
        int i5 = i2;
        if ((i4 & 16) != 0) {
            i3 = 100;
        }
        return pipelineTrace.validateScore(str, str2, i, i5, i3);
    }

    public final boolean validateScore(String stage, String scoreName, int value, int min, int max) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(scoreName, "scoreName");
        if (min <= value && value <= max) {
            return true;
        }
        warn(stage, scoreName + " out of range [" + min + ", " + max + "]: got " + value);
        return false;
    }

    public final boolean validateNonBlank(String stage, String fieldName, String value) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        String str = value;
        if (!(str == null || StringsKt.isBlank(str))) {
            return true;
        }
        warn(stage, fieldName + " is blank or null");
        return false;
    }

    public final String validateEnum(String stage, String fieldName, String value, Set<String> allowed, String fallback) {
        Intrinsics.checkNotNullParameter(stage, "stage");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(allowed, "allowed");
        Intrinsics.checkNotNullParameter(fallback, "fallback");
        if (value != null && allowed.contains(value)) {
            return value;
        }
        warn(stage, fieldName + " not in allowed set " + allowed + ": got '" + value + "', using fallback '" + fallback + OperatorName.SHOW_TEXT_LINE);
        return fallback;
    }
}
