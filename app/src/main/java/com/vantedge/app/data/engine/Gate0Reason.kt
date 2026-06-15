package com.vantedge.app.data.engine;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/engine/Gate0Reason;", "", "(Ljava/lang/String;I)V", "ACCEPTED", "LOW_STRUCTURAL_EVIDENCE", "HIGH_NARRATIVE_DENSITY", "NO_CHRONOLOGY", "NO_SECTIONAL_STRUCTURE", "OCR_TOO_FRAGMENTED", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public enum Gate0Reason {
    ACCEPTED,
    LOW_STRUCTURAL_EVIDENCE,
    HIGH_NARRATIVE_DENSITY,
    NO_CHRONOLOGY,
    NO_SECTIONAL_STRUCTURE,
    OCR_TOO_FRAGMENTED;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

    public static EnumEntries<Gate0Reason> getEntries() {
        return $ENTRIES;
    }
}
