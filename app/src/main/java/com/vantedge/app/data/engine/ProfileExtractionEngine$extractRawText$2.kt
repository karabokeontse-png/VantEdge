package com.vantedge.app.data.engine;

import android.net.Uri;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "Lkotlin/Result;", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$2", f = "ProfileExtractionEngine.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class ProfileExtractionEngine$extractRawText$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Result<? extends String>>, Object> {
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ ProfileExtractionEngine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProfileExtractionEngine$extractRawText$2(ProfileExtractionEngine profileExtractionEngine, Uri uri, Continuation<? super ProfileExtractionEngine$extractRawText$2> continuation) {
        super(2, continuation);
        this.this$0 = profileExtractionEngine;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProfileExtractionEngine$extractRawText$2(this.this$0, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Result<? extends String>> continuation) {
        return invoke2(coroutineScope, (Continuation<? super Result<String>>) continuation);
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super Result<String>> continuation) {
        return ((ProfileExtractionEngine$extractRawText$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00dc A[Catch: Exception -> 0x00fb, TryCatch #0 {Exception -> 0x00fb, blocks: (B:8:0x0017, B:11:0x002b, B:13:0x0042, B:16:0x004b, B:18:0x0058, B:20:0x0065, B:22:0x006d, B:25:0x0076, B:27:0x007e, B:29:0x0086, B:31:0x008e, B:34:0x0097, B:35:0x00d2, B:37:0x00dc, B:39:0x00f4, B:42:0x00a0, B:43:0x00a9, B:44:0x00b2, B:46:0x00c3), top: B:7:0x0017 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00f4 A[Catch: Exception -> 0x00fb, TRY_LEAVE, TryCatch #0 {Exception -> 0x00fb, blocks: (B:8:0x0017, B:11:0x002b, B:13:0x0042, B:16:0x004b, B:18:0x0058, B:20:0x0065, B:22:0x006d, B:25:0x0076, B:27:0x007e, B:29:0x0086, B:31:0x008e, B:34:0x0097, B:35:0x00d2, B:37:0x00dc, B:39:0x00f4, B:42:0x00a0, B:43:0x00a9, B:44:0x00b2, B:46:0x00c3), top: B:7:0x0017 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
