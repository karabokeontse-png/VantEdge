package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.DesignConfig;
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
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$applyDesign$1", f = "CycleViewModel.kt", i = {1}, l = {819, 825, 827}, m = "invokeSuspend", n = {"fullCycle"}, s = {"L$0"})
/* loaded from: classes9.dex */
final class CycleViewModel$applyDesign$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ GenerationCycle $cycle;
    final /* synthetic */ DesignConfig $design;
    Object L$0;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$applyDesign$1(CycleViewModel cycleViewModel, GenerationCycle generationCycle, DesignConfig designConfig, Continuation<? super CycleViewModel$applyDesign$1> continuation) {
        super(2, continuation);
        this.this$0 = cycleViewModel;
        this.$cycle = generationCycle;
        this.$design = designConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$applyDesign$1(this.this$0, this.$cycle, this.$design, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$applyDesign$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0079 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007a  */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.vantedge.app.data.viewmodel.CycleViewModel$applyDesign$1] */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel$applyDesign$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
