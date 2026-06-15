package com.vantedge.app.ui.screens;

import androidx.compose.foundation.layout.RowScope;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: ColorSchemeScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$ColorSchemeScreenKt {
    public static final ComposableSingletons$ColorSchemeScreenKt INSTANCE = new ComposableSingletons$ColorSchemeScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f99lambda1 = ComposableLambdaKt.composableLambdaInstance(1638714572, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C90@3132L172:ColorSchemeScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(1638714572, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt.lambda-1.<anonymous> (ColorSchemeScreen.kt:90)");
                }
                TextKt.m2466Text4IGK_g("Choose Color Scheme", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 196998, 0, 131034);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f100lambda2 = ComposableLambdaKt.composableLambdaInstance(1736316424, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C88@3075L261:ColorSchemeScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(1736316424, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt.lambda-2.<anonymous> (ColorSchemeScreen.kt:88)");
                }
                AppBarKt.TopAppBar(ComposableSingletons$ColorSchemeScreenKt.INSTANCE.m6465getLambda1$app_debug(), null, null, null, null, null, null, $composer, 6, WebSocketProtocol.PAYLOAD_SHORT);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                    return;
                }
                return;
            }
            $composer.skipToGroupEnd();
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static Function3<RowScope, Composer, Integer, Unit> f101lambda3 = ComposableLambdaKt.composableLambdaInstance(510805443, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C172@6494L12:ColorSchemeScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(510805443, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$ColorSchemeScreenKt.lambda-3.<anonymous> (ColorSchemeScreen.kt:172)");
            }
            TextKt.m2466Text4IGK_g("Back", (Modifier) null, 0L, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 6, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6465getLambda1$app_debug() {
        return f99lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6466getLambda2$app_debug() {
        return f100lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6467getLambda3$app_debug() {
        return f101lambda3;
    }
}
