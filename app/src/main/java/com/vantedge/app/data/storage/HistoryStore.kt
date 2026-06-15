package com.vantedge.app.data.storage;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.GenerationCycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: HistoryStore.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u0017H\u0086@¢\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\bH\u0086@¢\u0006\u0002\u0010\u0018J\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0086@¢\u0006\u0002\u0010\u0018J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@¢\u0006\u0002\u0010 J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001bH\u0086@¢\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u001bH\u0086@¢\u0006\u0002\u0010 J\u0016\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\bH\u0086@¢\u0006\u0002\u0010)R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\r¨\u0006+"}, d2 = {"Lcom/vantedge/app/data/storage/HistoryStore;", "", "cycleDao", "Lcom/vantedge/app/data/storage/CycleDao;", "(Lcom/vantedge/app/data/storage/CycleDao;)V", "_cyclesFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/vantedge/app/data/model/GenerationCycle;", "_visibleCyclesFlow", "cyclesFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getCyclesFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "gson", "Lcom/google/gson/Gson;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "visibleCyclesFlow", "getVisibleCyclesFlow", "clearActive", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCycle", "id", "", "getActiveCycle", "getAllCycles", "getCycleById", "getCycleByIdSuspend", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCyclesForJob", "jobTitle", "company", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markActive", "cycleId", "saveCycle", "cycle", "(Lcom/vantedge/app/data/model/GenerationCycle;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class HistoryStore {
    private static final String TAG = "HistoryStore";
    private final MutableStateFlow<List<GenerationCycle>> _cyclesFlow;
    private final MutableStateFlow<List<GenerationCycle>> _visibleCyclesFlow;
    private final CycleDao cycleDao;
    private final StateFlow<List<GenerationCycle>> cyclesFlow;
    private final Gson gson;
    private final Mutex mutex;
    private final CoroutineScope scope;
    private final StateFlow<List<GenerationCycle>> visibleCyclesFlow;
    public static final int $stable = 8;

    public HistoryStore(CycleDao cycleDao) {
        Intrinsics.checkNotNullParameter(cycleDao, "cycleDao");
        this.cycleDao = cycleDao;
        Gson create = new GsonBuilder().registerTypeAdapter(CycleState.class, new CycleStateSerializer()).registerTypeAdapter(CycleState.class, new CycleStateDeserializer()).create();
        Intrinsics.checkNotNullExpressionValue(create, "create(...)");
        this.gson = create;
        this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        this.mutex = MutexKt.Mutex$default(false, 1, null);
        this._cyclesFlow = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.cyclesFlow = this._cyclesFlow;
        this._visibleCyclesFlow = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.visibleCyclesFlow = this._visibleCyclesFlow;
        FlowKt.launchIn(FlowKt.onEach(this.cycleDao.getAllCycles(), new AnonymousClass1(null)), this.scope);
        FlowKt.launchIn(FlowKt.onEach(this.cycleDao.getVisibleCycles(), new AnonymousClass2(null)), this.scope);
    }

    public final StateFlow<List<GenerationCycle>> getCyclesFlow() {
        return this.cyclesFlow;
    }

    public final StateFlow<List<GenerationCycle>> getVisibleCyclesFlow() {
        return this.visibleCyclesFlow;
    }

    /* compiled from: HistoryStore.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", "entities", "", "Lcom/vantedge/app/data/storage/CycleEntity;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.vantedge.app.data.storage.HistoryStore$1", f = "HistoryStore.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.vantedge.app.data.storage.HistoryStore$1, reason: invalid class name */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<List<? extends CycleEntity>, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = HistoryStore.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(List<? extends CycleEntity> list, Continuation<? super Unit> continuation) {
            return invoke2((List<CycleEntity>) list, continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(List<CycleEntity> list, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(list, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            GenerationCycle generationCycle;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    Iterable entities = (List) this.L$0;
                    MutableStateFlow mutableStateFlow = HistoryStore.this._cyclesFlow;
                    Iterable $this$mapNotNullTo$iv$iv = entities;
                    HistoryStore historyStore = HistoryStore.this;
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$mapNotNullTo$iv$iv) {
                        CycleEntity entity = (CycleEntity) element$iv$iv;
                        try {
                            generationCycle = (GenerationCycle) historyStore.gson.fromJson(entity.getCycleJson(), GenerationCycle.class);
                        } catch (Exception e) {
                            Log.e(HistoryStore.TAG, "Failed to deserialize cycle entity=" + entity.getId() + ": " + e.getMessage());
                            generationCycle = null;
                        }
                        if (generationCycle != null) {
                            destination$iv$iv.add(generationCycle);
                        }
                    }
                    mutableStateFlow.setValue((List) destination$iv$iv);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* compiled from: HistoryStore.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", "entities", "", "Lcom/vantedge/app/data/storage/CycleEntity;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "com.vantedge.app.data.storage.HistoryStore$2", f = "HistoryStore.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.vantedge.app.data.storage.HistoryStore$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<List<? extends CycleEntity>, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = HistoryStore.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(List<? extends CycleEntity> list, Continuation<? super Unit> continuation) {
            return invoke2((List<CycleEntity>) list, continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(List<CycleEntity> list, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(list, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            GenerationCycle generationCycle;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    Iterable entities = (List) this.L$0;
                    MutableStateFlow mutableStateFlow = HistoryStore.this._visibleCyclesFlow;
                    Iterable $this$mapNotNullTo$iv$iv = entities;
                    HistoryStore historyStore = HistoryStore.this;
                    Collection destination$iv$iv = new ArrayList();
                    for (Object element$iv$iv : $this$mapNotNullTo$iv$iv) {
                        CycleEntity entity = (CycleEntity) element$iv$iv;
                        try {
                            generationCycle = (GenerationCycle) historyStore.gson.fromJson(entity.getCycleJson(), GenerationCycle.class);
                        } catch (Exception e) {
                            Log.e(HistoryStore.TAG, "Failed to deserialize visible cycle entity=" + entity.getId() + ": " + e.getMessage());
                            generationCycle = null;
                        }
                        if (generationCycle != null) {
                            destination$iv$iv.add(generationCycle);
                        }
                    }
                    mutableStateFlow.setValue((List) destination$iv$iv);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00d9 A[Catch: all -> 0x011f, TRY_LEAVE, TryCatch #1 {all -> 0x011f, blocks: (B:14:0x00c0, B:15:0x00d3, B:17:0x00d9), top: B:13:0x00c0 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0105 A[Catch: all -> 0x011d, TryCatch #0 {all -> 0x011d, blocks: (B:20:0x00e9, B:27:0x0105, B:28:0x010d, B:32:0x0109, B:22:0x00f4), top: B:19:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0109 A[Catch: all -> 0x011d, TryCatch #0 {all -> 0x011d, blocks: (B:20:0x00e9, B:27:0x0105, B:28:0x010d, B:32:0x0109, B:22:0x00f4), top: B:19:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00bd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object saveCycle(com.vantedge.app.data.model.GenerationCycle r19, kotlin.coroutines.Continuation<? super kotlin.Unit> r20) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.HistoryStore.saveCycle(com.vantedge.app.data.model.GenerationCycle, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object getAllCycles(Continuation<? super List<GenerationCycle>> continuation) {
        return this._cyclesFlow.getValue();
    }

    public final GenerationCycle getCycleById(String id) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(id, "id");
        Iterable $this$firstOrNull$iv = this._cyclesFlow.getValue();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                GenerationCycle it2 = (GenerationCycle) element$iv;
                if (Intrinsics.areEqual(it2.getId(), id)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        return (GenerationCycle) element$iv;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0061 A[Catch: Exception -> 0x003b, TRY_LEAVE, TryCatch #0 {Exception -> 0x003b, blocks: (B:13:0x0036, B:15:0x005c, B:18:0x0061, B:26:0x004b), top: B:7:0x0023 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getCycleByIdSuspend(java.lang.String r8, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.vantedge.app.data.storage.HistoryStore$getCycleByIdSuspend$1
            if (r0 == 0) goto L14
            r0 = r9
            com.vantedge.app.data.storage.HistoryStore$getCycleByIdSuspend$1 r0 = (com.vantedge.app.data.storage.HistoryStore$getCycleByIdSuspend$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L19
        L14:
            com.vantedge.app.data.storage.HistoryStore$getCycleByIdSuspend$1 r0 = new com.vantedge.app.data.storage.HistoryStore$getCycleByIdSuspend$1
            r0.<init>(r7, r9)
        L19:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            r3 = 0
            switch(r2) {
                case 0: goto L3d;
                case 1: goto L2e;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L2e:
            java.lang.Object r8 = r9.L$1
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r1 = r9.L$0
            com.vantedge.app.data.storage.HistoryStore r1 = (com.vantedge.app.data.storage.HistoryStore) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L3b
            r4 = r0
            goto L5c
        L3b:
            r1 = move-exception
            goto L72
        L3d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            com.vantedge.app.data.model.GenerationCycle r4 = r2.getCycleById(r8)
            if (r4 == 0) goto L4a
            r1 = r4
            r3 = 0
            return r1
        L4a:
            com.vantedge.app.data.storage.CycleDao r4 = r2.cycleDao     // Catch: java.lang.Exception -> L3b
            r9.L$0 = r2     // Catch: java.lang.Exception -> L3b
            r9.L$1 = r8     // Catch: java.lang.Exception -> L3b
            r5 = 1
            r9.label = r5     // Catch: java.lang.Exception -> L3b
            java.lang.Object r4 = r4.getCycleById(r8, r9)     // Catch: java.lang.Exception -> L3b
            if (r4 != r1) goto L5b
            return r1
        L5b:
            r1 = r2
        L5c:
            com.vantedge.app.data.storage.CycleEntity r4 = (com.vantedge.app.data.storage.CycleEntity) r4     // Catch: java.lang.Exception -> L3b
            if (r4 != 0) goto L61
            return r3
        L61:
            r2 = r4
            com.google.gson.Gson r4 = r1.gson     // Catch: java.lang.Exception -> L3b
            java.lang.String r5 = r2.getCycleJson()     // Catch: java.lang.Exception -> L3b
            java.lang.Class<com.vantedge.app.data.model.GenerationCycle> r6 = com.vantedge.app.data.model.GenerationCycle.class
            java.lang.Object r4 = r4.fromJson(r5, r6)     // Catch: java.lang.Exception -> L3b
            com.vantedge.app.data.model.GenerationCycle r4 = (com.vantedge.app.data.model.GenerationCycle) r4     // Catch: java.lang.Exception -> L3b
            r3 = r4
            goto L99
        L72:
            java.lang.String r2 = r1.getMessage()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to deserialize cycle by id="
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r5 = ": "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "HistoryStore"
            android.util.Log.e(r4, r2)
        L99:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.HistoryStore.getCycleByIdSuspend(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object getCyclesForJob(String jobTitle, String company, Continuation<? super List<GenerationCycle>> continuation) {
        Iterable $this$filter$iv = this._cyclesFlow.getValue();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GenerationCycle it = (GenerationCycle) element$iv$iv;
            if (StringsKt.equals(it.getJobTitle(), jobTitle, true) && StringsKt.equals(it.getCompany(), company, true)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    public final void deleteCycle(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new HistoryStore$deleteCycle$1(this, id, null), 3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object getActiveCycle(kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.GenerationCycle> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.vantedge.app.data.storage.HistoryStore$getActiveCycle$1
            if (r0 == 0) goto L14
            r0 = r8
            com.vantedge.app.data.storage.HistoryStore$getActiveCycle$1 r0 = (com.vantedge.app.data.storage.HistoryStore$getActiveCycle$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.vantedge.app.data.storage.HistoryStore$getActiveCycle$1 r0 = new com.vantedge.app.data.storage.HistoryStore$getActiveCycle$1
            r0.<init>(r7, r8)
        L19:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L36;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L2d:
            java.lang.Object r1 = r8.L$0
            com.vantedge.app.data.storage.HistoryStore r1 = (com.vantedge.app.data.storage.HistoryStore) r1
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r0
            goto L49
        L36:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            com.vantedge.app.data.storage.CycleDao r3 = r2.cycleDao
            r8.L$0 = r2
            r4 = 1
            r8.label = r4
            java.lang.Object r3 = r3.getActiveCycle(r8)
            if (r3 != r1) goto L48
            return r1
        L48:
            r1 = r2
        L49:
            com.vantedge.app.data.storage.CycleEntity r3 = (com.vantedge.app.data.storage.CycleEntity) r3
            r2 = 0
            if (r3 != 0) goto L4f
            return r2
        L4f:
            com.google.gson.Gson r4 = r1.gson     // Catch: java.lang.Exception -> L60
            java.lang.String r5 = r3.getCycleJson()     // Catch: java.lang.Exception -> L60
            java.lang.Class<com.vantedge.app.data.model.GenerationCycle> r6 = com.vantedge.app.data.model.GenerationCycle.class
            java.lang.Object r4 = r4.fromJson(r5, r6)     // Catch: java.lang.Exception -> L60
            com.vantedge.app.data.model.GenerationCycle r4 = (com.vantedge.app.data.model.GenerationCycle) r4     // Catch: java.lang.Exception -> L60
            r2 = r4
            goto L7e
        L60:
            r1 = move-exception
            java.lang.String r3 = r1.getMessage()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to deserialize active cycle: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "HistoryStore"
            android.util.Log.e(r4, r3)
        L7e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.HistoryStore.getActiveCycle(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object markActive(String cycleId, Continuation<? super Unit> continuation) {
        Object activeCycle = this.cycleDao.setActiveCycle(cycleId, continuation);
        return activeCycle == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? activeCycle : Unit.INSTANCE;
    }

    public final Object clearActive(Continuation<? super Unit> continuation) {
        Object clearActiveFlag = this.cycleDao.clearActiveFlag(continuation);
        return clearActiveFlag == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? clearActiveFlag : Unit.INSTANCE;
    }
}
