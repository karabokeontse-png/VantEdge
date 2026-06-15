package com.vantedge.app.data.domain;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DocumentExportUseCase.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/vantedge/app/data/domain/ExportReceipt;", "", "fileName", "", "jobTitle", "cycleId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCycleId", "()Ljava/lang/String;", "getFileName", "getJobTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final /* data */ class ExportReceipt {
    public static final int $stable = 0;
    private final String cycleId;
    private final String fileName;
    private final String jobTitle;

    public static /* synthetic */ ExportReceipt copy$default(ExportReceipt exportReceipt, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = exportReceipt.fileName;
        }
        if ((i & 2) != 0) {
            str2 = exportReceipt.jobTitle;
        }
        if ((i & 4) != 0) {
            str3 = exportReceipt.cycleId;
        }
        return exportReceipt.copy(str, str2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getFileName() {
        return this.fileName;
    }

    /* renamed from: component2, reason: from getter */
    public final String getJobTitle() {
        return this.jobTitle;
    }

    /* renamed from: component3, reason: from getter */
    public final String getCycleId() {
        return this.cycleId;
    }

    public final ExportReceipt copy(String fileName, String jobTitle, String cycleId) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(cycleId, "cycleId");
        return new ExportReceipt(fileName, jobTitle, cycleId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportReceipt)) {
            return false;
        }
        ExportReceipt exportReceipt = (ExportReceipt) other;
        return Intrinsics.areEqual(this.fileName, exportReceipt.fileName) && Intrinsics.areEqual(this.jobTitle, exportReceipt.jobTitle) && Intrinsics.areEqual(this.cycleId, exportReceipt.cycleId);
    }

    public int hashCode() {
        return (((this.fileName.hashCode() * 31) + this.jobTitle.hashCode()) * 31) + this.cycleId.hashCode();
    }

    public String toString() {
        return "ExportReceipt(fileName=" + this.fileName + ", jobTitle=" + this.jobTitle + ", cycleId=" + this.cycleId + ")";
    }

    public ExportReceipt(String fileName, String jobTitle, String cycleId) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(cycleId, "cycleId");
        this.fileName = fileName;
        this.jobTitle = jobTitle;
        this.cycleId = cycleId;
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final String getJobTitle() {
        return this.jobTitle;
    }

    public final String getCycleId() {
        return this.cycleId;
    }
}
