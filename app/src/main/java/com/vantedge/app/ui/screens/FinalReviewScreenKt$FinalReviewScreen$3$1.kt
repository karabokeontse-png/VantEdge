package com.vantedge.app.ui.screens;

import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: FinalReviewScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.FinalReviewScreenKt$FinalReviewScreen$3$1", f = "FinalReviewScreen.kt", i = {}, l = {46}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class FinalReviewScreenKt$FinalReviewScreen$3$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $errorMessage;
    final /* synthetic */ Function0<Unit> $onClearError;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FinalReviewScreenKt$FinalReviewScreen$3$1(String str, SnackbarHostState snackbarHostState, Function0<Unit> function0, Continuation<? super FinalReviewScreenKt$FinalReviewScreen$3$1> continuation) {
        super(2, continuation);
        this.$errorMessage = str;
        this.$snackbarHostState = snackbarHostState;
        this.$onClearError = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FinalReviewScreenKt$FinalReviewScreen$3$1(this.$errorMessage, this.$snackbarHostState, this.$onClearError, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FinalReviewScreenKt$FinalReviewScreen$3$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Function0<Unit> function0;
        FinalReviewScreenKt$FinalReviewScreen$3$1 finalReviewScreenKt$FinalReviewScreen$3$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                String it = this.$errorMessage;
                if (it != null) {
                    SnackbarHostState snackbarHostState = this.$snackbarHostState;
                    function0 = this.$onClearError;
                    SnackbarDuration snackbarDuration = SnackbarDuration.Short;
                    this.L$0 = function0;
                    this.label = 1;
                    if (SnackbarHostState.showSnackbar$default(snackbarHostState, it, null, false, snackbarDuration, this, 6, null) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    finalReviewScreenKt$FinalReviewScreen$3$1 = this;
                    function0.invoke();
                }
                return Unit.INSTANCE;
            case 1:
                finalReviewScreenKt$FinalReviewScreen$3$1 = this;
                function0 = (Function0) finalReviewScreenKt$FinalReviewScreen$3$1.L$0;
                ResultKt.throwOnFailure($result);
                function0.invoke();
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
