package com.vantedge.app.ui.screens;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.CardColors;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SkippableUpdater;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambda;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.LayoutKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.ClipboardManager;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.navigation.NavController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.PopUpToBuilder;
import androidx.profileinstaller.ProfileVerifier;
import com.google.mlkit.common.MlKitException;
import com.tom_roush.fontbox.ttf.OS2WindowsMetricsTable;
import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDWindowsLaunchParams;
import com.vantedge.app.data.model.CompatibilityRecord;
import com.vantedge.app.data.model.GapItem;
import com.vantedge.app.data.model.RelevancyItem;
import com.vantedge.app.data.viewmodel.ActionType;
import com.vantedge.app.data.viewmodel.ContentState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.ExportState;
import com.vantedge.app.data.viewmodel.ResultViewState;
import com.vantedge.app.ui.screens.ResultScreenMode;
import com.vantedge.app.ui.screens.TabInfo;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: ResultScreen.kt */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u001d\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0003¢\u0006\u0002\u0010\f\u001a4\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\n2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001az\u0010\u0016\u001a\u00020\b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u00132\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\b0\u001c2\u0006\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\n2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\b0\u001c2\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0 H\u0003ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u0015\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020%H\u0003¢\u0006\u0002\u0010&\u001a:\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a%\u0010/\u001a\u00020\b2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0007¢\u0006\u0002\u00106\u001a\u0015\u00107\u001a\u00020\u00012\u0006\u00108\u001a\u00020\u0013H\u0002¢\u0006\u0002\u00109\u001a\u0010\u0010:\u001a\u00020\n2\u0006\u00108\u001a\u00020\u0013H\u0002\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0005\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006;²\u0006\n\u0010<\u001a\u00020=X\u008a\u0084\u0002²\u0006\f\u0010>\u001a\u0004\u0018\u00010\u001eX\u008a\u008e\u0002²\u0006\n\u0010\u001a\u001a\u00020\u0013X\u008a\u008e\u0002²\u0006\n\u0010?\u001a\u00020@X\u008a\u0084\u0002²\u0006\n\u0010A\u001a\u00020BX\u008a\u0084\u0002²\u0006\n\u0010C\u001a\u00020\nX\u008a\u0084\u0002²\u0006\n\u0010D\u001a\u00020EX\u008a\u008e\u0002²\u0006\n\u0010F\u001a\u00020BX\u008a\u0084\u0002²\u0006\n\u0010G\u001a\u00020EX\u008a\u008e\u0002²\u0006\n\u0010H\u001a\u00020EX\u008a\u008e\u0002²\u0006\n\u0010H\u001a\u00020EX\u008a\u008e\u0002"}, d2 = {"RAmber", "Landroidx/compose/ui/graphics/Color;", OperatorName.SET_LINE_CAPSTYLE, "RBg", "RCard", "ROnBg", "RTeal", "AnalysisSectionCard", "", "heading", "", "body", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/runtime/Composer;I)V", "AnalysisTab", "compatibility", "Lcom/vantedge/app/data/model/CompatibilityRecord;", "scoreColor", "scoreLabel", "delta", "", "AnalysisTab-3IgeMak", "(Lcom/vantedge/app/data/model/CompatibilityRecord;JLjava/lang/String;Ljava/lang/Integer;Landroidx/compose/runtime/Composer;I)V", "CycleTabContent", "tabs", "", "Lcom/vantedge/app/ui/screens/TabInfo;", "selectedTab", "onTabSelected", "Lkotlin/Function1;", "onWebViewReady", "Landroid/webkit/WebView;", "onRetryClicked", "Lkotlin/Function0;", "CycleTabContent-8r3B23s", "(Ljava/util/List;ILkotlin/jvm/functions/Function1;JLjava/lang/String;Ljava/lang/Integer;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V", "GapCard", "gap", "Lcom/vantedge/app/data/model/GapItem;", "(Lcom/vantedge/app/data/model/GapItem;Landroidx/compose/runtime/Composer;I)V", "RelevancyCard", "name", "matchPct", "description", "groupLabel", "color", "RelevancyCard-qFjXxE8", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;JLandroidx/compose/runtime/Composer;I)V", "ResultScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/vantedge/app/data/viewmodel/CycleViewModel;", "mode", "Lcom/vantedge/app/ui/screens/ResultScreenMode;", "(Landroidx/navigation/NavController;Lcom/vantedge/app/data/viewmodel/CycleViewModel;Lcom/vantedge/app/ui/screens/ResultScreenMode;Landroidx/compose/runtime/Composer;I)V", "scoreToColor", "score", "(I)J", "scoreToLabel", "app_debug", "liveState", "Lcom/vantedge/app/data/viewmodel/ResultViewState;", "webViewRef", "exportState", "Lcom/vantedge/app/data/viewmodel/ExportState;", "rotation", "", "customInstructions", "isExpanded", "", "sweep", "showAdvanced", "expanded"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ResultScreenKt {
    private static final long RBg = ColorKt.Color(4279045389L);
    private static final long RCard = ColorKt.Color(4279900718L);
    private static final long RTeal = ColorKt.Color(4278239141L);
    private static final long RAmber = ColorKt.Color(4294948912L);
    private static final long ROnBg = ColorKt.Color(4292927712L);

    public static final void ResultScreen(final NavController navController, final CycleViewModel viewModel, final ResultScreenMode mode, Composer $composer, final int $changed) {
        ResultViewState computeSnapshotViewState;
        Object value$iv;
        final ResultViewState state;
        Object value$iv$iv$iv;
        Object value$iv2;
        Object value$iv3;
        String str;
        Object value$iv4;
        List $this$ResultScreen_u24lambda_u249_u24lambda_u248;
        Object value$iv5;
        Intrinsics.checkNotNullParameter(navController, "navController");
        Intrinsics.checkNotNullParameter(viewModel, "viewModel");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Composer $composer2 = $composer.startRestartGroup(-819366146);
        ComposerKt.sourceInformation($composer2, "C(ResultScreen)P(1,2)80@3362L16,81@3405L192,92@3791L7,93@3815L24,94@3859L32,95@3923L7,96@3953L43,101@4183L458,115@4665L30,117@4701L111,123@4859L16,125@4881L443,165@6498L20732:ResultScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-819366146, $changed, -1, "com.vantedge.app.ui.screens.ResultScreen (ResultScreen.kt:79)");
        }
        State liveState$delegate = SnapshotStateKt.collectAsState(viewModel.getResultViewState(), null, $composer2, 8, 1);
        $composer2.startReplaceableGroup(1422217955);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        boolean invalid$iv = ((($changed & 896) ^ KyberEngine.KyberPolyBytes) > 256 && $composer2.changed(mode)) || ($changed & KyberEngine.KyberPolyBytes) == 256;
        Object it$iv = $composer2.rememberedValue();
        if (invalid$iv || it$iv == Composer.INSTANCE.getEmpty()) {
            if (mode instanceof ResultScreenMode.Live) {
                computeSnapshotViewState = null;
            } else {
                if (!(mode instanceof ResultScreenMode.Historical)) {
                    throw new NoWhenBranchMatchedException();
                }
                computeSnapshotViewState = viewModel.computeSnapshotViewState(((ResultScreenMode.Historical) mode).getCycle());
            }
            value$iv = computeSnapshotViewState;
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        ResultViewState historicalState = (ResultViewState) value$iv;
        $composer2.endReplaceableGroup();
        if (mode instanceof ResultScreenMode.Live) {
            state = ResultScreen$lambda$0(liveState$delegate);
        } else {
            if (!(mode instanceof ResultScreenMode.Historical)) {
                throw new NoWhenBranchMatchedException();
            }
            if (historicalState == null) {
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$state$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(Composer composer, int i) {
                            ResultScreenKt.ResultScreen(NavController.this, viewModel, mode, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                        }
                    });
                    return;
                }
                return;
            }
            state = historicalState;
        }
        ProvidableCompositionLocal<ClipboardManager> localClipboardManager = CompositionLocalsKt.getLocalClipboardManager();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localClipboardManager);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final ClipboardManager clipboard = (ClipboardManager) consume;
        $composer2.startReplaceableGroup(773894976);
        ComposerKt.sourceInformation($composer2, "CC(rememberCoroutineScope)489@20472L144:Effects.kt#9igjgp");
        $composer2.startReplaceableGroup(-492369756);
        ComposerKt.sourceInformation($composer2, "CC(remember):Composables.kt#9igjgp");
        Object it$iv$iv$iv = $composer2.rememberedValue();
        if (it$iv$iv$iv == Composer.INSTANCE.getEmpty()) {
            value$iv$iv$iv = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, $composer2));
            $composer2.updateRememberedValue(value$iv$iv$iv);
        } else {
            value$iv$iv$iv = it$iv$iv$iv;
        }
        $composer2.endReplaceableGroup();
        CompositionScopedCoroutineScopeCanceller wrapper$iv = (CompositionScopedCoroutineScopeCanceller) value$iv$iv$iv;
        final CoroutineScope scope = wrapper$iv.getCoroutineScope();
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1422218409);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = new SnackbarHostState();
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final SnackbarHostState snackbar = (SnackbarHostState) value$iv2;
        $composer2.endReplaceableGroup();
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume2 = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume2;
        $composer2.startReplaceableGroup(1422218503);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(null, null, 2, null);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        final MutableState webViewRef$delegate = (MutableState) value$iv3;
        $composer2.endReplaceableGroup();
        ContentState it = state.getContent();
        long scoreColor = it != null ? scoreToColor(it.getScore()) : Color.INSTANCE.m3780getGray0d7_KjU();
        ContentState it2 = state.getContent();
        if (it2 == null || (str = scoreToLabel(it2.getScore())) == null) {
            str = "";
        }
        String scoreLabel = str;
        ContentState content = state.getContent();
        $composer2.startReplaceableGroup(1422218733);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        boolean invalid$iv2 = $composer2.changed(content);
        Object it$iv4 = $composer2.rememberedValue();
        if (invalid$iv2 || it$iv4 == Composer.INSTANCE.getEmpty()) {
            ContentState c = state.getContent();
            if (c != null) {
                List $this$ResultScreen_u24lambda_u249_u24lambda_u2482 = CollectionsKt.createListBuilder();
                if (c.getHasDocs()) {
                    $this$ResultScreen_u24lambda_u249_u24lambda_u248 = $this$ResultScreen_u24lambda_u249_u24lambda_u2482;
                    $this$ResultScreen_u24lambda_u249_u24lambda_u248.add(new TabInfo.Doc("CV", c.getCvContent(), c.isCvCorruptedJson()));
                    $this$ResultScreen_u24lambda_u249_u24lambda_u248.add(new TabInfo.Doc("Cover Letter", c.getCoverLetterContent(), c.isCoverLetterCorruptedJson()));
                } else {
                    $this$ResultScreen_u24lambda_u249_u24lambda_u248 = $this$ResultScreen_u24lambda_u249_u24lambda_u2482;
                }
                $this$ResultScreen_u24lambda_u249_u24lambda_u248.add(new TabInfo.Ana(c.getCompatibility()));
                value$iv4 = CollectionsKt.build($this$ResultScreen_u24lambda_u249_u24lambda_u2482);
            } else {
                value$iv4 = CollectionsKt.emptyList();
            }
            $composer2.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        final List tabs = (List) value$iv4;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(1422219215);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv5 = $composer2.rememberedValue();
        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(0, null, 2, null);
            $composer2.updateRememberedValue(value$iv5);
        } else {
            value$iv5 = it$iv5;
        }
        final MutableState selectedTab$delegate = (MutableState) value$iv5;
        $composer2.endReplaceableGroup();
        EffectsKt.LaunchedEffect(Integer.valueOf(tabs.size()), new ResultScreenKt$ResultScreen$1(tabs, selectedTab$delegate, null), $composer2, 64);
        State exportState$delegate = SnapshotStateKt.collectAsState(viewModel.getExportState(), null, $composer2, 8, 1);
        EffectsKt.LaunchedEffect(Unit.INSTANCE, new ResultScreenKt$ResultScreen$2(viewModel, snackbar, null), $composer2, 70);
        ContentState content2 = state.getContent();
        final Set actions = state.getActions();
        final boolean isHistorical = mode instanceof ResultScreenMode.Historical;
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, -2082736710, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                long j;
                ComposerKt.sourceInformation($composer3, "C224@9219L39,169@6620L2652:ResultScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-2082736710, $changed2, -1, "com.vantedge.app.ui.screens.ResultScreen.<anonymous> (ResultScreen.kt:169)");
                    }
                    final ResultViewState resultViewState = ResultViewState.this;
                    ComposableLambda composableLambda = ComposableLambdaKt.composableLambda($composer3, -403921794, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3.1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:24:0x01d1  */
                        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.runtime.Composer r76, int r77) {
                            /*
                                Method dump skipped, instructions count: 469
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3.AnonymousClass1.invoke(androidx.compose.runtime.Composer, int):void");
                        }
                    });
                    final NavController navController2 = navController;
                    ComposableLambda composableLambda2 = ComposableLambdaKt.composableLambda($composer3, -1219969024, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3.2
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                            invoke(composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(Composer $composer4, int $changed3) {
                            ComposerKt.sourceInformation($composer4, "C182@7114L523:ResultScreen.kt#fpoywd");
                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-1219969024, $changed3, -1, "com.vantedge.app.ui.screens.ResultScreen.<anonymous>.<anonymous> (ResultScreen.kt:182)");
                                }
                                final NavController navController3 = NavController.this;
                                IconButtonKt.IconButton(new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.2.1
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public /* bridge */ /* synthetic */ Unit invoke() {
                                        invoke2();
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2() {
                                        if (!NavController.this.popBackStack()) {
                                            NavController.this.navigate("dashboard", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.2.1.1
                                                @Override // kotlin.jvm.functions.Function1
                                                public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                                    invoke2(navOptionsBuilder);
                                                    return Unit.INSTANCE;
                                                }

                                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                                public final void invoke2(NavOptionsBuilder navigate) {
                                                    Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                                    navigate.popUpTo(0, new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.2.1.1.1
                                                        @Override // kotlin.jvm.functions.Function1
                                                        public /* bridge */ /* synthetic */ Unit invoke(PopUpToBuilder popUpToBuilder) {
                                                            invoke2(popUpToBuilder);
                                                            return Unit.INSTANCE;
                                                        }

                                                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                                        public final void invoke2(PopUpToBuilder popUpTo) {
                                                            Intrinsics.checkNotNullParameter(popUpTo, "$this$popUpTo");
                                                            popUpTo.setInclusive(true);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                }, null, false, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6512getLambda1$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    });
                    final List<TabInfo> list = tabs;
                    final boolean z = isHistorical;
                    final Set<ActionType> set = actions;
                    final MutableState<Integer> mutableState = selectedTab$delegate;
                    final ClipboardManager clipboardManager = clipboard;
                    final CoroutineScope coroutineScope = scope;
                    final SnackbarHostState snackbarHostState = snackbar;
                    final Context context2 = context;
                    final MutableState<WebView> mutableState2 = webViewRef$delegate;
                    final CycleViewModel cycleViewModel = viewModel;
                    final NavController navController3 = navController;
                    ComposableLambda composableLambda3 = ComposableLambdaKt.composableLambda($composer3, 175853417, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer, Integer num) {
                            invoke(rowScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(RowScope TopAppBar, Composer $composer4, int $changed3) {
                            int ResultScreen$lambda$11;
                            Intrinsics.checkNotNullParameter(TopAppBar, "$this$TopAppBar");
                            ComposerKt.sourceInformation($composer4, "C215@8790L366:ResultScreen.kt#fpoywd");
                            if (($changed3 & 81) != 16 || !$composer4.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(175853417, $changed3, -1, "com.vantedge.app.ui.screens.ResultScreen.<anonymous>.<anonymous> (ResultScreen.kt:197)");
                                }
                                List<TabInfo> list2 = list;
                                ResultScreen$lambda$11 = ResultScreenKt.ResultScreen$lambda$11(mutableState);
                                TabInfo activeTab = (TabInfo) CollectionsKt.getOrNull(list2, ResultScreen$lambda$11);
                                boolean canShowDocActions = (activeTab instanceof TabInfo.Doc) && (z || set.contains(ActionType.COPY) || set.contains(ActionType.SAVE_PDF));
                                $composer4.startReplaceableGroup(-1959633568);
                                ComposerKt.sourceInformation($composer4, "202@8055L349,208@8429L318");
                                if (canShowDocActions) {
                                    Intrinsics.checkNotNull(activeTab, "null cannot be cast to non-null type com.vantedge.app.ui.screens.TabInfo.Doc");
                                    final TabInfo.Doc docTab = (TabInfo.Doc) activeTab;
                                    final ClipboardManager clipboardManager2 = clipboardManager;
                                    final CoroutineScope coroutineScope2 = coroutineScope;
                                    final SnackbarHostState snackbarHostState2 = snackbarHostState;
                                    IconButtonKt.IconButton(new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.3.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }

                                        /* compiled from: ResultScreen.kt */
                                        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                                        @DebugMetadata(c = "com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3$3$1$1", f = "ResultScreen.kt", i = {}, l = {MlKitException.CODE_SCANNER_PIPELINE_INITIALIZATION_ERROR}, m = "invokeSuspend", n = {}, s = {})
                                        /* renamed from: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$3$3$1$1, reason: invalid class name and collision with other inner class name */
                                        static final class C00961 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                                            final /* synthetic */ SnackbarHostState $snackbar;
                                            int label;

                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            C00961(SnackbarHostState snackbarHostState, Continuation<? super C00961> continuation) {
                                                super(2, continuation);
                                                this.$snackbar = snackbarHostState;
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                                return new C00961(this.$snackbar, continuation);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                                return ((C00961) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                                            }

                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                            public final Object invokeSuspend(Object $result) {
                                                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                                switch (this.label) {
                                                    case 0:
                                                        ResultKt.throwOnFailure($result);
                                                        this.label = 1;
                                                        if (SnackbarHostState.showSnackbar$default(this.$snackbar, "Copied to clipboard", null, false, null, this, 14, null) != coroutine_suspended) {
                                                            break;
                                                        } else {
                                                            return coroutine_suspended;
                                                        }
                                                    case 1:
                                                        ResultKt.throwOnFailure($result);
                                                        break;
                                                    default:
                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }

                                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                            ClipboardManager.this.setText(new AnnotatedString(docTab.getContent(), null, null, 6, null));
                                            BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new C00961(snackbarHostState2, null), 3, null);
                                        }
                                    }, null, false, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6519getLambda2$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                    final Context context3 = context2;
                                    final CoroutineScope coroutineScope3 = coroutineScope;
                                    final MutableState<WebView> mutableState3 = mutableState2;
                                    final SnackbarHostState snackbarHostState3 = snackbarHostState;
                                    IconButtonKt.IconButton(new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.3.2
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public /* bridge */ /* synthetic */ Unit invoke() {
                                            invoke2();
                                            return Unit.INSTANCE;
                                        }

                                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2() {
                                            String label = Intrinsics.areEqual(TabInfo.Doc.this.getTitle(), "CV") ? "CV" : "CoverLetter";
                                            ResultScreenKt.ResultScreen$savePdf(context3, coroutineScope3, mutableState3, snackbarHostState3, label);
                                        }
                                    }, null, false, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6520getLambda3$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                }
                                $composer4.endReplaceableGroup();
                                final CycleViewModel cycleViewModel2 = cycleViewModel;
                                final NavController navController4 = navController3;
                                IconButtonKt.IconButton(new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.3.3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public /* bridge */ /* synthetic */ Unit invoke() {
                                        invoke2();
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2() {
                                        CycleViewModel.this.resetState();
                                        navController4.navigate("dashboard", new Function1<NavOptionsBuilder, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.3.3.1
                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Unit invoke(NavOptionsBuilder navOptionsBuilder) {
                                                invoke2(navOptionsBuilder);
                                                return Unit.INSTANCE;
                                            }

                                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(NavOptionsBuilder navigate) {
                                                Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                                                navigate.popUpTo("dashboard", new Function1<PopUpToBuilder, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.ResultScreen.3.3.3.1.1
                                                    @Override // kotlin.jvm.functions.Function1
                                                    public /* bridge */ /* synthetic */ Unit invoke(PopUpToBuilder popUpToBuilder) {
                                                        invoke2(popUpToBuilder);
                                                        return Unit.INSTANCE;
                                                    }

                                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                                    public final void invoke2(PopUpToBuilder popUpTo) {
                                                        Intrinsics.checkNotNullParameter(popUpTo, "$this$popUpTo");
                                                        popUpTo.setInclusive(true);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }, null, false, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6521getLambda4$app_debug(), $composer4, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer4.skipToGroupEnd();
                        }
                    });
                    TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
                    j = ResultScreenKt.RCard;
                    AppBarKt.TopAppBar(composableLambda, null, composableLambda2, composableLambda3, null, topAppBarDefaults.m2625topAppBarColorszjMxDiM(j, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 3462, 82);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, ComposableLambdaKt.composableLambda($composer2, -1664848008, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$4
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                ComposerKt.sourceInformation($composer3, "C167@6563L22:ResultScreen.kt#fpoywd");
                if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                    $composer3.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-1664848008, $changed2, -1, "com.vantedge.app.ui.screens.ResultScreen.<anonymous> (ResultScreen.kt:167)");
                }
                SnackbarHostKt.SnackbarHost(SnackbarHostState.this, null, null, $composer3, 6, 6);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }), null, 0, RBg, 0L, null, ComposableLambdaKt.composableLambda($composer2, -273402225, true, new ResultScreenKt$ResultScreen$5(state, viewModel, isHistorical, actions, mode, content2, tabs, scoreColor, scoreLabel, selectedTab$delegate, webViewRef$delegate, scope, navController, exportState$delegate)), $composer2, 806882352, 437);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup2 = $composer2.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ResultScreenKt.ResultScreen(NavController.this, viewModel, mode, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    private static final ResultViewState ResultScreen$lambda$0(State<ResultViewState> state) {
        Object thisObj$iv = state.getValue();
        return (ResultViewState) thisObj$iv;
    }

    private static final WebView ResultScreen$lambda$4(MutableState<WebView> mutableState) {
        MutableState<WebView> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ResultScreen$lambda$11(MutableState<Integer> mutableState) {
        MutableState<Integer> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ResultScreen$lambda$12(MutableState<Integer> mutableState, int value) {
        mutableState.setValue(Integer.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ExportState ResultScreen$lambda$13(State<? extends ExportState> state) {
        Object thisObj$iv = state.getValue();
        return (ExportState) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ResultScreen$savePdf(Context context, CoroutineScope scope, MutableState<WebView> mutableState, SnackbarHostState snackbar, String label) {
        WebView webView = ResultScreen$lambda$4(mutableState);
        if (webView == null) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new ResultScreenKt$ResultScreen$savePdf$webView$1$1(snackbar, null), 3, null);
            return;
        }
        try {
            String fileName = "VantEdge_" + label + "_" + System.currentTimeMillis();
            Object systemService = context.getSystemService(PDWindowsLaunchParams.OPERATION_PRINT);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.print.PrintManager");
            PrintManager printManager = (PrintManager) systemService;
            PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(fileName);
            Intrinsics.checkNotNullExpressionValue(printAdapter, "createPrintDocumentAdapter(...)");
            PrintAttributes attributes = new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4).setResolution(new PrintAttributes.Resolution("pdf", "pdf", OS2WindowsMetricsTable.WEIGHT_CLASS_SEMI_BOLD, OS2WindowsMetricsTable.WEIGHT_CLASS_SEMI_BOLD)).setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
            Intrinsics.checkNotNullExpressionValue(attributes, "build(...)");
            printManager.print(fileName, printAdapter, attributes);
        } catch (Exception e) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new ResultScreenKt$ResultScreen$savePdf$1(snackbar, e, null), 3, null);
        }
    }

    private static final long scoreToColor(int score) {
        return score >= 75 ? ColorKt.Color(4283215696L) : score >= 50 ? RAmber : ColorKt.Color(4293212469L);
    }

    private static final String scoreToLabel(int score) {
        return score >= 80 ? "Strong match" : score >= 60 ? "Decent match" : score >= 40 ? "Partial match" : "Weak match";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x08ed, code lost:
    
        if (r15.changedInstance(r12) == false) goto L137;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0b84  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0bab  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0979  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0499  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x040e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0386  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x038f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x054e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0b7b  */
    /* renamed from: CycleTabContent-8r3B23s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6554CycleTabContent8r3B23s(final java.util.List<? extends com.vantedge.app.ui.screens.TabInfo> r107, final int r108, final kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> r109, final long r110, final java.lang.String r112, final java.lang.Integer r113, final kotlin.jvm.functions.Function1<? super android.webkit.WebView, kotlin.Unit> r114, kotlin.jvm.functions.Function0<kotlin.Unit> r115, androidx.compose.runtime.Composer r116, final int r117, final int r118) {
        /*
            Method dump skipped, instructions count: 2992
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt.m6554CycleTabContent8r3B23s(java.util.List, int, kotlin.jvm.functions.Function1, long, java.lang.String, java.lang.Integer, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: AnalysisTab-3IgeMak, reason: not valid java name */
    public static final void m6553AnalysisTab3IgeMak(final CompatibilityRecord compatibility, final long scoreColor, final String scoreLabel, final Integer delta, Composer $composer, final int $changed) {
        Object value$iv;
        Composer $composer2 = $composer.startRestartGroup(-148962265);
        ComposerKt.sourceInformation($composer2, "C(AnalysisTab)P(!1,2:c#ui.graphics.Color,3)745@34492L141,750@34658L34,751@34697L7070:ResultScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-148962265, $changed, -1, "com.vantedge.app.ui.screens.AnalysisTab (ResultScreen.kt:744)");
        }
        final State sweep$delegate = AnimateAsStateKt.animateFloatAsState(270.0f * (compatibility.getScore() / 100.0f), AnimationSpecKt.tween$default(1000, 0, null, 6, null), 0.0f, "", null, $composer2, 3120, 20);
        $composer2.startReplaceableGroup(-488302550);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState showAdvanced$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        LazyDslKt.LazyColumn(SizeKt.fillMaxSize$default(Modifier.INSTANCE, 0.0f, 1, null), null, PaddingKt.m561PaddingValuesa9UjIt4$default(0.0f, 0.0f, 0.0f, Dp.m6092constructorimpl(40), 7, null), false, null, null, null, false, new Function1<LazyListScope, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LazyListScope lazyListScope) {
                invoke2(lazyListScope);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LazyListScope LazyColumn) {
                boolean AnalysisTab_3IgeMak$lambda$27;
                Intrinsics.checkNotNullParameter(LazyColumn, "$this$LazyColumn");
                final String str = scoreLabel;
                final long j = scoreColor;
                final Integer num = delta;
                final State<Float> state = sweep$delegate;
                final CompatibilityRecord compatibilityRecord = CompatibilityRecord.this;
                LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(-665690093, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num2) {
                        invoke(lazyItemScope, composer, num2.intValue());
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:24:0x01ff  */
                    /* JADX WARN: Removed duplicated region for block: B:27:0x020b  */
                    /* JADX WARN: Removed duplicated region for block: B:35:0x02ef  */
                    /* JADX WARN: Removed duplicated region for block: B:40:0x03c2  */
                    /* JADX WARN: Removed duplicated region for block: B:57:0x04bc  */
                    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:61:0x02fc A[ADDED_TO_REGION] */
                    /* JADX WARN: Removed duplicated region for block: B:64:0x0211  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public final void invoke(androidx.compose.foundation.lazy.LazyItemScope r73, androidx.compose.runtime.Composer r74, int r75) {
                        /*
                            Method dump skipped, instructions count: 1216
                            To view this dump add '--comments-level debug' option
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.AnonymousClass1.invoke(androidx.compose.foundation.lazy.LazyItemScope, androidx.compose.runtime.Composer, int):void");
                    }
                }), 3, null);
                if (!StringsKt.isBlank(CompatibilityRecord.this.getRoleSummary())) {
                    final CompatibilityRecord compatibilityRecord2 = CompatibilityRecord.this;
                    LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(22990104, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.2
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num2) {
                            invoke(lazyItemScope, composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                            Intrinsics.checkNotNullParameter(item, "$this$item");
                            ComposerKt.sourceInformation($composer3, "C810@37676L82,811@37775L40:ResultScreen.kt#fpoywd");
                            if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(22990104, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisTab.<anonymous>.<anonymous> (ResultScreen.kt:810)");
                                }
                                ResultScreenKt.AnalysisSectionCard("About this role", CompatibilityRecord.this.getRoleSummary(), $composer3, 6);
                                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(8)), $composer3, 6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer3.skipToGroupEnd();
                        }
                    }), 3, null);
                }
                if (!StringsKt.isBlank(CompatibilityRecord.this.getEligibilitySummary())) {
                    final CompatibilityRecord compatibilityRecord3 = CompatibilityRecord.this;
                    LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(1835810433, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.3
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num2) {
                            invoke(lazyItemScope, composer, num2.intValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                            Intrinsics.checkNotNullParameter(item, "$this$item");
                            ComposerKt.sourceInformation($composer3, "C816@37936L85,817@38038L41:ResultScreen.kt#fpoywd");
                            if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(1835810433, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisTab.<anonymous>.<anonymous> (ResultScreen.kt:816)");
                                }
                                ResultScreenKt.AnalysisSectionCard("How you fit", CompatibilityRecord.this.getEligibilitySummary(), $composer3, 6);
                                SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), $composer3, 6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                    return;
                                }
                                return;
                            }
                            $composer3.skipToGroupEnd();
                        }
                    }), 3, null);
                }
                final MutableState<Boolean> mutableState = showAdvanced$delegate;
                LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(1439104700, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num2) {
                        invoke(lazyItemScope, composer, num2.intValue());
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:27:0x024f  */
                    /* JADX WARN: Removed duplicated region for block: B:30:0x025b  */
                    /* JADX WARN: Removed duplicated region for block: B:38:0x0395  */
                    /* JADX WARN: Removed duplicated region for block: B:41:0x0405  */
                    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:44:0x03a0  */
                    /* JADX WARN: Removed duplicated region for block: B:47:0x0261  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public final void invoke(androidx.compose.foundation.lazy.LazyItemScope r100, androidx.compose.runtime.Composer r101, int r102) {
                        /*
                            Method dump skipped, instructions count: 1033
                            To view this dump add '--comments-level debug' option
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.AnonymousClass4.invoke(androidx.compose.foundation.lazy.LazyItemScope, androidx.compose.runtime.Composer, int):void");
                    }
                }), 3, null);
                AnalysisTab_3IgeMak$lambda$27 = ResultScreenKt.AnalysisTab_3IgeMak$lambda$27(showAdvanced$delegate);
                if (AnalysisTab_3IgeMak$lambda$27) {
                    if (CompatibilityRecord.this.getCriticalGapCount() > 0) {
                        final CompatibilityRecord compatibilityRecord4 = CompatibilityRecord.this;
                        LazyListScope.item$default(LazyColumn, null, null, ComposableLambdaKt.composableLambdaInstance(-1442803419, true, new Function3<LazyItemScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1.5
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Composer composer, Integer num2) {
                                invoke(lazyItemScope, composer, num2.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(LazyItemScope item, Composer $composer3, int $changed2) {
                                long m3748copywmQWz5c;
                                Intrinsics.checkNotNullParameter(item, "$this$item");
                                ComposerKt.sourceInformation($composer3, "C844@39349L41,845@39411L711:ResultScreen.kt#fpoywd");
                                if (($changed2 & 81) != 16 || !$composer3.getSkipping()) {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart(-1442803419, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisTab.<anonymous>.<anonymous> (ResultScreen.kt:844)");
                                    }
                                    SpacerKt.Spacer(SizeKt.m599height3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), $composer3, 6);
                                    Modifier m566paddingVpY3zN4$default = PaddingKt.m566paddingVpY3zN4$default(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), 0.0f, 2, null);
                                    m3748copywmQWz5c = Color.m3748copywmQWz5c(r5, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(r5) : 0.1f, (r12 & 2) != 0 ? Color.m3756getRedimpl(r5) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(r5) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(ColorKt.Color(4293212469L)) : 0.0f);
                                    RoundedCornerShape m834RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8));
                                    final CompatibilityRecord compatibilityRecord5 = CompatibilityRecord.this;
                                    SurfaceKt.m2318SurfaceT9BRK9s(m566paddingVpY3zN4$default, m834RoundedCornerShape0680j_4, m3748copywmQWz5c, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.composableLambda($composer3, -1598071702, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt.AnalysisTab.1.5.1
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num2) {
                                            invoke(composer, num2.intValue());
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(Composer $composer4, int $changed3) {
                                            ComposerKt.sourceInformation($composer4, "C850@39684L416:ResultScreen.kt#fpoywd");
                                            if (($changed3 & 11) != 2 || !$composer4.getSkipping()) {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart(-1598071702, $changed3, -1, "com.vantedge.app.ui.screens.AnalysisTab.<anonymous>.<anonymous>.<anonymous> (ResultScreen.kt:850)");
                                                }
                                                TextKt.m2466Text4IGK_g("You have " + CompatibilityRecord.this.getCriticalGapCount() + " critical " + (CompatibilityRecord.this.getCriticalGapCount() == 1 ? "gap" : "gaps") + " that this role specifically asks for.", PaddingKt.m564padding3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(12)), ColorKt.Color(4293212469L), TextUnitKt.getSp(13), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3504, 0, 131056);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                    return;
                                                }
                                                return;
                                            }
                                            $composer4.skipToGroupEnd();
                                        }
                                    }), $composer3, 12583302, 120);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                        return;
                                    }
                                    return;
                                }
                                $composer3.skipToGroupEnd();
                            }
                        }), 3, null);
                    }
                    if (!CompatibilityRecord.this.getGaps().isEmpty()) {
                        LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6517getLambda14$app_debug(), 3, null);
                        final List items$iv = CompatibilityRecord.this.getGaps();
                        final Function1 contentType$iv = new Function1() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$1
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                return invoke((GapItem) p1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Void invoke(GapItem gapItem) {
                                return null;
                            }
                        };
                        LazyColumn.items(items$iv.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Object invoke(Integer num2) {
                                return invoke(num2.intValue());
                            }

                            public final Object invoke(int index) {
                                return Function1.this.invoke(items$iv.get(index));
                            }
                        }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$4
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num2, Composer composer, Integer num3) {
                                invoke(lazyItemScope, num2.intValue(), composer, num3.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(LazyItemScope $this$items, int it, Composer $composer3, int $changed2) {
                                ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                                int $dirty = $changed2;
                                if (($changed2 & 14) == 0) {
                                    $dirty |= $composer3.changed($this$items) ? 4 : 2;
                                }
                                if (($changed2 & 112) == 0) {
                                    $dirty |= $composer3.changed(it) ? 32 : 16;
                                }
                                if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                    $composer3.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                                }
                                int i = $dirty & 14;
                                GapItem gap = (GapItem) items$iv.get(it);
                                ComposerKt.sourceInformationMarkerStart($composer3, 1682977253, "C*865@40539L12:ResultScreen.kt#fpoywd");
                                ResultScreenKt.GapCard(gap, $composer3, 8);
                                ComposerKt.sourceInformationMarkerEnd($composer3);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }));
                    }
                    if (!CompatibilityRecord.this.getRelevancyItems().isEmpty()) {
                        LazyListScope.item$default(LazyColumn, null, null, ComposableSingletons$ResultScreenKt.INSTANCE.m6518getLambda15$app_debug(), 3, null);
                        final List items$iv2 = CompatibilityRecord.this.getRelevancyItems();
                        final Function1 contentType$iv2 = new Function1() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$5
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                                return invoke((RelevancyItem) p1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Void invoke(RelevancyItem relevancyItem) {
                                return null;
                            }
                        };
                        LazyColumn.items(items$iv2.size(), null, new Function1<Integer, Object>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$7
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Object invoke(Integer num2) {
                                return invoke(num2.intValue());
                            }

                            public final Object invoke(int index) {
                                return Function1.this.invoke(items$iv2.get(index));
                            }
                        }, ComposableLambdaKt.composableLambdaInstance(-632812321, true, new Function4<LazyItemScope, Integer, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$1$invoke$$inlined$items$default$8
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public /* bridge */ /* synthetic */ Unit invoke(LazyItemScope lazyItemScope, Integer num2, Composer composer, Integer num3) {
                                invoke(lazyItemScope, num2.intValue(), composer, num3.intValue());
                                return Unit.INSTANCE;
                            }

                            public final void invoke(LazyItemScope $this$items, int it, Composer $composer3, int $changed2) {
                                long chipColor;
                                String groupLabel;
                                ComposerKt.sourceInformation($composer3, "C148@6730L22:LazyDsl.kt#428nma");
                                int $dirty = $changed2;
                                if (($changed2 & 14) == 0) {
                                    $dirty |= $composer3.changed($this$items) ? 4 : 2;
                                }
                                if (($changed2 & 112) == 0) {
                                    $dirty |= $composer3.changed(it) ? 32 : 16;
                                }
                                if (($dirty & 731) == 146 && $composer3.getSkipping()) {
                                    $composer3.skipToGroupEnd();
                                    return;
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart(-632812321, $dirty, -1, "androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:148)");
                                }
                                int i = $dirty & 14;
                                RelevancyItem relevancyItem = (RelevancyItem) items$iv2.get(it);
                                ComposerKt.sourceInformationMarkerStart($composer3, 1682977719, "C*883@41483L236:ResultScreen.kt#fpoywd");
                                String relevancyGroup = relevancyItem.getRelevancyGroup();
                                if (Intrinsics.areEqual(relevancyGroup, "HIGH")) {
                                    chipColor = ColorKt.Color(4283215696L);
                                } else if (Intrinsics.areEqual(relevancyGroup, "MEDIUM")) {
                                    chipColor = ResultScreenKt.RAmber;
                                } else {
                                    chipColor = Color.INSTANCE.m3780getGray0d7_KjU();
                                }
                                String relevancyGroup2 = relevancyItem.getRelevancyGroup();
                                if (Intrinsics.areEqual(relevancyGroup2, "HIGH")) {
                                    groupLabel = "Strong match";
                                } else {
                                    groupLabel = Intrinsics.areEqual(relevancyGroup2, "MEDIUM") ? "Moderate match" : "Weak match";
                                }
                                ResultScreenKt.m6555RelevancyCardqFjXxE8(relevancyItem.getName(), relevancyItem.getMatchPercent(), relevancyItem.getAiDescription(), groupLabel, chipColor, $composer3, 0);
                                ComposerKt.sourceInformationMarkerEnd($composer3);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }));
                    }
                }
            }
        }, $composer2, 390, 250);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisTab$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ResultScreenKt.m6553AnalysisTab3IgeMak(CompatibilityRecord.this, scoreColor, scoreLabel, delta, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float AnalysisTab_3IgeMak$lambda$25(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean AnalysisTab_3IgeMak$lambda$27(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void AnalysisTab_3IgeMak$lambda$28(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void AnalysisSectionCard(final String heading, String body, Composer $composer, final int $changed) {
        Composer $composer2;
        final String str;
        Composer $composer3 = $composer.startRestartGroup(825591601);
        ComposerKt.sourceInformation($composer3, "C(AnalysisSectionCard)P(1)895@41852L519:ResultScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(heading) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changed(body) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(825591601, $dirty2, -1, "com.vantedge.app.ui.screens.AnalysisSectionCard (ResultScreen.kt:894)");
            }
            Modifier modifier$iv = PaddingKt.m566paddingVpY3zN4$default(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), 0.0f, 2, null);
            $composer3.startReplaceableGroup(-483455358);
            ComposerKt.sourceInformation($composer3, "CC(Column)P(2,3,1)77@3865L61,78@3931L133:Column.kt#2w3rfo");
            Arrangement.Vertical verticalArrangement$iv = Arrangement.INSTANCE.getTop();
            Alignment.Horizontal horizontalAlignment$iv = Alignment.INSTANCE.getStart();
            MeasurePolicy measurePolicy$iv = ColumnKt.columnMeasurePolicy(verticalArrangement$iv, horizontalAlignment$iv, $composer3, ((6 >> 3) & 14) | ((6 >> 3) & 112));
            int $changed$iv$iv = (6 << 3) & 112;
            $composer3.startReplaceableGroup(-1323940314);
            ComposerKt.sourceInformation($composer3, "CC(Layout)P(!1,2)78@3182L23,80@3272L420:Layout.kt#80mrfh");
            int compositeKeyHash$iv$iv = ComposablesKt.getCurrentCompositeKeyHash($composer3, 0);
            CompositionLocalMap localMap$iv$iv = $composer3.getCurrentCompositionLocalMap();
            Function0 factory$iv$iv$iv = ComposeUiNode.INSTANCE.getConstructor();
            Function3 skippableUpdate$iv$iv$iv = LayoutKt.modifierMaterializerOf(modifier$iv);
            int $changed$iv$iv$iv = (($changed$iv$iv << 9) & 7168) | 6;
            if (!($composer3.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer3.startReusableNode();
            if ($composer3.getInserting()) {
                $composer3.createNode(factory$iv$iv$iv);
            } else {
                $composer3.useNode();
            }
            Composer $this$Layout_u24lambda_u240$iv$iv = Updater.m3280constructorimpl($composer3);
            Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, measurePolicy$iv, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m3287setimpl($this$Layout_u24lambda_u240$iv$iv, localMap$iv$iv, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2 block$iv$iv$iv = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if ($this$Layout_u24lambda_u240$iv$iv.getInserting() || !Intrinsics.areEqual($this$Layout_u24lambda_u240$iv$iv.rememberedValue(), Integer.valueOf(compositeKeyHash$iv$iv))) {
                $this$Layout_u24lambda_u240$iv$iv.updateRememberedValue(Integer.valueOf(compositeKeyHash$iv$iv));
                $this$Layout_u24lambda_u240$iv$iv.apply(Integer.valueOf(compositeKeyHash$iv$iv), block$iv$iv$iv);
            }
            skippableUpdate$iv$iv$iv.invoke(SkippableUpdater.m3271boximpl(SkippableUpdater.m3272constructorimpl($composer3)), $composer3, Integer.valueOf(($changed$iv$iv$iv >> 3) & 112));
            $composer3.startReplaceableGroup(2058660585);
            int i = ($changed$iv$iv$iv >> 9) & 14;
            ComposerKt.sourceInformationMarkerStart($composer3, 276693656, "C79@3979L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i2 = ((6 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer3, 68192694, "C896@41933L120,899@42150L34,897@42062L303:ResultScreen.kt#fpoywd");
            $composer2 = $composer3;
            TextKt.m2466Text4IGK_g(heading, PaddingKt.m568paddingqDBjuR0$default(Modifier.INSTANCE, 0.0f, 0.0f, 0.0f, Dp.m6092constructorimpl(6), 7, null), RTeal, TextUnitKt.getSp(13), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, ($dirty2 & 14) | 200112, 0, 131024);
            str = body;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(RCard, 0L, 0L, 0L, $composer3, (CardDefaults.$stable << 12) | 6, 14), null, null, ComposableLambdaKt.composableLambda($composer3, 1619222809, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisSectionCard$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(ColumnScope Card, Composer $composer4, int $changed2) {
                    long j;
                    Intrinsics.checkNotNullParameter(Card, "$this$Card");
                    ComposerKt.sourceInformation($composer4, "C902@42256L99:ResultScreen.kt#fpoywd");
                    if (($changed2 & 81) != 16 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(1619222809, $changed2, -1, "com.vantedge.app.ui.screens.AnalysisSectionCard.<anonymous>.<anonymous> (ResultScreen.kt:902)");
                        }
                        j = ResultScreenKt.ROnBg;
                        TextKt.m2466Text4IGK_g(str, PaddingKt.m564padding3ABfNKs(Modifier.INSTANCE, Dp.m6092constructorimpl(14)), j, TextUnitKt.getSp(14), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, TextUnitKt.getSp(22), 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer4, 3504, 6, 130032);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer4.skipToGroupEnd();
                }
            }), $composer3, 196614, 24);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            ComposerKt.sourceInformationMarkerEnd($composer3);
            $composer2.endReplaceableGroup();
            $composer2.endNode();
            $composer2.endReplaceableGroup();
            $composer2.endReplaceableGroup();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
            str = body;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$AnalysisSectionCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i3) {
                    ResultScreenKt.AnalysisSectionCard(heading, str, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void GapCard(final GapItem gap, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Composer $composer2 = $composer.startRestartGroup(-1979378757);
        ComposerKt.sourceInformation($composer2, "C(GapCard)910@42494L7,921@42841L34,924@43005L34,926@43102L24,922@42880L3136:ResultScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1979378757, $changed, -1, "com.vantedge.app.ui.screens.GapCard (ResultScreen.kt:909)");
        }
        ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
        ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
        Object consume = $composer2.consume(localContext);
        ComposerKt.sourceInformationMarkerEnd($composer2);
        final Context context = (Context) consume;
        String importance = gap.getImportance();
        final long importanceColor = Intrinsics.areEqual(importance, "CRITICAL") ? ColorKt.Color(4293212469L) : Intrinsics.areEqual(importance, "IMPORTANT") ? RAmber : Color.INSTANCE.m3780getGray0d7_KjU();
        String importance2 = gap.getImportance();
        final String importanceLabel = Intrinsics.areEqual(importance2, "CRITICAL") ? "Critical gap" : Intrinsics.areEqual(importance2, "IMPORTANT") ? "Worth addressing" : "Nice to have";
        $composer2.startReplaceableGroup(1948709460);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState expanded$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        Modifier m565paddingVpY3zN4 = PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(4));
        CardColors m1628cardColorsro_MJ88 = CardDefaults.INSTANCE.m1628cardColorsro_MJ88(RCard, 0L, 0L, 0L, $composer2, (CardDefaults.$stable << 12) | 6, 14);
        RoundedCornerShape m834RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10));
        $composer2.startReplaceableGroup(1948709721);
        ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$GapCard$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    boolean GapCard$lambda$31;
                    MutableState<Boolean> mutableState = expanded$delegate;
                    GapCard$lambda$31 = ResultScreenKt.GapCard$lambda$31(expanded$delegate);
                    ResultScreenKt.GapCard$lambda$32(mutableState, !GapCard$lambda$31);
                }
            };
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        $composer2.endReplaceableGroup();
        CardKt.Card((Function0) value$iv2, m565paddingVpY3zN4, false, m834RoundedCornerShape0680j_4, m1628cardColorsro_MJ88, null, null, null, ComposableLambdaKt.composableLambda($composer2, 1726796080, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$GapCard$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                invoke(columnScope, composer, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:24:0x01f0  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x01fc  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x0235  */
            /* JADX WARN: Removed duplicated region for block: B:35:0x0324  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x0330  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0363  */
            /* JADX WARN: Removed duplicated region for block: B:46:0x048f  */
            /* JADX WARN: Removed duplicated region for block: B:49:0x04e3  */
            /* JADX WARN: Removed duplicated region for block: B:69:0x067a  */
            /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:73:0x0658  */
            /* JADX WARN: Removed duplicated region for block: B:74:0x049a  */
            /* JADX WARN: Removed duplicated region for block: B:76:0x0379  */
            /* JADX WARN: Removed duplicated region for block: B:77:0x0334  */
            /* JADX WARN: Removed duplicated region for block: B:79:0x024b  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0202  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke(androidx.compose.foundation.layout.ColumnScope r107, androidx.compose.runtime.Composer r108, int r109) {
                /*
                    Method dump skipped, instructions count: 1662
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$GapCard$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
            }
        }), $composer2, 100663350, 228);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$GapCard$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ResultScreenKt.GapCard(GapItem.this, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean GapCard$lambda$31(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void GapCard$lambda$32(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: RelevancyCard-qFjXxE8, reason: not valid java name */
    public static final void m6555RelevancyCardqFjXxE8(final String name, final int matchPct, final String description, final String groupLabel, final long color, Composer $composer, final int $changed) {
        int $dirty;
        Object value$iv;
        long m3748copywmQWz5c;
        Object value$iv2;
        Composer $composer2 = $composer.startRestartGroup(-923273338);
        ComposerKt.sourceInformation($composer2, "C(RelevancyCard)P(4,3,1,2,0:c#ui.graphics.Color)979@46204L34,982@46368L54,984@46484L24,980@46243L1097:ResultScreen.kt#fpoywd");
        int $dirty2 = $changed;
        if (($changed & 14) == 0) {
            $dirty2 |= $composer2.changed(name) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty2 |= $composer2.changed(matchPct) ? 32 : 16;
        }
        if (($changed & 896) == 0) {
            $dirty2 |= $composer2.changed(description) ? 256 : 128;
        }
        if (($changed & 7168) == 0) {
            $dirty2 |= $composer2.changed(groupLabel) ? 2048 : 1024;
        }
        if ((57344 & $changed) == 0) {
            $dirty2 |= $composer2.changed(color) ? 16384 : 8192;
        }
        if ((46811 & $dirty2) == 9362 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            $dirty = $dirty2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-923273338, $dirty2, -1, "com.vantedge.app.ui.screens.RelevancyCard (ResultScreen.kt:978)");
            }
            $composer2.startReplaceableGroup(1213755520);
            ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
            Object it$iv = $composer2.rememberedValue();
            $dirty = $dirty2;
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
                $composer2.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState expanded$delegate = (MutableState) value$iv;
            $composer2.endReplaceableGroup();
            Modifier m565paddingVpY3zN4 = PaddingKt.m565paddingVpY3zN4(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), Dp.m6092constructorimpl(16), Dp.m6092constructorimpl(3));
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            m3748copywmQWz5c = Color.m3748copywmQWz5c(color, (r12 & 1) != 0 ? Color.m3752getAlphaimpl(color) : 0.08f, (r12 & 2) != 0 ? Color.m3756getRedimpl(color) : 0.0f, (r12 & 4) != 0 ? Color.m3755getGreenimpl(color) : 0.0f, (r12 & 8) != 0 ? Color.m3753getBlueimpl(color) : 0.0f);
            CardColors m1628cardColorsro_MJ88 = cardDefaults.m1628cardColorsro_MJ88(m3748copywmQWz5c, 0L, 0L, 0L, $composer2, CardDefaults.$stable << 12, 14);
            RoundedCornerShape m834RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(8));
            $composer2.startReplaceableGroup(1213755800);
            ComposerKt.sourceInformation($composer2, "CC(remember):ResultScreen.kt#9igjgp");
            Object it$iv2 = $composer2.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                value$iv2 = new Function0<Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$RelevancyCard$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        boolean RelevancyCard_qFjXxE8$lambda$35;
                        MutableState<Boolean> mutableState = expanded$delegate;
                        RelevancyCard_qFjXxE8$lambda$35 = ResultScreenKt.RelevancyCard_qFjXxE8$lambda$35(expanded$delegate);
                        ResultScreenKt.RelevancyCard_qFjXxE8$lambda$36(mutableState, !RelevancyCard_qFjXxE8$lambda$35);
                    }
                };
                $composer2.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            $composer2.endReplaceableGroup();
            CardKt.Card((Function0) value$iv2, m565paddingVpY3zN4, false, m834RoundedCornerShape0680j_4, m1628cardColorsro_MJ88, null, null, null, ComposableLambdaKt.composableLambda($composer2, 319801905, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$RelevancyCard$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:24:0x01fa  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0206  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x023f  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0330  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x033c  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x0375  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x04e9  */
                /* JADX WARN: Removed duplicated region for block: B:51:0x055b  */
                /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:55:0x038b  */
                /* JADX WARN: Removed duplicated region for block: B:56:0x0342  */
                /* JADX WARN: Removed duplicated region for block: B:58:0x0255  */
                /* JADX WARN: Removed duplicated region for block: B:59:0x020c  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r106, androidx.compose.runtime.Composer r107, int r108) {
                    /*
                        Method dump skipped, instructions count: 1375
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$RelevancyCard$2.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 100663350, 228);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ResultScreenKt$RelevancyCard$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer, int i) {
                    ResultScreenKt.m6555RelevancyCardqFjXxE8(name, matchPct, description, groupLabel, color, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean RelevancyCard_qFjXxE8$lambda$35(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void RelevancyCard_qFjXxE8$lambda$36(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
