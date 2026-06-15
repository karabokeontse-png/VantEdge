package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\tHÆ\u0003JK\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006!"}, d2 = {"Lcom/vantedge/app/data/model/ExtractedExperience;", "", "jobTitle", "Lcom/vantedge/app/data/model/ExtractedField;", "company", "startDate", "endDate", "description", "confidence", "", "(Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;F)V", "getCompany", "()Lcom/vantedge/app/data/model/ExtractedField;", "getConfidence", "()F", "getDescription", "getEndDate", "getJobTitle", "getStartDate", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractedExperience {
    public static final int $stable = 0;
    private final ExtractedField company;
    private final float confidence;
    private final ExtractedField description;
    private final ExtractedField endDate;
    private final ExtractedField jobTitle;
    private final ExtractedField startDate;

    public static /* synthetic */ ExtractedExperience copy$default(ExtractedExperience extractedExperience, ExtractedField extractedField, ExtractedField extractedField2, ExtractedField extractedField3, ExtractedField extractedField4, ExtractedField extractedField5, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            extractedField = extractedExperience.jobTitle;
        }
        if ((i & 2) != 0) {
            extractedField2 = extractedExperience.company;
        }
        ExtractedField extractedField6 = extractedField2;
        if ((i & 4) != 0) {
            extractedField3 = extractedExperience.startDate;
        }
        ExtractedField extractedField7 = extractedField3;
        if ((i & 8) != 0) {
            extractedField4 = extractedExperience.endDate;
        }
        ExtractedField extractedField8 = extractedField4;
        if ((i & 16) != 0) {
            extractedField5 = extractedExperience.description;
        }
        ExtractedField extractedField9 = extractedField5;
        if ((i & 32) != 0) {
            f = extractedExperience.confidence;
        }
        return extractedExperience.copy(extractedField, extractedField6, extractedField7, extractedField8, extractedField9, f);
    }

    /* renamed from: component1, reason: from getter */
    public final ExtractedField getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component2, reason: from getter */
    public final ExtractedField getCompany() {
        return this.company;
    }

    /* renamed from: component3, reason: from getter */
    public final ExtractedField getStartDate() {
        return this.startDate;
    }

    /* renamed from: component4, reason: from getter */
    public final ExtractedField getEndDate() {
        return this.endDate;
    }

    /* renamed from: component5, reason: from getter */
    public final ExtractedField getDescription() {
        return this.description;
    }

    /* renamed from: component6, reason: from getter */
    public final float getConfidence() {
        return this.confidence;
    }

    public final ExtractedExperience copy(ExtractedField jobTitle, ExtractedField company, ExtractedField startDate, ExtractedField endDate, ExtractedField description, float confidence) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        return new ExtractedExperience(jobTitle, company, startDate, endDate, description, confidence);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractedExperience)) {
            return false;
        }
        ExtractedExperience extractedExperience = (ExtractedExperience) other;
        return Intrinsics.areEqual(this.jobTitle, extractedExperience.jobTitle) && Intrinsics.areEqual(this.company, extractedExperience.company) && Intrinsics.areEqual(this.startDate, extractedExperience.startDate) && Intrinsics.areEqual(this.endDate, extractedExperience.endDate) && Intrinsics.areEqual(this.description, extractedExperience.description) && Float.compare(this.confidence, extractedExperience.confidence) == 0;
    }

    public int hashCode() {
        return (((((((((this.jobTitle.hashCode() * 31) + this.company.hashCode()) * 31) + (this.startDate == null ? 0 : this.startDate.hashCode())) * 31) + (this.endDate == null ? 0 : this.endDate.hashCode())) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + Float.hashCode(this.confidence);
    }

    public String toString() {
        return "ExtractedExperience(jobTitle=" + this.jobTitle + ", company=" + this.company + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", description=" + this.description + ", confidence=" + this.confidence + ")";
    }

    public ExtractedExperience(ExtractedField jobTitle, ExtractedField company, ExtractedField startDate, ExtractedField endDate, ExtractedField description, float confidence) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        this.jobTitle = jobTitle;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.confidence = confidence;
    }

    public final ExtractedField getJobTitle() {
        return this.jobTitle;
    }

    public final ExtractedField getCompany() {
        return this.company;
    }

    public final ExtractedField getStartDate() {
        return this.startDate;
    }

    public final ExtractedField getEndDate() {
        return this.endDate;
    }

    public final ExtractedField getDescription() {
        return this.description;
    }

    public final float getConfidence() {
        return this.confidence;
    }
}
