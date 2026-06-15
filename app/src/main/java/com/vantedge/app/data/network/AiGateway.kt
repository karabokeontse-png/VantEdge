package com.vantedge.app.data.network;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AiGateway.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J>\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f0\u000bH\u0086@¢\u0006\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/vantedge/app/data/network/AiGateway;", "", "geminiService", "Lcom/vantedge/app/data/network/GeminiService;", "(Lcom/vantedge/app/data/network/GeminiService;)V", "generate", "", "tag", "systemInstruction", "userPrompt", "onProgress", "Lkotlin/Function1;", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class AiGateway {
    public static final int $stable = 8;
    private final GeminiService geminiService;

    public AiGateway(GeminiService geminiService) {
        Intrinsics.checkNotNullParameter(geminiService, "geminiService");
        this.geminiService = geminiService;
    }

    public static /* synthetic */ Object generate$default(AiGateway aiGateway, String str, String str2, String str3, Function1 function1, Continuation continuation, int i, Object obj) {
        Function1 function12;
        if ((i & 8) == 0) {
            function12 = function1;
        } else {
            function12 = new Function1<String, Unit>() { // from class: com.vantedge.app.data.network.AiGateway$generate$2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str4) {
                    invoke2(str4);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            };
        }
        return aiGateway.generate(str, str2, str3, function12, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object generate(java.lang.String r10, java.lang.String r11, java.lang.String r12, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r13, kotlin.coroutines.Continuation<? super java.lang.String> r14) {
        /*
            r9 = this;
            boolean r0 = r14 instanceof com.vantedge.app.data.network.AiGateway$generate$1
            if (r0 == 0) goto L14
            r0 = r14
            com.vantedge.app.data.network.AiGateway$generate$1 r0 = (com.vantedge.app.data.network.AiGateway$generate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L19
        L14:
            com.vantedge.app.data.network.AiGateway$generate$1 r0 = new com.vantedge.app.data.network.AiGateway$generate$1
            r0.<init>(r9, r14)
        L19:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r14.label
            r3 = 1
            java.lang.String r4 = "["
            java.lang.String r5 = "AiGateway"
            switch(r2) {
                case 0: goto L3e;
                case 1: goto L32;
                default: goto L2a;
            }
        L2a:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L32:
            long r10 = r14.J$0
            java.lang.Object r12 = r14.L$0
            java.lang.String r12 = (java.lang.String) r12
            kotlin.ResultKt.throwOnFailure(r0)
            r13 = r12
            r12 = r0
            goto L7a
        L3e:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r9
            int r6 = r12.length()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r4)
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.String r8 = "] ENTRY promptLength="
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r6 = r7.append(r6)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r5, r6)
            long r6 = java.lang.System.currentTimeMillis()
            com.vantedge.app.data.network.GeminiService r8 = r2.geminiService
            r14.L$0 = r10
            r14.J$0 = r6
            r14.label = r3
            java.lang.Object r11 = r8.generate(r11, r12, r13, r14)
            if (r11 != r1) goto L77
            return r1
        L77:
            r13 = r10
            r12 = r11
            r10 = r6
        L7a:
            java.lang.String r12 = (java.lang.String) r12
            long r1 = java.lang.System.currentTimeMillis()
            long r1 = r1 - r10
            if (r12 == 0) goto L84
            goto L85
        L84:
            r3 = 0
        L85:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r11 = "] EXIT duration="
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r1)
            java.lang.String r11 = "ms success="
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r3)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r5, r10)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.network.AiGateway.generate(java.lang.String, java.lang.String, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
