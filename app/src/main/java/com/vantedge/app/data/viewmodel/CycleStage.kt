package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lcom/vantedge/app/data/viewmodel/CycleStage;", "", "(Ljava/lang/String;I)V", "ANALYZED", "READY_FOR_GENERATION", "DOCUMENTS_GENERATED", "IMPROVEMENT_ACTIVE", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public enum CycleStage {
    ANALYZED,
    READY_FOR_GENERATION,
    DOCUMENTS_GENERATED,
    IMPROVEMENT_ACTIVE;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

    public static EnumEntries<CycleStage> getEntries() {
        return $ENTRIES;
    }
}
