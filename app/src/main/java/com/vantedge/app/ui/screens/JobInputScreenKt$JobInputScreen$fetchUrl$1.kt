package com.vantedge.app.ui.screens;

import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.MutableState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: JobInputScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fetchUrl$1", f = "JobInputScreen.kt", i = {0}, l = {269, 293}, m = "invokeSuspend", n = {"startTime"}, s = {"J$0"})
/* loaded from: classes6.dex */
final class JobInputScreenKt$JobInputScreen$fetchUrl$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ MutableState<Boolean> $showUrlInput$delegate;
    final /* synthetic */ SnackbarHostState $snackbar;
    final /* synthetic */ MutableState<JobInputStage> $stage$delegate;
    final /* synthetic */ String $url;
    final /* synthetic */ MutableState<String> $urlInput$delegate;
    final /* synthetic */ CycleViewModel $viewModel;
    long J$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JobInputScreenKt$JobInputScreen$fetchUrl$1(CycleViewModel cycleViewModel, SnackbarHostState snackbarHostState, String str, MutableState<Boolean> mutableState, MutableState<String> mutableState2, MutableState<JobInputStage> mutableState3, Continuation<? super JobInputScreenKt$JobInputScreen$fetchUrl$1> continuation) {
        super(2, continuation);
        this.$viewModel = cycleViewModel;
        this.$snackbar = snackbarHostState;
        this.$url = str;
        this.$showUrlInput$delegate = mutableState;
        this.$urlInput$delegate = mutableState2;
        this.$stage$delegate = mutableState3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JobInputScreenKt$JobInputScreen$fetchUrl$1(this.$viewModel, this.$snackbar, this.$url, this.$showUrlInput$delegate, this.$urlInput$delegate, this.$stage$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((JobInputScreenKt$JobInputScreen$fetchUrl$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            switch(r1) {
                case 0: goto L21;
                case 1: goto L17;
                case 2: goto L11;
                default: goto L9;
            }
        L9:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L11:
            r0 = r12
            kotlin.ResultKt.throwOnFailure(r13)
            goto Lae
        L17:
            r1 = r12
            long r2 = r1.J$0
            kotlin.ResultKt.throwOnFailure(r13)
            r3 = r2
            r2 = r1
            r1 = r13
            goto L4d
        L21:
            kotlin.ResultKt.throwOnFailure(r13)
            r1 = r12
            long r2 = java.lang.System.currentTimeMillis()
            kotlinx.coroutines.CoroutineDispatcher r4 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r4 = (kotlin.coroutines.CoroutineContext) r4
            com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fetchUrl$1$result$1 r5 = new com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fetchUrl$1$result$1
            java.lang.String r6 = r1.$url
            r7 = 0
            r5.<init>(r6, r7)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.J$0 = r2
            r7 = 1
            r1.label = r7
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r6)
            if (r4 != r0) goto L48
            return r0
        L48:
            r11 = r1
            r1 = r13
            r13 = r4
            r3 = r2
            r2 = r11
        L4d:
            java.lang.String r13 = (java.lang.String) r13
            long r5 = java.lang.System.currentTimeMillis()
            long r5 = r5 - r3
            r3 = 0
            if (r13 == 0) goto L5c
            int r4 = r13.length()
            goto L5d
        L5c:
            r4 = r3
        L5d:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "sourceType=URL_FETCH durationMs="
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r5 = r7.append(r5)
            java.lang.String r6 = " resultLength="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "JobInputScreen"
            android.util.Log.d(r5, r4)
            if (r13 == 0) goto L95
            com.vantedge.app.data.viewmodel.CycleViewModel r0 = r2.$viewModel
            com.vantedge.app.data.model.JobSourceType r4 = com.vantedge.app.data.model.JobSourceType.URL
            r0.extractJobFields(r13, r4)
            androidx.compose.runtime.MutableState<java.lang.Boolean> r13 = r2.$showUrlInput$delegate
            com.vantedge.app.ui.screens.JobInputScreenKt.access$JobInputScreen$lambda$23(r13, r3)
            androidx.compose.runtime.MutableState<java.lang.String> r13 = r2.$urlInput$delegate
            java.lang.String r0 = ""
            com.vantedge.app.ui.screens.JobInputScreenKt.access$JobInputScreen$lambda$20(r13, r0)
            goto Lb7
        L95:
            androidx.compose.material3.SnackbarHostState r3 = r2.$snackbar
            java.lang.String r4 = "Could not fetch URL."
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = r2
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r9 = 14
            r10 = 0
            r13 = 2
            r2.label = r13
            java.lang.Object r13 = androidx.compose.material3.SnackbarHostState.showSnackbar$default(r3, r4, r5, r6, r7, r8, r9, r10)
            if (r13 != r0) goto Lac
            return r0
        Lac:
            r13 = r1
            r0 = r2
        Lae:
            androidx.compose.runtime.MutableState<com.vantedge.app.ui.screens.JobInputStage> r1 = r0.$stage$delegate
            com.vantedge.app.ui.screens.JobInputStage r2 = com.vantedge.app.ui.screens.JobInputStage.INPUT
            com.vantedge.app.ui.screens.JobInputScreenKt.access$JobInputScreen$lambda$8(r1, r2)
            r1 = r13
            r2 = r0
        Lb7:
            kotlin.Unit r13 = kotlin.Unit.INSTANCE
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fetchUrl$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
