package com.vantedge.app.data.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CycleEntity.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001d\b\u0087\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J\t\u0010 \u001a\u00020\tHÆ\u0003J\t\u0010!\u001a\u00020\tHÆ\u0003J\u0010\u0010\"\u001a\u0004\u0018\u00010\rHÆ\u0003¢\u0006\u0002\u0010\u0019J\t\u0010#\u001a\u00020\u0003HÆ\u0003Jj\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u0003HÆ\u0001¢\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020\t2\b\u0010'\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010(\u001a\u00020\rHÖ\u0001J\t\u0010)\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0016R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0016R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0015\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019¨\u0006*"}, d2 = {"Lcom/vantedge/app/data/storage/CycleEntity;", "", "id", "", "jobTitle", "company", "createdAt", "", "isCommitted", "", "isVisibleInHistory", "isActive", "version", "", "cycleJson", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JZZZLjava/lang/Integer;Ljava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getCreatedAt", "()J", "getCycleJson", "getId", "()Z", "getJobTitle", "getVersion", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JZZZLjava/lang/Integer;Ljava/lang/String;)Lcom/vantedge/app/data/storage/CycleEntity;", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CycleEntity {
    public static final int $stable = 0;
    private final String company;
    private final long createdAt;
    private final String cycleJson;
    private final String id;
    private final boolean isActive;
    private final boolean isCommitted;
    private final boolean isVisibleInHistory;
    private final String jobTitle;
    private final Integer version;

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
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
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getIsCommitted() {
        return this.isCommitted;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsVisibleInHistory() {
        return this.isVisibleInHistory;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean getIsActive() {
        return this.isActive;
    }

    /* renamed from: component8, reason: from getter */
    public final Integer getVersion() {
        return this.version;
    }

    /* renamed from: component9, reason: from getter */
    public final String getCycleJson() {
        return this.cycleJson;
    }

    public final CycleEntity copy(String id, String jobTitle, String company, long createdAt, boolean isCommitted, boolean isVisibleInHistory, boolean isActive, Integer version, String cycleJson) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(cycleJson, "cycleJson");
        return new CycleEntity(id, jobTitle, company, createdAt, isCommitted, isVisibleInHistory, isActive, version, cycleJson);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CycleEntity)) {
            return false;
        }
        CycleEntity cycleEntity = (CycleEntity) other;
        return Intrinsics.areEqual(this.id, cycleEntity.id) && Intrinsics.areEqual(this.jobTitle, cycleEntity.jobTitle) && Intrinsics.areEqual(this.company, cycleEntity.company) && this.createdAt == cycleEntity.createdAt && this.isCommitted == cycleEntity.isCommitted && this.isVisibleInHistory == cycleEntity.isVisibleInHistory && this.isActive == cycleEntity.isActive && Intrinsics.areEqual(this.version, cycleEntity.version) && Intrinsics.areEqual(this.cycleJson, cycleEntity.cycleJson);
    }

    public int hashCode() {
        return (((((((((((((((this.id.hashCode() * 31) + this.jobTitle.hashCode()) * 31) + this.company.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Boolean.hashCode(this.isCommitted)) * 31) + Boolean.hashCode(this.isVisibleInHistory)) * 31) + Boolean.hashCode(this.isActive)) * 31) + (this.version == null ? 0 : this.version.hashCode())) * 31) + this.cycleJson.hashCode();
    }

    public String toString() {
        return "CycleEntity(id=" + this.id + ", jobTitle=" + this.jobTitle + ", company=" + this.company + ", createdAt=" + this.createdAt + ", isCommitted=" + this.isCommitted + ", isVisibleInHistory=" + this.isVisibleInHistory + ", isActive=" + this.isActive + ", version=" + this.version + ", cycleJson=" + this.cycleJson + ")";
    }

    public CycleEntity(String id, String jobTitle, String company, long createdAt, boolean isCommitted, boolean isVisibleInHistory, boolean isActive, Integer version, String cycleJson) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(cycleJson, "cycleJson");
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.createdAt = createdAt;
        this.isCommitted = isCommitted;
        this.isVisibleInHistory = isVisibleInHistory;
        this.isActive = isActive;
        this.version = version;
        this.cycleJson = cycleJson;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ CycleEntity(java.lang.String r13, java.lang.String r14, java.lang.String r15, long r16, boolean r18, boolean r19, boolean r20, java.lang.Integer r21, java.lang.String r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r12 = this;
            r0 = r23 & 64
            if (r0 == 0) goto L7
            r0 = 0
            r9 = r0
            goto L9
        L7:
            r9 = r20
        L9:
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r7 = r18
            r8 = r19
            r10 = r21
            r11 = r22
            r1.<init>(r2, r3, r4, r5, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.storage.CycleEntity.<init>(java.lang.String, java.lang.String, java.lang.String, long, boolean, boolean, boolean, java.lang.Integer, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
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

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final boolean isCommitted() {
        return this.isCommitted;
    }

    public final boolean isVisibleInHistory() {
        return this.isVisibleInHistory;
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final Integer getVersion() {
        return this.version;
    }

    public final String getCycleJson() {
        return this.cycleJson;
    }
}
