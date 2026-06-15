package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J?\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u001e"}, d2 = {"Lcom/vantedge/app/data/model/ExtractedEducation;", "", "institution", "Lcom/vantedge/app/data/model/ExtractedField;", "qualification", "fieldOfStudy", "graduationYear", "confidence", "", "(Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;F)V", "getConfidence", "()F", "getFieldOfStudy", "()Lcom/vantedge/app/data/model/ExtractedField;", "getGraduationYear", "getInstitution", "getQualification", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractedEducation {
    public static final int $stable = 0;
    private final float confidence;
    private final ExtractedField fieldOfStudy;
    private final ExtractedField graduationYear;
    private final ExtractedField institution;
    private final ExtractedField qualification;

    public static /* synthetic */ ExtractedEducation copy$default(ExtractedEducation extractedEducation, ExtractedField extractedField, ExtractedField extractedField2, ExtractedField extractedField3, ExtractedField extractedField4, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            extractedField = extractedEducation.institution;
        }
        if ((i & 2) != 0) {
            extractedField2 = extractedEducation.qualification;
        }
        ExtractedField extractedField5 = extractedField2;
        if ((i & 4) != 0) {
            extractedField3 = extractedEducation.fieldOfStudy;
        }
        ExtractedField extractedField6 = extractedField3;
        if ((i & 8) != 0) {
            extractedField4 = extractedEducation.graduationYear;
        }
        ExtractedField extractedField7 = extractedField4;
        if ((i & 16) != 0) {
            f = extractedEducation.confidence;
        }
        return extractedEducation.copy(extractedField, extractedField5, extractedField6, extractedField7, f);
    }

    /* renamed from: component1, reason: from getter */
    public final ExtractedField getInstitution() {
        return this.institution;
    }

    /* renamed from: component2, reason: from getter */
    public final ExtractedField getQualification() {
        return this.qualification;
    }

    /* renamed from: component3, reason: from getter */
    public final ExtractedField getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    /* renamed from: component4, reason: from getter */
    public final ExtractedField getGraduationYear() {
        return this.graduationYear;
    }

    /* renamed from: component5, reason: from getter */
    public final float getConfidence() {
        return this.confidence;
    }

    public final ExtractedEducation copy(ExtractedField institution, ExtractedField qualification, ExtractedField fieldOfStudy, ExtractedField graduationYear, float confidence) {
        Intrinsics.checkNotNullParameter(institution, "institution");
        Intrinsics.checkNotNullParameter(qualification, "qualification");
        return new ExtractedEducation(institution, qualification, fieldOfStudy, graduationYear, confidence);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractedEducation)) {
            return false;
        }
        ExtractedEducation extractedEducation = (ExtractedEducation) other;
        return Intrinsics.areEqual(this.institution, extractedEducation.institution) && Intrinsics.areEqual(this.qualification, extractedEducation.qualification) && Intrinsics.areEqual(this.fieldOfStudy, extractedEducation.fieldOfStudy) && Intrinsics.areEqual(this.graduationYear, extractedEducation.graduationYear) && Float.compare(this.confidence, extractedEducation.confidence) == 0;
    }

    public int hashCode() {
        return (((((((this.institution.hashCode() * 31) + this.qualification.hashCode()) * 31) + (this.fieldOfStudy == null ? 0 : this.fieldOfStudy.hashCode())) * 31) + (this.graduationYear != null ? this.graduationYear.hashCode() : 0)) * 31) + Float.hashCode(this.confidence);
    }

    public String toString() {
        return "ExtractedEducation(institution=" + this.institution + ", qualification=" + this.qualification + ", fieldOfStudy=" + this.fieldOfStudy + ", graduationYear=" + this.graduationYear + ", confidence=" + this.confidence + ")";
    }

    public ExtractedEducation(ExtractedField institution, ExtractedField qualification, ExtractedField fieldOfStudy, ExtractedField graduationYear, float confidence) {
        Intrinsics.checkNotNullParameter(institution, "institution");
        Intrinsics.checkNotNullParameter(qualification, "qualification");
        this.institution = institution;
        this.qualification = qualification;
        this.fieldOfStudy = fieldOfStudy;
        this.graduationYear = graduationYear;
        this.confidence = confidence;
    }

    public final ExtractedField getInstitution() {
        return this.institution;
    }

    public final ExtractedField getQualification() {
        return this.qualification;
    }

    public final ExtractedField getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    public final ExtractedField getGraduationYear() {
        return this.graduationYear;
    }

    public final float getConfidence() {
        return this.confidence;
    }
}
