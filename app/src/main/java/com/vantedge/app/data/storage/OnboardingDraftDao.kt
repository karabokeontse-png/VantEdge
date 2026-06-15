package com.vantedge.app.data.storage;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* compiled from: OnboardingDraftDao.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H§@¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\bJ\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0006\u001a\u00020\u0007H§@¢\u0006\u0002\u0010\bJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\nH§@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/vantedge/app/data/storage/OnboardingDraftDao;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteDraft", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDraft", "Lcom/vantedge/app/data/storage/OnboardingDraftEntity;", "upsert", "entity", "(Lcom/vantedge/app/data/storage/OnboardingDraftEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public interface OnboardingDraftDao {
    Object clearAll(Continuation<? super Unit> continuation);

    Object deleteDraft(String str, Continuation<? super Unit> continuation);

    Object getDraft(String str, Continuation<? super OnboardingDraftEntity> continuation);

    Object upsert(OnboardingDraftEntity onboardingDraftEntity, Continuation<? super Unit> continuation);
}
