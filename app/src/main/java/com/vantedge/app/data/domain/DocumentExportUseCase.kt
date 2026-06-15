package com.vantedge.app.data.domain;

import com.vantedge.app.data.engine.DocxBuilder;
import com.vantedge.app.data.infrastructure.MediaStoreExporter;
import com.vantedge.app.data.model.CycleState;
import com.vantedge.app.data.model.GenerationCycle;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* compiled from: DocumentExportUseCase.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0000¢\u0006\u0002\b\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u0010"}, d2 = {"Lcom/vantedge/app/data/domain/DocumentExportUseCase;", "", "exporter", "Lcom/vantedge/app/data/infrastructure/MediaStoreExporter;", "(Lcom/vantedge/app/data/infrastructure/MediaStoreExporter;)V", "export", "Lkotlin/Result;", "Lcom/vantedge/app/data/domain/ExportReceipt;", "cycle", "Lcom/vantedge/app/data/model/GenerationCycle;", "export-IoAF18A", "(Lcom/vantedge/app/data/model/GenerationCycle;)Ljava/lang/Object;", "stripHtmlToPlainText", "", "html", "stripHtmlToPlainText$app_debug", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class DocumentExportUseCase {
    public static final int $stable = 8;
    private final MediaStoreExporter exporter;

    public DocumentExportUseCase(MediaStoreExporter exporter) {
        Intrinsics.checkNotNullParameter(exporter, "exporter");
        this.exporter = exporter;
    }

    /* renamed from: export-IoAF18A, reason: not valid java name */
    public final Object m6418exportIoAF18A(GenerationCycle cycle) {
        Intrinsics.checkNotNullParameter(cycle, "cycle");
        CycleState state = cycle.getState();
        CycleState.FullCycle fullState = state instanceof CycleState.FullCycle ? (CycleState.FullCycle) state : null;
        if (fullState == null) {
            Result.Companion companion = Result.INSTANCE;
            return Result.m6582constructorimpl(ResultKt.createFailure(new Exception("Export requires FullCycle state, but cycle is " + Reflection.getOrCreateKotlinClass(cycle.getState().getClass()).getSimpleName())));
        }
        String plainText = stripHtmlToPlainText$app_debug(fullState.getCvContent());
        if (StringsKt.isBlank(plainText)) {
            Result.Companion companion2 = Result.INSTANCE;
            return Result.m6582constructorimpl(ResultKt.createFailure(new Exception("CV content is empty — nothing to export")));
        }
        byte[] docxBytes = DocxBuilder.INSTANCE.build(plainText);
        Object m6423savegIAlus = this.exporter.m6423savegIAlus(cycle.getJobTitle(), docxBytes);
        if (!Result.m6589isSuccessimpl(m6423savegIAlus)) {
            return Result.m6582constructorimpl(m6423savegIAlus);
        }
        Result.Companion companion3 = Result.INSTANCE;
        String fileName = (String) m6423savegIAlus;
        return Result.m6582constructorimpl(new ExportReceipt(fileName, cycle.getJobTitle(), cycle.getId()));
    }

    public final String stripHtmlToPlainText$app_debug(String html) {
        Intrinsics.checkNotNullParameter(html, "html");
        return StringsKt.trim((CharSequence) new Regex("\n{3,}").replace(new Regex("<[^>]+>").replace(new Regex("<b>|</b>|<strong>|</strong>", RegexOption.IGNORE_CASE).replace(new Regex("<[bh]r\\s*/?>", RegexOption.IGNORE_CASE).replace(new Regex("</li>", RegexOption.IGNORE_CASE).replace(new Regex("<li[^>]*>", RegexOption.IGNORE_CASE).replace(new Regex("</p>", RegexOption.IGNORE_CASE).replace(html, "\n"), "- "), "\n"), "\n"), "**"), ""), "\n\n")).toString();
    }
}
