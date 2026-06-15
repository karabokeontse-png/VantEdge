package com.vantedge.app.util;

import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: FileUtil.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lcom/vantedge/app/util/HashUtils;", "", "()V", "sha256", "", "input", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class HashUtils {
    public static final int $stable = 0;
    public static final HashUtils INSTANCE = new HashUtils();

    private HashUtils() {
    }

    public final String sha256(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = input.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bytes2 = digest.digest(bytes);
        Intrinsics.checkNotNull(bytes2);
        return ArraysKt.joinToString$default(bytes2, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.vantedge.app.util.HashUtils$sha256$1
            public final CharSequence invoke(byte it) {
                String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(it)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                return format;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
    }
}
