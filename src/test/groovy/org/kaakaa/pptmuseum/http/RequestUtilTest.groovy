package org.kaakaa.pptmuseum.http

import org.apache.commons.fileupload.FileItem
import org.kaakaa.pptmuseum.db.document.util.RequestUtil
import spock.lang.Specification

/**
 * Created by kaakaa on 16/03/05.
 */
class RequestUtilTest extends Specification {
    def "MakeSlideModel"() {
        setup:
        def item = Mock(FileItem)

        when:
        item.isFormField() >> isFormField
        item.getFieldName() >> fieldName
        item.getString() >> value

        then:
        RequestUtil.makeSlideModel([item]).toString() == expected

        where:
        isFormField | fieldName | value || expected
        true | "title" | "test_title" || "Slide[title=test_title,description=<null>,tags=[]]"
        true | "title" | new String("日本語のTITLE".getBytes(), "ISO8859_1") || "Slide[title=日本語のTITLE,description=<null>,tags=[]]"
        false | "title"| "test_title" || "Slide[title=<null>,description=<null>,tags=[]]"
        true | "desc" | "test_description" || "Slide[title=<null>,description=test_description,tags=[]]"
        true | "desc" | new String("日本語のDESCRIPTION".getBytes(), "ISO8859_1") || "Slide[title=<null>,description=日本語のDESCRIPTION,tags=[]]"
        false | "desc"| "test_title" || "Slide[title=<null>,description=<null>,tags=[]]"
    }
}
