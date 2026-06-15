package com.vantedge.app.ui.screens;

import androidx.compose.runtime.MutableState;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: PipelineLoadingScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.PipelineLoadingScreenKt$FullPipelineLoadingScreen$2", f = "PipelineLoadingScreen.kt", i = {0, 1}, l = {167, 169}, m = "invokeSuspend", n = {"messages", "messages"}, s = {"L$0", "L$0"})
/* loaded from: classes6.dex */
final class PipelineLoadingScreenKt$FullPipelineLoadingScreen$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Map<Integer, List<String>> $stageMessages;
    final /* synthetic */ MutableState<Integer> $subMessageIndex$delegate;
    final /* synthetic */ MutableState<Boolean> $subVisible$delegate;
    final /* synthetic */ MutableState<Integer> $visualStep$delegate;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    PipelineLoadingScreenKt$FullPipelineLoadingScreen$2(Map<Integer, ? extends List<String>> map, MutableState<Integer> mutableState, MutableState<Integer> mutableState2, MutableState<Boolean> mutableState3, Continuation<? super PipelineLoadingScreenKt$FullPipelineLoadingScreen$2> continuation) {
        super(2, continuation);
        this.$stageMessages = map;
        this.$subMessageIndex$delegate = mutableState;
        this.$visualStep$delegate = mutableState2;
        this.$subVisible$delegate = mutableState3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PipelineLoadingScreenKt$FullPipelineLoadingScreen$2(this.$stageMessages, this.$subMessageIndex$delegate, this.$visualStep$delegate, this.$subVisible$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((PipelineLoadingScreenKt$FullPipelineLoadingScreen$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0055 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006b A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0069 -> B:7:0x006c). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 0
            r3 = 1
            switch(r1) {
                case 0: goto L25;
                case 1: goto L1c;
                case 2: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L13:
            r1 = r8
            java.lang.Object r4 = r1.L$0
            java.util.List r4 = (java.util.List) r4
            kotlin.ResultKt.throwOnFailure(r9)
            goto L6c
        L1c:
            r1 = r8
            java.lang.Object r4 = r1.L$0
            java.util.List r4 = (java.util.List) r4
            kotlin.ResultKt.throwOnFailure(r9)
            goto L56
        L25:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            androidx.compose.runtime.MutableState<java.lang.Integer> r4 = r1.$subMessageIndex$delegate
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$16(r4, r2)
            java.util.Map<java.lang.Integer, java.util.List<java.lang.String>> r4 = r1.$stageMessages
            androidx.compose.runtime.MutableState<java.lang.Integer> r5 = r1.$visualStep$delegate
            int r5 = com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$11(r5)
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
            java.lang.Object r4 = r4.get(r5)
            java.util.List r4 = (java.util.List) r4
            if (r4 != 0) goto L45
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L45:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r4
            r1.label = r3
            r6 = 2000(0x7d0, double:9.88E-321)
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r6, r5)
            if (r5 != r0) goto L56
            return r0
        L56:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r5 = r1.$subVisible$delegate
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$19(r5, r2)
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r4
            r6 = 2
            r1.label = r6
            r6 = 250(0xfa, double:1.235E-321)
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r6, r5)
            if (r5 != r0) goto L6c
            return r0
        L6c:
            androidx.compose.runtime.MutableState<java.lang.Integer> r5 = r1.$subMessageIndex$delegate
            androidx.compose.runtime.MutableState<java.lang.Integer> r6 = r1.$subMessageIndex$delegate
            int r6 = com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$15(r6)
            int r6 = r6 + r3
            int r7 = r4.size()
            int r6 = r6 % r7
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$16(r5, r6)
            androidx.compose.runtime.MutableState<java.lang.Boolean> r5 = r1.$subVisible$delegate
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$FullPipelineLoadingScreen$lambda$19(r5, r3)
            goto L45
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.PipelineLoadingScreenKt$FullPipelineLoadingScreen$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
