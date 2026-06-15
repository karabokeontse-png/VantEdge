package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: GeneratorEngine.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.engine.GeneratorEngine", f = "GeneratorEngine.kt", i = {0}, l = {43}, m = "generateCv", n = {"onResult"}, s = {"L$0"})
/* loaded from: classes6.dex */
final class GeneratorEngine$generateCv$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GeneratorEngine this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    GeneratorEngine$generateCv$1(GeneratorEngine generatorEngine, Continuation<? super GeneratorEngine$generateCv$1> continuation) {
        super(continuation);
        this.this$0 = generatorEngine;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.generateCv(null, null, null, null, null, null, null, this);
    }
}
