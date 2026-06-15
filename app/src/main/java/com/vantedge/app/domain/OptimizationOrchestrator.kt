package com.vantedge.app.domain;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.engine.CompatibilityEngine;
import com.vantedge.app.data.engine.GeneratorEngine;
import com.vantedge.app.data.model.GenerationCycle;
import com.vantedge.app.data.model.GenerationMode;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.storage.HistoryStore;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OptimizationOrchestrator.kt */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@¢\u0006\u0002\u0010\u000fJ.\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fH\u0082@¢\u0006\u0002\u0010\u0017J:\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\f2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\fH\u0086@¢\u0006\u0002\u0010\u001aJX\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\f2\u0014\b\u0002\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020!0\u001fH\u0086@¢\u0006\u0002\u0010\"J\"\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\fH\u0086@¢\u0006\u0002\u0010%R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/vantedge/app/domain/OptimizationOrchestrator;", "", "compatibilityEngine", "Lcom/vantedge/app/data/engine/CompatibilityEngine;", "generatorEngine", "Lcom/vantedge/app/data/engine/GeneratorEngine;", "historyStore", "Lcom/vantedge/app/data/storage/HistoryStore;", "(Lcom/vantedge/app/data/engine/CompatibilityEngine;Lcom/vantedge/app/data/engine/GeneratorEngine;Lcom/vantedge/app/data/storage/HistoryStore;)V", "applyDesign", "Lcom/vantedge/app/data/model/GenerationCycle;", "cycleId", "", "design", "Lcom/vantedge/app/data/model/DesignConfig;", "(Ljava/lang/String;Lcom/vantedge/app/data/model/DesignConfig;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runAnalysisFresh", "Lcom/vantedge/app/data/model/CompatibilityRecord;", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobTitle", "company", "jobDescription", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runAnalysisOnly", "improvementContext", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runFullPipeline", "mode", "Lcom/vantedge/app/data/model/GenerationMode;", "onProgress", "Lkotlin/Function1;", "Lcom/vantedge/app/domain/PipelineStep;", "", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/GenerationMode;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "runGenerationFromCycle", "cycle", "(Lcom/vantedge/app/data/model/GenerationCycle;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes11.dex */
public final class OptimizationOrchestrator {
    public static final int $stable = 8;
    private final CompatibilityEngine compatibilityEngine;
    private final GeneratorEngine generatorEngine;
    private final HistoryStore historyStore;

    public OptimizationOrchestrator(CompatibilityEngine compatibilityEngine, GeneratorEngine generatorEngine, HistoryStore historyStore) {
        Intrinsics.checkNotNullParameter(compatibilityEngine, "compatibilityEngine");
        Intrinsics.checkNotNullParameter(generatorEngine, "generatorEngine");
        Intrinsics.checkNotNullParameter(historyStore, "historyStore");
        this.compatibilityEngine = compatibilityEngine;
        this.generatorEngine = generatorEngine;
        this.historyStore = historyStore;
    }

    public static /* synthetic */ Object runAnalysisOnly$default(OptimizationOrchestrator optimizationOrchestrator, UserProfile userProfile, String str, String str2, String str3, String str4, Continuation continuation, int i, Object obj) {
        String str5;
        if ((i & 16) == 0) {
            str5 = str4;
        } else {
            str5 = null;
        }
        return optimizationOrchestrator.runAnalysisOnly(userProfile, str, str2, str3, str5, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x01af A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0120 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object runAnalysisOnly(com.vantedge.app.data.model.UserProfile r38, java.lang.String r39, java.lang.String r40, java.lang.String r41, java.lang.String r42, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r43) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OptimizationOrchestrator.runAnalysisOnly(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object runGenerationFromCycle$default(OptimizationOrchestrator optimizationOrchestrator, GenerationCycle generationCycle, String str, Continuation continuation, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return optimizationOrchestrator.runGenerationFromCycle(generationCycle, str, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01a1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object runGenerationFromCycle(com.vantedge.app.data.model.GenerationCycle r39, java.lang.String r40, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r41) {
        /*
            Method dump skipped, instructions count: 828
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OptimizationOrchestrator.runGenerationFromCycle(com.vantedge.app.data.model.GenerationCycle, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object runFullPipeline$default(OptimizationOrchestrator optimizationOrchestrator, UserProfile userProfile, String str, String str2, String str3, GenerationMode generationMode, String str4, Function1 function1, Continuation continuation, int i, Object obj) {
        String str5;
        Function1 function12;
        if ((i & 32) == 0) {
            str5 = str4;
        } else {
            str5 = null;
        }
        if ((i & 64) == 0) {
            function12 = function1;
        } else {
            function12 = new Function1<PipelineStep, Unit>() { // from class: com.vantedge.app.domain.OptimizationOrchestrator$runFullPipeline$2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PipelineStep pipelineStep) {
                    invoke2(pipelineStep);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PipelineStep it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            };
        }
        return optimizationOrchestrator.runFullPipeline(userProfile, str, str2, str3, generationMode, str5, function12, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0227 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0192 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0321 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02b8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0158  */
    /* JADX WARN: Type inference failed for: r9v39, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v46 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object runFullPipeline(com.vantedge.app.data.model.UserProfile r43, java.lang.String r44, java.lang.String r45, java.lang.String r46, com.vantedge.app.data.model.GenerationMode r47, java.lang.String r48, kotlin.jvm.functions.Function1<? super com.vantedge.app.domain.PipelineStep, kotlin.Unit> r49, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r50) {
        /*
            Method dump skipped, instructions count: 1220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OptimizationOrchestrator.runFullPipeline(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, com.vantedge.app.data.model.GenerationMode, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00bc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object runAnalysisFresh(com.vantedge.app.data.model.UserProfile r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.CompatibilityRecord> r21) {
        /*
            r16 = this;
            r0 = r21
            boolean r1 = r0 instanceof com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$1
            if (r1 == 0) goto L19
            r1 = r0
            com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$1 r1 = (com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L19
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r0 = r1
            r2 = r16
            goto L21
        L19:
            com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$1 r1 = new com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$1
            r2 = r16
            r1.<init>(r2, r0)
            r0 = r1
        L21:
            java.lang.Object r1 = r0.result
            java.lang.Object r10 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r11 = 1
            java.lang.String r12 = "OptimizationOrchestrator"
            switch(r3) {
                case 0: goto L41;
                case 1: goto L37;
                default: goto L2f;
            }
        L2f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L37:
            long r3 = r0.J$0
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref.ObjectRef) r5
            kotlin.ResultKt.throwOnFailure(r1)
            goto L8b
        L41:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r16
            r5 = r18
            r7 = r20
            r4 = r17
            r6 = r19
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "ENTRY runAnalysisFresh job="
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r5)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r12, r8)
            long r13 = java.lang.System.currentTimeMillis()
            kotlin.jvm.internal.Ref$ObjectRef r8 = new kotlin.jvm.internal.Ref$ObjectRef
            r8.<init>()
            r15 = r8
            com.vantedge.app.data.engine.CompatibilityEngine r3 = r3.compatibilityEngine
            com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$2 r8 = new com.vantedge.app.domain.OptimizationOrchestrator$runAnalysisFresh$2
            r8.<init>()
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            r0.L$0 = r15
            r0.J$0 = r13
            r0.label = r11
            r9 = r0
            java.lang.Object r3 = r3.analyze(r4, r5, r6, r7, r8, r9)
            if (r3 != r10) goto L89
            return r10
        L89:
            r3 = r13
            r5 = r15
        L8b:
            long r6 = java.lang.System.currentTimeMillis()
            long r6 = r6 - r3
            T r8 = r5.element
            if (r8 == 0) goto L95
            goto L96
        L95:
            r11 = 0
        L96:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "EXIT runAnalysisFresh duration="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.String r4 = "ms result="
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r11)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r12, r3)
            T r3 = r5.element
            com.vantedge.app.data.model.CompatibilityRecord r3 = (com.vantedge.app.data.model.CompatibilityRecord) r3
            if (r3 == 0) goto Lbd
            return r3
        Lbd:
            java.lang.Exception r3 = new java.lang.Exception
            java.lang.String r4 = "Analysis failed. Check your connection."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OptimizationOrchestrator.runAnalysisFresh(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x01c6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object applyDesign(java.lang.String r32, com.vantedge.app.data.model.DesignConfig r33, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r34) {
        /*
            Method dump skipped, instructions count: 512
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.domain.OptimizationOrchestrator.applyDesign(java.lang.String, com.vantedge.app.data.model.DesignConfig, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
