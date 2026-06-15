package com.vantedge.app.ui.screens;

import androidx.autofill.HintConstants;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.EnterTransition;
import androidx.compose.animation.ExitTransition;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.tom_roush.fontbox.ttf.OS2WindowsMetricsTable;
import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.UserProfile;
import com.vantedge.app.data.model.WorkExperience;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.i18n.MessageBundle;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: EditProfileScreen.kt */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001aL\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001aX\u0010\u0013\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00032\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010\u000f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000fH\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a+\u0010\u001b\u001a\u00020\u00012\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00010\u000fH\u0007¢\u0006\u0002\u0010\u001f\u001a;\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u00032\u001c\u0010#\u001a\u0018\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00010\u000f¢\u0006\u0002\b%¢\u0006\u0002\b&H\u0003¢\u0006\u0002\u0010'\u001a;\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010+\u001a\u00020\u0017H\u0003¢\u0006\u0002\u0010,\u001aL\u0010-\u001a\u00020\u00012\u0006\u0010.\u001a\u00020/2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020/\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b0\u00101\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u00062²\u0006\n\u00103\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u00104\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u00105\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u00106\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u00107\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u00108\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\u00030:X\u008a\u008e\u0002²\u0006\u0010\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00030:X\u008a\u008e\u0002²\u0006\u0010\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00030:X\u008a\u008e\u0002²\u0006\u0010\u0010=\u001a\b\u0012\u0004\u0012\u00020/0:X\u008a\u008e\u0002²\u0006\u0010\u0010>\u001a\b\u0012\u0004\u0012\u00020\f0:X\u008a\u008e\u0002²\u0006\n\u0010?\u001a\u00020@X\u008a\u008e\u0002²\u0006\n\u0010A\u001a\u00020\u0003X\u008a\u008e\u0002"}, d2 = {"EditAddButton", "", AnnotatedPrivateKey.LABEL, "", "teal", "Landroidx/compose/ui/graphics/Color;", "onClick", "Lkotlin/Function0;", "EditAddButton-iJQMabo", "(Ljava/lang/String;JLkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "EditCertCard", "cert", "Lcom/vantedge/app/data/model/Certification;", "cardDark", "onUpdate", "Lkotlin/Function1;", "onRemove", "EditCertCard-eopBjH0", "(Lcom/vantedge/app/data/model/Certification;JJLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "EditChipList", "items", "", "placeholder", "", "onAdd", "EditChipList-sW7UJKQ", "(Ljava/util/List;JLjava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Landroidx/compose/runtime/Composer;I)V", "EditProfileScreen", "initialProfile", "Lcom/vantedge/app/data/model/UserProfile;", "onContinue", "(Lcom/vantedge/app/data/model/UserProfile;Lkotlin/jvm/functions/Function1;Landroidx/compose/runtime/Composer;I)V", "EditSection", MessageBundle.TITLE_ENTRY, "icon", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;I)V", "EditTextField", "value", "onValueChange", "minLines", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;ILandroidx/compose/runtime/Composer;II)V", "EditWorkCard", "experience", "Lcom/vantedge/app/data/model/WorkExperience;", "EditWorkCard-eopBjH0", "(Lcom/vantedge/app/data/model/WorkExperience;JJLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug", "name", "email", HintConstants.AUTOFILL_HINT_PHONE, "location", "linkedIn", ErrorBundle.SUMMARY_ENTRY, "skills", "", "languages", "education", "workHistory", "certifications", "showContent", "", "inputValue"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class EditProfileScreenKt {
    public static final void EditProfileScreen(final UserProfile initialProfile, final Function1<? super UserProfile, Unit> onContinue, Composer $composer, final int $changed) {
        Object value$iv;
        Object value$iv2;
        Object value$iv3;
        Object value$iv4;
        Object value$iv5;
        Object value$iv6;
        Object value$iv7;
        Object value$iv8;
        Object value$iv9;
        Object value$iv10;
        Object value$iv11;
        Object value$iv12;
        EditProfileScreenKt$EditProfileScreen$1$1 value$iv13;
        ArrayList arrayList;
        List<Certification> certifications;
        ArrayList arrayList2;
        List<WorkExperience> workHistory;
        ArrayList arrayList3;
        List<String> education;
        ArrayList arrayList4;
        List<String> languages;
        ArrayList arrayList5;
        List<String> skills;
        String summary;
        String linkedIn;
        String location;
        String phone;
        String email;
        String name;
        Intrinsics.checkNotNullParameter(onContinue, "onContinue");
        Composer $composer2 = $composer.startRestartGroup(159767832);
        ComposerKt.sourceInformation($composer2, "C(EditProfileScreen)40@1565L55,41@1638L56,42@1712L56,43@1789L59,44@1869L59,45@1948L58,47@2026L99,50@2147L102,53@2271L102,56@2397L104,59@2528L107,66@2804L34,67@2864L22,67@2843L43,69@2892L9436:EditProfileScreen.kt#fpoywd");
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(159767832, $changed, -1, "com.vantedge.app.ui.screens.EditProfileScreen (EditProfileScreen.kt:34)");
        }
        final long teal = ColorKt.Color(4278239141L);
        final long bg = ColorKt.Color(4279045389L);
        final long cardDark = ColorKt.Color(4279900718L);
        $composer2.startReplaceableGroup(-1102056973);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv = $composer2.rememberedValue();
        String str = "";
        if (it$iv == Composer.INSTANCE.getEmpty()) {
            value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((initialProfile == null || (name = initialProfile.getName()) == null) ? "" : name, null, 2, null);
            $composer2.updateRememberedValue(value$iv);
        } else {
            value$iv = it$iv;
        }
        final MutableState name$delegate = (MutableState) value$iv;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056900);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv2 = $composer2.rememberedValue();
        if (it$iv2 == Composer.INSTANCE.getEmpty()) {
            value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((initialProfile == null || (email = initialProfile.getEmail()) == null) ? "" : email, null, 2, null);
            $composer2.updateRememberedValue(value$iv2);
        } else {
            value$iv2 = it$iv2;
        }
        final MutableState email$delegate = (MutableState) value$iv2;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056826);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv3 = $composer2.rememberedValue();
        if (it$iv3 == Composer.INSTANCE.getEmpty()) {
            value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((initialProfile == null || (phone = initialProfile.getPhone()) == null) ? "" : phone, null, 2, null);
            $composer2.updateRememberedValue(value$iv3);
        } else {
            value$iv3 = it$iv3;
        }
        final MutableState phone$delegate = (MutableState) value$iv3;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056749);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv4 = $composer2.rememberedValue();
        if (it$iv4 == Composer.INSTANCE.getEmpty()) {
            value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((initialProfile == null || (location = initialProfile.getLocation()) == null) ? "" : location, null, 2, null);
            $composer2.updateRememberedValue(value$iv4);
        } else {
            value$iv4 = it$iv4;
        }
        final MutableState location$delegate = (MutableState) value$iv4;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056669);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv5 = $composer2.rememberedValue();
        if (it$iv5 == Composer.INSTANCE.getEmpty()) {
            value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((initialProfile == null || (linkedIn = initialProfile.getLinkedIn()) == null) ? "" : linkedIn, null, 2, null);
            $composer2.updateRememberedValue(value$iv5);
        } else {
            value$iv5 = it$iv5;
        }
        final MutableState linkedIn$delegate = (MutableState) value$iv5;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056590);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv6 = $composer2.rememberedValue();
        if (it$iv6 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile != null && (summary = initialProfile.getSummary()) != null) {
                str = summary;
            }
            value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(str, null, 2, null);
            $composer2.updateRememberedValue(value$iv6);
        } else {
            value$iv6 = it$iv6;
        }
        final MutableState summary$delegate = (MutableState) value$iv6;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056512);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv7 = $composer2.rememberedValue();
        if (it$iv7 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile == null || (skills = initialProfile.getSkills()) == null || (arrayList5 = CollectionsKt.toMutableList((Collection) skills)) == null) {
                arrayList5 = new ArrayList();
            }
            value$iv7 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(arrayList5, null, 2, null);
            $composer2.updateRememberedValue(value$iv7);
        } else {
            value$iv7 = it$iv7;
        }
        final MutableState skills$delegate = (MutableState) value$iv7;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056391);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv8 = $composer2.rememberedValue();
        if (it$iv8 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile == null || (languages = initialProfile.getLanguages()) == null || (arrayList4 = CollectionsKt.toMutableList((Collection) languages)) == null) {
                arrayList4 = new ArrayList();
            }
            value$iv8 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(arrayList4, null, 2, null);
            $composer2.updateRememberedValue(value$iv8);
        } else {
            value$iv8 = it$iv8;
        }
        final MutableState languages$delegate = (MutableState) value$iv8;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056267);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv9 = $composer2.rememberedValue();
        if (it$iv9 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile == null || (education = initialProfile.getEducation()) == null || (arrayList3 = CollectionsKt.toMutableList((Collection) education)) == null) {
                arrayList3 = new ArrayList();
            }
            value$iv9 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(arrayList3, null, 2, null);
            $composer2.updateRememberedValue(value$iv9);
        } else {
            value$iv9 = it$iv9;
        }
        final MutableState education$delegate = (MutableState) value$iv9;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056141);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv10 = $composer2.rememberedValue();
        if (it$iv10 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile == null || (workHistory = initialProfile.getWorkHistory()) == null || (arrayList2 = CollectionsKt.toMutableList((Collection) workHistory)) == null) {
                arrayList2 = new ArrayList();
            }
            value$iv10 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(arrayList2, null, 2, null);
            $composer2.updateRememberedValue(value$iv10);
        } else {
            value$iv10 = it$iv10;
        }
        final MutableState workHistory$delegate = (MutableState) value$iv10;
        $composer2.endReplaceableGroup();
        $composer2.startReplaceableGroup(-1102056010);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv11 = $composer2.rememberedValue();
        if (it$iv11 == Composer.INSTANCE.getEmpty()) {
            if (initialProfile == null || (certifications = initialProfile.getCertifications()) == null || (arrayList = CollectionsKt.toMutableList((Collection) certifications)) == null) {
                arrayList = new ArrayList();
            }
            value$iv11 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(arrayList, null, 2, null);
            $composer2.updateRememberedValue(value$iv11);
        } else {
            value$iv11 = it$iv11;
        }
        final MutableState certifications$delegate = (MutableState) value$iv11;
        $composer2.endReplaceableGroup();
        final boolean canContinue = (StringsKt.isBlank(EditProfileScreen$lambda$1(name$delegate)) ^ true) && (StringsKt.isBlank(EditProfileScreen$lambda$4(email$delegate)) ^ true) && (EditProfileScreen$lambda$19(skills$delegate).isEmpty() ^ true);
        $composer2.startReplaceableGroup(-1102055734);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv12 = $composer2.rememberedValue();
        if (it$iv12 == Composer.INSTANCE.getEmpty()) {
            value$iv12 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
            $composer2.updateRememberedValue(value$iv12);
        } else {
            value$iv12 = it$iv12;
        }
        final MutableState showContent$delegate = (MutableState) value$iv12;
        $composer2.endReplaceableGroup();
        Unit unit = Unit.INSTANCE;
        $composer2.startReplaceableGroup(-1102055674);
        ComposerKt.sourceInformation($composer2, "CC(remember):EditProfileScreen.kt#9igjgp");
        Object it$iv13 = $composer2.rememberedValue();
        if (it$iv13 == Composer.INSTANCE.getEmpty()) {
            value$iv13 = new EditProfileScreenKt$EditProfileScreen$1$1(showContent$delegate, null);
            $composer2.updateRememberedValue(value$iv13);
        } else {
            value$iv13 = it$iv13;
        }
        $composer2.endReplaceableGroup();
        EffectsKt.LaunchedEffect(unit, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) value$iv13, $composer2, 70);
        ScaffoldKt.m2121ScaffoldTvnljyQ(null, ComposableLambdaKt.composableLambda($composer2, 1763874004, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                invoke(composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer $composer3, int $changed2) {
                ComposerKt.sourceInformation($composer3, "C81@3299L36,72@2962L387:EditProfileScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(1763874004, $changed2, -1, "com.vantedge.app.ui.screens.EditProfileScreen.<anonymous> (EditProfileScreen.kt:72)");
                    }
                    AppBarKt.TopAppBar(ComposableSingletons$EditProfileScreenKt.INSTANCE.m6487getLambda1$app_debug(), null, null, null, null, TopAppBarDefaults.INSTANCE.m2625topAppBarColorszjMxDiM(bg, 0L, 0L, 0L, 0L, $composer3, (TopAppBarDefaults.$stable << 15) | 6, 30), null, $composer3, 6, 94);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), ComposableLambdaKt.composableLambda($composer2, -1408814093, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$3
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
                ComposerKt.sourceInformation($composer3, "C85@3395L2076:EditProfileScreen.kt#fpoywd");
                if (($changed2 & 11) != 2 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1408814093, $changed2, -1, "com.vantedge.app.ui.screens.EditProfileScreen.<anonymous> (EditProfileScreen.kt:85)");
                    }
                    boolean z = canContinue;
                    EnterTransition plus = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), 0.0f, 2, null).plus(EnterExitTransitionKt.slideInVertically(AnimationSpecKt.tween$default(OS2WindowsMetricsTable.WEIGHT_CLASS_NORMAL, 0, null, 6, null), new Function1<Integer, Integer>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$3.1
                        public final Integer invoke(int it) {
                            return Integer.valueOf(it);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Integer invoke(Integer num) {
                            return invoke(num.intValue());
                        }
                    }));
                    final long j = bg;
                    final Function1<UserProfile, Unit> function1 = onContinue;
                    final long j2 = teal;
                    final MutableState<String> mutableState = name$delegate;
                    final MutableState<String> mutableState2 = email$delegate;
                    final MutableState<String> mutableState3 = phone$delegate;
                    final MutableState<String> mutableState4 = location$delegate;
                    final MutableState<String> mutableState5 = linkedIn$delegate;
                    final MutableState<String> mutableState6 = summary$delegate;
                    final MutableState<List<String>> mutableState7 = skills$delegate;
                    final MutableState<List<String>> mutableState8 = languages$delegate;
                    final MutableState<List<String>> mutableState9 = education$delegate;
                    final MutableState<List<WorkExperience>> mutableState10 = workHistory$delegate;
                    final MutableState<List<Certification>> mutableState11 = certifications$delegate;
                    AnimatedVisibilityKt.AnimatedVisibility(z, (Modifier) null, plus, (ExitTransition) null, (String) null, ComposableLambdaKt.composableLambda($composer3, 1058828315, true, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$3.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        /* JADX WARN: Multi-variable type inference failed */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
                            invoke(animatedVisibilityScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:22:0x029b  */
                        /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r65, androidx.compose.runtime.Composer r66, int r67) {
                            /*
                                Method dump skipped, instructions count: 671
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$3.AnonymousClass2.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
                        }
                    }), $composer3, 196992, 26);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), null, null, 0, bg, 0L, null, ComposableLambdaKt.composableLambda($composer2, -1233011095, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                invoke(paddingValues, composer, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(final PaddingValues padding, Composer $composer3, int $changed2) {
                boolean EditProfileScreen$lambda$34;
                Intrinsics.checkNotNullParameter(padding, "padding");
                ComposerKt.sourceInformation($composer3, "C132@5510L6812:EditProfileScreen.kt#fpoywd");
                int $dirty = $changed2;
                if (($changed2 & 14) == 0) {
                    $dirty |= $composer3.changed(padding) ? 4 : 2;
                }
                int $dirty2 = $dirty;
                if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-1233011095, $dirty2, -1, "com.vantedge.app.ui.screens.EditProfileScreen.<anonymous> (EditProfileScreen.kt:132)");
                    }
                    EditProfileScreen$lambda$34 = EditProfileScreenKt.EditProfileScreen$lambda$34(showContent$delegate);
                    EnterTransition fadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(500, 0, null, 6, null), 0.0f, 2, null);
                    final MutableState<String> mutableState = name$delegate;
                    final MutableState<String> mutableState2 = email$delegate;
                    final MutableState<String> mutableState3 = phone$delegate;
                    final MutableState<String> mutableState4 = location$delegate;
                    final MutableState<String> mutableState5 = linkedIn$delegate;
                    final MutableState<String> mutableState6 = summary$delegate;
                    final long j = teal;
                    final MutableState<List<String>> mutableState7 = skills$delegate;
                    final MutableState<List<WorkExperience>> mutableState8 = workHistory$delegate;
                    final long j2 = cardDark;
                    final MutableState<List<String>> mutableState9 = education$delegate;
                    final MutableState<List<Certification>> mutableState10 = certifications$delegate;
                    final MutableState<List<String>> mutableState11 = languages$delegate;
                    AnimatedVisibilityKt.AnimatedVisibility(EditProfileScreen$lambda$34, (Modifier) null, fadeIn$default, (ExitTransition) null, (String) null, ComposableLambdaKt.composableLambda($composer3, -747273407, true, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$4.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
                            invoke(animatedVisibilityScope, composer, num.intValue());
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:17:0x0224  */
                        /* JADX WARN: Removed duplicated region for block: B:20:0x0230  */
                        /* JADX WARN: Removed duplicated region for block: B:28:0x0439  */
                        /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:34:0x0236  */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct add '--show-bad-code' argument
                        */
                        public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r114, androidx.compose.runtime.Composer r115, int r116) {
                            /*
                                Method dump skipped, instructions count: 1085
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$4.AnonymousClass1.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
                        }
                    }), $composer3, 196992, 26);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                        return;
                    }
                    return;
                }
                $composer3.skipToGroupEnd();
            }
        }), $composer2, 806879664, 441);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditProfileScreen$5
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

                public final void invoke(Composer composer, int i) {
                    EditProfileScreenKt.EditProfileScreen(UserProfile.this, onContinue, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$1(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$4(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$7(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$10(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$13(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditProfileScreen$lambda$16(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> EditProfileScreen$lambda$19(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> EditProfileScreen$lambda$22(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> EditProfileScreen$lambda$25(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<WorkExperience> EditProfileScreen$lambda$28(MutableState<List<WorkExperience>> mutableState) {
        MutableState<List<WorkExperience>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Certification> EditProfileScreen$lambda$31(MutableState<List<Certification>> mutableState) {
        MutableState<List<Certification>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean EditProfileScreen$lambda$34(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void EditProfileScreen$lambda$35(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0393  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void EditSection(final java.lang.String r77, final java.lang.String r78, kotlin.jvm.functions.Function3<? super androidx.compose.foundation.layout.ColumnScope, ? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r79, androidx.compose.runtime.Composer r80, final int r81) {
        /*
            Method dump skipped, instructions count: 948
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt.EditSection(java.lang.String, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void EditTextField(final String value, final String label, final Function1<? super String, Unit> function1, int minLines, Composer $composer, final int $changed, final int i) {
        int i2;
        int minLines2;
        Composer $composer2 = $composer.startRestartGroup(-2102228369);
        ComposerKt.sourceInformation($composer2, "C(EditTextField)P(3!1,2)322@13408L361,316@13160L658:EditProfileScreen.kt#fpoywd");
        int $dirty = $changed;
        if ((i & 1) != 0) {
            $dirty |= 6;
        } else if (($changed & 14) == 0) {
            $dirty |= $composer2.changed(value) ? 4 : 2;
        }
        if ((i & 2) != 0) {
            $dirty |= 48;
        } else if (($changed & 112) == 0) {
            $dirty |= $composer2.changed(label) ? 32 : 16;
        }
        if ((i & 4) != 0) {
            $dirty |= KyberEngine.KyberPolyBytes;
        } else if (($changed & 896) == 0) {
            $dirty |= $composer2.changedInstance(function1) ? 256 : 128;
        }
        int i3 = i & 8;
        if (i3 != 0) {
            $dirty |= 3072;
            i2 = minLines;
        } else if (($changed & 7168) == 0) {
            i2 = minLines;
            $dirty |= $composer2.changed(i2) ? 2048 : 1024;
        } else {
            i2 = minLines;
        }
        if (($dirty & 5851) == 1170 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
            minLines2 = i2;
        } else {
            int minLines3 = i3 != 0 ? 1 : i2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-2102228369, $dirty, -1, "com.vantedge.app.ui.screens.EditTextField (EditProfileScreen.kt:315)");
            }
            OutlinedTextFieldKt.OutlinedTextField(value, function1, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableLambdaKt.composableLambda($composer2, -425208299, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditTextField$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
                    invoke(composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer $composer3, int $changed2) {
                    ComposerKt.sourceInformation($composer3, "C319@13259L29:EditProfileScreen.kt#fpoywd");
                    if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(-425208299, $changed2, -1, "com.vantedge.app.ui.screens.EditTextField.<anonymous> (EditProfileScreen.kt:319)");
                    }
                    TextKt.m2466Text4IGK_g(label, (Modifier) null, 0L, TextUnitKt.getSp(12), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer3, 3072, 0, 131062);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }), (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, (Function2<? super Composer, ? super Integer, Unit>) null, false, (VisualTransformation) null, (KeyboardOptions) null, (KeyboardActions) null, false, 0, minLines3, (MutableInteractionSource) null, (Shape) RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(10)), OutlinedTextFieldDefaults.INSTANCE.m2068colors0hiis_0(Color.INSTANCE.m3787getWhite0d7_KjU(), Color.INSTANCE.m3787getWhite0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, ColorKt.Color(4278239141L), 0L, null, ColorKt.Color(4278239141L), ColorKt.Color(4280953386L), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, ColorKt.Color(4278239141L), ColorKt.Color(4288585374L), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, $composer2, 100663350, 432, 27648, 0, 3072, 2122311420, 4095), $composer2, 1573248 | ($dirty & 14) | (($dirty >> 3) & 112), ($dirty << 18) & 1879048192, 0, 1572792);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            minLines2 = minLines3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            final int i4 = minLines2;
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditTextField$2
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

                public final void invoke(Composer composer, int i5) {
                    EditProfileScreenKt.EditTextField(value, label, function1, i4, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x0920, code lost:
    
        r12.updateScope(new com.vantedge.app.ui.screens.EditProfileScreenKt$EditChipList$2(r195, r196, r198, r199, r200, r202));
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0937, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0872, code lost:
    
        r2 = (kotlin.jvm.functions.Function0) new com.vantedge.app.ui.screens.EditProfileScreenKt$EditChipList$1$2$3$1(r9, r8);
        r11.updateRememberedValue(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0854, code lost:
    
        if ((r202 & 24576) != 16384) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x084b, code lost:
    
        if (r11.changedInstance(r9) == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0856, code lost:
    
        r21 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0858, code lost:
    
        r2 = r21;
        r6 = r11.rememberedValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0861, code lost:
    
        if (r2 != false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x086b, code lost:
    
        if (r6 != androidx.compose.runtime.Composer.INSTANCE.getEmpty()) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x086e, code lost:
    
        r2 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0881, code lost:
    
        r11.endReplaceableGroup();
        r0 = androidx.compose.foundation.layout.SizeKt.m613size3ABfNKs(androidx.compose.ui.Modifier.INSTANCE, androidx.compose.ui.unit.Dp.m6092constructorimpl(48));
        r5 = androidx.compose.ui.graphics.Color.m3748copywmQWz5c(r196, (r12 & 1) != 0 ? androidx.compose.ui.graphics.Color.m3752getAlphaimpl(r196) : 0.15f, (r12 & 2) != 0 ? androidx.compose.ui.graphics.Color.m3756getRedimpl(r196) : 0.0f, (r12 & 4) != 0 ? androidx.compose.ui.graphics.Color.m3755getGreenimpl(r196) : 0.0f, (r12 & 8) != 0 ? androidx.compose.ui.graphics.Color.m3753getBlueimpl(r196) : 0.0f);
        androidx.compose.material3.IconButtonKt.IconButton((kotlin.jvm.functions.Function0) r2, androidx.compose.foundation.BackgroundKt.m210backgroundbw27NRU(r0, r5, androidx.compose.foundation.shape.RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(androidx.compose.ui.unit.Dp.m6092constructorimpl(8))), false, null, null, androidx.compose.runtime.internal.ComposableLambdaKt.composableLambda(r11, -798601965, true, new com.vantedge.app.ui.screens.EditProfileScreenKt$EditChipList$1$2$4(r196)), r11, androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 28);
        androidx.compose.runtime.ComposerKt.sourceInformationMarkerEnd(r11);
        androidx.compose.runtime.ComposerKt.sourceInformationMarkerEnd(r11);
        r11.endReplaceableGroup();
        r11.endNode();
        r11.endReplaceableGroup();
        r11.endReplaceableGroup();
        androidx.compose.runtime.ComposerKt.sourceInformationMarkerEnd(r11);
        androidx.compose.runtime.ComposerKt.sourceInformationMarkerEnd(r45);
        r11.endReplaceableGroup();
        r11.endNode();
        r11.endReplaceableGroup();
        r11.endReplaceableGroup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0915, code lost:
    
        if (androidx.compose.runtime.ComposerKt.isTraceInProgress() == false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0917, code lost:
    
        androidx.compose.runtime.ComposerKt.traceEventEnd();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x091a, code lost:
    
        r12 = r11.endRestartGroup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x091e, code lost:
    
        if (r12 == null) goto L127;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x084e  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x07d9  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0653  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x05cd  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0643  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x064f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x07c5  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0845  */
    /* renamed from: EditChipList-sW7UJKQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6541EditChipListsW7UJKQ(final java.util.List<java.lang.String> r195, final long r196, final java.lang.String r198, final kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> r199, final kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r200, androidx.compose.runtime.Composer r201, final int r202) {
        /*
            Method dump skipped, instructions count: 2360
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt.m6541EditChipListsW7UJKQ(java.util.List, long, java.lang.String, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String EditChipList_sW7UJKQ$lambda$40(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: EditWorkCard-eopBjH0, reason: not valid java name */
    public static final void m6542EditWorkCardeopBjH0(final WorkExperience experience, final long cardDark, final long teal, final Function1<? super WorkExperience, Unit> function1, final Function0<Unit> function0, Composer $composer, final int $changed) {
        int $dirty;
        Composer $composer2 = $composer.startRestartGroup(706775111);
        ComposerKt.sourceInformation($composer2, "C(EditWorkCard)P(1,0:c#ui.graphics.Color,4:c#ui.graphics.Color,3)427@17262L37,425@17182L2471:EditProfileScreen.kt#fpoywd");
        int $dirty2 = $changed;
        if (($changed & 14) == 0) {
            $dirty2 |= $composer2.changed(experience) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty2 |= $composer2.changed(cardDark) ? 32 : 16;
        }
        if (($changed & 896) == 0) {
            $dirty2 |= $composer2.changed(teal) ? 256 : 128;
        }
        if (($changed & 7168) == 0) {
            $dirty2 |= $composer2.changedInstance(function1) ? 2048 : 1024;
        }
        if ((57344 & $changed) == 0) {
            $dirty2 |= $composer2.changedInstance(function0) ? 16384 : 8192;
        }
        if ((46811 & $dirty2) != 9362 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(706775111, $dirty2, -1, "com.vantedge.app.ui.screens.EditWorkCard (EditProfileScreen.kt:424)");
            }
            $dirty = $dirty2;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(cardDark, 0L, 0L, 0L, $composer2, (($dirty2 >> 3) & 14) | (CardDefaults.$stable << 12), 14), null, null, ComposableLambdaKt.composableLambda($composer2, -1522674667, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditWorkCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:101:0x0819 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:103:0x0787 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:104:0x073e  */
                /* JADX WARN: Removed duplicated region for block: B:106:0x0681 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:108:0x05ed A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:109:0x05a4  */
                /* JADX WARN: Removed duplicated region for block: B:111:0x04b2 A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:112:0x0469  */
                /* JADX WARN: Removed duplicated region for block: B:117:0x0200  */
                /* JADX WARN: Removed duplicated region for block: B:24:0x01ee  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x01fa  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x02b8  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x036d  */
                /* JADX WARN: Removed duplicated region for block: B:43:0x03ba  */
                /* JADX WARN: Removed duplicated region for block: B:48:0x0457  */
                /* JADX WARN: Removed duplicated region for block: B:51:0x0463  */
                /* JADX WARN: Removed duplicated region for block: B:54:0x049c  */
                /* JADX WARN: Removed duplicated region for block: B:59:0x0592  */
                /* JADX WARN: Removed duplicated region for block: B:62:0x059e  */
                /* JADX WARN: Removed duplicated region for block: B:65:0x05d7  */
                /* JADX WARN: Removed duplicated region for block: B:70:0x0674  */
                /* JADX WARN: Removed duplicated region for block: B:75:0x072c  */
                /* JADX WARN: Removed duplicated region for block: B:78:0x0738  */
                /* JADX WARN: Removed duplicated region for block: B:81:0x0771  */
                /* JADX WARN: Removed duplicated region for block: B:86:0x080c  */
                /* JADX WARN: Removed duplicated region for block: B:91:0x088d  */
                /* JADX WARN: Removed duplicated region for block: B:96:0x08d6  */
                /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r87, androidx.compose.runtime.Composer r88, int r89) {
                    /*
                        Method dump skipped, instructions count: 2266
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt$EditWorkCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 196614, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
            $dirty = $dirty2;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditWorkCard$2
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

                public final void invoke(Composer composer, int i) {
                    EditProfileScreenKt.m6542EditWorkCardeopBjH0(WorkExperience.this, cardDark, teal, function1, function0, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: EditCertCard-eopBjH0, reason: not valid java name */
    public static final void m6540EditCertCardeopBjH0(final Certification cert, final long cardDark, final long teal, final Function1<? super Certification, Unit> function1, final Function0<Unit> function0, Composer $composer, final int $changed) {
        int $dirty;
        Composer $composer2 = $composer.startRestartGroup(1697662227);
        ComposerKt.sourceInformation($composer2, "C(EditCertCard)P(1,0:c#ui.graphics.Color,4:c#ui.graphics.Color,3)500@19910L37,498@19830L1507:EditProfileScreen.kt#fpoywd");
        int $dirty2 = $changed;
        if (($changed & 14) == 0) {
            $dirty2 |= $composer2.changed(cert) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty2 |= $composer2.changed(cardDark) ? 32 : 16;
        }
        if (($changed & 896) == 0) {
            $dirty2 |= $composer2.changed(teal) ? 256 : 128;
        }
        if (($changed & 7168) == 0) {
            $dirty2 |= $composer2.changedInstance(function1) ? 2048 : 1024;
        }
        if ((57344 & $changed) == 0) {
            $dirty2 |= $composer2.changedInstance(function0) ? 16384 : 8192;
        }
        if ((46811 & $dirty2) != 9362 || !$composer2.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1697662227, $dirty2, -1, "com.vantedge.app.ui.screens.EditCertCard (EditProfileScreen.kt:497)");
            }
            $dirty = $dirty2;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(cardDark, 0L, 0L, 0L, $composer2, (($dirty2 >> 3) & 14) | (CardDefaults.$stable << 12), 14), null, null, ComposableLambdaKt.composableLambda($composer2, -1837938811, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditCertCard$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(ColumnScope columnScope, Composer composer, Integer num) {
                    invoke(columnScope, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Removed duplicated region for block: B:24:0x01ee  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x01fa  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0233  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x02b4  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x0366  */
                /* JADX WARN: Removed duplicated region for block: B:43:0x03b3  */
                /* JADX WARN: Removed duplicated region for block: B:48:0x03fc  */
                /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:54:0x0249  */
                /* JADX WARN: Removed duplicated region for block: B:55:0x0200  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.foundation.layout.ColumnScope r71, androidx.compose.runtime.Composer r72, int r73) {
                    /*
                        Method dump skipped, instructions count: 1024
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt$EditCertCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
                }
            }), $composer2, 196614, 24);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer2.skipToGroupEnd();
            $dirty = $dirty2;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.EditProfileScreenKt$EditCertCard$2
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

                public final void invoke(Composer composer, int i) {
                    EditProfileScreenKt.m6540EditCertCardeopBjH0(Certification.this, cardDark, teal, function1, function0, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x03e1  */
    /* renamed from: EditAddButton-iJQMabo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6539EditAddButtoniJQMabo(final java.lang.String r69, final long r70, final kotlin.jvm.functions.Function0<kotlin.Unit> r72, androidx.compose.runtime.Composer r73, final int r74) {
        /*
            Method dump skipped, instructions count: 1025
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.EditProfileScreenKt.m6539EditAddButtoniJQMabo(java.lang.String, long, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }
}
