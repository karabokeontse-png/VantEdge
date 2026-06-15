package com.vantedge.app.ui.screens;

import androidx.compose.ui.graphics.Color;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColorSchemeScreen.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u0016\u0010\u0017\u001a\u00020\u0007HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\fJ\u0016\u0010\u0019\u001a\u00020\u0007HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\fJ\u0016\u0010\u001b\u001a\u00020\u0007HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\fJO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u0019\u0010\t\u001a\u00020\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0019\u0010\u0006\u001a\u00020\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u0011\u0010\fR\u0019\u0010\b\u001a\u00020\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006&"}, d2 = {"Lcom/vantedge/app/ui/screens/ColorScheme;", "", "id", "", "name", "vibe", "primary", "Landroidx/compose/ui/graphics/Color;", "secondary", "accent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAccent-0d7_KjU", "()J", OperatorName.SET_LINE_CAPSTYLE, "getId", "()Ljava/lang/String;", "getName", "getPrimary-0d7_KjU", "getSecondary-0d7_KjU", "getVibe", "component1", "component2", "component3", "component4", "component4-0d7_KjU", "component5", "component5-0d7_KjU", "component6", "component6-0d7_KjU", "copy", "copy-15-ZiRo", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJJ)Lcom/vantedge/app/ui/screens/ColorScheme;", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final /* data */ class ColorScheme {
    public static final int $stable = 0;
    private final long accent;
    private final String id;
    private final String name;
    private final long primary;
    private final long secondary;
    private final String vibe;

    public /* synthetic */ ColorScheme(String str, String str2, String str3, long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, j, j2, j3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String getVibe() {
        return this.vibe;
    }

    /* renamed from: component4-0d7_KjU, reason: not valid java name and from getter */
    public final long getPrimary() {
        return this.primary;
    }

    /* renamed from: component5-0d7_KjU, reason: not valid java name and from getter */
    public final long getSecondary() {
        return this.secondary;
    }

    /* renamed from: component6-0d7_KjU, reason: not valid java name and from getter */
    public final long getAccent() {
        return this.accent;
    }

    /* renamed from: copy-15-ZiRo, reason: not valid java name */
    public final ColorScheme m6442copy15ZiRo(String id, String name, String vibe, long primary, long secondary, long accent) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(vibe, "vibe");
        return new ColorScheme(id, name, vibe, primary, secondary, accent, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ColorScheme)) {
            return false;
        }
        ColorScheme colorScheme = (ColorScheme) other;
        return Intrinsics.areEqual(this.id, colorScheme.id) && Intrinsics.areEqual(this.name, colorScheme.name) && Intrinsics.areEqual(this.vibe, colorScheme.vibe) && Color.m3751equalsimpl0(this.primary, colorScheme.primary) && Color.m3751equalsimpl0(this.secondary, colorScheme.secondary) && Color.m3751equalsimpl0(this.accent, colorScheme.accent);
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.vibe.hashCode()) * 31) + Color.m3757hashCodeimpl(this.primary)) * 31) + Color.m3757hashCodeimpl(this.secondary)) * 31) + Color.m3757hashCodeimpl(this.accent);
    }

    public String toString() {
        return "ColorScheme(id=" + this.id + ", name=" + this.name + ", vibe=" + this.vibe + ", primary=" + Color.m3758toStringimpl(this.primary) + ", secondary=" + Color.m3758toStringimpl(this.secondary) + ", accent=" + Color.m3758toStringimpl(this.accent) + ")";
    }

    private ColorScheme(String id, String name, String vibe, long primary, long secondary, long accent) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(vibe, "vibe");
        this.id = id;
        this.name = name;
        this.vibe = vibe;
        this.primary = primary;
        this.secondary = secondary;
        this.accent = accent;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getVibe() {
        return this.vibe;
    }

    /* renamed from: getPrimary-0d7_KjU, reason: not valid java name */
    public final long m6444getPrimary0d7_KjU() {
        return this.primary;
    }

    /* renamed from: getSecondary-0d7_KjU, reason: not valid java name */
    public final long m6445getSecondary0d7_KjU() {
        return this.secondary;
    }

    /* renamed from: getAccent-0d7_KjU, reason: not valid java name */
    public final long m6443getAccent0d7_KjU() {
        return this.accent;
    }
}
