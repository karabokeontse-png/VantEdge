package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycle$1", f = "CycleViewModel.kt", i = {}, l = {925, 926}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class CycleViewModel$commitCurrentCycle$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ GenerationCycle $cycle;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$commitCurrentCycle$1(GenerationCycle generationCycle, CycleViewModel cycleViewModel, Continuation<? super CycleViewModel$commitCurrentCycle$1> continuation) {
        super(2, continuation);
        this.$cycle = generationCycle;
        this.this$0 = cycleViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$commitCurrentCycle$1(this.$cycle, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$commitCurrentCycle$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0065  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r21) {
        /*
            r20 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r20
            int r2 = r1.label
            switch(r2) {
                case 0: goto L23;
                case 1: goto L1b;
                case 2: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L13:
            r0 = r20
            r2 = r21
            kotlin.ResultKt.throwOnFailure(r2)
            goto L67
        L1b:
            r2 = r20
            r3 = r21
            kotlin.ResultKt.throwOnFailure(r3)
            goto L56
        L23:
            kotlin.ResultKt.throwOnFailure(r21)
            r2 = r20
            r3 = r21
            com.vantedge.app.data.model.GenerationCycle r4 = r2.$cycle
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r15 = 0
            r16 = 1
            r17 = 0
            r18 = 3071(0xbff, float:4.303E-42)
            r19 = 0
            com.vantedge.app.data.model.GenerationCycle r4 = com.vantedge.app.data.model.GenerationCycle.copy$default(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r15, r16, r17, r18, r19)
            com.vantedge.app.data.viewmodel.CycleViewModel r5 = r2.this$0
            com.vantedge.app.data.storage.HistoryStore r5 = com.vantedge.app.data.viewmodel.CycleViewModel.access$getHistoryStore$p(r5)
            r6 = r2
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r7 = 1
            r2.label = r7
            java.lang.Object r4 = r5.saveCycle(r4, r6)
            if (r4 != r0) goto L56
            return r0
        L56:
            com.vantedge.app.data.viewmodel.CycleViewModel r4 = r2.this$0
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6 = 2
            r2.label = r6
            java.lang.Object r4 = com.vantedge.app.data.viewmodel.CycleViewModel.access$clearActiveCycle(r4, r5)
            if (r4 != r0) goto L65
            return r0
        L65:
            r0 = r2
            r2 = r3
        L67:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel$commitCurrentCycle$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
