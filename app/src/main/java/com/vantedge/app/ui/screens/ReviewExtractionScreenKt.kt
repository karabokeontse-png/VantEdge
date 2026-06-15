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
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.OutlinedTextFieldDefaults;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
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
import com.vantedge.app.data.model.Certification;
import com.vantedge.app.data.model.ExtractedCertification;
import com.vantedge.app.data.model.ExtractedEducation;
import com.vantedge.app.data.model.ExtractedExperience;
import com.vantedge.app.data.model.ExtractedField;
import com.vantedge.app.data.model.StructuredProfileExtraction;
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
import kotlinx.coroutines.CoroutineScope;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.i18n.MessageBundle;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;

/* compiled from: ReviewExtractionScreen.kt */
@Metadata(d1 = {"\u0000t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001aL\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001aP\u0010\u0013\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u000f2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000fH\u0003ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019\u001a\"\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0004\u001a\u00020\u0005H\u0003ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a7\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007¢\u0006\u0002\u0010%\u001a;\u0010&\u001a\u00020\u00012\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\u001c\u0010)\u001a\u0018\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u00010\u000f¢\u0006\u0002\b+¢\u0006\u0002\b,H\u0003¢\u0006\u0002\u0010-\u001a;\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u00101\u001a\u00020\u0016H\u0003¢\u0006\u0002\u00102\u001a\u001b\u00103\u001a\u00020\u00012\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015H\u0003¢\u0006\u0002\u00105\u001aL\u00106\u001a\u00020\u00012\u0006\u00107\u001a\u0002082\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003ø\u0001\u0000¢\u0006\u0004\b9\u0010:\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006;²\u0006\n\u0010<\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u0010=\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u0010>\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u0010?\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u0010@\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\n\u0010A\u001a\u00020\u0003X\u008a\u008e\u0002²\u0006\u0010\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00030CX\u008a\u008e\u0002²\u0006\u0010\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00030CX\u008a\u008e\u0002²\u0006\u0010\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00030CX\u008a\u008e\u0002²\u0006\u0010\u0010F\u001a\b\u0012\u0004\u0012\u0002080CX\u008a\u008e\u0002²\u0006\u0010\u0010G\u001a\b\u0012\u0004\u0012\u00020\f0CX\u008a\u008e\u0002²\u0006\n\u0010H\u001a\u00020IX\u008a\u008e\u0002²\u0006\n\u0010J\u001a\u00020\u0003X\u008a\u008e\u0002"}, d2 = {"AddItemButton", "", AnnotatedPrivateKey.LABEL, "", "teal", "Landroidx/compose/ui/graphics/Color;", "onClick", "Lkotlin/Function0;", "AddItemButton-iJQMabo", "(Ljava/lang/String;JLkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "CertificationCard", "cert", "Lcom/vantedge/app/data/model/Certification;", "cardDark", "onUpdate", "Lkotlin/Function1;", "onRemove", "CertificationCard-eopBjH0", "(Lcom/vantedge/app/data/model/Certification;JJLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "ChipEditList", "items", "", "", "onAdd", "ChipEditList-3IgeMak", "(Ljava/util/List;JLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Landroidx/compose/runtime/Composer;I)V", "ConfidenceBanner", "confidence", "", "ConfidenceBanner-RPmYEkk", "(FJLandroidx/compose/runtime/Composer;I)V", "ReviewExtractionScreen", "extraction", "Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "onConfirm", "Lcom/vantedge/app/data/model/UserProfile;", "onBack", "(Lcom/vantedge/app/data/model/StructuredProfileExtraction;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "ReviewSection", MessageBundle.TITLE_ENTRY, "icon", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function3;Landroidx/compose/runtime/Composer;I)V", "ReviewTextField", "value", "onValueChange", "minLines", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function1;ILandroidx/compose/runtime/Composer;II)V", "WarningsBanner", "warnings", "(Ljava/util/List;Landroidx/compose/runtime/Composer;I)V", "WorkExperienceCard", "experience", "Lcom/vantedge/app/data/model/WorkExperience;", "WorkExperienceCard-eopBjH0", "(Lcom/vantedge/app/data/model/WorkExperience;JJLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "app_debug", "name", "email", HintConstants.AUTOFILL_HINT_PHONE, "location", "linkedIn", ErrorBundle.SUMMARY_ENTRY, "skills", "", "languages", "education", "workHistory", "certifications", "showContent", "", "inputValue"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ReviewExtractionScreenKt {
    public static final void ReviewExtractionScreen(final StructuredProfileExtraction extraction, final Function1<? super UserProfile, Unit> onConfirm, final Function0<Unit> onBack, Composer $composer, final int $changed) {
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
        Object value$iv13;
        Composer $composer2;
        String value;
        String value2;
        String value3;
        String value4;
        int i;
        int i2;
        String value5;
        String value6;
        String value7;
        String value8;
        String value9;
        String value10;
        Intrinsics.checkNotNullParameter(extraction, "extraction");
        Intrinsics.checkNotNullParameter(onConfirm, "onConfirm");
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer3 = $composer.startRestartGroup(738111017);
        ComposerKt.sourceInformation($composer3, "C(ReviewExtractionScreen)P(!1,2)37@1367L70,38@1455L71,39@1544L71,40@1636L74,41@1731L74,42@1825L60,44@1905L91,47@2018L94,51@2135L467,63@2626L453,76@3106L279,87@3410L34,88@3470L22,88@3449L43,90@3498L8455:ReviewExtractionScreen.kt#fpoywd");
        int $dirty = $changed;
        if (($changed & 14) == 0) {
            $dirty |= $composer3.changed(extraction) ? 4 : 2;
        }
        if (($changed & 112) == 0) {
            $dirty |= $composer3.changedInstance(onConfirm) ? 32 : 16;
        }
        int $dirty2 = $dirty;
        if (($dirty2 & 91) != 18 || !$composer3.getSkipping()) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(738111017, $dirty2, -1, "com.vantedge.app.ui.screens.ReviewExtractionScreen (ReviewExtractionScreen.kt:31)");
            }
            final long teal = ColorKt.Color(4278239141L);
            final long cardDark = ColorKt.Color(4279900718L);
            final long bg = ColorKt.Color(4279045389L);
            $composer3.startReplaceableGroup(1579627691);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv = $composer3.rememberedValue();
            if (it$iv == Composer.INSTANCE.getEmpty()) {
                ExtractedField name = extraction.getPersonalInfo().getName();
                value$iv = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((name == null || (value10 = name.getValue()) == null) ? "" : value10, null, 2, null);
                $composer3.updateRememberedValue(value$iv);
            } else {
                value$iv = it$iv;
            }
            final MutableState name$delegate = (MutableState) value$iv;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579627779);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv2 = $composer3.rememberedValue();
            if (it$iv2 == Composer.INSTANCE.getEmpty()) {
                ExtractedField email = extraction.getPersonalInfo().getEmail();
                value$iv2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((email == null || (value9 = email.getValue()) == null) ? "" : value9, null, 2, null);
                $composer3.updateRememberedValue(value$iv2);
            } else {
                value$iv2 = it$iv2;
            }
            final MutableState email$delegate = (MutableState) value$iv2;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579627868);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv3 = $composer3.rememberedValue();
            if (it$iv3 == Composer.INSTANCE.getEmpty()) {
                ExtractedField phone = extraction.getPersonalInfo().getPhone();
                value$iv3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((phone == null || (value8 = phone.getValue()) == null) ? "" : value8, null, 2, null);
                $composer3.updateRememberedValue(value$iv3);
            } else {
                value$iv3 = it$iv3;
            }
            final MutableState phone$delegate = (MutableState) value$iv3;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579627960);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv4 = $composer3.rememberedValue();
            if (it$iv4 == Composer.INSTANCE.getEmpty()) {
                ExtractedField location = extraction.getPersonalInfo().getLocation();
                value$iv4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((location == null || (value7 = location.getValue()) == null) ? "" : value7, null, 2, null);
                $composer3.updateRememberedValue(value$iv4);
            } else {
                value$iv4 = it$iv4;
            }
            final MutableState location$delegate = (MutableState) value$iv4;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628055);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv5 = $composer3.rememberedValue();
            if (it$iv5 == Composer.INSTANCE.getEmpty()) {
                ExtractedField linkedIn = extraction.getPersonalInfo().getLinkedIn();
                value$iv5 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((linkedIn == null || (value6 = linkedIn.getValue()) == null) ? "" : value6, null, 2, null);
                $composer3.updateRememberedValue(value$iv5);
            } else {
                value$iv5 = it$iv5;
            }
            final MutableState linkedIn$delegate = (MutableState) value$iv5;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628149);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv6 = $composer3.rememberedValue();
            if (it$iv6 == Composer.INSTANCE.getEmpty()) {
                ExtractedField summary = extraction.getSummary();
                value$iv6 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default((summary == null || (value5 = summary.getValue()) == null) ? "" : value5, null, 2, null);
                $composer3.updateRememberedValue(value$iv6);
            } else {
                value$iv6 = it$iv6;
            }
            final MutableState summary$delegate = (MutableState) value$iv6;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628229);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv7 = $composer3.rememberedValue();
            if (it$iv7 == Composer.INSTANCE.getEmpty()) {
                Iterable $this$map$iv = extraction.getSkills();
                int $i$f$cache = CollectionsKt.collectionSizeOrDefault($this$map$iv, 10);
                Collection destination$iv$iv = new ArrayList($i$f$cache);
                for (Object item$iv$iv : $this$map$iv) {
                    ExtractedField it = (ExtractedField) item$iv$iv;
                    destination$iv$iv.add(it.getValue());
                }
                value$iv7 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(CollectionsKt.toMutableList(destination$iv$iv), null, 2, null);
                $composer3.updateRememberedValue(value$iv7);
            } else {
                value$iv7 = it$iv7;
            }
            final MutableState skills$delegate = (MutableState) value$iv7;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628342);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv8 = $composer3.rememberedValue();
            if (it$iv8 == Composer.INSTANCE.getEmpty()) {
                Iterable $this$map$iv2 = extraction.getLanguages();
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv2;
                for (Object item$iv$iv2 : $this$mapTo$iv$iv) {
                    ExtractedField it2 = (ExtractedField) item$iv$iv2;
                    destination$iv$iv2.add(it2.getValue());
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                }
                value$iv8 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(CollectionsKt.toMutableList(destination$iv$iv2), null, 2, null);
                $composer3.updateRememberedValue(value$iv8);
            } else {
                value$iv8 = it$iv8;
            }
            final MutableState languages$delegate = (MutableState) value$iv8;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628459);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv9 = $composer3.rememberedValue();
            int i3 = 0;
            if (it$iv9 == Composer.INSTANCE.getEmpty()) {
                int i4 = 0;
                Iterable $this$map$iv3 = extraction.getEducation();
                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
                Iterable $this$mapTo$iv$iv2 = $this$map$iv3;
                int $i$f$mapTo = 0;
                for (Object item$iv$iv3 : $this$mapTo$iv$iv2) {
                    ExtractedEducation edu = (ExtractedEducation) item$iv$iv3;
                    StringBuilder $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426 = new StringBuilder();
                    Iterable $this$mapTo$iv$iv3 = $this$mapTo$iv$iv2;
                    int $i$f$mapTo2 = $i$f$mapTo;
                    $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426.append(edu.getQualification().getValue());
                    if (edu.getFieldOfStudy() != null) {
                        i = i3;
                        i2 = i4;
                        $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426.append(" in " + edu.getFieldOfStudy().getValue());
                    } else {
                        i = i3;
                        i2 = i4;
                    }
                    $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426.append(" — " + edu.getInstitution().getValue());
                    if (edu.getGraduationYear() != null) {
                        $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426.append(" (" + edu.getGraduationYear().getValue() + ")");
                    }
                    String sb = $this$ReviewExtractionScreen_u24lambda_u2428_u24lambda_u2427_u24lambda_u2426.toString();
                    Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                    destination$iv$iv3.add(sb);
                    i3 = i;
                    $i$f$mapTo = $i$f$mapTo2;
                    $this$mapTo$iv$iv2 = $this$mapTo$iv$iv3;
                    i4 = i2;
                }
                value$iv9 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(CollectionsKt.toMutableList(destination$iv$iv3), null, 2, null);
                $composer3.updateRememberedValue(value$iv9);
            } else {
                value$iv9 = it$iv9;
            }
            final MutableState education$delegate = (MutableState) value$iv9;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579628950);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv10 = $composer3.rememberedValue();
            if (it$iv10 == Composer.INSTANCE.getEmpty()) {
                Iterable $this$map$iv4 = extraction.getWorkHistory();
                Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv4, 10));
                Iterable $this$mapTo$iv$iv4 = $this$map$iv4;
                for (Object item$iv$iv4 : $this$mapTo$iv$iv4) {
                    ExtractedExperience exp = (ExtractedExperience) item$iv$iv4;
                    Iterable $this$mapTo$iv$iv5 = $this$mapTo$iv$iv4;
                    String value11 = exp.getJobTitle().getValue();
                    String value12 = exp.getCompany().getValue();
                    ExtractedField startDate = exp.getStartDate();
                    String str = (startDate == null || (value4 = startDate.getValue()) == null) ? "" : value4;
                    ExtractedField endDate = exp.getEndDate();
                    String str2 = (endDate == null || (value3 = endDate.getValue()) == null) ? "" : value3;
                    ExtractedField description = exp.getDescription();
                    destination$iv$iv4.add(new WorkExperience(value11, value12, str, str2, (description == null || (value2 = description.getValue()) == null) ? "" : value2));
                    $this$mapTo$iv$iv4 = $this$mapTo$iv$iv5;
                }
                value$iv10 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(CollectionsKt.toMutableList(destination$iv$iv4), null, 2, null);
                $composer3.updateRememberedValue(value$iv10);
            } else {
                value$iv10 = it$iv10;
            }
            final MutableState workHistory$delegate = (MutableState) value$iv10;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579629430);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv11 = $composer3.rememberedValue();
            if (it$iv11 == Composer.INSTANCE.getEmpty()) {
                Iterable $this$map$iv5 = extraction.getCertifications();
                int $i$f$cache2 = CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10);
                Collection destination$iv$iv5 = new ArrayList($i$f$cache2);
                Iterable $this$mapTo$iv$iv6 = $this$map$iv5;
                for (Object item$iv$iv5 : $this$mapTo$iv$iv6) {
                    ExtractedCertification cert = (ExtractedCertification) item$iv$iv5;
                    Iterable $this$mapTo$iv$iv7 = $this$mapTo$iv$iv6;
                    String value13 = cert.getName().getValue();
                    ExtractedField issuer = cert.getIssuer();
                    destination$iv$iv5.add(new Certification(null, value13, (issuer == null || (value = issuer.getValue()) == null) ? "" : value, null, null, null, 0L, 0L, 0L, 505, null));
                    $this$mapTo$iv$iv6 = $this$mapTo$iv$iv7;
                }
                value$iv11 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(CollectionsKt.toMutableList(destination$iv$iv5), null, 2, null);
                $composer3.updateRememberedValue(value$iv11);
            } else {
                value$iv11 = it$iv11;
            }
            final MutableState certifications$delegate = (MutableState) value$iv11;
            $composer3.endReplaceableGroup();
            $composer3.startReplaceableGroup(1579629734);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv12 = $composer3.rememberedValue();
            if (it$iv12 == Composer.INSTANCE.getEmpty()) {
                value$iv12 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(false, null, 2, null);
                $composer3.updateRememberedValue(value$iv12);
            } else {
                value$iv12 = it$iv12;
            }
            final MutableState showContent$delegate = (MutableState) value$iv12;
            $composer3.endReplaceableGroup();
            Unit unit = Unit.INSTANCE;
            $composer3.startReplaceableGroup(1579629794);
            ComposerKt.sourceInformation($composer3, "CC(remember):ReviewExtractionScreen.kt#9igjgp");
            Object it$iv13 = $composer3.rememberedValue();
            if (it$iv13 == Composer.INSTANCE.getEmpty()) {
                value$iv13 = new ReviewExtractionScreenKt$ReviewExtractionScreen$1$1(showContent$delegate, null);
                $composer3.updateRememberedValue(value$iv13);
            } else {
                value$iv13 = it$iv13;
            }
            $composer3.endReplaceableGroup();
            EffectsKt.LaunchedEffect(unit, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) value$iv13, $composer3, 70);
            $composer2 = $composer3;
            ScaffoldKt.m2121ScaffoldTvnljyQ(null, null, ComposableLambdaKt.composableLambda($composer3, -1818299516, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$2
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

                /* JADX WARN: Removed duplicated region for block: B:29:0x02ab  */
                /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final void invoke(androidx.compose.runtime.Composer r64, int r65) {
                    /*
                        Method dump skipped, instructions count: 687
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$2.invoke(androidx.compose.runtime.Composer, int):void");
                }
            }), null, null, 0, bg, 0L, null, ComposableLambdaKt.composableLambda($composer3, -260572422, true, new Function3<PaddingValues, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(PaddingValues paddingValues, Composer composer, Integer num) {
                    invoke(paddingValues, composer, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(final PaddingValues padding, Composer $composer4, int $changed2) {
                    boolean ReviewExtractionScreen$lambda$40;
                    Intrinsics.checkNotNullParameter(padding, "padding");
                    ComposerKt.sourceInformation($composer4, "C134@5340L6607:ReviewExtractionScreen.kt#fpoywd");
                    int $dirty3 = $changed2;
                    if (($changed2 & 14) == 0) {
                        $dirty3 |= $composer4.changed(padding) ? 4 : 2;
                    }
                    int $dirty4 = $dirty3;
                    if (($dirty4 & 91) != 18 || !$composer4.getSkipping()) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart(-260572422, $dirty4, -1, "com.vantedge.app.ui.screens.ReviewExtractionScreen.<anonymous> (ReviewExtractionScreen.kt:134)");
                        }
                        ReviewExtractionScreen$lambda$40 = ReviewExtractionScreenKt.ReviewExtractionScreen$lambda$40(showContent$delegate);
                        EnterTransition fadeIn$default = EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(500, 0, null, 6, null), 0.0f, 2, null);
                        final StructuredProfileExtraction structuredProfileExtraction = extraction;
                        final long j = teal;
                        final MutableState<String> mutableState = name$delegate;
                        final MutableState<String> mutableState2 = email$delegate;
                        final MutableState<String> mutableState3 = phone$delegate;
                        final MutableState<String> mutableState4 = location$delegate;
                        final MutableState<String> mutableState5 = linkedIn$delegate;
                        final MutableState<String> mutableState6 = summary$delegate;
                        final MutableState<List<String>> mutableState7 = skills$delegate;
                        final MutableState<List<WorkExperience>> mutableState8 = workHistory$delegate;
                        final long j2 = cardDark;
                        final MutableState<List<String>> mutableState9 = education$delegate;
                        final MutableState<List<Certification>> mutableState10 = certifications$delegate;
                        final MutableState<List<String>> mutableState11 = languages$delegate;
                        AnimatedVisibilityKt.AnimatedVisibility(ReviewExtractionScreen$lambda$40, (Modifier) null, fadeIn$default, (ExitTransition) null, (String) null, ComposableLambdaKt.composableLambda($composer4, -478923822, true, new Function3<AnimatedVisibilityScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$3.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public /* bridge */ /* synthetic */ Unit invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, Integer num) {
                                invoke(animatedVisibilityScope, composer, num.intValue());
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:17:0x022e  */
                            /* JADX WARN: Removed duplicated region for block: B:20:0x023a  */
                            /* JADX WARN: Removed duplicated region for block: B:28:0x043f  */
                            /* JADX WARN: Removed duplicated region for block: B:31:0x047d  */
                            /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:37:0x0240  */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public final void invoke(androidx.compose.animation.AnimatedVisibilityScope r115, androidx.compose.runtime.Composer r116, int r117) {
                                /*
                                    Method dump skipped, instructions count: 1153
                                    To view this dump add '--comments-level debug' option
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$3.AnonymousClass1.invoke(androidx.compose.animation.AnimatedVisibilityScope, androidx.compose.runtime.Composer, int):void");
                            }
                        }), $composer4, 196992, 26);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                            return;
                        }
                        return;
                    }
                    $composer4.skipToGroupEnd();
                }
            }), $composer3, 806879616, 443);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            $composer3.skipToGroupEnd();
            $composer2 = $composer3;
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewExtractionScreen$4
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
                    ReviewExtractionScreenKt.ReviewExtractionScreen(StructuredProfileExtraction.this, onConfirm, onBack, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$1(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$4(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$7(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$10(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$13(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ReviewExtractionScreen$lambda$16(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> ReviewExtractionScreen$lambda$20(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> ReviewExtractionScreen$lambda$24(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> ReviewExtractionScreen$lambda$29(MutableState<List<String>> mutableState) {
        MutableState<List<String>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<WorkExperience> ReviewExtractionScreen$lambda$33(MutableState<List<WorkExperience>> mutableState) {
        MutableState<List<WorkExperience>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<Certification> ReviewExtractionScreen$lambda$37(MutableState<List<Certification>> mutableState) {
        MutableState<List<Certification>> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ReviewExtractionScreen$lambda$40(MutableState<Boolean> mutableState) {
        MutableState<Boolean> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ReviewExtractionScreen$lambda$41(MutableState<Boolean> mutableState, boolean value) {
        mutableState.setValue(Boolean.valueOf(value));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x02be  */
    /* renamed from: ConfidenceBanner-RPmYEkk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6562ConfidenceBannerRPmYEkk(final float r80, final long r81, androidx.compose.runtime.Composer r83, final int r84) {
        /*
            Method dump skipped, instructions count: 727
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt.m6562ConfidenceBannerRPmYEkk(float, long, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0393  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void ReviewSection(final java.lang.String r77, final java.lang.String r78, kotlin.jvm.functions.Function3<? super androidx.compose.foundation.layout.ColumnScope, ? super androidx.compose.runtime.Composer, ? super java.lang.Integer, kotlin.Unit> r79, androidx.compose.runtime.Composer r80, final int r81) {
        /*
            Method dump skipped, instructions count: 948
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt.ReviewSection(java.lang.String, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ReviewTextField(final String value, final String label, final Function1<? super String, Unit> function1, int minLines, Composer $composer, final int $changed, final int i) {
        int i2;
        int minLines2;
        Composer $composer2 = $composer.startRestartGroup(-1731441253);
        ComposerKt.sourceInformation($composer2, "C(ReviewTextField)P(3!1,2)353@14163L361,347@13915L658:ReviewExtractionScreen.kt#fpoywd");
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
                ComposerKt.traceEventStart(-1731441253, $dirty, -1, "com.vantedge.app.ui.screens.ReviewTextField (ReviewExtractionScreen.kt:346)");
            }
            OutlinedTextFieldKt.OutlinedTextField(value, function1, SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), false, false, (TextStyle) null, (Function2<? super Composer, ? super Integer, Unit>) ComposableLambdaKt.composableLambda($composer2, 459258677, true, new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewTextField$1
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
                    ComposerKt.sourceInformation($composer3, "C350@14014L29:ReviewExtractionScreen.kt#fpoywd");
                    if (($changed2 & 11) == 2 && $composer3.getSkipping()) {
                        $composer3.skipToGroupEnd();
                        return;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart(459258677, $changed2, -1, "com.vantedge.app.ui.screens.ReviewTextField.<anonymous> (ReviewExtractionScreen.kt:350)");
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
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$ReviewTextField$2
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
                    ReviewExtractionScreenKt.ReviewTextField(value, label, function1, i4, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1), i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x082d, code lost:
    
        if (r10.changedInstance(r6) == false) goto L106;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x090b  */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0858 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x083b  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0830  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x07ce  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x064a  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x063a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0646  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x07bc  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0827  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x084b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0902  */
    /* renamed from: ChipEditList-3IgeMak, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6561ChipEditList3IgeMak(final java.util.List<java.lang.String> r194, final long r195, final kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> r197, final kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r198, androidx.compose.runtime.Composer r199, final int r200) {
        /*
            Method dump skipped, instructions count: 2337
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt.m6561ChipEditList3IgeMak(java.util.List, long, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String ChipEditList_3IgeMak$lambda$47(MutableState<String> mutableState) {
        MutableState<String> $this$getValue$iv = mutableState;
        return $this$getValue$iv.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: WorkExperienceCard-eopBjH0, reason: not valid java name */
    public static final void m6563WorkExperienceCardeopBjH0(final WorkExperience experience, final long cardDark, final long teal, final Function1<? super WorkExperience, Unit> function1, final Function0<Unit> function0, Composer $composer, final int $changed) {
        int $dirty;
        Composer $composer2 = $composer.startRestartGroup(1418345521);
        ComposerKt.sourceInformation($composer2, "C(WorkExperienceCard)P(1,0:c#ui.graphics.Color,4:c#ui.graphics.Color,3)451@17887L37,449@17807L2470:ReviewExtractionScreen.kt#fpoywd");
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
                ComposerKt.traceEventStart(1418345521, $dirty2, -1, "com.vantedge.app.ui.screens.WorkExperienceCard (ReviewExtractionScreen.kt:448)");
            }
            $dirty = $dirty2;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(cardDark, 0L, 0L, 0L, $composer2, (($dirty2 >> 3) & 14) | (CardDefaults.$stable << 12), 14), null, null, ComposableLambdaKt.composableLambda($composer2, -239918173, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$WorkExperienceCard$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$WorkExperienceCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
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
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$WorkExperienceCard$2
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
                    ReviewExtractionScreenKt.m6563WorkExperienceCardeopBjH0(WorkExperience.this, cardDark, teal, function1, function0, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: CertificationCard-eopBjH0, reason: not valid java name */
    public static final void m6560CertificationCardeopBjH0(final Certification cert, final long cardDark, final long teal, final Function1<? super Certification, Unit> function1, final Function0<Unit> function0, Composer $composer, final int $changed) {
        int $dirty;
        Composer $composer2 = $composer.startRestartGroup(861334613);
        ComposerKt.sourceInformation($composer2, "C(CertificationCard)P(1,0:c#ui.graphics.Color,4:c#ui.graphics.Color,3)523@20539L37,521@20459L1510:ReviewExtractionScreen.kt#fpoywd");
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
                ComposerKt.traceEventStart(861334613, $dirty2, -1, "com.vantedge.app.ui.screens.CertificationCard (ReviewExtractionScreen.kt:520)");
            }
            $dirty = $dirty2;
            CardKt.Card(SizeKt.fillMaxWidth$default(Modifier.INSTANCE, 0.0f, 1, null), RoundedCornerShapeKt.m834RoundedCornerShape0680j_4(Dp.m6092constructorimpl(12)), CardDefaults.INSTANCE.m1628cardColorsro_MJ88(cardDark, 0L, 0L, 0L, $composer2, (($dirty2 >> 3) & 14) | (CardDefaults.$stable << 12), 14), null, null, ComposableLambdaKt.composableLambda($composer2, 1509447, true, new Function3<ColumnScope, Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$CertificationCard$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$CertificationCard$1.invoke(androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):void");
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
            endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.vantedge.app.ui.screens.ReviewExtractionScreenKt$CertificationCard$2
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
                    ReviewExtractionScreenKt.m6560CertificationCardeopBjH0(Certification.this, cardDark, teal, function1, function0, composer, RecomposeScopeImplKt.updateChangedFlags($changed | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x03e1  */
    /* renamed from: AddItemButton-iJQMabo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m6559AddItemButtoniJQMabo(final java.lang.String r69, final long r70, final kotlin.jvm.functions.Function0<kotlin.Unit> r72, androidx.compose.runtime.Composer r73, final int r74) {
        /*
            Method dump skipped, instructions count: 1025
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt.m6559AddItemButtoniJQMabo(java.lang.String, long, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0202 A[LOOP:0: B:16:0x01fc->B:18:0x0202, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void WarningsBanner(final java.util.List<java.lang.String> r56, androidx.compose.runtime.Composer r57, final int r58) {
        /*
            Method dump skipped, instructions count: 670
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.ui.screens.ReviewExtractionScreenKt.WarningsBanner(java.util.List, androidx.compose.runtime.Composer, int):void");
    }
}
