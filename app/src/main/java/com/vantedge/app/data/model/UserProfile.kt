package com.vantedge.app.data.model;

import androidx.autofill.HintConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.bouncycastle.i18n.ErrorBundle;

/* compiled from: UserProfile.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000f0\nHÆ\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\u000f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\f0\nHÆ\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0003J\u0095\u0001\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\nHÆ\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020/HÖ\u0001J\t\u00100\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013¨\u00061"}, d2 = {"Lcom/vantedge/app/data/model/UserProfile;", "", "name", "", "email", HintConstants.AUTOFILL_HINT_PHONE, "location", "linkedIn", ErrorBundle.SUMMARY_ENTRY, "skills", "", "workHistory", "Lcom/vantedge/app/data/model/WorkExperience;", "education", "certifications", "Lcom/vantedge/app/data/model/Certification;", "languages", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getCertifications", "()Ljava/util/List;", "getEducation", "getEmail", "()Ljava/lang/String;", "getLanguages", "getLinkedIn", "getLocation", "getName", "getPhone", "getSkills", "getSummary", "getWorkHistory", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class UserProfile {
    public static final int $stable = 8;
    private final List<Certification> certifications;
    private final List<String> education;
    private final String email;
    private final List<String> languages;
    private final String linkedIn;
    private final String location;
    private final String name;
    private final String phone;
    private final List<String> skills;
    private final String summary;
    private final List<WorkExperience> workHistory;

    public UserProfile() {
        this(null, null, null, null, null, null, null, null, null, null, null, 2047, null);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final List<Certification> component10() {
        return this.certifications;
    }

    public final List<String> component11() {
        return this.languages;
    }

    /* renamed from: component2, reason: from getter */
    public final String getEmail() {
        return this.email;
    }

    /* renamed from: component3, reason: from getter */
    public final String getPhone() {
        return this.phone;
    }

    /* renamed from: component4, reason: from getter */
    public final String getLocation() {
        return this.location;
    }

    /* renamed from: component5, reason: from getter */
    public final String getLinkedIn() {
        return this.linkedIn;
    }

    /* renamed from: component6, reason: from getter */
    public final String getSummary() {
        return this.summary;
    }

    public final List<String> component7() {
        return this.skills;
    }

    public final List<WorkExperience> component8() {
        return this.workHistory;
    }

    public final List<String> component9() {
        return this.education;
    }

    public final UserProfile copy(String name, String email, String phone, String location, String linkedIn, String summary, List<String> skills, List<WorkExperience> workHistory, List<String> education, List<Certification> certifications, List<String> languages) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(email, "email");
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(linkedIn, "linkedIn");
        Intrinsics.checkNotNullParameter(summary, "summary");
        Intrinsics.checkNotNullParameter(skills, "skills");
        Intrinsics.checkNotNullParameter(workHistory, "workHistory");
        Intrinsics.checkNotNullParameter(education, "education");
        Intrinsics.checkNotNullParameter(certifications, "certifications");
        Intrinsics.checkNotNullParameter(languages, "languages");
        return new UserProfile(name, email, phone, location, linkedIn, summary, skills, workHistory, education, certifications, languages);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UserProfile)) {
            return false;
        }
        UserProfile userProfile = (UserProfile) other;
        return Intrinsics.areEqual(this.name, userProfile.name) && Intrinsics.areEqual(this.email, userProfile.email) && Intrinsics.areEqual(this.phone, userProfile.phone) && Intrinsics.areEqual(this.location, userProfile.location) && Intrinsics.areEqual(this.linkedIn, userProfile.linkedIn) && Intrinsics.areEqual(this.summary, userProfile.summary) && Intrinsics.areEqual(this.skills, userProfile.skills) && Intrinsics.areEqual(this.workHistory, userProfile.workHistory) && Intrinsics.areEqual(this.education, userProfile.education) && Intrinsics.areEqual(this.certifications, userProfile.certifications) && Intrinsics.areEqual(this.languages, userProfile.languages);
    }

    public int hashCode() {
        return (((((((((((((((((((this.name.hashCode() * 31) + this.email.hashCode()) * 31) + this.phone.hashCode()) * 31) + this.location.hashCode()) * 31) + this.linkedIn.hashCode()) * 31) + this.summary.hashCode()) * 31) + this.skills.hashCode()) * 31) + this.workHistory.hashCode()) * 31) + this.education.hashCode()) * 31) + this.certifications.hashCode()) * 31) + this.languages.hashCode();
    }

    public String toString() {
        return "UserProfile(name=" + this.name + ", email=" + this.email + ", phone=" + this.phone + ", location=" + this.location + ", linkedIn=" + this.linkedIn + ", summary=" + this.summary + ", skills=" + this.skills + ", workHistory=" + this.workHistory + ", education=" + this.education + ", certifications=" + this.certifications + ", languages=" + this.languages + ")";
    }

    public UserProfile(String name, String email, String phone, String location, String linkedIn, String summary, List<String> skills, List<WorkExperience> workHistory, List<String> education, List<Certification> certifications, List<String> languages) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(email, "email");
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(linkedIn, "linkedIn");
        Intrinsics.checkNotNullParameter(summary, "summary");
        Intrinsics.checkNotNullParameter(skills, "skills");
        Intrinsics.checkNotNullParameter(workHistory, "workHistory");
        Intrinsics.checkNotNullParameter(education, "education");
        Intrinsics.checkNotNullParameter(certifications, "certifications");
        Intrinsics.checkNotNullParameter(languages, "languages");
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.linkedIn = linkedIn;
        this.summary = summary;
        this.skills = skills;
        this.workHistory = workHistory;
        this.education = education;
        this.certifications = certifications;
        this.languages = languages;
    }

    public /* synthetic */ UserProfile(String str, String str2, String str3, String str4, String str5, String str6, List list, List list2, List list3, List list4, List list5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) == 0 ? str6 : "", (i & 64) != 0 ? CollectionsKt.emptyList() : list, (i & 128) != 0 ? CollectionsKt.emptyList() : list2, (i & 256) != 0 ? CollectionsKt.emptyList() : list3, (i & 512) != 0 ? CollectionsKt.emptyList() : list4, (i & 1024) != 0 ? CollectionsKt.emptyList() : list5);
    }

    public final String getName() {
        return this.name;
    }

    public final String getEmail() {
        return this.email;
    }

    public final String getPhone() {
        return this.phone;
    }

    public final String getLocation() {
        return this.location;
    }

    public final String getLinkedIn() {
        return this.linkedIn;
    }

    public final String getSummary() {
        return this.summary;
    }

    public final List<String> getSkills() {
        return this.skills;
    }

    public final List<WorkExperience> getWorkHistory() {
        return this.workHistory;
    }

    public final List<String> getEducation() {
        return this.education;
    }

    public final List<Certification> getCertifications() {
        return this.certifications;
    }

    public final List<String> getLanguages() {
        return this.languages;
    }
}
