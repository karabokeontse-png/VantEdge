package com.vantedge.app.data.storage;

import android.content.Context;
import com.google.gson.Gson;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnboardingDraftStore.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\fH\u0086@¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0086@¢\u0006\u0002\u0010\rJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0086@¢\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0086@¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/vantedge/app/data/storage/OnboardingDraftStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "ACTIVE_ID", "", "dao", "Lcom/vantedge/app/data/storage/OnboardingDraftDao;", "gson", "Lcom/google/gson/Gson;", "clearDraft", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadDraft", "Lcom/vantedge/app/data/model/OnboardingDraft;", "resetAndSave", "draft", "(Lcom/vantedge/app/data/model/OnboardingDraft;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveDraft", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class OnboardingDraftStore {
    public static final int $stable = 8;
    private final String ACTIVE_ID;
    private final OnboardingDraftDao dao;
    private final Gson gson;

    public OnboardingDraftStore(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.dao = VantEdgeDatabase.INSTANCE.getInstance(context).onboardingDraftDao();
        this.gson = new Gson();
        this.ACTIVE_ID = "active_draft";
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002d A[Catch: Exception -> 0x0031, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x0031, blocks: (B:12:0x002d, B:16:0x003b, B:17:0x0056, B:21:0x0044), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0079 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object saveDraft(com.vantedge.app.data.model.OnboardingDraft r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.vantedge.app.data.storage.OnboardingDraftStore$saveDraft$1
            if (r0 == 0) goto L14
            r0 = r8
            com.vantedge.app.data.storage.OnboardingDraftStore$saveDraft$1 r0 = (com.vantedge.app.data.storage.OnboardingDraftStore$saveDraft$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.vantedge.app.data.storage.OnboardingDraftStore$saveDraft$1 r0 = new com.vantedge.app.data.storage.OnboardingDraftStore$saveDraft$1
            r0.<init>(r6, r8)
        L19:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L3f;
                case 1: goto L33;
                case 2: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L31
            goto L7a
        L31:
            r7 = move-exception
            goto L7e
        L33:
            java.lang.Object r7 = r8.L$1
            com.vantedge.app.data.model.OnboardingDraft r7 = (com.vantedge.app.data.model.OnboardingDraft) r7
            java.lang.Object r2 = r8.L$0
            com.vantedge.app.data.storage.OnboardingDraftStore r2 = (com.vantedge.app.data.storage.OnboardingDraftStore) r2
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L31
            goto L56
        L3f:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            com.vantedge.app.data.storage.OnboardingDraftDao r3 = r2.dao     // Catch: java.lang.Exception -> L31
            java.lang.String r4 = r2.ACTIVE_ID     // Catch: java.lang.Exception -> L31
            r8.L$0 = r2     // Catch: java.lang.Exception -> L31
            r8.L$1 = r7     // Catch: java.lang.Exception -> L31
            r5 = 1
            r8.label = r5     // Catch: java.lang.Exception -> L31
            java.lang.Object r3 = r3.deleteDraft(r4, r8)     // Catch: java.lang.Exception -> L31
            if (r3 != r1) goto L56
            return r1
        L56:
            com.vantedge.app.data.storage.OnboardingDraftEntity r3 = new com.vantedge.app.data.storage.OnboardingDraftEntity     // Catch: java.lang.Exception -> L31
            java.lang.String r4 = r2.ACTIVE_ID     // Catch: java.lang.Exception -> L31
            com.google.gson.Gson r5 = r2.gson     // Catch: java.lang.Exception -> L31
            java.lang.String r5 = r5.toJson(r7)     // Catch: java.lang.Exception -> L31
            java.lang.String r7 = "toJson(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r7)     // Catch: java.lang.Exception -> L31
            r3.<init>(r4, r5)     // Catch: java.lang.Exception -> L31
            r7 = r3
            com.vantedge.app.data.storage.OnboardingDraftDao r3 = r2.dao     // Catch: java.lang.Exception -> L31
            r4 = 0
            r8.L$0 = r4     // Catch: java.lang.Exception -> L31
            r8.L$1 = r4     // Catch: java.lang.Exception -> L31
            r4 = 2
            r8.label = r4     // Catch: java.lang.Exception -> L31
            java.lang.Object r3 = r3.upsert(r7, r8)     // Catch: java.lang.Exception -> L31
            if (r3 != r1) goto L7a
            return r1
        L7a:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L7e:
            java.lang.Exception r1 = new java.lang.Exception
            java.lang.String r7 = r7.getMessage()
            if (r7 != 0) goto L88
            java.lang.String r7 = "unknown"
        L88:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to save draft: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r7 = r2.append(r7)
            java.lang.String r7 = r7.toString()
            r1.<init>(r7)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.OnboardingDraftStore.saveDraft(com.vantedge.app.data.model.OnboardingDraft, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadDraft(kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.OnboardingDraft> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.vantedge.app.data.storage.OnboardingDraftStore$loadDraft$1
            if (r0 == 0) goto L14
            r0 = r8
            com.vantedge.app.data.storage.OnboardingDraftStore$loadDraft$1 r0 = (com.vantedge.app.data.storage.OnboardingDraftStore$loadDraft$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.vantedge.app.data.storage.OnboardingDraftStore$loadDraft$1 r0 = new com.vantedge.app.data.storage.OnboardingDraftStore$loadDraft$1
            r0.<init>(r7, r8)
        L19:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            r3 = 0
            switch(r2) {
                case 0: goto L39;
                case 1: goto L2e;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L2e:
            java.lang.Object r1 = r8.L$0
            com.vantedge.app.data.storage.OnboardingDraftStore r1 = (com.vantedge.app.data.storage.OnboardingDraftStore) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L37
            r4 = r0
            goto L4f
        L37:
            r2 = move-exception
            goto L54
        L39:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            com.vantedge.app.data.storage.OnboardingDraftDao r4 = r2.dao     // Catch: java.lang.Exception -> L52
            java.lang.String r5 = r2.ACTIVE_ID     // Catch: java.lang.Exception -> L52
            r8.L$0 = r2     // Catch: java.lang.Exception -> L52
            r6 = 1
            r8.label = r6     // Catch: java.lang.Exception -> L52
            java.lang.Object r4 = r4.getDraft(r5, r8)     // Catch: java.lang.Exception -> L52
            if (r4 != r1) goto L4e
            return r1
        L4e:
            r1 = r2
        L4f:
            com.vantedge.app.data.storage.OnboardingDraftEntity r4 = (com.vantedge.app.data.storage.OnboardingDraftEntity) r4     // Catch: java.lang.Exception -> L37
            goto L55
        L52:
            r1 = move-exception
            r1 = r2
        L54:
            r4 = r3
        L55:
            if (r4 != 0) goto L58
            return r3
        L58:
            r2 = r4
            com.google.gson.Gson r4 = r1.gson     // Catch: java.lang.Exception -> L6a
            java.lang.String r5 = r2.getDraftJson()     // Catch: java.lang.Exception -> L6a
            java.lang.Class<com.vantedge.app.data.model.OnboardingDraft> r6 = com.vantedge.app.data.model.OnboardingDraft.class
            java.lang.Object r4 = r4.fromJson(r5, r6)     // Catch: java.lang.Exception -> L6a
            com.vantedge.app.data.model.OnboardingDraft r4 = (com.vantedge.app.data.model.OnboardingDraft) r4     // Catch: java.lang.Exception -> L6a
            r3 = r4
            goto L6c
        L6a:
            r1 = move-exception
        L6c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.OnboardingDraftStore.loadDraft(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|8|13|14|15))|22|6|7|8|13|14|15) */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d A[Catch: Exception -> 0x0031, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x0031, blocks: (B:12:0x002d, B:18:0x0038), top: B:7:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object clearDraft(kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.vantedge.app.data.storage.OnboardingDraftStore$clearDraft$1
            if (r0 == 0) goto L14
            r0 = r7
            com.vantedge.app.data.storage.OnboardingDraftStore$clearDraft$1 r0 = (com.vantedge.app.data.storage.OnboardingDraftStore$clearDraft$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            com.vantedge.app.data.storage.OnboardingDraftStore$clearDraft$1 r0 = new com.vantedge.app.data.storage.OnboardingDraftStore$clearDraft$1
            r0.<init>(r6, r7)
        L19:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L33;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)     // Catch: java.lang.Exception -> L31
            goto L46
        L31:
            r1 = move-exception
            goto L47
        L33:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            com.vantedge.app.data.storage.OnboardingDraftDao r3 = r2.dao     // Catch: java.lang.Exception -> L31
            java.lang.String r4 = r2.ACTIVE_ID     // Catch: java.lang.Exception -> L31
            r5 = 1
            r7.label = r5     // Catch: java.lang.Exception -> L31
            java.lang.Object r3 = r3.deleteDraft(r4, r7)     // Catch: java.lang.Exception -> L31
            if (r3 != r1) goto L46
            return r1
        L46:
        L47:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.OnboardingDraftStore.clearDraft(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object resetAndSave(com.vantedge.app.data.model.OnboardingDraft r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.vantedge.app.data.storage.OnboardingDraftStore$resetAndSave$1
            if (r0 == 0) goto L14
            r0 = r6
            com.vantedge.app.data.storage.OnboardingDraftStore$resetAndSave$1 r0 = (com.vantedge.app.data.storage.OnboardingDraftStore$resetAndSave$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            com.vantedge.app.data.storage.OnboardingDraftStore$resetAndSave$1 r0 = new com.vantedge.app.data.storage.OnboardingDraftStore$resetAndSave$1
            r0.<init>(r4, r6)
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
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L5e
        L31:
            java.lang.Object r5 = r6.L$1
            com.vantedge.app.data.model.OnboardingDraft r5 = (com.vantedge.app.data.model.OnboardingDraft) r5
            java.lang.Object r2 = r6.L$0
            com.vantedge.app.data.storage.OnboardingDraftStore r2 = (com.vantedge.app.data.storage.OnboardingDraftStore) r2
            kotlin.ResultKt.throwOnFailure(r0)
            goto L4f
        L3d:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r4
            r6.L$0 = r2
            r6.L$1 = r5
            r3 = 1
            r6.label = r3
            java.lang.Object r3 = r2.clearDraft(r6)
            if (r3 != r1) goto L4f
            return r1
        L4f:
            r3 = 0
            r6.L$0 = r3
            r6.L$1 = r3
            r3 = 2
            r6.label = r3
            java.lang.Object r5 = r2.saveDraft(r5, r6)
            if (r5 != r1) goto L5e
            return r1
        L5e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.OnboardingDraftStore.resetAndSave(com.vantedge.app.data.model.OnboardingDraft, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
