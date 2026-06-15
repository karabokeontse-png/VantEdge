package com.vantedge.app.util;

import android.content.Context;
import kotlin.Metadata;

/* compiled from: FailureLogBundle.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/util/FailureLogBundle;", "", "()V", "attachCvFailure", "", "context", "Landroid/content/Context;", "error", "", "contextData", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class FailureLogBundle {
    public static final int $stable = 0;
    public static final FailureLogBundle INSTANCE = new FailureLogBundle();

    private FailureLogBundle() {
    }

    public static /* synthetic */ void attachCvFailure$default(FailureLogBundle failureLogBundle, Context context, Throwable th, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = null;
        }
        failureLogBundle.attachCvFailure(context, th, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0107 A[Catch: Exception -> 0x0167, TryCatch #1 {Exception -> 0x0167, blocks: (B:3:0x0013, B:5:0x0068, B:6:0x0073, B:10:0x0081, B:12:0x00fb, B:17:0x0107, B:18:0x012c, B:30:0x0163, B:31:0x0166, B:32:0x006b, B:26:0x0160, B:8:0x0075), top: B:2:0x0013, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void attachCvFailure(android.content.Context r17, java.lang.Throwable r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.util.FailureLogBundle.attachCvFailure(android.content.Context, java.lang.Throwable, java.lang.String):void");
    }
}
