package com.vantedge.app.data.engine;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.autofill.HintConstants;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.common.PDPageLabelRange;
import com.tom_roush.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.vantedge.app.data.model.ExtractedCertification;
import com.vantedge.app.data.model.ExtractedEducation;
import com.vantedge.app.data.model.ExtractedExperience;
import com.vantedge.app.data.model.ExtractedField;
import com.vantedge.app.data.model.ExtractedPersonalInfo;
import com.vantedge.app.data.model.ExtractionMetadata;
import com.vantedge.app.data.model.ExtractionSourceType;
import com.vantedge.app.data.model.StructuredProfileExtraction;
import com.vantedge.app.data.network.AiGateway;
import com.vantedge.app.util.TelemetryCollector;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import kotlinx.collections.immutable.ExtensionsKt;
import kotlinx.serialization.json.internal.AbstractJsonLexerKt;
import org.bouncycastle.i18n.ErrorBundle;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProfileExtractionEngine.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 02\u00020\u0001:\u000201B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ*\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000f0\u000eH\u0082@¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J$\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\f0\u00192\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\nH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\"\u001a\u00020!H\u0002J\u0018\u0010$\u001a\u00020%2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\fH\u0002J\u0010\u0010+\u001a\u00020\f2\u0006\u0010*\u001a\u00020\fH\u0002JN\u0010,\u001a\b\u0012\u0004\u0012\u00020\n0\u00192\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010&\u001a\u00020'2\b\b\u0002\u0010-\u001a\u00020\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u000f0\u000eH\u0086@ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b.\u0010/R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u00062"}, d2 = {"Lcom/vantedge/app/data/engine/ProfileExtractionEngine;", "", "context", "Landroid/content/Context;", "aiGateway", "Lcom/vantedge/app/data/network/AiGateway;", "telemetryCollector", "Lcom/vantedge/app/util/TelemetryCollector;", "(Landroid/content/Context;Lcom/vantedge/app/data/network/AiGateway;Lcom/vantedge/app/util/TelemetryCollector;)V", "callAi", "Lcom/vantedge/app/data/model/StructuredProfileExtraction;", "rawText", "", "onProgress", "Lkotlin/Function1;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "extractDocx", "uri", "Landroid/net/Uri;", "extractImageOcr", "extractPdf", "extractPdfOcr", "extractPlain", "extractRawText", "Lkotlin/Result;", "extractRawText-gIAlu-s", "(Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFileName", "isValidExtraction", "", "extraction", "normalizeRootKeys", "Lorg/json/JSONObject;", "json", "parse", "runGate0", "Lcom/vantedge/app/data/engine/Gate0Result;", "extractionMode", "Lcom/vantedge/app/data/engine/ExtractionMode;", "safeRepairJson", "Lcom/vantedge/app/data/engine/ProfileExtractionEngine$RepairResult;", "raw", "sanitizeForGate0", "structureProfile", "sessionId", "structureProfile-yxL6bBk", "(Ljava/lang/String;Lcom/vantedge/app/data/engine/ExtractionMode;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "RepairResult", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ProfileExtractionEngine {
    private static final int GATE_0_PASS_THRESHOLD = 3;
    private static final String TAG = "ProfileExtractionEngine";
    private final AiGateway aiGateway;
    private final Context context;
    private final TelemetryCollector telemetryCollector;
    public static final int $stable = 8;
    private static final List<String> CV_KEYWORDS = CollectionsKt.listOf((Object[]) new String[]{"experience", "work", "education", "skills", "employment", "projects", "certification", "resume", "cv", Scopes.PROFILE, ErrorBundle.SUMMARY_ENTRY});
    private static final List<String> CV_SECTION_HEADERS = CollectionsKt.listOf((Object[]) new String[]{"experience", "education", "skills", "work history", "projects", "certifications", "qualifications"});
    private static final Regex YEAR_PATTERN = new Regex("\\b(19|20)\\d{2}\\b");
    private static final Regex MONTH_YEAR_PATTERN = new Regex("\\b(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s*(19|20)\\d{2}\\b", RegexOption.IGNORE_CASE);
    private static final Regex EMAIL_PATTERN = new Regex("[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}");
    private static final Regex PHONE_PATTERN = new Regex("(\\+?\\d[\\d\\s\\-().]{7,}\\d)");
    private static final Regex URL_PATTERN = new Regex("https?://\\S+|www\\.\\S+|linkedin\\.com\\S*", RegexOption.IGNORE_CASE);

    public ProfileExtractionEngine(Context context, AiGateway aiGateway, TelemetryCollector telemetryCollector) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(aiGateway, "aiGateway");
        Intrinsics.checkNotNullParameter(telemetryCollector, "telemetryCollector");
        this.context = context;
        this.aiGateway = aiGateway;
        this.telemetryCollector = telemetryCollector;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* renamed from: extractRawText-gIAlu-s, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m6421extractRawTextgIAlus(android.net.Uri r7, kotlin.coroutines.Continuation<? super kotlin.Result<java.lang.String>> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$1
            if (r0 == 0) goto L14
            r0 = r8
            com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$1 r0 = (com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L19
        L14:
            com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$1 r0 = new com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$1
            r0.<init>(r6, r8)
        L19:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L32;
                case 1: goto L2d;
                default: goto L25;
            }
        L25:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2d:
            kotlin.ResultKt.throwOnFailure(r0)
            r7 = r0
            goto L4e
        L32:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            kotlinx.coroutines.CoroutineDispatcher r3 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
            com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$2 r4 = new com.vantedge.app.data.engine.ProfileExtractionEngine$extractRawText$2
            r5 = 0
            r4.<init>(r2, r7, r5)
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r5 = 1
            r8.label = r5
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r8)
            if (r7 != r1) goto L4e
            return r1
        L4e:
            kotlin.Result r7 = (kotlin.Result) r7
            java.lang.Object r7 = r7.getValue()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.ProfileExtractionEngine.m6421extractRawTextgIAlus(android.net.Uri, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0196 A[Catch: Exception -> 0x01b6, TryCatch #0 {Exception -> 0x01b6, blocks: (B:15:0x018e, B:17:0x0196, B:19:0x01af, B:46:0x0176), top: B:45:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x01af A[Catch: Exception -> 0x01b6, TRY_LEAVE, TryCatch #0 {Exception -> 0x01b6, blocks: (B:15:0x018e, B:17:0x0196, B:19:0x01af, B:46:0x0176), top: B:45:0x0176 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    /* renamed from: structureProfile-yxL6bBk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m6422structureProfileyxL6bBk(java.lang.String r22, com.vantedge.app.data.engine.ExtractionMode r23, java.lang.String r24, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r25, kotlin.coroutines.Continuation<? super kotlin.Result<com.vantedge.app.data.model.StructuredProfileExtraction>> r26) {
        /*
            Method dump skipped, instructions count: 502
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.ProfileExtractionEngine.m6422structureProfileyxL6bBk(java.lang.String, com.vantedge.app.data.engine.ExtractionMode, java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Gate0Result runGate0(String rawText, ExtractionMode extractionMode) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        String sanitizeForGate0 = sanitizeForGate0(rawText);
        List<String> lines = StringsKt.lines(sanitizeForGate0);
        ArrayList arrayList = new ArrayList();
        for (Object obj : lines) {
            if (true ^ StringsKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = lines.size();
        String lowerCase = sanitizeForGate0.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        Log.i(TAG, "[Gate0] ===== STRUCTURAL ANALYSIS =====");
        List<String> list = CV_SECTION_HEADERS;
        if ((list instanceof Collection) && list.isEmpty()) {
            i = 0;
        } else {
            i = 0;
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                List<String> list2 = list;
                if (StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) it.next(), false, 2, (Object) null)) {
                    i++;
                    if (i < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                    list = list2;
                } else {
                    list = list2;
                }
            }
        }
        int i8 = i;
        int i9 = i8 >= 2 ? 2 : 0;
        Log.i(TAG, "[Gate0] StructuralSections: matches=" + i8 + ", contribution=" + (i9 > 0 ? "+" + i9 : PDLayoutAttributeObject.GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES));
        String str = PDLayoutAttributeObject.GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES;
        int count = SequencesKt.count(Regex.findAll$default(YEAR_PATTERN, lowerCase, 0, 2, null)) + SequencesKt.count(Regex.findAll$default(MONTH_YEAR_PATTERN, lowerCase, 0, 2, null));
        int i10 = count < 2 ? 0 : 2;
        Log.i(TAG, "[Gate0] ChronologyDensity: matches=" + count + ", contribution=" + (i10 > 0 ? "+" + i10 : str));
        String joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.take(lines, 15), "\n", null, null, 0, null, null, 62, null);
        if (EMAIL_PATTERN.containsMatchIn(joinToString$default) || PHONE_PATTERN.containsMatchIn(joinToString$default) || URL_PATTERN.containsMatchIn(joinToString$default)) {
            i2 = 1;
            i3 = 1;
        } else {
            i2 = 0;
            i3 = 0;
        }
        Log.i(TAG, "[Gate0] IdentitySignals: matches=" + i2 + ", contribution=" + (i3 > 0 ? "+" + i3 : str));
        List<String> list3 = lines;
        if ((list3 instanceof Collection) && list3.isEmpty()) {
            i5 = i2;
            i4 = 0;
        } else {
            i4 = 0;
            Iterator<T> it2 = list3.iterator();
            while (it2.hasNext()) {
                int i11 = i2;
                int length = StringsKt.trim((CharSequence) it2.next()).toString().length();
                List<String> list4 = list3;
                if (1 <= length && length < 36) {
                    i4++;
                    if (i4 < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                    list3 = list4;
                    i2 = i11;
                } else {
                    list3 = list4;
                    i2 = i11;
                }
            }
            i5 = i2;
        }
        float f = size > 0 ? i4 / size : 0.0f;
        int i12 = f >= 0.5f ? 1 : 0;
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        Log.i(TAG, "[Gate0] LayoutFragmentation: ratio=" + format + ", contribution=" + (i12 > 0 ? "+" + i12 : str));
        ArrayList arrayList3 = arrayList2;
        int i13 = 0;
        if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
            i6 = 0;
        } else {
            i6 = 0;
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                ArrayList arrayList4 = arrayList3;
                int i14 = i13;
                if (StringsKt.trim((CharSequence) it3.next()).toString().length() > 60) {
                    i6++;
                    if (i6 < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                    i13 = i14;
                    arrayList3 = arrayList4;
                } else {
                    i13 = i14;
                    arrayList3 = arrayList4;
                }
            }
        }
        float size2 = arrayList2.isEmpty() ^ true ? i6 / arrayList2.size() : 0.0f;
        int i15 = size2 > 0.6f ? -1 : 0;
        if (!arrayList2.isEmpty()) {
            Iterator it4 = arrayList2.iterator();
            int i16 = 0;
            while (it4.hasNext()) {
                i16 += StringsKt.trim((CharSequence) it4.next()).toString().length();
            }
            i7 = i16 / arrayList2.size();
        } else {
            i7 = 0;
        }
        int i17 = i7;
        Object obj2 = str;
        if (i15 < 0) {
            obj2 = Integer.valueOf(i15);
        }
        Log.i(TAG, "[Gate0] NarrativeDensity: avgLineLength=" + i17 + ", penalty=" + obj2);
        int max = Math.max(i9 + i10 + i3 + i12 + i15, 0);
        boolean z = max >= 3;
        Gate0Reason gate0Reason = z ? Gate0Reason.ACCEPTED : (i15 >= 0 || size2 <= 0.6f || i9 != 0) ? i9 == 0 ? Gate0Reason.NO_SECTIONAL_STRUCTURE : i10 == 0 ? Gate0Reason.NO_CHRONOLOGY : (extractionMode != ExtractionMode.OCR || f >= 0.2f) ? Gate0Reason.LOW_STRUCTURAL_EVIDENCE : Gate0Reason.OCR_TOO_FRAGMENTED : Gate0Reason.HIGH_NARRATIVE_DENSITY;
        Log.i(TAG, "[Gate0] FINAL: total=" + max + ", threshold=3, decision=" + (z ? "ACCEPT" : "REJECT") + ", reason=" + gate0Reason + ", mode=" + extractionMode);
        return new Gate0Result(max, 3, z, gate0Reason, extractionMode);
    }

    private final String sanitizeForGate0(String raw) {
        CharSequence $this$any$iv;
        Iterable $this$map$iv = StringsKt.lines(StringsKt.replace$default(raw, "\u00ad", "", false, 4, (Object) null));
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(StringsKt.trim((CharSequence) new Regex("\\s+").replace((String) item$iv$iv, " ")).toString());
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            CharSequence line = (String) element$iv$iv;
            boolean z = true;
            if (!(line.length() == 0)) {
                CharSequence $this$any$iv2 = line;
                int i = 0;
                while (true) {
                    if (i < $this$any$iv2.length()) {
                        char element$iv = $this$any$iv2.charAt(i);
                        if (Character.isLetterOrDigit(element$iv)) {
                            $this$any$iv = 1;
                            break;
                        }
                        i++;
                    } else {
                        $this$any$iv = null;
                        break;
                    }
                }
                if ($this$any$iv == null) {
                    z = false;
                }
            }
            if (z) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        return CollectionsKt.joinToString$default((List) destination$iv$iv2, "\n", null, null, 0, null, null, 62, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object callAi(java.lang.String r11, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r12, kotlin.coroutines.Continuation<? super com.vantedge.app.data.model.StructuredProfileExtraction> r13) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vantedge.app.data.engine.ProfileExtractionEngine.callAi(java.lang.String, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: ProfileExtractionEngine.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lcom/vantedge/app/data/engine/ProfileExtractionEngine$RepairResult;", "", "json", "", "isSafe", "", "(Ljava/lang/String;Z)V", "()Z", "getJson", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class RepairResult {
        private final boolean isSafe;
        private final String json;

        public static /* synthetic */ RepairResult copy$default(RepairResult repairResult, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = repairResult.json;
            }
            if ((i & 2) != 0) {
                z = repairResult.isSafe;
            }
            return repairResult.copy(str, z);
        }

        /* renamed from: component1, reason: from getter */
        public final String getJson() {
            return this.json;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getIsSafe() {
            return this.isSafe;
        }

        public final RepairResult copy(String json, boolean isSafe) {
            Intrinsics.checkNotNullParameter(json, "json");
            return new RepairResult(json, isSafe);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RepairResult)) {
                return false;
            }
            RepairResult repairResult = (RepairResult) other;
            return Intrinsics.areEqual(this.json, repairResult.json) && this.isSafe == repairResult.isSafe;
        }

        public int hashCode() {
            return (this.json.hashCode() * 31) + Boolean.hashCode(this.isSafe);
        }

        public String toString() {
            return "RepairResult(json=" + this.json + ", isSafe=" + this.isSafe + ")";
        }

        public RepairResult(String json, boolean isSafe) {
            Intrinsics.checkNotNullParameter(json, "json");
            this.json = json;
            this.isSafe = isSafe;
        }

        public final String getJson() {
            return this.json;
        }

        public final boolean isSafe() {
            return this.isSafe;
        }
    }

    private final RepairResult safeRepairJson(String raw) {
        Character ch;
        Character ch2;
        StringBuilder sb = new StringBuilder();
        Stack stack = new Stack();
        boolean inString = false;
        boolean escaped = false;
        String $this$forEach$iv = raw;
        for (int i = 0; i < $this$forEach$iv.length(); i++) {
            char element$iv = $this$forEach$iv.charAt(i);
            sb.append(element$iv);
            if (inString) {
                if (escaped) {
                    escaped = false;
                } else if (element$iv == '\\') {
                    escaped = true;
                } else if (element$iv == '\"') {
                    inString = false;
                }
            } else if (element$iv == '\"') {
                inString = true;
            } else if (element$iv == '{') {
                stack.push(Character.valueOf(AbstractJsonLexerKt.BEGIN_OBJ));
            } else if (element$iv == '[') {
                stack.push(Character.valueOf(AbstractJsonLexerKt.BEGIN_LIST));
            } else if (element$iv == '}') {
                if ((!stack.isEmpty()) && (ch2 = (Character) stack.peek()) != null && ch2.charValue() == '{') {
                    stack.pop();
                }
            } else if (element$iv == ']' && (!stack.isEmpty()) && (ch = (Character) stack.peek()) != null && ch.charValue() == '[') {
                stack.pop();
            }
        }
        int missingBrackets = stack.size();
        if (inString || missingBrackets > 2) {
            return new RepairResult(raw, false);
        }
        while (!stack.isEmpty()) {
            Character ch3 = (Character) stack.pop();
            if (ch3 != null && ch3.charValue() == '{') {
                sb.append("}");
            } else if (ch3 != null && ch3.charValue() == '[') {
                sb.append("]");
            }
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        String finalString = new Regex(",\\s*\\]").replace(new Regex(",\\s*\\}").replace(sb2, "}"), "]");
        return new RepairResult(finalString, true);
    }

    private final JSONObject normalizeRootKeys(JSONObject json) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", json.optString("name", json.optString("fullName", "")));
        jSONObject.put("email", json.optString("email", json.optString(HintConstants.AUTOFILL_HINT_EMAIL_ADDRESS, "")));
        jSONObject.put(HintConstants.AUTOFILL_HINT_PHONE, json.optString(HintConstants.AUTOFILL_HINT_PHONE, json.optString(HintConstants.AUTOFILL_HINT_PHONE_NUMBER, "")));
        jSONObject.put(ErrorBundle.SUMMARY_ENTRY, json.optString(ErrorBundle.SUMMARY_ENTRY, json.optString(Scopes.PROFILE, json.optString("about", ""))));
        JSONArray skillsArray = json.optJSONArray("skills");
        if (skillsArray == null) {
            skillsArray = json.optJSONArray("core_skills");
        }
        if (skillsArray == null) {
            skillsArray = new JSONArray();
        }
        jSONObject.put("skills", skillsArray);
        JSONArray workArray = json.optJSONArray("workHistory");
        if (workArray == null) {
            workArray = json.optJSONArray("experience");
        }
        if (workArray == null) {
            workArray = new JSONArray();
        }
        JSONArray normalizedWork = new JSONArray();
        int length = workArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject w = workArray.optJSONObject(i);
            if (w != null) {
                JSONObject nw = new JSONObject();
                nw.put("jobTitle", w.optString("jobTitle", w.optString("position", w.optString("role", ""))));
                nw.put("company", w.optString("company", w.optString("employer", w.optString("organization", ""))));
                String dates = w.optString("dates", w.optString("period", ""));
                Intrinsics.checkNotNull(dates);
                if (!StringsKt.isBlank(dates)) {
                    String optString = w.optString("startDate");
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    if (StringsKt.isBlank(optString)) {
                        nw.put("startDate", dates);
                        nw.put("endDate", "");
                        nw.put("description", w.optString("description", w.optString("responsibilities", w.optString("duties", ""))));
                        normalizedWork.put(nw);
                    }
                }
                nw.put("startDate", w.optString("startDate", ""));
                nw.put("endDate", w.optString("endDate", ""));
                nw.put("description", w.optString("description", w.optString("responsibilities", w.optString("duties", ""))));
                normalizedWork.put(nw);
            }
        }
        jSONObject.put("workHistory", normalizedWork);
        JSONArray eduArray = json.optJSONArray("education");
        if (eduArray == null) {
            eduArray = json.optJSONArray("academic");
        }
        if (eduArray == null) {
            eduArray = new JSONArray();
        }
        JSONArray normalizedEdu = new JSONArray();
        int length2 = eduArray.length();
        for (int i2 = 0; i2 < length2; i2++) {
            JSONObject e = eduArray.optJSONObject(i2);
            if (e != null) {
                JSONObject ne = new JSONObject();
                ne.put("institution", e.optString("institution", e.optString("school", e.optString("university", ""))));
                ne.put("qualification", e.optString("qualification", e.optString("degree", "")));
                ne.put("fieldOfStudy", e.optString("fieldOfStudy", e.optString("major", "")));
                ne.put("graduationYear", e.optString("graduationYear", e.optString("year", "")));
                normalizedEdu.put(ne);
            }
        }
        jSONObject.put("education", normalizedEdu);
        JSONArray optJSONArray = json.optJSONArray("certifications");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        jSONObject.put("certifications", optJSONArray);
        return jSONObject;
    }

    private final StructuredProfileExtraction parse(JSONObject json) {
        float f;
        JSONArray eduArray;
        int i;
        JSONArray skillsArray;
        JSONArray workArray;
        int i2;
        JSONArray skillsArray2 = json.optJSONArray("skills");
        JSONArray workArray2 = json.optJSONArray("workHistory");
        JSONArray eduArray2 = json.optJSONArray("education");
        JSONArray certArray = json.optJSONArray("certifications");
        List skills = new ArrayList();
        int i3 = 0;
        int length = skillsArray2 != null ? skillsArray2.length() : 0;
        while (true) {
            f = 0.8f;
            if (i3 >= length) {
                break;
            }
            Intrinsics.checkNotNull(skillsArray2);
            String skill = skillsArray2.optString(i3);
            Intrinsics.checkNotNull(skill);
            if (!StringsKt.isBlank(skill)) {
                skills.add(new ExtractedField(skill, 0.8f, ExtractionSourceType.INFERRED));
            }
            i3++;
        }
        List work = new ArrayList();
        int i4 = 0;
        int length2 = workArray2 != null ? workArray2.length() : 0;
        while (i4 < length2) {
            Intrinsics.checkNotNull(workArray2);
            JSONObject o = workArray2.optJSONObject(i4);
            if (o == null) {
                skillsArray = skillsArray2;
                workArray = workArray2;
                i2 = length2;
            } else {
                String title = o.optString("jobTitle");
                String company = o.optString("company");
                Intrinsics.checkNotNull(title);
                if (!(!StringsKt.isBlank(title))) {
                    Intrinsics.checkNotNull(company);
                    if (!(!StringsKt.isBlank(company))) {
                        skillsArray = skillsArray2;
                        workArray = workArray2;
                        i2 = length2;
                    }
                }
                ExtractedField extractedField = new ExtractedField(title, f, ExtractionSourceType.INFERRED);
                Intrinsics.checkNotNull(company);
                skillsArray = skillsArray2;
                ExtractedField extractedField2 = new ExtractedField(company, f, ExtractionSourceType.INFERRED);
                String optString = o.optString("startDate");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                workArray = workArray2;
                i2 = length2;
                ExtractedField it = new ExtractedField(optString, 0.8f, ExtractionSourceType.INFERRED);
                ExtractedField extractedField3 = StringsKt.isBlank(it.getValue()) ^ true ? it : null;
                String optString2 = o.optString("endDate");
                Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                ExtractedField it2 = new ExtractedField(optString2, 0.8f, ExtractionSourceType.INFERRED);
                ExtractedField extractedField4 = StringsKt.isBlank(it2.getValue()) ^ true ? it2 : null;
                String optString3 = o.optString("description");
                Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                ExtractedField it3 = new ExtractedField(optString3, 0.8f, ExtractionSourceType.INFERRED);
                work.add(new ExtractedExperience(extractedField, extractedField2, extractedField3, extractedField4, StringsKt.isBlank(it3.getValue()) ^ true ? it3 : null, 0.8f));
            }
            i4++;
            skillsArray2 = skillsArray;
            workArray2 = workArray;
            length2 = i2;
            f = 0.8f;
        }
        List edu = new ArrayList();
        int i5 = 0;
        int length3 = eduArray2 != null ? eduArray2.length() : 0;
        while (i5 < length3) {
            Intrinsics.checkNotNull(eduArray2);
            JSONObject o2 = eduArray2.optJSONObject(i5);
            if (o2 == null) {
                eduArray = eduArray2;
                i = length3;
            } else {
                String inst = o2.optString("institution");
                String qual = o2.optString("qualification");
                Intrinsics.checkNotNull(inst);
                if (!(!StringsKt.isBlank(inst))) {
                    Intrinsics.checkNotNull(qual);
                    if (!(!StringsKt.isBlank(qual))) {
                        eduArray = eduArray2;
                        i = length3;
                    }
                }
                ExtractedField extractedField5 = new ExtractedField(inst, 0.8f, ExtractionSourceType.INFERRED);
                Intrinsics.checkNotNull(qual);
                ExtractedField extractedField6 = new ExtractedField(qual, 0.8f, ExtractionSourceType.INFERRED);
                String optString4 = o2.optString("fieldOfStudy");
                Intrinsics.checkNotNullExpressionValue(optString4, "optString(...)");
                eduArray = eduArray2;
                i = length3;
                ExtractedField it4 = new ExtractedField(optString4, 0.8f, ExtractionSourceType.INFERRED);
                ExtractedField extractedField7 = StringsKt.isBlank(it4.getValue()) ^ true ? it4 : null;
                String optString5 = o2.optString("graduationYear");
                Intrinsics.checkNotNullExpressionValue(optString5, "optString(...)");
                ExtractedField it5 = new ExtractedField(optString5, 0.8f, ExtractionSourceType.INFERRED);
                edu.add(new ExtractedEducation(extractedField5, extractedField6, extractedField7, StringsKt.isBlank(it5.getValue()) ^ true ? it5 : null, 0.8f));
            }
            i5++;
            eduArray2 = eduArray;
            length3 = i;
        }
        List certs = new ArrayList();
        int length4 = certArray != null ? certArray.length() : 0;
        for (int i6 = 0; i6 < length4; i6++) {
            Intrinsics.checkNotNull(certArray);
            JSONObject o3 = certArray.optJSONObject(i6);
            if (o3 != null) {
                String name = o3.optString("name");
                Intrinsics.checkNotNull(name);
                if (!StringsKt.isBlank(name)) {
                    ExtractedField extractedField8 = new ExtractedField(name, 0.8f, ExtractionSourceType.INFERRED);
                    String optString6 = o3.optString("issuer");
                    Intrinsics.checkNotNullExpressionValue(optString6, "optString(...)");
                    ExtractedField it6 = new ExtractedField(optString6, 0.8f, ExtractionSourceType.INFERRED);
                    if (!(!StringsKt.isBlank(it6.getValue()))) {
                        it6 = null;
                    }
                    certs.add(new ExtractedCertification(extractedField8, it6, 0.8f));
                }
            }
        }
        float confidence = ((skills.isEmpty() ^ true) && (work.isEmpty() ^ true)) ? 0.85f : ((skills.isEmpty() ^ true) || (work.isEmpty() ^ true) || (edu.isEmpty() ^ true)) ? 0.55f : 0.2f;
        String optString7 = json.optString("name");
        Intrinsics.checkNotNullExpressionValue(optString7, "optString(...)");
        ExtractedField it7 = new ExtractedField(optString7, 0.8f, ExtractionSourceType.INFERRED);
        ExtractedField extractedField9 = StringsKt.isBlank(it7.getValue()) ^ true ? it7 : null;
        String optString8 = json.optString("email");
        Intrinsics.checkNotNullExpressionValue(optString8, "optString(...)");
        ExtractedField it8 = new ExtractedField(optString8, 0.8f, ExtractionSourceType.INFERRED);
        ExtractedField extractedField10 = StringsKt.isBlank(it8.getValue()) ^ true ? it8 : null;
        String optString9 = json.optString(HintConstants.AUTOFILL_HINT_PHONE);
        Intrinsics.checkNotNullExpressionValue(optString9, "optString(...)");
        ExtractedField it9 = new ExtractedField(optString9, 0.8f, ExtractionSourceType.INFERRED);
        ExtractedField extractedField11 = StringsKt.isBlank(it9.getValue()) ^ true ? it9 : null;
        String optString10 = json.optString(ErrorBundle.SUMMARY_ENTRY);
        Intrinsics.checkNotNullExpressionValue(optString10, "optString(...)");
        ExtractedField it10 = new ExtractedField(optString10, 0.8f, ExtractionSourceType.INFERRED);
        ExtractedPersonalInfo extractedPersonalInfo = new ExtractedPersonalInfo(extractedField9, extractedField10, extractedField11, null, null, StringsKt.isBlank(it10.getValue()) ^ true ? it10 : null);
        String optString11 = json.optString(ErrorBundle.SUMMARY_ENTRY);
        Intrinsics.checkNotNullExpressionValue(optString11, "optString(...)");
        ExtractedField it11 = new ExtractedField(optString11, 0.8f, ExtractionSourceType.INFERRED);
        return new StructuredProfileExtraction(extractedPersonalInfo, StringsKt.isBlank(it11.getValue()) ^ true ? it11 : null, ExtensionsKt.toImmutableList(skills), ExtensionsKt.persistentListOf(), ExtensionsKt.toImmutableList(work), ExtensionsKt.toImmutableList(edu), ExtensionsKt.toImmutableList(certs), confidence, ExtensionsKt.persistentListOf(), new ExtractionMetadata("openrouter-strict"));
    }

    private final boolean isValidExtraction(StructuredProfileExtraction extraction) {
        boolean hasIdentity = (extraction.getPersonalInfo().getName() == null && extraction.getPersonalInfo().getEmail() == null) ? false : true;
        boolean hasSemanticData = (extraction.getSkills().isEmpty() ^ true) || (extraction.getWorkHistory().isEmpty() ^ true);
        return hasIdentity && hasSemanticData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String extractPdf(Uri uri) {
        InputStream input = this.context.getContentResolver().openInputStream(uri);
        if (input == null) {
            return "";
        }
        PDDocument doc = PDDocument.load(input);
        try {
            String text = new PDFTextStripper().getText(doc);
            Intrinsics.checkNotNull(text);
            return text;
        } finally {
            doc.close();
            input.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String extractPdfOcr(Uri uri) {
        ParcelFileDescriptor descriptor = this.context.getContentResolver().openFileDescriptor(uri, PDPageLabelRange.STYLE_ROMAN_LOWER);
        Intrinsics.checkNotNull(descriptor);
        PdfRenderer renderer = new PdfRenderer(descriptor);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Intrinsics.checkNotNullExpressionValue(recognizer, "getClient(...)");
        StringBuilder sb = new StringBuilder();
        try {
            int pages = Math.min(renderer.getPageCount(), 5);
            for (int i = 0; i < pages; i++) {
                PdfRenderer.Page page = renderer.openPage(i);
                Bitmap bmp = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                Intrinsics.checkNotNullExpressionValue(bmp, "createBitmap(...)");
                page.render(bmp, null, null, 1);
                InputImage img = InputImage.fromBitmap(bmp, 0);
                Intrinsics.checkNotNullExpressionValue(img, "fromBitmap(...)");
                Text text = (Text) Tasks.await(recognizer.process(img));
                sb.append(text.getText()).append("\n");
                bmp.recycle();
                page.close();
            }
            renderer.close();
            descriptor.close();
            recognizer.close();
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
            return sb2;
        } catch (Throwable th) {
            renderer.close();
            descriptor.close();
            recognizer.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String extractImageOcr(Uri uri) {
        InputStream input = this.context.getContentResolver().openInputStream(uri);
        Intrinsics.checkNotNull(input);
        Bitmap bmp = BitmapFactory.decodeStream(input);
        Intrinsics.checkNotNull(bmp);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Intrinsics.checkNotNullExpressionValue(recognizer, "getClient(...)");
        try {
            InputImage img = InputImage.fromBitmap(bmp, 0);
            Intrinsics.checkNotNullExpressionValue(img, "fromBitmap(...)");
            String text = ((Text) Tasks.await(recognizer.process(img))).getText();
            Intrinsics.checkNotNull(text);
            return text;
        } finally {
            bmp.recycle();
            input.close();
            recognizer.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String extractDocx(Uri uri) {
        InputStream input = this.context.getContentResolver().openInputStream(uri);
        Intrinsics.checkNotNull(input);
        ZipInputStream zip = new ZipInputStream(input);
        StringBuilder sb = new StringBuilder();
        ZipEntry entry = zip.getNextEntry();
        while (true) {
            if (entry == null) {
                break;
            }
            if (Intrinsics.areEqual(entry.getName(), "word/document.xml")) {
                String xml = new String(ByteStreamsKt.readBytes(zip), Charsets.UTF_8);
                sb.append(new Regex("\\s+").replace(new Regex("<[^>]+>").replace(xml, " "), " "));
                break;
            }
            entry = zip.getNextEntry();
        }
        zip.close();
        input.close();
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String extractPlain(Uri uri) {
        InputStream input = this.context.getContentResolver().openInputStream(uri);
        Intrinsics.checkNotNull(input);
        Reader inputStreamReader = new InputStreamReader(input, Charsets.UTF_8);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        try {
            BufferedReader it = bufferedReader;
            String readText = TextStreamsKt.readText(it);
            CloseableKt.closeFinally(bufferedReader, null);
            return readText;
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getFileName(Uri uri) {
        String str = "";
        Cursor query = this.context.getContentResolver().query(uri, null, null, null, null);
        if (query != null) {
            Cursor cursor = query;
            try {
                Cursor c = cursor;
                int i = c.getColumnIndex("_display_name");
                if (c.moveToFirst() && i >= 0) {
                    String string = c.getString(i);
                    if (string == null) {
                        string = "";
                    } else {
                        Intrinsics.checkNotNull(string);
                    }
                    str = string;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        return str;
    }
}
