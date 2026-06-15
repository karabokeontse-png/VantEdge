package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/vantedge/app/data/model/ExtractionMetadata;", "", "modelUsed", "", "(Ljava/lang/String;)V", "getModelUsed", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractionMetadata {
    public static final int $stable = 0;
    private final String modelUsed;

    public static /* synthetic */ ExtractionMetadata copy$default(ExtractionMetadata extractionMetadata, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = extractionMetadata.modelUsed;
        }
        return extractionMetadata.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String getModelUsed() {
        return this.modelUsed;
    }

    public final ExtractionMetadata copy(String modelUsed) {
        Intrinsics.checkNotNullParameter(modelUsed, "modelUsed");
        return new ExtractionMetadata(modelUsed);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ExtractionMetadata) && Intrinsics.areEqual(this.modelUsed, ((ExtractionMetadata) other).modelUsed);
    }

    public int hashCode() {
        return this.modelUsed.hashCode();
    }

    public String toString() {
        return "ExtractionMetadata(modelUsed=" + this.modelUsed + ")";
    }

    public ExtractionMetadata(String modelUsed) {
        Intrinsics.checkNotNullParameter(modelUsed, "modelUsed");
        this.modelUsed = modelUsed;
    }

    public final String getModelUsed() {
        return this.modelUsed;
    }
}
