package com.vantedge.app.ui.screens;

import android.webkit.WebView;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import androidx.navigation.NavController;
import com.vantedge.app.data.viewmodel.ActionType;
import com.vantedge.app.data.viewmodel.ContentState;
import com.vantedge.app.data.viewmodel.CycleViewModel;
import com.vantedge.app.data.viewmodel.ExportState;
import com.vantedge.app.data.viewmodel.ResultViewState;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: ResultScreen.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u000b¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "invoke", "(Landroidx/compose/foundation/layout/PaddingValues;Landroidx/compose/runtime/Composer;I)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
final class ResultScreenKt$ResultScreen$5 extends Lambda implements Function3<PaddingValues, Composer, Integer, Unit> {
    final /* synthetic */ Set<ActionType> $actions;
    final /* synthetic */ ContentState $content;
    final /* synthetic */ State<ExportState> $exportState$delegate;
    final /* synthetic */ boolean $isHistorical;
    final /* synthetic */ ResultScreenMode $mode;
    final /* synthetic */ NavController $navController;
    final /* synthetic */ CoroutineScope $scope;
    final /* synthetic */ long $scoreColor;
    final /* synthetic */ String $scoreLabel;
    final /* synthetic */ MutableState<Integer> $selectedTab$delegate;
    final /* synthetic */ ResultViewState $state;
    final /* synthetic */ List<TabInfo> $tabs;
    final /* synthetic */ CycleViewModel $viewModel;
    final /* synthetic */ MutableState<WebView> $webViewRef$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    ResultScreenKt$ResultScreen$5(ResultViewState resultViewState, CycleViewModel cycleViewModel, boolean z, Set<? extends ActionType> set, ResultScreenMode resultScreenMode, ContentState contentState, List<? extends TabInfo> list, long j, String str, MutableState<Integer> mutableState, MutableState<WebView> mutableState2, CoroutineScope coroutineScope, NavController navController, State<? extends ExportState> state) {
        super(3);
        this.$state = resultViewState;
        this.$viewModel = cycleViewModel;
        this.$isHistorical = z;
        this.$actions = set;
        this.$mode = resultScreenMode;
        this.$content = contentState;
        this.$tabs = list;
        this.$scoreColor = j;
        this.$scoreLabel = str;
        this.$selectedTab$delegate = mutableState;
        this.$webViewRef$delegate = mutableState2;
        this.$scope = coroutineScope;
        this.$navController = navController;
        this.$exportState$delegate = state;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
        invoke(paddingValues, composer, num.intValue());
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0922  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x09d1  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0a22  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0a6e  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0acf  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0a08  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x09c0  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x051d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void invoke(androidx.compose.foundation.layout.PaddingValues r104, androidx.compose.runtime.Composer r105, int r106) {
        /*
            Method dump skipped, instructions count: 2771
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ResultScreenKt$ResultScreen$5.invoke(androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float invoke$lambda$12$lambda$7$lambda$2$lambda$0(State<Float> state) {
        Object thisObj$iv = state.getValue();
        return ((Number) thisObj$iv).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String invoke$lambda$12$lambda$8(State<String> state) {
        Object thisObj$iv = state.getValue();
        return (String) thisObj$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean invoke$lambda$12$lambda$10(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$12$lambda$11(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }
}
