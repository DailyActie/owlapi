package org.obolibrary.oboformat;

import static junit.framework.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class CAROTest extends OboFormatTestBasics {
    static boolean useSystemOut = false;

    @Test
    public void testParseCARO() throws Exception {
        OBODoc obodoc = parseOBOFile("caro.obo");
        if (useSystemOut) {
            System.out.println("F:" + obodoc);
        }
        assertTrue(obodoc.getTermFrames().size() > 2);
        Frame cc = obodoc.getTermFrame("CARO:0000014");
        assertEquals("cell component", cc.getTagValue(OboFormatTag.TAG_NAME));
        assertEquals("Anatomical structure that is a direct part of the cell.",
                cc.getTagValue(OboFormatTag.TAG_DEF));
        Clause dc = cc.getClause(OboFormatTag.TAG_DEF);
        if (useSystemOut) {
            System.out.println("dc=" + dc);
        }
        Collection<Xref> dcxs = dc.getXrefs();
        if (useSystemOut) {
            System.out.println("dcxs=" + dcxs);
        }
        assertEquals("CARO:MAH", dcxs.iterator().next().getIdref());
        /*
         * Collection<Xref> defxrefs = cc.getTagXrefs("def");
         * System.out.println("def xrefs = "+defxrefs);
         * assertTrue(defxrefs.iterator().next().getIdref().equals("CARO:MAH"));
         */
        // assertTrue(frame.getClause(OboFormatTag.TAG_NAME.getTag()).getValue().equals("x1"));
    }
}
