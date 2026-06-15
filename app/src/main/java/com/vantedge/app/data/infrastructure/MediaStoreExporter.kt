package com.vantedge.app.data.infrastructure;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: MediaStoreExporter.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\r"}, d2 = {"Lcom/vantedge/app/data/infrastructure/MediaStoreExporter;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "save", "Lkotlin/Result;", "", "jobTitle", "docxBytes", "", "save-gIAlu-s", "(Ljava/lang/String;[B)Ljava/lang/Object;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class MediaStoreExporter {
    public static final int $stable = 8;
    private final Context context;

    public MediaStoreExporter(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* renamed from: save-gIAlu-s, reason: not valid java name */
    public final Object m6423savegIAlus(String jobTitle, byte[] docxBytes) {
        Intrinsics.checkNotNullParameter(jobTitle, "jobTitle");
        Intrinsics.checkNotNullParameter(docxBytes, "docxBytes");
        try {
            Result.Companion companion = Result.INSTANCE;
            MediaStoreExporter $this$save_gIAlu_s_u24lambda_u242 = this;
            String sanitised = StringsKt.replace$default(new Regex("[^a-zA-Z0-9_ -]").replace(jobTitle, ""), " ", "_", false, 4, (Object) null);
            String fileName = "VantEdge_CV_" + sanitised + "_" + System.currentTimeMillis() + ".docx";
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put("_display_name", fileName);
                values.put("mime_type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                values.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
                Uri uri = $this$save_gIAlu_s_u24lambda_u242.context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    throw new Exception("Failed to create MediaStore entry");
                }
                Intrinsics.checkNotNull(uri);
                OutputStream openOutputStream = $this$save_gIAlu_s_u24lambda_u242.context.getContentResolver().openOutputStream(uri);
                Unit unit = null;
                if (openOutputStream != null) {
                    OutputStream outputStream = openOutputStream;
                    try {
                        OutputStream stream = outputStream;
                        stream.write(docxBytes);
                        Unit unit2 = Unit.INSTANCE;
                        CloseableKt.closeFinally(outputStream, null);
                        unit = Unit.INSTANCE;
                    } finally {
                    }
                }
                if (unit == null) {
                    throw new Exception("Failed to open output stream");
                }
            } else {
                File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                FilesKt.writeBytes(new File(downloads, fileName), docxBytes);
            }
            return Result.m6582constructorimpl(fileName);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            return Result.m6582constructorimpl(ResultKt.createFailure(th));
        }
    }
}
