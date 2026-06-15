package com.vantedge.app.ui.screens;

import androidx.compose.runtime.MutableState;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ChoosePathScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.ChoosePathScreenKt$ChoosePathScreen$1$1", f = "ChoosePathScreen.kt", i = {}, l = {34, 36}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class ChoosePathScreenKt$ChoosePathScreen$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<Boolean> $showCards$delegate;
    final /* synthetic */ MutableState<Boolean> $showContent$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ChoosePathScreenKt$ChoosePathScreen$1$1(MutableState<Boolean> mutableState, MutableState<Boolean> mutableState2, Continuation<? super ChoosePathScreenKt$ChoosePathScreen$1$1> continuation) {
        super(2, continuation);
        this.$showContent$delegate = mutableState;
        this.$showCards$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ChoosePathScreenKt$ChoosePathScreen$1$1(this.$showContent$delegate, this.$showCards$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ChoosePathScreenKt$ChoosePathScreen$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r6.label
            r2 = 1
            switch(r1) {
                case 0: goto L1c;
                case 1: goto L17;
                case 2: goto L12;
                default: goto La;
            }
        La:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L12:
            r0 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L17:
            r1 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L2e
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            r1 = r6
            r3 = r1
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r1.label = r2
            r4 = 200(0xc8, double:9.9E-322)
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r4, r3)
            if (r3 != r0) goto L2e
            return r0
        L2e:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r3 = r1.$showContent$delegate
            com.vantedge.app.ui.screens.ChoosePathScreenKt.access$ChoosePathScreen$lambda$2(r3, r2)
            r3 = r1
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4 = 2
            r1.label = r4
            r4 = 400(0x190, double:1.976E-321)
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r4, r3)
            if (r3 != r0) goto L42
            return r0
        L42:
            r0 = r1
        L43:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r1 = r0.$showCards$delegate
            com.vantedge.app.ui.screens.ChoosePathScreenKt.access$ChoosePathScreen$lambda$5(r1, r2)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ChoosePathScreenKt$ChoosePathScreen$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
