package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.i18n.MessageBundle;

/* compiled from: GenerationCycle.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b(\b\u0087\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0014J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0011HÆ\u0003J\t\u0010)\u001a\u00020\u0011HÆ\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\bHÆ\u0003J\t\u0010/\u001a\u00020\nHÆ\u0003J\t\u00100\u001a\u00020\fHÆ\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\nHÆ\u0003¢\u0006\u0002\u0010%J\t\u00102\u001a\u00020\u000fHÆ\u0003J\u008a\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u00104J\u0013\u00105\u001a\u00020\u00112\b\u00106\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00107\u001a\u00020\nHÖ\u0001J\t\u00108\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001aR\u0011\u0010\u0012\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0015\u0010\r\u001a\u0004\u0018\u00010\n¢\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%¨\u00069"}, d2 = {"Lcom/vantedge/app/data/model/GenerationCycle;", "", "id", "", "jobTitle", "company", "jobDescription", "profileSnapshot", "Lcom/vantedge/app/data/model/UserProfile;", "profileSchemaVersion", "", "state", "Lcom/vantedge/app/data/model/CycleState;", "version", "createdAt", "", "isCommitted", "", "isVisibleInHistory", MessageBundle.TITLE_ENTRY, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/UserProfile;ILcom/vantedge/app/data/model/CycleState;Ljava/lang/Integer;JZZLjava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getCreatedAt", "()J", "getId", "()Z", "getJobDescription", "getJobTitle", "getProfileSchemaVersion", "()I", "getProfileSnapshot", "()Lcom/vantedge/app/data/model/UserProfile;", "getState", "()Lcom/vantedge/app/data/model/CycleState;", "getTitle", "getVersion", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/vantedge/app/data/model/UserProfile;ILcom/vantedge/app/data/model/CycleState;Ljava/lang/Integer;JZZLjava/lang/String;)Lcom/vantedge/app/data/model/GenerationCycle;", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class GenerationCycle {
    public static final int $stable = 8;
    private final String company;
    private final long createdAt;
    private final String id;
    private final boolean isCommitted;
    private final boolean isVisibleInHistory;
    private final String jobDescription;
    private final String jobTitle;
    private final int profileSchemaVersion;
    private final UserProfile profileSnapshot;
    private final CycleState state;
    private final String title;
    private final Integer version;

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean getIsCommitted() {
        return this.isCommitted;
    }

    /* renamed from: component11, reason: from getter */
    public final boolean getIsVisibleInHistory() {
        return this.isVisibleInHistory;
    }

    /* renamed from: component12, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component2, reason: from getter */
    public final String getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCompany() {
        return this.company;
    }

    /* renamed from: component4, reason: from getter */
    public final String getJobDescription() {
        return this.jobDescription;
    }

    /* renamed from: component5, reason: from getter */
    public final UserProfile getProfileSnapshot() {
        return this.profileSnapshot;
    }

    /* renamed from: component6, reason: from getter */
    public final int getProfileSchemaVersion() {
        return this.profileSchemaVersion;
    }

    /* renamed from: component7, reason: from getter */
    public final CycleState getState() {
        return this.state;
    }

    /* renamed from: component8, reason: from getter */
    public final Integer getVersion() {
        return this.version;
    }

    /* renamed from: component9, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final GenerationCycle copy(String id, String jobTitle, String company, String jobDescription, UserProfile profileSnapshot, int profileSchemaVersion, CycleState state, Integer version, long createdAt, boolean isCommitted, boolean isVisibleInHistory, String title) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(profileSnapshot, "profileSnapshot");
        Intrinsics.checkNotNullParameter(state, "state");
        return new GenerationCycle(id, jobTitle, company, jobDescription, profileSnapshot, profileSchemaVersion, state, version, createdAt, isCommitted, isVisibleInHistory, title);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenerationCycle)) {
            return false;
        }
        GenerationCycle generationCycle = (GenerationCycle) other;
        return Intrinsics.areEqual(this.id, generationCycle.id) && Intrinsics.areEqual(this.jobTitle, generationCycle.jobTitle) && Intrinsics.areEqual(this.company, generationCycle.company) && Intrinsics.areEqual(this.jobDescription, generationCycle.jobDescription) && Intrinsics.areEqual(this.profileSnapshot, generationCycle.profileSnapshot) && this.profileSchemaVersion == generationCycle.profileSchemaVersion && Intrinsics.areEqual(this.state, generationCycle.state) && Intrinsics.areEqual(this.version, generationCycle.version) && this.createdAt == generationCycle.createdAt && this.isCommitted == generationCycle.isCommitted && this.isVisibleInHistory == generationCycle.isVisibleInHistory && Intrinsics.areEqual(this.title, generationCycle.title);
    }

    public int hashCode() {
        return (((((((((((((((((((((this.id.hashCode() * 31) + this.jobTitle.hashCode()) * 31) + this.company.hashCode()) * 31) + this.jobDescription.hashCode()) * 31) + this.profileSnapshot.hashCode()) * 31) + Integer.hashCode(this.profileSchemaVersion)) * 31) + this.state.hashCode()) * 31) + (this.version == null ? 0 : this.version.hashCode())) * 31) + Long.hashCode(this.createdAt)) * 31) + Boolean.hashCode(this.isCommitted)) * 31) + Boolean.hashCode(this.isVisibleInHistory)) * 31) + (this.title != null ? this.title.hashCode() : 0);
    }

    public String toString() {
        return "GenerationCycle(id=" + this.id + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", jobDescription=" + this.jobDescription + ", profileSnapshot=" + this.profileSnapshot + ", profileSchemaVersion=" + this.profileSchemaVersion + ", state=" + this.state + ", version=" + this.version + ", createdAt=" + this.createdAt + ", isCommitted=" + this.isCommitted + ", isVisibleInHistory=" + this.isVisibleInHistory + ", title=" + this.title + ")";
    }

    public GenerationCycle(String id, String jobTitle, String company, String jobDescription, UserProfile profileSnapshot, int profileSchemaVersion, CycleState state, Integer version, long createdAt, boolean isCommitted, boolean isVisibleInHistory, String title) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(jobDescription, "jobDescription");
        Intrinsics.checkNotNullParameter(profileSnapshot, "profileSnapshot");
        Intrinsics.checkNotNullParameter(state, "state");
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.jobDescription = jobDescription;
        this.profileSnapshot = profileSnapshot;
        this.profileSchemaVersion = profileSchemaVersion;
        this.state = state;
        this.version = version;
        this.createdAt = createdAt;
        this.isCommitted = isCommitted;
        this.isVisibleInHistory = isVisibleInHistory;
        this.title = title;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ GenerationCycle(java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, com.vantedge.app.data.model.UserProfile r22, int r23, com.vantedge.app.data.model.CycleState r24, java.lang.Integer r25, long r26, boolean r28, boolean r29, java.lang.String r30, int r31, kotlin.jvm.internal.DefaultConstructorMarker r32) {
        /*
            r17 = this;
            r0 = r31
            r1 = r0 & 1
            if (r1 == 0) goto L15
            java.util.UUID r1 = java.util.UUID.randomUUID()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "toString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            r4 = r1
            goto L17
        L15:
            r4 = r18
        L17:
            r1 = r0 & 32
            if (r1 == 0) goto L1e
            r1 = 1
            r9 = r1
            goto L20
        L1e:
            r9 = r23
        L20:
            r1 = r0 & 128(0x80, float:1.8E-43)
            r2 = 0
            if (r1 == 0) goto L27
            r11 = r2
            goto L29
        L27:
            r11 = r25
        L29:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L33
            long r5 = java.lang.System.currentTimeMillis()
            r12 = r5
            goto L35
        L33:
            r12 = r26
        L35:
            r1 = r0 & 512(0x200, float:7.17E-43)
            r3 = 0
            if (r1 == 0) goto L3c
            r14 = r3
            goto L3e
        L3c:
            r14 = r28
        L3e:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L44
            r15 = r3
            goto L46
        L44:
            r15 = r29
        L46:
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L4d
            r16 = r2
            goto L4f
        L4d:
            r16 = r30
        L4f:
            r3 = r17
            r5 = r19
            r6 = r20
            r7 = r21
            r8 = r22
            r10 = r24
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.model.GenerationCycle.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.vantedge.app.data.model.UserProfile, int, com.vantedge.app.data.model.CycleState, java.lang.Integer, long, boolean, boolean, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getId() {
        return this.id;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCompany() {
        return this.company;
    }

    public final String getJobDescription() {
        return this.jobDescription;
    }

    public final UserProfile getProfileSnapshot() {
        return this.profileSnapshot;
    }

    public final int getProfileSchemaVersion() {
        return this.profileSchemaVersion;
    }

    public final CycleState getState() {
        return this.state;
    }

    public final Integer getVersion() {
        return this.version;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final boolean isCommitted() {
        return this.isCommitted;
    }

    public final boolean isVisibleInHistory() {
        return this.isVisibleInHistory;
    }

    public final String getTitle() {
        return this.title;
    }
}
