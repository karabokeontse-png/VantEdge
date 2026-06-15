package com.vantedge.app.ui.screens;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: JobInputScreen.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "com.vantedge.app.ui.screens.JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1", f = "JobInputScreen.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes6.dex */
final class JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ String $mimeType;
    final /* synthetic */ Uri $uri;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1(String str, Context context, Uri uri, Continuation<? super JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1> continuation) {
        super(2, continuation);
        this.$mimeType = str;
        this.$context = context;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1(this.$mimeType, this.$context, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
        return ((JobInputScreenKt$JobInputScreen$fileLauncher$1$1$extracted$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                try {
                    String str = this.$mimeType;
                    if (Intrinsics.areEqual(str, "application/pdf")) {
                        PDFBoxResourceLoader.init(this.$context);
                        InputStream inputStream = this.$context.getContentResolver().openInputStream(this.$uri);
                        PDDocument doc = PDDocument.load(inputStream);
                        String text = new PDFTextStripper().getText(doc);
                        doc.close();
                        return text;
                    }
                    if (!Intrinsics.areEqual(str, "text/plain")) {
                        return null;
                    }
                    InputStream openInputStream = this.$context.getContentResolver().openInputStream(this.$uri);
                    if (openInputStream != null) {
                        Reader inputStreamReader = new InputStreamReader(openInputStream, Charsets.UTF_8);
                        String readText = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                        if (readText != null) {
                            return readText;
                        }
                    }
                    return "";
                } catch (Exception e) {
                    Log.e("JobInputScreen", "File error: " + e.getMessage());
                    return null;
                }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
