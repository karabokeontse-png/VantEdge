package com.vantedge.app.data.storage;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: HistoryStore.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.storage.HistoryStore$deleteCycle$1", f = "HistoryStore.kt", i = {1}, l = {125, 158}, m = "invokeSuspend", n = {"$this$withLock_u24default$iv"}, s = {"L$0"})
/* loaded from: classes5.dex */
final class HistoryStore$deleteCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $id;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ HistoryStore this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    HistoryStore$deleteCycle$1(HistoryStore historyStore, String str, Continuation<? super HistoryStore$deleteCycle$1> continuation) {
        super(2, continuation);
        this.this$0 = historyStore;
        this.$id = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new HistoryStore$deleteCycle$1(this.this$0, this.$id, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((HistoryStore$deleteCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0093 A[Catch: all -> 0x00c3, TryCatch #0 {all -> 0x00c3, blocks: (B:9:0x0072, B:10:0x008d, B:12:0x0093, B:16:0x00ab, B:22:0x00b0), top: B:8:0x0072 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006f A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r17
            int r2 = r1.label
            r3 = 1
            switch(r2) {
                case 0: goto L35;
                case 1: goto L2d;
                case 2: goto L14;
                default: goto Lc;
            }
        Lc:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L14:
            r0 = r17
            r2 = r18
            r4 = 0
            java.lang.Object r5 = r0.L$2
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r6 = r0.L$1
            com.vantedge.app.data.storage.HistoryStore r6 = (com.vantedge.app.data.storage.HistoryStore) r6
            r7 = 0
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.sync.Mutex r8 = (kotlinx.coroutines.sync.Mutex) r8
            kotlin.ResultKt.throwOnFailure(r2)
            r9 = r4
            r4 = r2
            r2 = r0
            goto L70
        L2d:
            r2 = r17
            r4 = r18
            kotlin.ResultKt.throwOnFailure(r4)
            goto L50
        L35:
            kotlin.ResultKt.throwOnFailure(r18)
            r2 = r17
            r4 = r18
            com.vantedge.app.data.storage.HistoryStore r5 = r2.this$0
            com.vantedge.app.data.storage.CycleDao r5 = com.vantedge.app.data.storage.HistoryStore.access$getCycleDao$p(r5)
            java.lang.String r6 = r2.$id
            r7 = r2
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r2.label = r3
            java.lang.Object r5 = r5.deleteCycleById(r6, r7)
            if (r5 != r0) goto L50
            return r0
        L50:
            com.vantedge.app.data.storage.HistoryStore r5 = r2.this$0
            kotlinx.coroutines.sync.Mutex r8 = com.vantedge.app.data.storage.HistoryStore.access$getMutex$p(r5)
            com.vantedge.app.data.storage.HistoryStore r6 = r2.this$0
            java.lang.String r5 = r2.$id
            r7 = 0
            r9 = 0
            r10 = r2
            kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
            r2.L$0 = r8
            r2.L$1 = r6
            r2.L$2 = r5
            r11 = 2
            r2.label = r11
            java.lang.Object r10 = r8.lock(r7, r10)
            if (r10 != r0) goto L70
            return r0
        L70:
            r0 = 0
            kotlinx.coroutines.flow.MutableStateFlow r10 = com.vantedge.app.data.storage.HistoryStore.access$get_cyclesFlow$p(r6)     // Catch: java.lang.Throwable -> Lc3
            kotlinx.coroutines.flow.MutableStateFlow r6 = com.vantedge.app.data.storage.HistoryStore.access$get_cyclesFlow$p(r6)     // Catch: java.lang.Throwable -> Lc3
            java.lang.Object r6 = r6.getValue()     // Catch: java.lang.Throwable -> Lc3
            java.lang.Iterable r6 = (java.lang.Iterable) r6     // Catch: java.lang.Throwable -> Lc3
            r11 = 0
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lc3
            r12.<init>()     // Catch: java.lang.Throwable -> Lc3
            java.util.Collection r12 = (java.util.Collection) r12     // Catch: java.lang.Throwable -> Lc3
            r13 = 0
            java.util.Iterator r14 = r6.iterator()     // Catch: java.lang.Throwable -> Lc3
        L8d:
            boolean r6 = r14.hasNext()     // Catch: java.lang.Throwable -> Lc3
            if (r6 == 0) goto Lb0
            java.lang.Object r6 = r14.next()     // Catch: java.lang.Throwable -> Lc3
            r15 = r6
            com.vantedge.app.data.model.GenerationCycle r15 = (com.vantedge.app.data.model.GenerationCycle) r15     // Catch: java.lang.Throwable -> Lc3
            r16 = 0
            java.lang.String r3 = r15.getId()     // Catch: java.lang.Throwable -> Lc3
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r5)     // Catch: java.lang.Throwable -> Lc3
            if (r3 != 0) goto La8
            r3 = 1
            goto La9
        La8:
            r3 = 0
        La9:
            if (r3 == 0) goto Lae
            r12.add(r6)     // Catch: java.lang.Throwable -> Lc3
        Lae:
            r3 = 1
            goto L8d
        Lb0:
            r3 = r12
            java.util.List r3 = (java.util.List) r3     // Catch: java.lang.Throwable -> Lc3
            r10.setValue(r3)     // Catch: java.lang.Throwable -> Lc3
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> Lc3
            r8.unlock(r7)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        Lc3:
            r0 = move-exception
            r8.unlock(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.HistoryStore$deleteCycle$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
