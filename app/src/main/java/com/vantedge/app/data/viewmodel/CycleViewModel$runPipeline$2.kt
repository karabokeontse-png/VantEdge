package com.vantedge.app.data.viewmodel;

import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.UserProfile;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2", f = "CycleViewModel.kt", i = {0, 2, 3, 6, 8, 11}, l = {666, 668, 675, 685, 687, 693, 699, 701, 708, 710, 714, 727, 729}, m = "invokeSuspend", n = {"existing", "startTime", "cycle", "readyCycle", "existing", "cycle"}, s = {"L$0", "J$0", "L$0", "L$0", "L$0", "L$0"})
/* loaded from: classes9.dex */
final class CycleViewModel$runPipeline$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $company;
    final /* synthetic */ String $improvementContext;
    final /* synthetic */ String $jobDescription;
    final /* synthetic */ String $jobTitle;
    final /* synthetic */ GenerationMode $mode;
    final /* synthetic */ UserProfile $profile;
    long J$0;
    Object L$0;
    int label;
    final /* synthetic */ CycleViewModel this$0;

    /* compiled from: CycleViewModel.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GenerationMode.values().length];
            try {
                iArr[GenerationMode.NEW_APPLICATION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GenerationMode.QUICK_ANALYSIS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GenerationMode.IMPROVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GenerationMode.QUICK_GENERATE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CycleViewModel$runPipeline$2(GenerationMode generationMode, CycleViewModel cycleViewModel, String str, String str2, String str3, UserProfile userProfile, String str4, Continuation<? super CycleViewModel$runPipeline$2> continuation) {
        super(2, continuation);
        this.$mode = generationMode;
        this.this$0 = cycleViewModel;
        this.$jobTitle = str;
        this.$company = str2;
        this.$jobDescription = str3;
        this.$profile = userProfile;
        this.$improvementContext = str4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CycleViewModel$runPipeline$2(this.$mode, this.this$0, this.$jobTitle, this.$company, this.$jobDescription, this.$profile, this.$improvementContext, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CycleViewModel$runPipeline$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x006f: MOVE (r3 I:??[OBJECT, ARRAY] A[D('this' com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2)]) = (r6 I:??[OBJECT, ARRAY] A[D('$result' java.lang.Object)]), block:B:112:0x006d */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x0074: MOVE (r3 I:??[OBJECT, ARRAY] A[D('this' com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2)]) = (r6 I:??[OBJECT, ARRAY] A[D('$result' java.lang.Object)]), block:B:108:0x0073 */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x0079: MOVE (r3 I:??[OBJECT, ARRAY] A[D('this' com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2)]) = (r6 I:??[OBJECT, ARRAY] A[D('$result' java.lang.Object)]), block:B:105:0x0078 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 942
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.viewmodel.CycleViewModel$runPipeline$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
