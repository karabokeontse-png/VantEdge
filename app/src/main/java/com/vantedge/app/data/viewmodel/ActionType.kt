package com.vantedge.app.data.viewmodel;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: CycleViewModel.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/vantedge/app/data/viewmodel/ActionType;", "", "(Ljava/lang/String;I)V", "SAVE_ANALYSIS", "DISMISS", "CONTINUE_TO_CV", "SAVE_APPLICATION", "EXPORT", "IMPROVE", "COPY", "SAVE_PDF", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes9.dex */
public enum ActionType {
    SAVE_ANALYSIS,
    DISMISS,
    CONTINUE_TO_CV,
    SAVE_APPLICATION,
    EXPORT,
    IMPROVE,
    COPY,
    SAVE_PDF;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

    public static EnumEntries<ActionType> getEntries() {
        return $ENTRIES;
    }
}
