import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild

import java.util.concurrent.ExecutionException

import static groovyx.net.http.ContentType.TEXT
import spock.lang.Specification

import org.cyberneko.html.parsers.SAXParser
import groovy.util.XmlSlurper

class WebNavigatorTest extends Specification {
    void 'teste temporatio'(){
        //Object returnValue
        when:
        SAXParser parser = new SAXParser();
        XmlSlurper slurper = new XmlSlurper(parser)

        WebNavigator navigator = new WebNavigator('http://172.22.1.108:8080/pagamento-glosamax/')
        try {
            String returnValue = (navigator.httpBuilder.get(contentType: TEXT) as StringReader).str

            //returnValue.find(/<a\s*href\s*=\s*".*">(?=\s*br\.com\.zgsolucoes\.glosamax\.ArquivoController\s*<\\/a>)/)

            GPathResult html = slurper.parseText(returnValue)

            NodeChild controller = html.depthFirst().find{ it.@id == 'controllers'}.depthFirst().find { child -> child.@class == 'controller'}
            String path = controller.depthFirst().list[0].@href.text()

            returnValue = (navigator.get(path: path, contentType: TEXT) as StringReader).str    //TODO: da erro aqui ainda, arrumar


        }catch(Exception ex){
            println(ex.message)
        }
        then:
        returnValue


    }
}
