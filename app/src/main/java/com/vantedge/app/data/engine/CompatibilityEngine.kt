package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.network.AiGateway;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CompatibilityEngine.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JD\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\u00060\u000eH\u0086@¢\u0006\u0002\u0010\u0010Ji\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\n2Q\u0010\r\u001aM\u0012\u0015\u0012\u0013\u0018\u00010\n¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\t\u0012\u0015\u0012\u0013\u0018\u00010\n¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\u000b\u0012\u0015\u0012\u0013\u0018\u00010\n¢\u0006\f\b\u0014\u0012\b\b\u0015\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00060\u0013H\u0086@¢\u0006\u0002\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/vantedge/app/data/engine/CompatibilityEngine;", "", "aiGateway", "Lcom/vantedge/app/data/network/AiGateway;", "(Lcom/vantedge/app/data/network/AiGateway;)V", "analyze", "", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobTitle", "", "company", "jobDescription", "onResult", "Lkotlin/Function1;", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "(Lcom/vantedge/app/data/model/UserProfile;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractJobFieldsLegacy", "rawText", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "(Ljava/lang/String;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CompatibilityEngine {
    public static final int $stable = 8;
    private final AiGateway aiGateway;

    public CompatibilityEngine(AiGateway aiGateway) {
        Intrinsics.checkNotNullParameter(aiGateway, "aiGateway");
        this.aiGateway = aiGateway;
    }

    /* JADX WARN: Code restructure failed: missing block: B:167:0x05e2, code lost:
    
        if (r8.isEmpty() != false) goto L139;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0214 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003c  */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v26 */
    /* JADX WARN: Type inference failed for: r6v27 */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v3, types: [kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object analyze(com.vantedge.app.data.model.UserProfile r65, java.lang.String r66, java.lang.String r67, java.lang.String r68, kotlin.jvm.functions.Function1<? super com.vantedge.app.data.model.CompatibilityRecord, kotlin.Unit> r69, kotlin.coroutines.Continuation<? super kotlin.Unit> r70) {
        /*
            Method dump skipped, instructions count: 1704
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.CompatibilityEngine.analyze(com.vantedge.app.data.model.UserProfile, java.lang.String, java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object extractJobFieldsLegacy(java.lang.String r22, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> r23, kotlin.coroutines.Continuation<? super kotlin.Unit> r24) {
        /*
            Method dump skipped, instructions count: 314
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.CompatibilityEngine.extractJobFieldsLegacy(java.lang.String, kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
