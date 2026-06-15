package com.vantedge.app.data.model;

import androidx.autofill.HintConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StructuredProfileExtraction.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÆ\u0003JQ\u0010\u0017\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001f"}, d2 = {"Lcom/vantedge/app/data/model/ExtractedPersonalInfo;", "", "name", "Lcom/vantedge/app/data/model/ExtractedField;", "email", HintConstants.AUTOFILL_HINT_PHONE, "location", "linkedIn", "headline", "(Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;Lcom/vantedge/app/data/model/ExtractedField;)V", "getEmail", "()Lcom/vantedge/app/data/model/ExtractedField;", "getHeadline", "getLinkedIn", "getLocation", "getName", "getPhone", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ExtractedPersonalInfo {
    public static final int $stable = 0;
    private final ExtractedField email;
    private final ExtractedField headline;
    private final ExtractedField linkedIn;
    private final ExtractedField location;
    private final ExtractedField name;
    private final ExtractedField phone;

    public static /* synthetic */ ExtractedPersonalInfo copy$default(ExtractedPersonalInfo extractedPersonalInfo, ExtractedField extractedField, ExtractedField extractedField2, ExtractedField extractedField3, ExtractedField extractedField4, ExtractedField extractedField5, ExtractedField extractedField6, int i, Object obj) {
        if ((i & 1) != 0) {
            extractedField = extractedPersonalInfo.name;
        }
        if ((i & 2) != 0) {
            extractedField2 = extractedPersonalInfo.email;
        }
        ExtractedField extractedField7 = extractedField2;
        if ((i & 4) != 0) {
            extractedField3 = extractedPersonalInfo.phone;
        }
        ExtractedField extractedField8 = extractedField3;
        if ((i & 8) != 0) {
            extractedField4 = extractedPersonalInfo.location;
        }
        ExtractedField extractedField9 = extractedField4;
        if ((i & 16) != 0) {
            extractedField5 = extractedPersonalInfo.linkedIn;
        }
        ExtractedField extractedField10 = extractedField5;
        if ((i & 32) != 0) {
            extractedField6 = extractedPersonalInfo.headline;
        }
        return extractedPersonalInfo.copy(extractedField, extractedField7, extractedField8, extractedField9, extractedField10, extractedField6);
    }

    /* renamed from: component1, reason: from getter */
    public final ExtractedField getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final ExtractedField getEmail() {
        return this.email;
    }

    /* renamed from: component3, reason: from getter */
    public final ExtractedField getPhone() {
        return this.phone;
    }

    /* renamed from: component4, reason: from getter */
    public final ExtractedField getLocation() {
        return this.location;
    }

    /* renamed from: component5, reason: from getter */
    public final ExtractedField getLinkedIn() {
        return this.linkedIn;
    }

    /* renamed from: component6, reason: from getter */
    public final ExtractedField getHeadline() {
        return this.headline;
    }

    public final ExtractedPersonalInfo copy(ExtractedField name, ExtractedField email, ExtractedField phone, ExtractedField location, ExtractedField linkedIn, ExtractedField headline) {
        return new ExtractedPersonalInfo(name, email, phone, location, linkedIn, headline);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExtractedPersonalInfo)) {
            return false;
        }
        ExtractedPersonalInfo extractedPersonalInfo = (ExtractedPersonalInfo) other;
        return Intrinsics.areEqual(this.name, extractedPersonalInfo.name) && Intrinsics.areEqual(this.email, extractedPersonalInfo.email) && Intrinsics.areEqual(this.phone, extractedPersonalInfo.phone) && Intrinsics.areEqual(this.location, extractedPersonalInfo.location) && Intrinsics.areEqual(this.linkedIn, extractedPersonalInfo.linkedIn) && Intrinsics.areEqual(this.headline, extractedPersonalInfo.headline);
    }

    public int hashCode() {
        return ((((((((((this.name == null ? 0 : this.name.hashCode()) * 31) + (this.email == null ? 0 : this.email.hashCode())) * 31) + (this.phone == null ? 0 : this.phone.hashCode())) * 31) + (this.location == null ? 0 : this.location.hashCode())) * 31) + (this.linkedIn == null ? 0 : this.linkedIn.hashCode())) * 31) + (this.headline != null ? this.headline.hashCode() : 0);
    }

    public String toString() {
        return "ExtractedPersonalInfo(name=" + this.name + ", email=" + this.email + ", phone=" + this.phone + ", location=" + this.location + ", linkedIn=" + this.linkedIn + ", headline=" + this.headline + ")";
    }

    public ExtractedPersonalInfo(ExtractedField name, ExtractedField email, ExtractedField phone, ExtractedField location, ExtractedField linkedIn, ExtractedField headline) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.linkedIn = linkedIn;
        this.headline = headline;
    }

    public final ExtractedField getName() {
        return this.name;
    }

    public final ExtractedField getEmail() {
        return this.email;
    }

    public final ExtractedField getPhone() {
        return this.phone;
    }

    public final ExtractedField getLocation() {
        return this.location;
    }

    public final ExtractedField getLinkedIn() {
        return this.linkedIn;
    }

    public final ExtractedField getHeadline() {
        return this.headline;
    }
}
