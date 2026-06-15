package com.vantedge.app.data.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Certification.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bc\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n¢\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\nHÆ\u0003J\t\u0010 \u001a\u00020\nHÆ\u0003J\t\u0010!\u001a\u00020\nHÆ\u0003Ji\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\nHÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020'HÖ\u0001J\t\u0010(\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f¨\u0006)"}, d2 = {"Lcom/vantedge/app/data/model/Certification;", "", "id", "", "name", "issuer", "localFileUri", "cloudUrl", "verificationUrl", "issueDate", "", "expiryDate", "dateAdded", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJJ)V", "getCloudUrl", "()Ljava/lang/String;", "getDateAdded", "()J", "getExpiryDate", "getId", "getIssueDate", "getIssuer", "getLocalFileUri", "getName", "getVerificationUrl", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Certification {
    public static final int $stable = 0;
    private final String cloudUrl;
    private final long dateAdded;
    private final long expiryDate;
    private final String id;
    private final long issueDate;
    private final String issuer;
    private final String localFileUri;
    private final String name;
    private final String verificationUrl;

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getIssuer() {
        return this.issuer;
    }

    /* renamed from: component4, reason: from getter */
    public final String getLocalFileUri() {
        return this.localFileUri;
    }

    /* renamed from: component5, reason: from getter */
    public final String getCloudUrl() {
        return this.cloudUrl;
    }

    /* renamed from: component6, reason: from getter */
    public final String getVerificationUrl() {
        return this.verificationUrl;
    }

    /* renamed from: component7, reason: from getter */
    public final long getIssueDate() {
        return this.issueDate;
    }

    /* renamed from: component8, reason: from getter */
    public final long getExpiryDate() {
        return this.expiryDate;
    }

    /* renamed from: component9, reason: from getter */
    public final long getDateAdded() {
        return this.dateAdded;
    }

    public final Certification copy(String id, String name, String issuer, String localFileUri, String cloudUrl, String verificationUrl, long issueDate, long expiryDate, long dateAdded) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(issuer, "issuer");
        return new Certification(id, name, issuer, localFileUri, cloudUrl, verificationUrl, issueDate, expiryDate, dateAdded);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Certification)) {
            return false;
        }
        Certification certification = (Certification) other;
        return Intrinsics.areEqual(this.id, certification.id) && Intrinsics.areEqual(this.name, certification.name) && Intrinsics.areEqual(this.issuer, certification.issuer) && Intrinsics.areEqual(this.localFileUri, certification.localFileUri) && Intrinsics.areEqual(this.cloudUrl, certification.cloudUrl) && Intrinsics.areEqual(this.verificationUrl, certification.verificationUrl) && this.issueDate == certification.issueDate && this.expiryDate == certification.expiryDate && this.dateAdded == certification.dateAdded;
    }

    public int hashCode() {
        return (((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.issuer.hashCode()) * 31) + (this.localFileUri == null ? 0 : this.localFileUri.hashCode())) * 31) + (this.cloudUrl == null ? 0 : this.cloudUrl.hashCode())) * 31) + (this.verificationUrl != null ? this.verificationUrl.hashCode() : 0)) * 31) + Long.hashCode(this.issueDate)) * 31) + Long.hashCode(this.expiryDate)) * 31) + Long.hashCode(this.dateAdded);
    }

    public String toString() {
        return "Certification(id=" + this.id + ", name=" + this.name + ", issuer=" + this.issuer + ", localFileUri=" + this.localFileUri + ", cloudUrl=" + this.cloudUrl + ", verificationUrl=" + this.verificationUrl + ", issueDate=" + this.issueDate + ", expiryDate=" + this.expiryDate + ", dateAdded=" + this.dateAdded + ")";
    }

    public Certification(String id, String name, String issuer, String localFileUri, String cloudUrl, String verificationUrl, long issueDate, long expiryDate, long dateAdded) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(issuer, "issuer");
        this.id = id;
        this.name = name;
        this.issuer = issuer;
        this.localFileUri = localFileUri;
        this.cloudUrl = cloudUrl;
        this.verificationUrl = verificationUrl;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.dateAdded = dateAdded;
    }

    public /* synthetic */ Certification(String str, String str2, String str3, String str4, String str5, String str6, long j, long j2, long j3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? String.valueOf(System.currentTimeMillis()) : str, str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? 0L : j, (i & 128) != 0 ? 0L : j2, (i & 256) != 0 ? System.currentTimeMillis() : j3);
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getIssuer() {
        return this.issuer;
    }

    public final String getLocalFileUri() {
        return this.localFileUri;
    }

    public final String getCloudUrl() {
        return this.cloudUrl;
    }

    public final String getVerificationUrl() {
        return this.verificationUrl;
    }

    public final long getIssueDate() {
        return this.issueDate;
    }

    public final long getExpiryDate() {
        return this.expiryDate;
    }

    public final long getDateAdded() {
        return this.dateAdded;
    }
}
