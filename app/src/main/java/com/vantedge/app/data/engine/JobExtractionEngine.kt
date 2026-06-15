package com.vantedge.app.data.engine;

import android.util.Log;
import com.vantedge.app.data.model.ConfidenceBreakdown;
import com.vantedge.app.data.model.Gate0JobReason;
import com.vantedge.app.data.model.Gate0JobResult;
import com.vantedge.app.data.model.JobExtractionMetrics;
import com.vantedge.app.data.model.JobExtractionResult;
import com.vantedge.app.data.model.JobSourceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.bouncycastle.i18n.MessageBundle;

/* compiled from: JobExtractionEngine.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0002J)\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0010\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u0006H\u0002\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006 "}, d2 = {"Lcom/vantedge/app/data/engine/JobExtractionEngine;", "", "()V", "calculateConfidence", "Lcom/vantedge/app/data/model/ConfidenceBreakdown;", MessageBundle.TITLE_ENTRY, "", "company", "gate0Score", "", "calculateNarrativeDensity", "", "nonEmptyLines", "", "extractJob", "Lkotlin/Result;", "Lcom/vantedge/app/data/model/JobExtractionResult;", "rawText", "sourceType", "Lcom/vantedge/app/data/model/JobSourceType;", "extractJob-gIAlu-s", "(Ljava/lang/String;Lcom/vantedge/app/data/model/JobSourceType;)Ljava/lang/Object;", "runGate0", "Lcom/vantedge/app/data/model/Gate0JobResult;", "runGate1", "Lcom/vantedge/app/data/engine/JobExtractionEngine$Gate1Result;", "sanitizeForGate0", "raw", "sanitizeForGate2", "description", "Companion", "Gate1Result", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class JobExtractionEngine {
    public static final int $stable = 0;
    private static final int GATE_0_PASS_THRESHOLD = 2;
    private static final String TAG = "JobExtractionEngine";
    private static final List<String> GATE_0_SIGNALS = CollectionsKt.listOf((Object[]) new String[]{"responsibilities", "requirements", "qualifications", "vacancy", "closing date", "job description", "about the role", "key duties", "experience required", "apply now", "position", "employment type", "salary"});

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.vantedge.app.data.engine.JobExtractionEngine] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* renamed from: extractJob-gIAlu-s, reason: not valid java name */
    public final Object m6419extractJobgIAlus(String rawText, JobSourceType sourceType) {
        String str;
        String str2 = this;
        Intrinsics.checkNotNullParameter(rawText, "rawText");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        Log.d(TAG, "[JobExtraction] THREAD=" + Thread.currentThread().getName() + " | RAW_LENGTH=" + rawText.length());
        try {
            long startTime = System.currentTimeMillis();
            if (StringsKt.isBlank(rawText)) {
                Log.e(TAG, "[JobExtraction] FAILED: empty input");
                Result.Companion companion = Result.INSTANCE;
                return Result.m6582constructorimpl(ResultKt.createFailure(new Exception("EMPTY_DESCRIPTION")));
            }
            long gate0Start = System.currentTimeMillis();
            Gate0JobResult gate0Result = runGate0(rawText);
            long gate0Duration = System.currentTimeMillis() - gate0Start;
            String format = String.format("[Gate0] SIGNAL_ANALYTICS: score=" + gate0Result.getScore() + " | signals=" + gate0Result.getDetectedSignals() + " | penalties=" + gate0Result.getAppliedPenalties() + " | narrativeDensity=%.2f", Arrays.copyOf(new Object[]{Float.valueOf(gate0Result.getNarrativeDensity())}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            Log.i(TAG, format);
            if (!gate0Result.getAccepted()) {
                Log.i(TAG, "[Gate0] REJECTED: reason=" + gate0Result.getReason());
                Result.Companion companion2 = Result.INSTANCE;
                return Result.m6582constructorimpl(ResultKt.createFailure(new Exception("GATE0_REJECTED")));
            }
            long gate1Start = System.currentTimeMillis();
            Gate1Result gate1Result = runGate1(rawText);
            long gate1Duration = System.currentTimeMillis() - gate1Start;
            Log.i(TAG, "[Gate1] EXIT: titleFound=" + gate1Result.getTitleMethod() + " | companyFound=" + gate1Result.getCompanyMethod() + " | descLength=" + gate1Result.getDescription().length());
            long gate2Start = System.currentTimeMillis();
            String normalizedDescription = str2.sanitizeForGate2(gate1Result.getDescription());
            long gate2Duration = System.currentTimeMillis() - gate2Start;
            Log.i(TAG, "[Gate2] EXIT: normalizedDescLength=" + normalizedDescription.length());
            long gate3Start = System.currentTimeMillis();
            ConfidenceBreakdown confidenceResult = str2.calculateConfidence(gate1Result.getTitle(), gate1Result.getCompany(), gate0Result.getScore());
            long gate3Duration = System.currentTimeMillis() - gate3Start;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format2 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(confidenceResult.getFinalScore())}, 1));
            Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
            Log.i(TAG, "[Gate3] EXIT: confidence=" + format2 + " | breakdown=" + confidenceResult);
            long gate4Start = System.currentTimeMillis();
            try {
                if (StringsKt.isBlank(normalizedDescription)) {
                    str = TAG;
                } else {
                    if (normalizedDescription.length() >= 10) {
                        String extractionId = UUID.randomUUID().toString();
                        Intrinsics.checkNotNullExpressionValue(extractionId, "toString(...)");
                        long extractedAt = System.currentTimeMillis();
                        long totalDuration = extractedAt - startTime;
                        try {
                            JobExtractionMetrics metrics = new JobExtractionMetrics(totalDuration, sourceType, rawText.length(), gate0Duration, gate1Duration, gate2Duration, gate3Duration, System.currentTimeMillis() - gate4Start, gate0Result.getAccepted(), gate0Result.getNarrativeDensity());
                            String it = gate1Result.getTitle();
                            String str3 = StringsKt.isBlank(it) ^ true ? it : null;
                            String it2 = gate1Result.getCompany();
                            JobExtractionResult result = new JobExtractionResult(extractionId, extractedAt, str3, StringsKt.isBlank(it2) ^ true ? it2 : null, normalizedDescription, confidenceResult.getFinalScore(), confidenceResult, gate0Result, metrics);
                            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                            String format3 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(result.getConfidenceScore())}, 1));
                            Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
                            Log.i(TAG, "[Gate4] EXIT: extractionId=" + extractionId + " | confidence=" + format3 + " | passed=true");
                            Result.Companion companion3 = Result.INSTANCE;
                            return Result.m6582constructorimpl(result);
                        } catch (Exception e) {
                            e = e;
                            str2 = TAG;
                            Log.e(str2, "[JobExtraction] EXCEPTION: " + e.getMessage(), e);
                            Result.Companion companion4 = Result.INSTANCE;
                            return Result.m6582constructorimpl(ResultKt.createFailure(e));
                        }
                    }
                    str = TAG;
                }
                Log.i(str, "[Gate4] REJECTED: description blank or too short (len=" + normalizedDescription.length() + ")");
                Result.Companion companion5 = Result.INSTANCE;
                return Result.m6582constructorimpl(ResultKt.createFailure(new Exception("EMPTY_DESCRIPTION")));
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            str2 = TAG;
        }
    }

    private final Gate0JobResult runGate0(String rawText) {
        Gate0JobReason reason;
        String sanitized = sanitizeForGate0(rawText);
        String lower = sanitized.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
        Iterable lines = StringsKt.lines(sanitized);
        Iterable $this$filter$iv = lines;
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            String it2 = (String) element$iv$iv;
            if (true ^ StringsKt.isBlank(it2)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List nonEmptyLines = (List) destination$iv$iv;
        int score = 0;
        List detectedSignals = new ArrayList();
        List appliedPenalties = new ArrayList();
        for (String signal : GATE_0_SIGNALS) {
            if (StringsKt.contains$default((CharSequence) lower, (CharSequence) signal, false, 2, (Object) null)) {
                detectedSignals.add(signal);
                score++;
            }
        }
        float narrativeDensity = calculateNarrativeDensity(nonEmptyLines);
        if (narrativeDensity > 0.6f) {
            score--;
            appliedPenalties.add("HIGH_NARRATIVE_DENSITY");
        }
        int score2 = Math.max(score, 0);
        boolean accepted = score2 >= 2;
        if (accepted) {
            reason = Gate0JobReason.ACCEPTED;
        } else if (detectedSignals.isEmpty()) {
            reason = Gate0JobReason.NO_VACANCY_SIGNALS;
        } else {
            reason = (narrativeDensity <= 0.6f || detectedSignals.size() >= 2) ? Gate0JobReason.LOW_STRUCTURAL_EVIDENCE : Gate0JobReason.HIGH_NARRATIVE_DENSITY;
        }
        return new Gate0JobResult(score2, 2, accepted, reason, detectedSignals, appliedPenalties, !accepted ? CollectionsKt.listOf(reason.name()) : CollectionsKt.emptyList(), narrativeDensity);
    }

    private final float calculateNarrativeDensity(List<String> nonEmptyLines) {
        int count$iv;
        Iterable $this$count$iv;
        Iterable $this$count$iv2;
        char element$iv;
        if (nonEmptyLines.isEmpty()) {
            return 0.0f;
        }
        List<String> $this$count$iv3 = nonEmptyLines;
        int i = 0;
        if (!($this$count$iv3 instanceof Collection) || !$this$count$iv3.isEmpty()) {
            int count$iv2 = 0;
            for (Object element$iv2 : $this$count$iv3) {
                String line = (String) element$iv2;
                String $this$count$iv4 = line;
                int count$iv3 = 0;
                for (int i2 = i; i2 < $this$count$iv4.length(); i2++) {
                    char element$iv3 = $this$count$iv4.charAt(i2);
                    if (Character.isLetter(element$iv3)) {
                        count$iv3++;
                    }
                }
                float alphabeticRatio = count$iv3 / Math.max(line.length(), 1);
                Iterable $this$count$iv5 = new Regex("\\s+").split(line, i);
                if (($this$count$iv5 instanceof Collection) && ((Collection) $this$count$iv5).isEmpty()) {
                    $this$count$iv = $this$count$iv3;
                    count$iv = i;
                } else {
                    count$iv = 0;
                    for (Object element$iv4 : $this$count$iv5) {
                        CharSequence token = (String) element$iv4;
                        CharSequence $this$any$iv = token;
                        int i3 = 0;
                        while (true) {
                            $this$count$iv2 = $this$count$iv3;
                            if (i3 < $this$any$iv.length()) {
                                char element$iv5 = $this$any$iv.charAt(i3);
                                if (Character.isDigit(element$iv5)) {
                                    element$iv = 1;
                                    break;
                                }
                                i3++;
                                $this$count$iv3 = $this$count$iv2;
                            } else {
                                element$iv = 0;
                                break;
                            }
                        }
                        if (element$iv != 0) {
                            count$iv++;
                            if (count$iv < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                            $this$count$iv3 = $this$count$iv2;
                        } else {
                            $this$count$iv3 = $this$count$iv2;
                        }
                    }
                    $this$count$iv = $this$count$iv3;
                }
                int numericTokens = count$iv;
                if (alphabeticRatio > 0.8f && numericTokens < 2) {
                    count$iv2++;
                    if (count$iv2 < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                    $this$count$iv3 = $this$count$iv;
                    i = 0;
                } else {
                    $this$count$iv3 = $this$count$iv;
                    i = 0;
                }
            }
            i = count$iv2;
        }
        int narrativeLines = i;
        return narrativeLines / nonEmptyLines.size();
    }

    /* compiled from: JobExtractionEngine.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/vantedge/app/data/engine/JobExtractionEngine$Gate1Result;", "", MessageBundle.TITLE_ENTRY, "", "company", "description", "titleMethod", "companyMethod", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCompany", "()Ljava/lang/String;", "getCompanyMethod", "getDescription", "getTitle", "getTitleMethod", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Gate1Result {
        private final String company;
        private final String companyMethod;
        private final String description;
        private final String title;
        private final String titleMethod;

        public static /* synthetic */ Gate1Result copy$default(Gate1Result gate1Result, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
            if ((i & 1) != 0) {
                str = gate1Result.title;
            }
            if ((i & 2) != 0) {
                str2 = gate1Result.company;
            }
            String str6 = str2;
            if ((i & 4) != 0) {
                str3 = gate1Result.description;
            }
            String str7 = str3;
            if ((i & 8) != 0) {
                str4 = gate1Result.titleMethod;
            }
            String str8 = str4;
            if ((i & 16) != 0) {
                str5 = gate1Result.companyMethod;
            }
            return gate1Result.copy(str, str6, str7, str8, str5);
        }

        /* renamed from: component1, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        /* renamed from: component2, reason: from getter */
        public final String getCompany() {
            return this.company;
        }

        /* renamed from: component3, reason: from getter */
        public final String getDescription() {
            return this.description;
        }

        /* renamed from: component4, reason: from getter */
        public final String getTitleMethod() {
            return this.titleMethod;
        }

        /* renamed from: component5, reason: from getter */
        public final String getCompanyMethod() {
            return this.companyMethod;
        }

        public final Gate1Result copy(String title, String company, String description, String titleMethod, String companyMethod) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(company, "company");
            Intrinsics.checkNotNullParameter(description, "description");
            Intrinsics.checkNotNullParameter(titleMethod, "titleMethod");
            Intrinsics.checkNotNullParameter(companyMethod, "companyMethod");
            return new Gate1Result(title, company, description, titleMethod, companyMethod);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Gate1Result)) {
                return false;
            }
            Gate1Result gate1Result = (Gate1Result) other;
            return Intrinsics.areEqual(this.title, gate1Result.title) && Intrinsics.areEqual(this.company, gate1Result.company) && Intrinsics.areEqual(this.description, gate1Result.description) && Intrinsics.areEqual(this.titleMethod, gate1Result.titleMethod) && Intrinsics.areEqual(this.companyMethod, gate1Result.companyMethod);
        }

        public int hashCode() {
            return (((((((this.title.hashCode() * 31) + this.company.hashCode()) * 31) + this.description.hashCode()) * 31) + this.titleMethod.hashCode()) * 31) + this.companyMethod.hashCode();
        }

        public String toString() {
            return "Gate1Result(title=" + this.title + ", company=" + this.company + ", description=" + this.description + ", titleMethod=" + this.titleMethod + ", companyMethod=" + this.companyMethod + ")";
        }

        public Gate1Result(String title, String company, String description, String titleMethod, String companyMethod) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(company, "company");
            Intrinsics.checkNotNullParameter(description, "description");
            Intrinsics.checkNotNullParameter(titleMethod, "titleMethod");
            Intrinsics.checkNotNullParameter(companyMethod, "companyMethod");
            this.title = title;
            this.company = company;
            this.description = description;
            this.titleMethod = titleMethod;
            this.companyMethod = companyMethod;
        }

        public final String getTitle() {
            return this.title;
        }

        public final String getCompany() {
            return this.company;
        }

        public final String getDescription() {
            return this.description;
        }

        public final String getTitleMethod() {
            return this.titleMethod;
        }

        public final String getCompanyMethod() {
            return this.companyMethod;
        }
    }

    private final Gate1Result runGate1(String rawText) {
        int i;
        Iterable $this$map$iv = StringsKt.lines(rawText);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            destination$iv$iv.add(StringsKt.trim((CharSequence) it).toString());
        }
        Iterable $this$filter$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it2 = (String) element$iv$iv;
            if (!StringsKt.isBlank(it2)) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        List lines = (List) destination$iv$iv2;
        String title = "";
        String titleMethod = "NONE";
        String company = "";
        String companyMethod = "NONE";
        int consumedLines = 0;
        Iterator it3 = lines.iterator();
        int i2 = 0;
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            int idx = i2;
            i2++;
            String line = (String) it3.next();
            String lower = line.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
            if (new Regex("\\b(position|role|vacancy|job title)\\b", RegexOption.IGNORE_CASE).containsMatchIn(lower)) {
                title = line;
                titleMethod = "KEYWORD_MATCH";
                consumedLines = idx + 1;
                break;
            }
        }
        if (StringsKt.isBlank(title) && (!lines.isEmpty())) {
            Iterator it4 = lines.iterator();
            int i3 = 0;
            while (true) {
                if (!it4.hasNext()) {
                    break;
                }
                int idx2 = i3;
                i3++;
                String line2 = (String) it4.next();
                if (line2.length() < 60) {
                    String $this$count$iv = line2;
                    int count$iv = 0;
                    for (int i4 = 0; i4 < $this$count$iv.length(); i4++) {
                        char element$iv = $this$count$iv.charAt(i4);
                        if (Character.isUpperCase(element$iv)) {
                            count$iv++;
                        }
                    }
                    if (count$iv / line2.length() > 0.4f) {
                        title = line2;
                        titleMethod = "UPPERCASE_HEURISTIC";
                        consumedLines = idx2 + 1;
                        break;
                    }
                }
            }
        }
        if (!StringsKt.isBlank(title) || !(!lines.isEmpty())) {
            i = 0;
        } else {
            i = 0;
            title = (String) lines.get(0);
            titleMethod = "FIRST_LINE";
            consumedLines = 1;
        }
        Iterator it5 = lines.iterator();
        while (true) {
            if (!it5.hasNext()) {
                break;
            }
            int idx3 = i;
            i++;
            String line3 = (String) it5.next();
            if (idx3 >= consumedLines) {
                String lower2 = line3.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lower2, "toLowerCase(...)");
                if (new Regex("Ltd|Inc|Pty|Limited|Corp|LLC", RegexOption.IGNORE_CASE).containsMatchIn(line3)) {
                    company = line3;
                    companyMethod = "LEGAL_ENTITY_PATTERN";
                    consumedLines = Math.max(consumedLines, idx3 + 1);
                    break;
                }
                if (new Regex("we are hiring|join us|about our company").containsMatchIn(lower2)) {
                    company = line3;
                    companyMethod = "BRANDING_PATTERN";
                    consumedLines = Math.max(consumedLines, idx3 + 1);
                    break;
                }
            }
        }
        if (StringsKt.isBlank(company) && lines.size() > consumedLines) {
            String candidate = (String) lines.get(consumedLines);
            boolean isLocationPattern = new Regex("remote|location|based", RegexOption.IGNORE_CASE).containsMatchIn(candidate);
            if (!isLocationPattern) {
                company = candidate;
                companyMethod = "SECOND_LINE_FALLBACK";
                consumedLines++;
            }
        }
        String description = CollectionsKt.joinToString$default(CollectionsKt.drop(lines, consumedLines), "\n", null, null, 0, null, null, 62, null);
        return new Gate1Result(title, company, description, titleMethod, companyMethod);
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

    private final String sanitizeForGate2(String description) {
        return StringsKt.trim((CharSequence) new Regex("\\s+").replace(StringsKt.replace$default(description, "\u00ad", "", false, 4, (Object) null), " ")).toString();
    }

    private final ConfidenceBreakdown calculateConfidence(String title, String company, int gate0Score) {
        List penalties = new ArrayList();
        float titleContribution = (!(StringsKt.isBlank(title) ^ true) || title.length() <= 5) ? 0.0f : 0.2f;
        float baseScore = 0.5f + titleContribution;
        float companyContribution = (!(StringsKt.isBlank(company) ^ true) || company.length() <= 3) ? 0.0f : 0.2f;
        float baseScore2 = baseScore + companyContribution;
        float qualificationContribution = gate0Score >= 4 ? 0.1f : 0.0f;
        float baseScore3 = baseScore2 + qualificationContribution;
        if (StringsKt.isBlank(title) && StringsKt.isBlank(company)) {
            baseScore3 -= 0.1f;
            penalties.add("MISSING_TITLE_AND_COMPANY");
        } else if (StringsKt.isBlank(title)) {
            baseScore3 -= 0.05f;
            penalties.add("MISSING_TITLE");
        } else if (StringsKt.isBlank(company)) {
            baseScore3 -= 0.05f;
            penalties.add("MISSING_COMPANY");
        }
        float finalScore = RangesKt.coerceIn(baseScore3, 0.0f, 1.0f);
        return new ConfidenceBreakdown(0.5f, titleContribution, companyContribution, qualificationContribution, penalties, finalScore);
    }
}
