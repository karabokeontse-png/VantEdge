package com.vantedge.app.data.storage;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: CycleDao.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H§@¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u0004\u0018\u00010\nH§@¢\u0006\u0002\u0010\u0004J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r0\fH'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0006\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\bJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r0\fH'J\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\nH§@¢\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\bJ\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0007H\u0097@¢\u0006\u0002\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/vantedge/app/data/storage/CycleDao;", "", "clearActiveFlag", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCycleById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveCycle", "Lcom/vantedge/app/data/storage/CycleEntity;", "getAllCycles", "Lkotlinx/coroutines/flow/Flow;", "", "getCycleById", "getVisibleCycles", "insertCycle", "cycle", "(Lcom/vantedge/app/data/storage/CycleEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markCycleActive", "cycleId", "setActiveCycle", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public interface CycleDao {
    Object clearActiveFlag(Continuation<? super Unit> continuation);

    Object deleteCycleById(String str, Continuation<? super Unit> continuation);

    Object getActiveCycle(Continuation<? super CycleEntity> continuation);

    Flow<List<CycleEntity>> getAllCycles();

    Object getCycleById(String str, Continuation<? super CycleEntity> continuation);

    Flow<List<CycleEntity>> getVisibleCycles();

    Object insertCycle(CycleEntity cycleEntity, Continuation<? super Unit> continuation);

    Object markCycleActive(String str, Continuation<? super Unit> continuation);

    Object setActiveCycle(String str, Continuation<? super Unit> continuation);

    /* compiled from: CycleDao.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x005f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static java.lang.Object setActiveCycle(com.vantedge.app.data.storage.CycleDao r4, java.lang.String r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
            /*
                boolean r0 = r6 instanceof com.vantedge.app.data.storage.CycleDao$setActiveCycle$1
                if (r0 == 0) goto L14
                r0 = r6
                com.vantedge.app.data.storage.CycleDao$setActiveCycle$1 r0 = (com.vantedge.app.data.storage.CycleDao$setActiveCycle$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L14
                int r6 = r0.label
                int r6 = r6 - r2
                r0.label = r6
                goto L19
            L14:
                com.vantedge.app.data.storage.CycleDao$setActiveCycle$1 r0 = new com.vantedge.app.data.storage.CycleDao$setActiveCycle$1
                r0.<init>(r6)
            L19:
                r6 = r0
                java.lang.Object r0 = r6.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r6.label
                switch(r2) {
                    case 0: goto L3d;
                    case 1: goto L31;
                    case 2: goto L2d;
                    default: goto L25;
                }
            L25:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2d:
                kotlin.ResultKt.throwOnFailure(r0)
                goto L60
            L31:
                java.lang.Object r4 = r6.L$1
                java.lang.String r4 = (java.lang.String) r4
                java.lang.Object r5 = r6.L$0
                com.vantedge.app.data.storage.CycleDao r5 = (com.vantedge.app.data.storage.CycleDao) r5
                kotlin.ResultKt.throwOnFailure(r0)
                goto L51
            L3d:
                kotlin.ResultKt.throwOnFailure(r0)
                r6.L$0 = r4
                r6.L$1 = r5
                r2 = 1
                r6.label = r2
                java.lang.Object r2 = r4.clearActiveFlag(r6)
                if (r2 != r1) goto L4e
                return r1
            L4e:
                r3 = r5
                r5 = r4
                r4 = r3
            L51:
                r2 = 0
                r6.L$0 = r2
                r6.L$1 = r2
                r2 = 2
                r6.label = r2
                java.lang.Object r4 = r5.markCycleActive(r4, r6)
                if (r4 != r1) goto L60
                return r1
            L60:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.CycleDao.DefaultImpls.setActiveCycle(com.vantedge.app.data.storage.CycleDao, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
