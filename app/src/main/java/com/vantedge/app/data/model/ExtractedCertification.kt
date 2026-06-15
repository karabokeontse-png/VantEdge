package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J)\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0018"}, d2 = {"Lcom/vantedge/app/data/model/ExtractedCertification;", "", "name", "Lcom/vantedge/app/data/model/ExtractedField;", "issuer", "confidence", "", "(Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;F)V", "getConfidence", "()F", "getIssuer", "()Lcom/vantedge/app/data/model/ExtractedField;", "getName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractedCertification {
    public static final int $stable = 0;
    private final float confidence;
    private final ExtractedField issuer;
    private final ExtractedField name;

    public static /* synthetic */ ExtractedCertification copy$default(ExtractedCertification extractedCertification, ExtractedField extractedField, ExtractedField extractedField2, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            extractedField = extractedCertification.name;
        }
        if ((i & 2) != 0) {
            extractedField2 = extractedCertification.issuer;
        }
        if ((i & 4) != 0) {
            f = extractedCertification.confidence;
        }
        return extractedCertification.copy(extractedField, extractedField2, f);
    }

    /* renamed from: component1, reason: from getter */
    public final ExtractedField getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final ExtractedField getIssuer() {
        return this.issuer;
    }

    /* renamed from: component3, reason: from getter */
    public final float getConfidence() {
        return this.confidence;
    }

    public final ExtractedCertification copy(ExtractedField name, ExtractedField issuer, float confidence) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new ExtractedCertification(name, issuer, confidence);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractedCertification)) {
            return false;
        }
        ExtractedCertification extractedCertification = (ExtractedCertification) other;
        return Intrinsics.areEqual(this.name, extractedCertification.name) && Intrinsics.areEqual(this.issuer, extractedCertification.issuer) && Float.compare(this.confidence, extractedCertification.confidence) == 0;
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + (this.issuer == null ? 0 : this.issuer.hashCode())) * 31) + Float.hashCode(this.confidence);
    }

    public String toString() {
        return "ExtractedCertification(name=" + this.name + ", issuer=" + this.issuer + ", confidence=" + this.confidence + ")";
    }

    public ExtractedCertification(ExtractedField name, ExtractedField issuer, float confidence) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.issuer = issuer;
        this.confidence = confidence;
    }

    public final ExtractedField getName() {
        return this.name;
    }

    public final ExtractedField getIssuer() {
        return this.issuer;
    }

    public final float getConfidence() {
        return this.confidence;
    }
}
