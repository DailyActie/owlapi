package org.obolibrary.oboformat;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class PropertyValueTest extends OboFormatTestBasics {
    static boolean useSystemOut = false;

    @Test
    public void testExpand() throws Exception {
        OBODoc obodoc = parseOBOFile("property_value_test.obo");
        Clause propertyValue = obodoc.getTermFrame("UBERON:0004657").getClause(
                OboFormatTag.TAG_PROPERTY_VALUE);
        assertEquals("IAO:0000412", propertyValue.getValue());
        assertEquals("http://purl.obolibrary.org/obo/uberon.owl",
                propertyValue.getValue2());
    }

    @Test
    public void testWriteReadValues() throws Exception {
        OBODoc doc = createPVDoc();
        String oboString = renderOboToString(doc);
        if (useSystemOut) {
            System.out.println(oboString);
        }
        OBODoc doc2 = parseOboToString(oboString);
        OBODocDiffer dd = new OBODocDiffer();
        List<Diff> diffs = dd.getDiffs(doc, doc2);
        for (Diff diff : diffs) {
            System.out.println(diff);
        }
        assertEquals("Expected no diffs", 0, diffs.size());
    }

    private OBODoc createPVDoc() {
        OBODoc oboDoc = new OBODoc();
        Frame headerFrame = new Frame(FrameType.HEADER);
        headerFrame.addClause(new Clause(OboFormatTag.TAG_FORMAT_VERSION, "1.2"));
        headerFrame.addClause(new Clause(OboFormatTag.TAG_ONTOLOGY, "test"));
        addPropertyValue(headerFrame, "http://purl.org/dc/elements/1.1/title",
                "Ontology for Biomedical Investigation", "xsd:string");
        addPropertyValue(headerFrame, "defaultLanguage", "en", "xsd:string");
        oboDoc.setHeaderFrame(headerFrame);
        return oboDoc;
    }

    private void addPropertyValue(Frame frame, String v1, String v2, String v3) {
        Clause cl = new Clause(OboFormatTag.TAG_PROPERTY_VALUE);
        cl.addValue(v1);
        cl.addValue(v2);
        if (v3 != null) {
            cl.addValue(v3);
        }
        frame.addClause(cl);
    }
}
