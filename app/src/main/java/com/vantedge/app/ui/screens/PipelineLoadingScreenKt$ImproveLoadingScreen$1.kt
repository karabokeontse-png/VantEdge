package com.vantedge.app.ui.screens;

import androidx.compose.runtime.MutableState;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.asn1.eac.EACTags;

/* compiled from: PipelineLoadingScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.PipelineLoadingScreenKt$ImproveLoadingScreen$1", f = "PipelineLoadingScreen.kt", i = {}, l = {EACTags.ADDRESS, EACTags.APPLICATION_IMAGE}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class PipelineLoadingScreenKt$ImproveLoadingScreen$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ List<String> $improveMessages;
    final /* synthetic */ MutableState<Integer> $messageIndex$delegate;
    final /* synthetic */ MutableState<Boolean> $visible$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PipelineLoadingScreenKt$ImproveLoadingScreen$1(List<String> list, MutableState<Boolean> mutableState, MutableState<Integer> mutableState2, Continuation<? super PipelineLoadingScreenKt$ImproveLoadingScreen$1> continuation) {
        super(2, continuation);
        this.$improveMessages = list;
        this.$visible$delegate = mutableState;
        this.$messageIndex$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new PipelineLoadingScreenKt$ImproveLoadingScreen$1(this.$improveMessages, this.$visible$delegate, this.$messageIndex$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((PipelineLoadingScreenKt$ImproveLoadingScreen$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:10:0x002e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0043 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x0041 -> B:7:0x0044). Please report as a decompilation issue!!! */
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
            r1 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L44
        L17:
            r1 = r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L2f
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            r1 = r6
        L20:
            r3 = r1
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r1.label = r2
            r4 = 2200(0x898, double:1.087E-320)
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r4, r3)
            if (r3 != r0) goto L2f
            return r0
        L2f:
            androidx.compose.runtime.MutableState<java.lang.Boolean> r3 = r1.$visible$delegate
            r4 = 0
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$ImproveLoadingScreen$lambda$6(r3, r4)
            r3 = r1
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4 = 2
            r1.label = r4
            r4 = 300(0x12c, double:1.48E-321)
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r4, r3)
            if (r3 != r0) goto L44
            return r0
        L44:
            androidx.compose.runtime.MutableState<java.lang.Integer> r3 = r1.$messageIndex$delegate
            androidx.compose.runtime.MutableState<java.lang.Integer> r4 = r1.$messageIndex$delegate
            int r4 = com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$ImproveLoadingScreen$lambda$2(r4)
            int r4 = r4 + r2
            java.util.List<java.lang.String> r5 = r1.$improveMessages
            int r5 = r5.size()
            int r4 = r4 % r5
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$ImproveLoadingScreen$lambda$3(r3, r4)
            androidx.compose.runtime.MutableState<java.lang.Boolean> r3 = r1.$visible$delegate
            com.vantedge.app.ui.screens.PipelineLoadingScreenKt.access$ImproveLoadingScreen$lambda$6(r3, r2)
            goto L20
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.PipelineLoadingScreenKt$ImproveLoadingScreen$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
