package com.vantedge.app.data.engine;

import com.google.android.gms.common.Scopes;
import com.vantedge.app.data.engine.CVTemplate;
import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.model.WorkExperience;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.bouncycastle.i18n.TextBundle;

/* compiled from: CVTemplate.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\bÇ\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J6\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0004H\u0002J\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nH\u0002J6\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J6\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J<\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004J6\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004¨\u0006\u001a"}, d2 = {"Lcom/vantedge/app/data/engine/CVTemplate;", "", "()V", "creativeTemplate", "", Scopes.PROFILE, "Lcom/vantedge/app/data/model/UserProfile;", "jobTitle", "company", "keywords", "", "colors", "Lcom/vantedge/app/data/engine/CVTemplate$Colors;", "executiveTemplate", "getColors", "schemeId", "highlightKeywords", TextBundle.TEXT_ENTRY, "minimalistTemplate", "modernTemplate", "render", "matchedKeywords", "designId", "renderCoverLetter", "content", "Colors", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CVTemplate {
    public static final int $stable = 0;
    public static final CVTemplate INSTANCE = new CVTemplate();

    private CVTemplate() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    public final String render(UserProfile profile, String jobTitle, String company, List<String> matchedKeywords, String designId, String schemeId) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(matchedKeywords, "matchedKeywords");
        Intrinsics.checkNotNullParameter(designId, "designId");
        Intrinsics.checkNotNullParameter(schemeId, "schemeId");
        Colors colors = getColors(schemeId);
        switch (designId.hashCode()) {
            case -1090974744:
                if (designId.equals("executive")) {
                    return executiveTemplate(profile, jobTitle, company, matchedKeywords, colors);
                }
                break;
            case -1068799201:
                if (designId.equals("modern")) {
                    return modernTemplate(profile, jobTitle, company, matchedKeywords, colors);
                }
                break;
            case -401597623:
                if (designId.equals("minimalist")) {
                    return minimalistTemplate(profile, jobTitle, company, matchedKeywords, colors);
                }
                break;
            case 1820422063:
                if (designId.equals("creative")) {
                    return creativeTemplate(profile, jobTitle, company, matchedKeywords, colors);
                }
                break;
        }
        return modernTemplate(profile, jobTitle, company, matchedKeywords, colors);
    }

    public final String renderCoverLetter(UserProfile profile, String jobTitle, String company, String content, String designId, String schemeId) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(company, "company");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(designId, "designId");
        Intrinsics.checkNotNullParameter(schemeId, "schemeId");
        Colors colors = getColors(schemeId);
        String font = Intrinsics.areEqual(designId, "executive") ? "Georgia, serif" : "Arial, sans-serif";
        return StringsKt.trimIndent("\n<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n<style>\nbody { font-family: " + font + "; margin: 20mm; color: " + colors.getText() + "; background: " + colors.getBackground() + "; font-size: 11pt; line-height: 1.6; }\n.header { border-bottom: 2px solid " + colors.getPrimary() + "; padding-bottom: 10px; margin-bottom: 20px; }\n.name { font-size: 20pt; color: " + colors.getPrimary() + "; font-weight: bold; }\n.contact { color: " + colors.getSecondary() + "; font-size: 10pt; margin-top: 4px; }\np { margin: 0 0 12px 0; }\n</style>\n</head>\n<body>\n<div class=\"header\">\n<div class=\"name\">" + profile.getName() + "</div>\n<div class=\"contact\">" + profile.getEmail() + " | " + profile.getPhone() + " | " + profile.getLocation() + "</div>\n</div>\n" + content + "\n</body>\n</html>\n        ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CVTemplate.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003JO\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006!"}, d2 = {"Lcom/vantedge/app/data/engine/CVTemplate$Colors;", "", "primary", "", "secondary", "accent", "background", TextBundle.TEXT_ENTRY, "sidebarBg", "sidebarText", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccent", "()Ljava/lang/String;", "getBackground", "getPrimary", "getSecondary", "getSidebarBg", "getSidebarText", "getText", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class Colors {
        private final String accent;
        private final String background;
        private final String primary;
        private final String secondary;
        private final String sidebarBg;
        private final String sidebarText;
        private final String text;

        public static /* synthetic */ Colors copy$default(Colors colors, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, Object obj) {
            if ((i & 1) != 0) {
                str = colors.primary;
            }
            if ((i & 2) != 0) {
                str2 = colors.secondary;
            }
            String str8 = str2;
            if ((i & 4) != 0) {
                str3 = colors.accent;
            }
            String str9 = str3;
            if ((i & 8) != 0) {
                str4 = colors.background;
            }
            String str10 = str4;
            if ((i & 16) != 0) {
                str5 = colors.text;
            }
            String str11 = str5;
            if ((i & 32) != 0) {
                str6 = colors.sidebarBg;
            }
            String str12 = str6;
            if ((i & 64) != 0) {
                str7 = colors.sidebarText;
            }
            return colors.copy(str, str8, str9, str10, str11, str12, str7);
        }

        /* renamed from: component1, reason: from getter */
        public final String getPrimary() {
            return this.primary;
        }

        /* renamed from: component2, reason: from getter */
        public final String getSecondary() {
            return this.secondary;
        }

        /* renamed from: component3, reason: from getter */
        public final String getAccent() {
            return this.accent;
        }

        /* renamed from: component4, reason: from getter */
        public final String getBackground() {
            return this.background;
        }

        /* renamed from: component5, reason: from getter */
        public final String getText() {
            return this.text;
        }

        /* renamed from: component6, reason: from getter */
        public final String getSidebarBg() {
            return this.sidebarBg;
        }

        /* renamed from: component7, reason: from getter */
        public final String getSidebarText() {
            return this.sidebarText;
        }

        public final Colors copy(String primary, String secondary, String accent, String background, String text, String sidebarBg, String sidebarText) {
            Intrinsics.checkNotNullParameter(primary, "primary");
            Intrinsics.checkNotNullParameter(secondary, "secondary");
            Intrinsics.checkNotNullParameter(accent, "accent");
            Intrinsics.checkNotNullParameter(background, "background");
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(sidebarBg, "sidebarBg");
            Intrinsics.checkNotNullParameter(sidebarText, "sidebarText");
            return new Colors(primary, secondary, accent, background, text, sidebarBg, sidebarText);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Colors)) {
                return false;
            }
            Colors colors = (Colors) other;
            return Intrinsics.areEqual(this.primary, colors.primary) && Intrinsics.areEqual(this.secondary, colors.secondary) && Intrinsics.areEqual(this.accent, colors.accent) && Intrinsics.areEqual(this.background, colors.background) && Intrinsics.areEqual(this.text, colors.text) && Intrinsics.areEqual(this.sidebarBg, colors.sidebarBg) && Intrinsics.areEqual(this.sidebarText, colors.sidebarText);
        }

        public int hashCode() {
            return (((((((((((this.primary.hashCode() * 31) + this.secondary.hashCode()) * 31) + this.accent.hashCode()) * 31) + this.background.hashCode()) * 31) + this.text.hashCode()) * 31) + this.sidebarBg.hashCode()) * 31) + this.sidebarText.hashCode();
        }

        public String toString() {
            return "Colors(primary=" + this.primary + ", secondary=" + this.secondary + ", accent=" + this.accent + ", background=" + this.background + ", text=" + this.text + ", sidebarBg=" + this.sidebarBg + ", sidebarText=" + this.sidebarText + ")";
        }

        public Colors(String primary, String secondary, String accent, String background, String text, String sidebarBg, String sidebarText) {
            Intrinsics.checkNotNullParameter(primary, "primary");
            Intrinsics.checkNotNullParameter(secondary, "secondary");
            Intrinsics.checkNotNullParameter(accent, "accent");
            Intrinsics.checkNotNullParameter(background, "background");
            Intrinsics.checkNotNullParameter(text, "text");
            Intrinsics.checkNotNullParameter(sidebarBg, "sidebarBg");
            Intrinsics.checkNotNullParameter(sidebarText, "sidebarText");
            this.primary = primary;
            this.secondary = secondary;
            this.accent = accent;
            this.background = background;
            this.text = text;
            this.sidebarBg = sidebarBg;
            this.sidebarText = sidebarText;
        }

        public final String getPrimary() {
            return this.primary;
        }

        public final String getSecondary() {
            return this.secondary;
        }

        public final String getAccent() {
            return this.accent;
        }

        public final String getBackground() {
            return this.background;
        }

        public final String getText() {
            return this.text;
        }

        public final String getSidebarBg() {
            return this.sidebarBg;
        }

        public final String getSidebarText() {
            return this.sidebarText;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    private final Colors getColors(String schemeId) {
        switch (schemeId.hashCode()) {
            case -1905977571:
                if (schemeId.equals("monochrome")) {
                    return new Colors("#000000", "#616161", "#FAFAFA", "#FFFFFF", "#212121", "#000000", "#FFFFFF");
                }
                break;
            case -1634062812:
                if (schemeId.equals("emerald")) {
                    return new Colors("#263238", "#2E7D32", "#EEEEEE", "#FFFFFF", "#212121", "#263238", "#FFFFFF");
                }
                break;
            case -1177090378:
                if (schemeId.equals("burgundy")) {
                    return new Colors("#4A0E2B", "#C4A882", "#2C1810", "#FFFFFF", "#212121", "#4A0E2B", "#FFFFFF");
                }
                break;
            case 3374006:
                if (schemeId.equals("navy")) {
                    return new Colors("#1A237E", "#607D8B", "#ECEFF1", "#FFFFFF", "#212121", "#1A237E", "#FFFFFF");
                }
                break;
        }
        return new Colors("#1A237E", "#607D8B", "#ECEFF1", "#FFFFFF", "#212121", "#1A237E", "#FFFFFF");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String highlightKeywords(String text, List<String> keywords) {
        String str = text;
        List<String> $this$forEach$iv = keywords;
        for (Object element$iv : $this$forEach$iv) {
            String keyword = (String) element$iv;
            str = StringsKt.replace(str, keyword, "<strong>" + keyword + "</strong>", true);
        }
        return str;
    }

    private final String modernTemplate(UserProfile profile, String jobTitle, String company, final List<String> keywords, final Colors colors) {
        String experience = CollectionsKt.joinToString$default(profile.getWorkHistory(), "", null, null, 0, null, new Function1<WorkExperience, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$modernTemplate$experience$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(WorkExperience exp) {
                String highlightKeywords;
                Intrinsics.checkNotNullParameter(exp, "exp");
                String primary = CVTemplate.Colors.this.getPrimary();
                String role = exp.getRole();
                String secondary = CVTemplate.Colors.this.getSecondary();
                String company2 = exp.getCompany();
                String startDate = exp.getStartDate();
                String endDate = exp.getEndDate();
                highlightKeywords = CVTemplate.INSTANCE.highlightKeywords(exp.getDescription(), keywords);
                return StringsKt.trimIndent("\n            <div style='margin-bottom:12px;'>\n            <div style='font-weight:bold;color:" + primary + ";'>" + role + "</div>\n            <div style='color:" + secondary + ";font-size:10pt;'>" + company2 + " | " + startDate + " – " + endDate + "</div>\n            <div style='margin-top:4px;'>" + highlightKeywords + "</div>\n            </div>\n            ");
            }
        }, 30, null);
        String education = CollectionsKt.joinToString$default(profile.getEducation(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$modernTemplate$education$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:6px;'>" + it + "</div>";
            }
        }, 30, null);
        String text = colors.getText();
        String sidebarBg = colors.getSidebarBg();
        String sidebarText = colors.getSidebarText();
        String sidebarText2 = colors.getSidebarText();
        String primary = colors.getPrimary();
        String primary2 = colors.getPrimary();
        String name = profile.getName();
        String email = profile.getEmail();
        String phone = profile.getPhone();
        String location = profile.getLocation();
        String linkedIn = profile.getLinkedIn();
        String joinToString$default = CollectionsKt.joinToString$default(profile.getSkills(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$modernTemplate$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div class='contact-item'>• " + it + "</div>";
            }
        }, 30, null);
        String education2 = CollectionsKt.joinToString$default(profile.getLanguages(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$modernTemplate$2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div class='contact-item'>• " + it + "</div>";
            }
        }, 30, null);
        String joinToString$default2 = CollectionsKt.joinToString$default(profile.getCertifications(), "", null, null, 0, null, new Function1<Certification, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$modernTemplate$3
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Certification it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div class='contact-item'>• " + it + "</div>";
            }
        }, 30, null);
        String experience2 = profile.getSummary();
        return StringsKt.trimIndent("\n<!DOCTYPE html>\n<html>\n<head><meta charset=\"UTF-8\">\n<style>\n* { margin:0; padding:0; box-sizing:border-box; }\nbody { font-family: Arial, sans-serif; background:#fff; color:" + text + "; font-size:10pt; }\n.container { display:flex; min-height:297mm; width:210mm; margin:auto; }\n.sidebar { width:35%; background:" + sidebarBg + "; color:" + sidebarText + "; padding:20px; }\n.main { width:65%; padding:20px; }\n.name { font-size:18pt; font-weight:bold; margin-bottom:4px; }\n.jobtitle { font-size:11pt; opacity:0.85; margin-bottom:16px; }\n.section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:1px; border-bottom:1px solid " + sidebarText2 + "; padding-bottom:4px; margin:16px 0 8px 0; opacity:0.9; }\n.main-section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:1px; color:" + primary + "; border-bottom:2px solid " + primary2 + "; padding-bottom:4px; margin:16px 0 10px 0; }\n.contact-item { font-size:9pt; margin-bottom:6px; opacity:0.9; }\n</style>\n</head>\n<body>\n<div class=\"container\">\n<div class=\"sidebar\">\n<div class=\"name\">" + name + "</div>\n<div class=\"jobtitle\">" + jobTitle + "</div>\n<div class=\"section-title\">Contact</div>\n<div class=\"contact-item\">" + email + "</div>\n<div class=\"contact-item\">" + phone + "</div>\n<div class=\"contact-item\">" + location + "</div>\n<div class=\"contact-item\">" + linkedIn + "</div>\n<div class=\"section-title\">Skills</div>\n" + joinToString$default + "\n<div class=\"section-title\">Languages</div>\n" + education2 + "\n<div class=\"section-title\">Certifications</div>\n" + joinToString$default2 + "\n</div>\n<div class=\"main\">\n<div class=\"main-section-title\">Professional Summary</div>\n<p>" + highlightKeywords(experience2, keywords) + "</p>\n<div class=\"main-section-title\">Experience</div>\n" + experience + "\n<div class=\"main-section-title\">Education</div>\n" + education + "\n</div>\n</div>\n</body>\n</html>\n        ");
    }

    private final String minimalistTemplate(UserProfile profile, String jobTitle, String company, final List<String> keywords, final Colors colors) {
        String experience = CollectionsKt.joinToString$default(profile.getWorkHistory(), "", null, null, 0, null, new Function1<WorkExperience, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$minimalistTemplate$experience$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(WorkExperience exp) {
                String highlightKeywords;
                Intrinsics.checkNotNullParameter(exp, "exp");
                String role = exp.getRole();
                String company2 = exp.getCompany();
                String secondary = CVTemplate.Colors.this.getSecondary();
                String startDate = exp.getStartDate();
                String endDate = exp.getEndDate();
                highlightKeywords = CVTemplate.INSTANCE.highlightKeywords(exp.getDescription(), keywords);
                return StringsKt.trimIndent("\n            <div style='margin-bottom:14px;'>\n            <div style='font-weight:bold;'>" + role + " — " + company2 + "</div>\n            <div style='color:" + secondary + ";font-size:10pt;margin-bottom:4px;'>" + startDate + " – " + endDate + "</div>\n            <div>" + highlightKeywords + "</div>\n            </div>\n            ");
            }
        }, 30, null);
        return StringsKt.trimIndent("\n<!DOCTYPE html>\n<html>\n<head><meta charset=\"UTF-8\">\n<style>\nbody { font-family: Helvetica, Arial, sans-serif; margin:25mm 30mm; color:" + colors.getText() + "; font-size:10.5pt; line-height:1.7; background:#fff; }\n.name { font-size:26pt; font-weight:300; letter-spacing:2px; color:" + colors.getPrimary() + "; margin-bottom:4px; }\n.contact { color:" + colors.getSecondary() + "; font-size:9.5pt; margin-bottom:30px; }\n.section { margin-bottom:22px; }\n.section-title { font-size:8pt; font-weight:bold; text-transform:uppercase; letter-spacing:3px; color:" + colors.getSecondary() + "; margin-bottom:10px; }\nhr { border:none; border-top:1px solid " + colors.getAccent() + "; margin:6px 0 16px 0; }\n</style>\n</head>\n<body>\n<div class=\"name\">" + profile.getName() + "</div>\n<div class=\"contact\">" + profile.getEmail() + " &nbsp;|&nbsp; " + profile.getPhone() + " &nbsp;|&nbsp; " + profile.getLocation() + " &nbsp;|&nbsp; " + profile.getLinkedIn() + "</div>\n<div class=\"section\">\n<div class=\"section-title\">Profile</div><hr>\n<p>" + highlightKeywords(profile.getSummary(), keywords) + "</p>\n</div>\n<div class=\"section\">\n<div class=\"section-title\">Experience</div><hr>\n" + experience + "\n</div>\n<div class=\"section\">\n<div class=\"section-title\">Education</div><hr>\n" + CollectionsKt.joinToString$default(profile.getEducation(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$minimalistTemplate$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:6px;'>" + it + "</div>";
            }
        }, 30, null) + "\n</div>\n<div class=\"section\">\n<div class=\"section-title\">Skills</div><hr>\n<div>" + CollectionsKt.joinToString$default(profile.getSkills(), " &nbsp;·&nbsp; ", null, null, 0, null, null, 62, null) + "</div>\n</div>\n<div class=\"section\">\n<div class=\"section-title\">Certifications</div><hr>\n<div>" + CollectionsKt.joinToString$default(profile.getCertifications(), " &nbsp;·&nbsp; ", null, null, 0, null, null, 62, null) + "</div>\n</div>\n</body>\n</html>\n        ");
    }

    private final String creativeTemplate(UserProfile profile, String jobTitle, String company, final List<String> keywords, final Colors colors) {
        String experience = CollectionsKt.joinToString$default(profile.getWorkHistory(), "", null, null, 0, null, new Function1<WorkExperience, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$creativeTemplate$experience$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(WorkExperience exp) {
                String highlightKeywords;
                Intrinsics.checkNotNullParameter(exp, "exp");
                String secondary = CVTemplate.Colors.this.getSecondary();
                String primary = CVTemplate.Colors.this.getPrimary();
                String role = exp.getRole();
                String secondary2 = CVTemplate.Colors.this.getSecondary();
                String company2 = exp.getCompany();
                String startDate = exp.getStartDate();
                String endDate = exp.getEndDate();
                highlightKeywords = CVTemplate.INSTANCE.highlightKeywords(exp.getDescription(), keywords);
                return StringsKt.trimIndent("\n            <div style='margin-bottom:14px;border-left:3px solid " + secondary + ";padding-left:12px;'>\n            <div style='font-weight:bold;color:" + primary + ";'>" + role + "</div>\n            <div style='color:" + secondary2 + ";font-size:10pt;'>" + company2 + " | " + startDate + " – " + endDate + "</div>\n            <div style='margin-top:4px;'>" + highlightKeywords + "</div>\n            </div>\n            ");
            }
        }, 30, null);
        return StringsKt.trimIndent("\n<!DOCTYPE html>\n<html>\n<head><meta charset=\"UTF-8\">\n<style>\n* { margin:0; padding:0; box-sizing:border-box; }\nbody { font-family: Arial, sans-serif; background:#fff; color:" + colors.getText() + "; font-size:10pt; }\n.header { background:" + colors.getPrimary() + "; color:#fff; padding:24px 28px; }\n.name { font-size:22pt; font-weight:bold; letter-spacing:1px; }\n.jobtitle { font-size:12pt; opacity:0.85; margin-top:4px; }\n.contact-bar { background:" + colors.getSecondary() + "; color:#fff; padding:8px 28px; font-size:9pt; display:flex; gap:20px; }\n.body { display:flex; padding:20px 28px; gap:24px; }\n.left { width:60%; }\n.right { width:40%; }\n.section-title { font-size:11pt; font-weight:bold; color:" + colors.getPrimary() + "; text-transform:uppercase; letter-spacing:1px; margin:16px 0 8px 0; border-bottom:2px solid " + colors.getSecondary() + "; padding-bottom:4px; }\n.skill-tag { background:" + colors.getAccent() + ";color:" + colors.getPrimary() + ";padding:3px 10px;margin:3px 2px;display:inline-block;border-radius:12px;font-size:9.5pt; }\n</style>\n</head>\n<body>\n<div class=\"header\">\n<div class=\"name\">" + profile.getName() + "</div>\n<div class=\"jobtitle\">" + jobTitle + "</div>\n</div>\n<div class=\"contact-bar\">\n<span>" + profile.getEmail() + "</span>\n<span>" + profile.getPhone() + "</span>\n<span>" + profile.getLocation() + "</span>\n<span>" + profile.getLinkedIn() + "</span>\n</div>\n<div class=\"body\">\n<div class=\"left\">\n<div class=\"section-title\">Experience</div>\n" + experience + "\n<div class=\"section-title\">Education</div>\n" + CollectionsKt.joinToString$default(profile.getEducation(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$creativeTemplate$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:6px;'>" + it + "</div>";
            }
        }, 30, null) + "\n</div>\n<div class=\"right\">\n<div class=\"section-title\">Profile</div>\n<p>" + highlightKeywords(profile.getSummary(), keywords) + "</p>\n<div class=\"section-title\">Skills</div>\n" + CollectionsKt.joinToString$default(profile.getSkills(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$creativeTemplate$2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<span class='skill-tag'>" + it + "</span>";
            }
        }, 30, null) + "\n<div class=\"section-title\">Certifications</div>\n" + CollectionsKt.joinToString$default(profile.getCertifications(), "", null, null, 0, null, new Function1<Certification, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$creativeTemplate$3
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Certification it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:4px;'>• " + it + "</div>";
            }
        }, 30, null) + "\n<div class=\"section-title\">Languages</div>\n" + CollectionsKt.joinToString$default(profile.getLanguages(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$creativeTemplate$4
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:4px;'>• " + it + "</div>";
            }
        }, 30, null) + "\n</div>\n</div>\n</body>\n</html>\n        ");
    }

    private final String executiveTemplate(UserProfile profile, String jobTitle, String company, final List<String> keywords, final Colors colors) {
        String experience = CollectionsKt.joinToString$default(profile.getWorkHistory(), "", null, null, 0, null, new Function1<WorkExperience, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$executiveTemplate$experience$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(WorkExperience exp) {
                String highlightKeywords;
                Intrinsics.checkNotNullParameter(exp, "exp");
                String role = exp.getRole();
                String company2 = exp.getCompany();
                String secondary = CVTemplate.Colors.this.getSecondary();
                String startDate = exp.getStartDate();
                String endDate = exp.getEndDate();
                highlightKeywords = CVTemplate.INSTANCE.highlightKeywords(exp.getDescription(), keywords);
                return StringsKt.trimIndent("\n            <div style='margin-bottom:14px;'>\n            <div style='font-weight:bold;'>" + role + ", " + company2 + "</div>\n            <div style='color:" + secondary + ";font-size:10pt;font-style:italic;margin-bottom:4px;'>" + startDate + " – " + endDate + "</div>\n            <div>" + highlightKeywords + "</div>\n            </div>\n            ");
            }
        }, 30, null);
        return StringsKt.trimIndent("\n<!DOCTYPE html>\n<html>\n<head><meta charset=\"UTF-8\">\n<style>\nbody { font-family: Georgia, serif; margin:20mm 25mm; color:" + colors.getText() + "; font-size:10.5pt; line-height:1.6; background:#fff; }\n.name { font-size:20pt; font-weight:bold; text-align:center; color:" + colors.getPrimary() + "; letter-spacing:2px; margin-bottom:4px; }\n.jobtitle { text-align:center; color:" + colors.getSecondary() + "; font-size:11pt; font-style:italic; margin-bottom:6px; }\n.contact { text-align:center; font-size:9.5pt; color:" + colors.getSecondary() + "; margin-bottom:16px; }\n.divider { border:none; border-top:2px solid " + colors.getPrimary() + "; margin:10px 0; }\n.thin-divider { border:none; border-top:1px solid " + colors.getSecondary() + "; margin:8px 0; }\n.section-title { font-size:11pt; font-weight:bold; text-transform:uppercase; letter-spacing:2px; color:" + colors.getPrimary() + "; margin:16px 0 8px 0; }\n</style>\n</head>\n<body>\n<div class=\"name\">" + profile.getName() + "</div>\n<div class=\"jobtitle\">" + jobTitle + "</div>\n<div class=\"contact\">" + profile.getEmail() + " | " + profile.getPhone() + " | " + profile.getLocation() + " | " + profile.getLinkedIn() + "</div>\n<hr class=\"divider\">\n<div class=\"section-title\">Executive Summary</div>\n<hr class=\"thin-divider\">\n<p>" + highlightKeywords(profile.getSummary(), keywords) + "</p>\n<div class=\"section-title\">Professional Experience</div>\n<hr class=\"thin-divider\">\n" + experience + "\n<div class=\"section-title\">Education</div>\n<hr class=\"thin-divider\">\n" + CollectionsKt.joinToString$default(profile.getEducation(), "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.CVTemplate$executiveTemplate$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "<div style='margin-bottom:6px;'>" + it + "</div>";
            }
        }, 30, null) + "\n<div class=\"section-title\">Core Competencies</div>\n<hr class=\"thin-divider\">\n<div>" + CollectionsKt.joinToString$default(profile.getSkills(), " &nbsp;|&nbsp; ", null, null, 0, null, null, 62, null) + "</div>\n<div class=\"section-title\">Certifications</div>\n<hr class=\"thin-divider\">\n<div>" + CollectionsKt.joinToString$default(profile.getCertifications(), " &nbsp;|&nbsp; ", null, null, 0, null, null, 62, null) + "</div>\n</body>\n</html>\n        ");
    }
}
