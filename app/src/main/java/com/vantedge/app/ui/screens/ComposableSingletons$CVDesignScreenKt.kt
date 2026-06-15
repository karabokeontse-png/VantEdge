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

/* compiled from: CVDesignScreen.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ComposableSingletons$CVDesignScreenKt {
    public static final ComposableSingletons$CVDesignScreenKt INSTANCE = new ComposableSingletons$CVDesignScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static Function2<Composer, Integer, Unit> f93lambda1 = ComposableLambdaKt.composableLambdaInstance(921309078, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C80@2967L173:CVDesignScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(921309078, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt.lambda-1.<anonymous> (CVDesignScreen.kt:80)");
                }
                TextKt.m2466Text4IGK_g("Choose Your CV Style", (Modifier) null, Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 196998, 0, 131034);
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
    public static Function2<Composer, Integer, Unit> f94lambda2 = ComposableLambdaKt.composableLambdaInstance(550106578, false, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
            invoke(composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer $composer, int $changed) {
            ComposerKt.sourceInformation($composer, "C78@2910L262:CVDesignScreen.kt#fpoywd");
            if (($changed & 11) != 2 || !$composer.getSkipping()) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(550106578, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt.lambda-2.<anonymous> (CVDesignScreen.kt:78)");
                }
                AppBarKt.TopAppBar(ComposableSingletons$CVDesignScreenKt.INSTANCE.m6459getLambda1$app_debug(), null, null, null, null, null, null, $composer, 6, WebSocketProtocol.PAYLOAD_SHORT);
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
    public static Function3<RowScope, Composer, Integer, Unit> f95lambda3 = ComposableLambdaKt.composableLambdaInstance(-274772531, false, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
            invoke(rowScope, composer, num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(RowScope Button, Composer $composer, int $changed) {
            Intrinsics.checkNotNullParameter(Button, "$this$Button");
            ComposerKt.sourceInformation($composer, "C157@6038L12:CVDesignScreen.kt#fpoywd");
            if (($changed & 81) == 16 && $composer.getSkipping()) {
                $composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-274772531, $changed, -1, "com.vantedge.app.ui.screens.ComposableSingletons$CVDesignScreenKt.lambda-3.<anonymous> (CVDesignScreen.kt:157)");
            }
            TextKt.m2466Text4IGK_g("Back", (Modifier) null, 0L, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 6, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* renamed from: getLambda-1$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6459getLambda1$app_debug() {
        return f93lambda1;
    }

    /* renamed from: getLambda-2$app_debug, reason: not valid java name */
    public final Function2<Composer, Integer, Unit> m6460getLambda2$app_debug() {
        return f94lambda2;
    }

    /* renamed from: getLambda-3$app_debug, reason: not valid java name */
    public final Function3<RowScope, Composer, Integer, Unit> m6461getLambda3$app_debug() {
        return f95lambda3;
    }
}
