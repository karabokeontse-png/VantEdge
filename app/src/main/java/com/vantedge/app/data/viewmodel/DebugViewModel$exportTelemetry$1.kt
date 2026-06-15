package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: DebugViewmodel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.DebugViewModel$exportTelemetry$1", f = "DebugViewmodel.kt", i = {}, l = {162, 166, 183}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class DebugViewModel$exportTelemetry$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ DebugViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DebugViewModel$exportTelemetry$1(DebugViewModel debugViewModel, Continuation<? super DebugViewModel$exportTelemetry$1> continuation) {
        super(2, continuation);
        this.this$0 = debugViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DebugViewModel$exportTelemetry$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DebugViewModel$exportTelemetry$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0062 A[Catch: all -> 0x00e9, TRY_LEAVE, TryCatch #1 {all -> 0x00e9, blocks: (B:19:0x005e, B:21:0x0062, B:25:0x008d, B:27:0x00be, B:28:0x00c3), top: B:18:0x005e }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008d A[Catch: all -> 0x00e9, TRY_ENTER, TryCatch #1 {all -> 0x00e9, blocks: (B:19:0x005e, B:21:0x0062, B:25:0x008d, B:27:0x00be, B:28:0x00c3), top: B:18:0x005e }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.vantedge.app.data.viewmodel.DebugViewModel$exportTelemetry$1] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.DebugViewModel$exportTelemetry$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
