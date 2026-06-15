package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.collections.immutable.ImmutableList;
import org.bouncycastle.i18n.ErrorBundle;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B{\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0007\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007\u0012\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0014HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007HÆ\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\n0\u0007HÆ\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\f0\u0007HÆ\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0007HÆ\u0003J\t\u0010-\u001a\u00020\u0010HÆ\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007HÆ\u0003J\u0093\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00072\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u0014HÆ\u0001J\u0013\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00103\u001a\u000204HÖ\u0001J\t\u00105\u001a\u00020\u0012HÖ\u0001R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017¨\u00066"}, d2 = {"Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "", "personalInfo", "Lcom/vantedge/app/data/model/ExtractedPersonalInfo;", ErrorBundle.SUMMARY_ENTRY, "Lcom/vantedge/app/data/model/ExtractedField;", "skills", "Lkotlinx/collections/immutable/ImmutableList;", "languages", "workHistory", "Lcom/vantedge/app/data/model/ExtractedExperience;", "education", "Lcom/vantedge/app/data/model/ExtractedEducation;", "certifications", "Lcom/vantedge/app/data/model/ExtractedCertification;", "overallConfidence", "", "warnings", "", "metadata", "Lcom/vantedge/app/data/model/ExtractionMetadata;", "(Lcom/vantedge/app/data/model/ExtractedPersonalInfo;Lcom/vantedge/app/data/model/ExtractedField;Lkotlinx/collections/immutable/ImmutableList;Lkotlinx/collections/immutable/ImmutableList;Lkotlinx/collections/immutable/ImmutableList;Lkotlinx/collections/immutable/ImmutableList;Lkotlinx/collections/immutable/ImmutableList;FLkotlinx/collections/immutable/ImmutableList;Lcom/vantedge/app/data/model/ExtractionMetadata;)V", "getCertifications", "()Lkotlinx/collections/immutable/ImmutableList;", "getEducation", "getLanguages", "getMetadata", "()Lcom/vantedge/app/data/model/ExtractionMetadata;", "getOverallConfidence", "()F", "getPersonalInfo", "()Lcom/vantedge/app/data/model/ExtractedPersonalInfo;", "getSkills", "getSummary", "()Lcom/vantedge/app/data/model/ExtractedField;", "getWarnings", "getWorkHistory", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class StructuredProfileExtraction {
    public static final int $stable = 0;
    private final ImmutableList<ExtractedCertification> certifications;
    private final ImmutableList<ExtractedEducation> education;
    private final ImmutableList<ExtractedField> languages;
    private final ExtractionMetadata metadata;
    private final float overallConfidence;
    private final ExtractedPersonalInfo personalInfo;
    private final ImmutableList<ExtractedField> skills;
    private final ExtractedField summary;
    private final ImmutableList<String> warnings;
    private final ImmutableList<ExtractedExperience> workHistory;

    /* renamed from: component1, reason: from getter */
    public final ExtractedPersonalInfo getPersonalInfo() {
        return this.personalInfo;
    }

    /* renamed from: component10, reason: from getter */
    public final ExtractionMetadata getMetadata() {
        return this.metadata;
    }

    /* renamed from: component2, reason: from getter */
    public final ExtractedField getSummary() {
        return this.summary;
    }

    public final ImmutableList<ExtractedField> component3() {
        return this.skills;
    }

    public final ImmutableList<ExtractedField> component4() {
        return this.languages;
    }

    public final ImmutableList<ExtractedExperience> component5() {
        return this.workHistory;
    }

    public final ImmutableList<ExtractedEducation> component6() {
        return this.education;
    }

    public final ImmutableList<ExtractedCertification> component7() {
        return this.certifications;
    }

    /* renamed from: component8, reason: from getter */
    public final float getOverallConfidence() {
        return this.overallConfidence;
    }

    public final ImmutableList<String> component9() {
        return this.warnings;
    }

    public final StructuredProfileExtraction copy(ExtractedPersonalInfo personalInfo, ExtractedField summary, ImmutableList<ExtractedField> skills, ImmutableList<ExtractedField> languages, ImmutableList<ExtractedExperience> workHistory, ImmutableList<ExtractedEducation> education, ImmutableList<ExtractedCertification> certifications, float overallConfidence, ImmutableList<String> warnings, ExtractionMetadata metadata) {
        Intrinsics.checkNotNullParameter(personalInfo, "personalInfo");
        Intrinsics.checkNotNullParameter(skills, "skills");
        Intrinsics.checkNotNullParameter(languages, "languages");
        Intrinsics.checkNotNullParameter(workHistory, "workHistory");
        Intrinsics.checkNotNullParameter(education, "education");
        Intrinsics.checkNotNullParameter(certifications, "certifications");
        Intrinsics.checkNotNullParameter(warnings, "warnings");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        return new StructuredProfileExtraction(personalInfo, summary, skills, languages, workHistory, education, certifications, overallConfidence, warnings, metadata);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StructuredProfileExtraction)) {
            return false;
        }
        StructuredProfileExtraction structuredProfileExtraction = (StructuredProfileExtraction) other;
        return Intrinsics.areEqual(this.personalInfo, structuredProfileExtraction.personalInfo) && Intrinsics.areEqual(this.summary, structuredProfileExtraction.summary) && Intrinsics.areEqual(this.skills, structuredProfileExtraction.skills) && Intrinsics.areEqual(this.languages, structuredProfileExtraction.languages) && Intrinsics.areEqual(this.workHistory, structuredProfileExtraction.workHistory) && Intrinsics.areEqual(this.education, structuredProfileExtraction.education) && Intrinsics.areEqual(this.certifications, structuredProfileExtraction.certifications) && Float.compare(this.overallConfidence, structuredProfileExtraction.overallConfidence) == 0 && Intrinsics.areEqual(this.warnings, structuredProfileExtraction.warnings) && Intrinsics.areEqual(this.metadata, structuredProfileExtraction.metadata);
    }

    public int hashCode() {
        return (((((((((((((((((this.personalInfo.hashCode() * 31) + (this.summary == null ? 0 : this.summary.hashCode())) * 31) + this.skills.hashCode()) * 31) + this.languages.hashCode()) * 31) + this.workHistory.hashCode()) * 31) + this.education.hashCode()) * 31) + this.certifications.hashCode()) * 31) + Float.hashCode(this.overallConfidence)) * 31) + this.warnings.hashCode()) * 31) + this.metadata.hashCode();
    }

    public String toString() {
        return "StructuredProfileExtraction(personalInfo=" + this.personalInfo + ", summary=" + this.summary + ", skills=" + this.skills + ", languages=" + this.languages + ", workHistory=" + this.workHistory + ", education=" + this.education + ", certifications=" + this.certifications + ", overallConfidence=" + this.overallConfidence + ", warnings=" + this.warnings + ", metadata=" + this.metadata + ")";
    }

    public StructuredProfileExtraction(ExtractedPersonalInfo personalInfo, ExtractedField summary, ImmutableList<ExtractedField> skills, ImmutableList<ExtractedField> languages, ImmutableList<ExtractedExperience> workHistory, ImmutableList<ExtractedEducation> education, ImmutableList<ExtractedCertification> certifications, float overallConfidence, ImmutableList<String> warnings, ExtractionMetadata metadata) {
        Intrinsics.checkNotNullParameter(personalInfo, "personalInfo");
        Intrinsics.checkNotNullParameter(skills, "skills");
        Intrinsics.checkNotNullParameter(languages, "languages");
        Intrinsics.checkNotNullParameter(workHistory, "workHistory");
        Intrinsics.checkNotNullParameter(education, "education");
        Intrinsics.checkNotNullParameter(certifications, "certifications");
        Intrinsics.checkNotNullParameter(warnings, "warnings");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        this.personalInfo = personalInfo;
        this.summary = summary;
        this.skills = skills;
        this.languages = languages;
        this.workHistory = workHistory;
        this.education = education;
        this.certifications = certifications;
        this.overallConfidence = overallConfidence;
        this.warnings = warnings;
        this.metadata = metadata;
    }

    public final ExtractedPersonalInfo getPersonalInfo() {
        return this.personalInfo;
    }

    public final ExtractedField getSummary() {
        return this.summary;
    }

    public final ImmutableList<ExtractedField> getSkills() {
        return this.skills;
    }

    public final ImmutableList<ExtractedField> getLanguages() {
        return this.languages;
    }

    public final ImmutableList<ExtractedExperience> getWorkHistory() {
        return this.workHistory;
    }

    public final ImmutableList<ExtractedEducation> getEducation() {
        return this.education;
    }

    public final ImmutableList<ExtractedCertification> getCertifications() {
        return this.certifications;
    }

    public final float getOverallConfidence() {
        return this.overallConfidence;
    }

    public final ImmutableList<String> getWarnings() {
        return this.warnings;
    }

    public final ExtractionMetadata getMetadata() {
        return this.metadata;
    }
}
