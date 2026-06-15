package com.vantedge.app.data.engine;

import com.tom_roush.pdfbox.contentstream.operator.OperatorName;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.bouncycastle.i18n.TextBundle;

/* compiled from: DocxBuilder.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0002J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0002¨\u0006\n"}, d2 = {"Lcom/vantedge/app/data/engine/DocxBuilder;", "", "()V", "build", "", "plainText", "", "escapeXml", TextBundle.TEXT_ENTRY, "processInline", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DocxBuilder {
    public static final int $stable = 0;
    public static final DocxBuilder INSTANCE = new DocxBuilder();

    private DocxBuilder() {
    }

    public final byte[] build(String plainText) {
        Intrinsics.checkNotNullParameter(plainText, "plainText");
        List lines = StringsKt.split$default((CharSequence) plainText, new String[]{"\n"}, false, 0, 6, (Object) null);
        String paragraphs = CollectionsKt.joinToString$default(lines, "", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.vantedge.app.data.engine.DocxBuilder$build$paragraphs$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String line) {
                String processInline;
                String content;
                String escapeXml;
                Intrinsics.checkNotNullParameter(line, "line");
                String trimmed = StringsKt.trim((CharSequence) line).toString();
                if (trimmed.length() == 0) {
                    return "<w:p/>";
                }
                String upperCase = trimmed.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                if (Intrinsics.areEqual(trimmed, upperCase) && trimmed.length() > 3) {
                    escapeXml = DocxBuilder.INSTANCE.escapeXml(trimmed);
                    return "<w:p><w:pPr><w:pStyle w:val=\"Heading1\"/></w:pPr><w:r><w:rPr><w:b/><w:sz w:val=\"24\"/></w:rPr><w:t xml:space=\"preserve\">" + escapeXml + "</w:t></w:r></w:p>";
                }
                if (StringsKt.startsWith$default(trimmed, "- ", false, 2, (Object) null)) {
                    content = DocxBuilder.INSTANCE.processInline(StringsKt.removePrefix(trimmed, (CharSequence) "- "));
                    return "<w:p><w:pPr><w:ind w:left=\"360\"/></w:pPr><w:r><w:t xml:space=\"preserve\">• </w:t></w:r>" + content + "</w:p>";
                }
                processInline = DocxBuilder.INSTANCE.processInline(trimmed);
                return "<w:p>" + processInline + "</w:p>";
            }
        }, 30, null);
        String documentXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<w:document xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\"\n  xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"\n  xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">\n<w:body>\n" + paragraphs + "\n<w:sectPr>\n  <w:pgSz w:w=\"12240\" w:h=\"15840\"/>\n  <w:pgMar w:top=\"1440\" w:right=\"1440\" w:bottom=\"1440\" w:left=\"1440\"/>\n</w:sectPr>\n</w:body>\n</w:document>";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(baos);
        build$addEntry(zip, "[Content_Types].xml", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">\n  <Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>\n  <Default Extension=\"xml\" ContentType=\"application/xml\"/>\n  <Override PartName=\"/word/document.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml\"/>\n  <Override PartName=\"/word/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml\"/>\n</Types>");
        build$addEntry(zip, "_rels/.rels", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"word/document.xml\"/>\n</Relationships>");
        build$addEntry(zip, "word/document.xml", documentXml);
        build$addEntry(zip, "word/_rels/document.xml.rels", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>\n</Relationships>");
        build$addEntry(zip, "word/styles.xml", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<w:styles xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">\n  <w:style w:type=\"paragraph\" w:styleId=\"Normal\">\n    <w:name w:val=\"Normal\"/>\n    <w:rPr><w:sz w:val=\"22\"/></w:rPr>\n  </w:style>\n  <w:style w:type=\"paragraph\" w:styleId=\"Heading1\">\n    <w:name w:val=\"heading 1\"/>\n    <w:rPr><w:b/><w:sz w:val=\"26\"/></w:rPr>\n  </w:style>\n</w:styles>");
        zip.finish();
        byte[] byteArray = baos.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    private static final void build$addEntry(ZipOutputStream zip, String name, String content) {
        zip.putNextEntry(new ZipEntry(name));
        byte[] bytes = content.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        zip.write(bytes);
        zip.closeEntry();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String processInline(String text) {
        Iterable parts = StringsKt.split$default((CharSequence) text, new String[]{"**"}, false, 0, 6, (Object) null);
        Iterable $this$mapIndexed$iv = parts;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        Iterator it = $this$mapIndexed$iv.iterator();
        while (true) {
            String str = "";
            if (!it.hasNext()) {
                return CollectionsKt.joinToString$default((List) destination$iv$iv, "", null, null, 0, null, null, 62, null);
            }
            Object item$iv$iv = it.next();
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String part = (String) item$iv$iv;
            if (!(part.length() == 0)) {
                str = index$iv$iv % 2 == 1 ? "<w:r><w:rPr><w:b/></w:rPr><w:t xml:space=\"preserve\">" + INSTANCE.escapeXml(part) + "</w:t></w:r>" : "<w:r><w:t xml:space=\"preserve\">" + INSTANCE.escapeXml(part) + "</w:t></w:r>";
            }
            destination$iv$iv.add(str);
            index$iv$iv = index$iv$iv2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String escapeXml(String text) {
        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(text, "&", "&amp;", false, 4, (Object) null), "<", "&lt;", false, 4, (Object) null), ">", "&gt;", false, 4, (Object) null), OperatorName.SHOW_TEXT_LINE_AND_SPACE, "&quot;", false, 4, (Object) null), OperatorName.SHOW_TEXT_LINE, "&apos;", false, 4, (Object) null);
    }
}
