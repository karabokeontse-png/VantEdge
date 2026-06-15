package com.vantedge.app.data.storage;

import com.vantedge.app.data.storage.CycleDao;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: CycleDao.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.data.storage.CycleDao$DefaultImpls", f = "CycleDao.kt", i = {0, 0}, l = {39, 40}, m = "setActiveCycle", n = {"$this", "cycleId"}, s = {"L$0", "L$1"})
/* loaded from: classes5.dex */
final class CycleDao$setActiveCycle$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    CycleDao$setActiveCycle$1(Continuation<? super CycleDao$setActiveCycle$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return CycleDao.DefaultImpls.setActiveCycle(null, null, this);
    }
}
