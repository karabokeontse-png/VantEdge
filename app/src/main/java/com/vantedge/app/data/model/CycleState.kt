package com.vantedge.app.data.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GenerationCycle.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lcom/vantedge/app/data/model/CycleState;", "", "()V", "AnalysisOnly", "FullCycle", "GenerationReady", "Lcom/vantedge/app/data/model/CycleState$AnalysisOnly;", "Lcom/vantedge/app/data/model/CycleState$FullCycle;", "Lcom/vantedge/app/data/model/CycleState$GenerationReady;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class CycleState {
    public static final int $stable = 0;

    public /* synthetic */ CycleState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private CycleState() {
    }

    /* compiled from: GenerationCycle.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/vantedge/app/data/model/CycleState$AnalysisOnly;", "Lcom/vantedge/app/data/model/CycleState;", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "(Lcom/vantedge/app/data/model/CompatibilityRecord;)V", "getCompatibility", "()Lcom/vantedge/app/data/model/CompatibilityRecord;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class AnalysisOnly extends CycleState {
        public static final int $stable = 8;
        private final CompatibilityRecord compatibility;

        public static /* synthetic */ AnalysisOnly copy$default(AnalysisOnly analysisOnly, CompatibilityRecord compatibilityRecord, int i, Object obj) {
            if ((i & 1) != 0) {
                compatibilityRecord = analysisOnly.compatibility;
            }
            return analysisOnly.copy(compatibilityRecord);
        }

        /* renamed from: component1, reason: from getter */
        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        public final AnalysisOnly copy(CompatibilityRecord compatibility) {
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            return new AnalysisOnly(compatibility);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof AnalysisOnly) && Intrinsics.areEqual(this.compatibility, ((AnalysisOnly) other).compatibility);
        }

        public int hashCode() {
            return this.compatibility.hashCode();
        }

        public String toString() {
            return "AnalysisOnly(compatibility=" + this.compatibility + ")";
        }

        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnalysisOnly(CompatibilityRecord compatibility) {
            super(null);
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            this.compatibility = compatibility;
        }
    }

    /* compiled from: GenerationCycle.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J-\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/vantedge/app/data/model/CycleState$GenerationReady;", "Lcom/vantedge/app/data/model/CycleState;", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "matchedKeywords", "", "", "coverLetterBody", "(Lcom/vantedge/app/data/model/CompatibilityRecord;Ljava/util/List;Ljava/lang/String;)V", "getCompatibility", "()Lcom/vantedge/app/data/model/CompatibilityRecord;", "getCoverLetterBody", "()Ljava/lang/String;", "getMatchedKeywords", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class GenerationReady extends CycleState {
        public static final int $stable = 8;
        private final CompatibilityRecord compatibility;
        private final String coverLetterBody;
        private final List<String> matchedKeywords;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ GenerationReady copy$default(GenerationReady generationReady, CompatibilityRecord compatibilityRecord, List list, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                compatibilityRecord = generationReady.compatibility;
            }
            if ((i & 2) != 0) {
                list = generationReady.matchedKeywords;
            }
            if ((i & 4) != 0) {
                str = generationReady.coverLetterBody;
            }
            return generationReady.copy(compatibilityRecord, list, str);
        }

        /* renamed from: component1, reason: from getter */
        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        public final List<String> component2() {
            return this.matchedKeywords;
        }

        /* renamed from: component3, reason: from getter */
        public final String getCoverLetterBody() {
            return this.coverLetterBody;
        }

        public final GenerationReady copy(CompatibilityRecord compatibility, List<String> matchedKeywords, String coverLetterBody) {
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            Intrinsics.checkNotNullParameter(matchedKeywords, "matchedKeywords");
            Intrinsics.checkNotNullParameter(coverLetterBody, "coverLetterBody");
            return new GenerationReady(compatibility, matchedKeywords, coverLetterBody);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GenerationReady)) {
                return false;
            }
            GenerationReady generationReady = (GenerationReady) other;
            return Intrinsics.areEqual(this.compatibility, generationReady.compatibility) && Intrinsics.areEqual(this.matchedKeywords, generationReady.matchedKeywords) && Intrinsics.areEqual(this.coverLetterBody, generationReady.coverLetterBody);
        }

        public int hashCode() {
            return (((this.compatibility.hashCode() * 31) + this.matchedKeywords.hashCode()) * 31) + this.coverLetterBody.hashCode();
        }

        public String toString() {
            return "GenerationReady(compatibility=" + this.compatibility + ", matchedKeywords=" + this.matchedKeywords + ", coverLetterBody=" + this.coverLetterBody + ")";
        }

        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        public final List<String> getMatchedKeywords() {
            return this.matchedKeywords;
        }

        public final String getCoverLetterBody() {
            return this.coverLetterBody;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GenerationReady(CompatibilityRecord compatibility, List<String> matchedKeywords, String coverLetterBody) {
            super(null);
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            Intrinsics.checkNotNullParameter(matchedKeywords, "matchedKeywords");
            Intrinsics.checkNotNullParameter(coverLetterBody, "coverLetterBody");
            this.compatibility = compatibility;
            this.matchedKeywords = matchedKeywords;
            this.coverLetterBody = coverLetterBody;
        }
    }

    /* compiled from: GenerationCycle.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lcom/vantedge/app/data/model/CycleState$FullCycle;", "Lcom/vantedge/app/data/model/CycleState;", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "cvContent", "", "coverLetterContent", "design", "Lcom/vantedge/app/data/model/DesignConfig;", "(Lcom/vantedge/app/data/model/CompatibilityRecord;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/DesignConfig;)V", "getCompatibility", "()Lcom/vantedge/app/data/model/CompatibilityRecord;", "getCoverLetterContent", "()Ljava/lang/String;", "getCvContent", "getDesign", "()Lcom/vantedge/app/data/model/DesignConfig;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class FullCycle extends CycleState {
        public static final int $stable = 8;
        private final CompatibilityRecord compatibility;
        private final String coverLetterContent;
        private final String cvContent;
        private final DesignConfig design;

        public static /* synthetic */ FullCycle copy$default(FullCycle fullCycle, CompatibilityRecord compatibilityRecord, String str, String str2, DesignConfig designConfig, int i, Object obj) {
            if ((i & 1) != 0) {
                compatibilityRecord = fullCycle.compatibility;
            }
            if ((i & 2) != 0) {
                str = fullCycle.cvContent;
            }
            if ((i & 4) != 0) {
                str2 = fullCycle.coverLetterContent;
            }
            if ((i & 8) != 0) {
                designConfig = fullCycle.design;
            }
            return fullCycle.copy(compatibilityRecord, str, str2, designConfig);
        }

        /* renamed from: component1, reason: from getter */
        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        /* renamed from: component2, reason: from getter */
        public final String getCvContent() {
            return this.cvContent;
        }

        /* renamed from: component3, reason: from getter */
        public final String getCoverLetterContent() {
            return this.coverLetterContent;
        }

        /* renamed from: component4, reason: from getter */
        public final DesignConfig getDesign() {
            return this.design;
        }

        public final FullCycle copy(CompatibilityRecord compatibility, String cvContent, String coverLetterContent, DesignConfig design) {
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            Intrinsics.checkNotNullParameter(cvContent, "cvContent");
            Intrinsics.checkNotNullParameter(coverLetterContent, "coverLetterContent");
            Intrinsics.checkNotNullParameter(design, "design");
            return new FullCycle(compatibility, cvContent, coverLetterContent, design);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FullCycle)) {
                return false;
            }
            FullCycle fullCycle = (FullCycle) other;
            return Intrinsics.areEqual(this.compatibility, fullCycle.compatibility) && Intrinsics.areEqual(this.cvContent, fullCycle.cvContent) && Intrinsics.areEqual(this.coverLetterContent, fullCycle.coverLetterContent) && Intrinsics.areEqual(this.design, fullCycle.design);
        }

        public int hashCode() {
            return (((((this.compatibility.hashCode() * 31) + this.cvContent.hashCode()) * 31) + this.coverLetterContent.hashCode()) * 31) + this.design.hashCode();
        }

        public String toString() {
            return "FullCycle(compatibility=" + this.compatibility + ", cvContent=" + this.cvContent + ", coverLetterContent=" + this.coverLetterContent + ", design=" + this.design + ")";
        }

        public final CompatibilityRecord getCompatibility() {
            return this.compatibility;
        }

        public final String getCvContent() {
            return this.cvContent;
        }

        public final String getCoverLetterContent() {
            return this.coverLetterContent;
        }

        public final DesignConfig getDesign() {
            return this.design;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FullCycle(CompatibilityRecord compatibility, String cvContent, String coverLetterContent, DesignConfig design) {
            super(null);
            Intrinsics.checkNotNullParameter(compatibility, "compatibility");
            Intrinsics.checkNotNullParameter(cvContent, "cvContent");
            Intrinsics.checkNotNullParameter(coverLetterContent, "coverLetterContent");
            Intrinsics.checkNotNullParameter(design, "design");
            this.compatibility = compatibility;
            this.cvContent = cvContent;
            this.coverLetterContent = coverLetterContent;
            this.design = design;
        }
    }
}
