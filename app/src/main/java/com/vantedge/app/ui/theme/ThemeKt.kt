package com.vantedge.app.ui.theme;

import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.Typography;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.font.SystemFontFamily;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnitKt;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Theme.kt */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u0011\u0010\u0010\u001a\r\u0012\u0004\u0012\u00020\r0\u0011¢\u0006\u0002\b\u0012H\u0007¢\u0006\u0002\u0010\u0013\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0010\u0010\u0007\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u000b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002¨\u0006\u0014"}, d2 = {"AmberAccent", "Landroidx/compose/ui/graphics/Color;", OperatorName.SET_LINE_CAPSTYLE, "AppTypography", "Landroidx/compose/material3/Typography;", "getAppTypography", "()Landroidx/compose/material3/Typography;", "BgBlack", "CardDark", "DarkColorScheme", "Landroidx/compose/material3/ColorScheme;", "TealPrimary", "VantEdgeTheme", "", "darkTheme", "", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "(ZLkotlin/jvm/functions/Function2;Landroidx/compose/runtime/Composer;II)V", "app_debug"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ThemeKt {
    private static final Typography AppTypography;
    private static final long BgBlack = androidx.compose.ui.graphics.ColorKt.Color(4279045389L);
    private static final long TealPrimary = androidx.compose.ui.graphics.ColorKt.Color(4278239141L);
    private static final long AmberAccent = androidx.compose.ui.graphics.ColorKt.Color(4294948912L);
    private static final long CardDark = androidx.compose.ui.graphics.ColorKt.Color(4279900718L);
    private static final ColorScheme DarkColorScheme = ColorSchemeKt.m1735darkColorSchemeCXl9yA$default(TealPrimary, Color.INSTANCE.m3776getBlack0d7_KjU(), 0, 0, 0, AmberAccent, Color.INSTANCE.m3776getBlack0d7_KjU(), 0, 0, 0, 0, 0, 0, BgBlack, Color.INSTANCE.m3787getWhite0d7_KjU(), CardDark, Color.INSTANCE.m3787getWhite0d7_KjU(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -122980, 15, null);

    static {
        SystemFontFamily systemFontFamily = FontFamily.INSTANCE.getDefault();
        TextStyle textStyle = new TextStyle(0L, TextUnitKt.getSp(16), FontWeight.INSTANCE.getNormal(), (FontStyle) null, (FontSynthesis) null, systemFontFamily, (String) null, TextUnitKt.getSp(0.5d), (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, TextUnitKt.getSp(24), (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16645977, (DefaultConstructorMarker) null);
        SystemFontFamily systemFontFamily2 = FontFamily.INSTANCE.getDefault();
        TextStyle textStyle2 = new TextStyle(0L, TextUnitKt.getSp(22), FontWeight.INSTANCE.getBold(), (FontStyle) null, (FontSynthesis) null, systemFontFamily2, (String) null, TextUnitKt.getSp(0), (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, TextUnitKt.getSp(28), (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16645977, (DefaultConstructorMarker) null);
        SystemFontFamily systemFontFamily3 = FontFamily.INSTANCE.getDefault();
        AppTypography = new Typography(null, null, null, null, null, null, textStyle2, null, null, textStyle, null, null, null, new TextStyle(0L, TextUnitKt.getSp(12), FontWeight.INSTANCE.getMedium(), (FontStyle) null, (FontSynthesis) null, systemFontFamily3, (String) null, TextUnitKt.getSp(0.5d), (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, TextUnitKt.getSp(16), (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16645977, (DefaultConstructorMarker) null), null, 23999, null);
    }

    public static final Typography getAppTypography() {
        return AppTypography;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:
    
        if ((r13 & 1) != 0) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void VantEdgeTheme(final boolean r9, final kotlin.jvm.functions.Function2<? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r10, androidx.compose.runtime.Composer r11, final int r12, final int r13) {
        /*
            java.lang.String r0 = "content"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            r0 = -1200987357(0xffffffffb86a6323, float:-5.5882276E-5)
            androidx.compose.runtime.Composer r11 = r11.startRestartGroup(r0)
            java.lang.String r1 = "C(VantEdgeTheme)P(1)57@1708L21,61@1838L121:Theme.kt#louycv"
            androidx.compose.runtime.ComposerKt.sourceInformation(r11, r1)
            r1 = r12
            r2 = r13 & 2
            r3 = 16
            if (r2 == 0) goto L1b
            r1 = r1 | 48
            goto L2a
        L1b:
            r2 = r12 & 112(0x70, float:1.57E-43)
            if (r2 != 0) goto L2a
            boolean r2 = r11.changedInstance(r10)
            if (r2 == 0) goto L28
            r2 = 32
            goto L29
        L28:
            r2 = r3
        L29:
            r1 = r1 | r2
        L2a:
            r2 = r1 & 81
            if (r2 != r3) goto L39
            boolean r2 = r11.getSkipping()
            if (r2 != 0) goto L35
            goto L39
        L35:
            r11.skipToGroupEnd()
            goto L85
        L39:
            r11.startDefaults()
            r2 = r12 & 1
            if (r2 == 0) goto L4f
            boolean r2 = r11.getDefaultsInvalid()
            if (r2 == 0) goto L47
            goto L4f
        L47:
            r11.skipToGroupEnd()
            r2 = r13 & 1
            if (r2 == 0) goto L5a
            goto L58
        L4f:
            r2 = r13 & 1
            if (r2 == 0) goto L5a
            r2 = 0
            boolean r9 = androidx.compose.foundation.DarkThemeKt.isSystemInDarkTheme(r11, r2)
        L58:
            r1 = r1 & (-15)
        L5a:
            r8 = r1
            r11.endDefaults()
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L6a
            r1 = -1
            java.lang.String r2 = "com.vantedge.app.ui.theme.VantEdgeTheme (Theme.kt:59)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0, r8, r1, r2)
        L6a:
            androidx.compose.material3.ColorScheme r1 = com.vantedge.app.ui.theme.ThemeKt.DarkColorScheme
            r2 = 0
            androidx.compose.material3.Typography r3 = com.vantedge.app.ui.theme.ThemeKt.AppTypography
            int r0 = r8 << 6
            r0 = r0 & 7168(0x1c00, float:1.0045E-41)
            r6 = r0 | 390(0x186, float:5.47E-43)
            r7 = 2
            r4 = r10
            r5 = r11
            androidx.compose.material3.MaterialThemeKt.MaterialTheme(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L84
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L84:
            r1 = r8
        L85:
            androidx.compose.runtime.ScopeUpdateScope r0 = r11.endRestartGroup()
            if (r0 == 0) goto L95
            com.vantedge.app.ui.theme.ThemeKt$VantEdgeTheme$1 r2 = new com.vantedge.app.ui.theme.ThemeKt$VantEdgeTheme$1
            r2.<init>()
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2
            r0.updateScope(r2)
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.theme.ThemeKt.VantEdgeTheme(boolean, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }
}
