package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/vantedge/app/data/model/ExtractedField;", "", "value", "", "confidence", "", "sourceType", "Lcom/vantedge/app/data/model/ExtractionSourceType;", "(Ljava/lang/String;FLcom/vantedge/app/data/model/ExtractionSourceType;)V", "getConfidence", "()F", "getSourceType", "()Lcom/vantedge/app/data/model/ExtractionSourceType;", "getValue", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractedField {
    public static final int $stable = 0;
    private final float confidence;
    private final ExtractionSourceType sourceType;
    private final String value;

    public static /* synthetic */ ExtractedField copy$default(ExtractedField extractedField, String str, float f, ExtractionSourceType extractionSourceType, int i, Object obj) {
        if ((i & 1) != 0) {
            str = extractedField.value;
        }
        if ((i & 2) != 0) {
            f = extractedField.confidence;
        }
        if ((i & 4) != 0) {
            extractionSourceType = extractedField.sourceType;
        }
        return extractedField.copy(str, f, extractionSourceType);
    }

    /* renamed from: component1, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    /* renamed from: component2, reason: from getter */
    public final float getConfidence() {
        return this.confidence;
    }

    /* renamed from: component3, reason: from getter */
    public final ExtractionSourceType getSourceType() {
        return this.sourceType;
    }

    public final ExtractedField copy(String value, float confidence, ExtractionSourceType sourceType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        return new ExtractedField(value, confidence, sourceType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractedField)) {
            return false;
        }
        ExtractedField extractedField = (ExtractedField) other;
        return Intrinsics.areEqual(this.value, extractedField.value) && Float.compare(this.confidence, extractedField.confidence) == 0 && this.sourceType == extractedField.sourceType;
    }

    public int hashCode() {
        return (((this.value.hashCode() * 31) + Float.hashCode(this.confidence)) * 31) + this.sourceType.hashCode();
    }

    public String toString() {
        return "ExtractedField(value=" + this.value + ", confidence=" + this.confidence + ", sourceType=" + this.sourceType + ")";
    }

    public ExtractedField(String value, float confidence, ExtractionSourceType sourceType) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        this.value = value;
        this.confidence = confidence;
        this.sourceType = sourceType;
    }

    public final String getValue() {
        return this.value;
    }

    public final float getConfidence() {
        return this.confidence;
    }

    public final ExtractionSourceType getSourceType() {
        return this.sourceType;
    }
}
