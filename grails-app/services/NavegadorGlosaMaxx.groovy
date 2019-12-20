import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HTTPBuilder
import org.cyberneko.html.parsers.SAXParser
import static groovyx.net.http.ContentType.TEXT


class NavegadorGlosaMaxx {
    HTTPBuilder httpBuilder;

    NavegadorGlosaMaxx(){
        this.httpBuilder = new HTTPBuilder('http://172.22.1.108:8080')
    }

    private GPathResult obtemPaginaHtmlParseada(Map params){
        SAXParser parser = new SAXParser();
        XmlSlurper slurper = new XmlSlurper(parser)
        String returnValue = (httpBuilder.get(params) as StringReader).str
        return slurper.parseText(returnValue)
    }

    private extraiHtml(String ){}

    public void paginaPrincipal(){
        Map params = [path: '/pagamento-glosamax', contentType: TEXT]
        GPathResult html = obtemPaginaHtmlParseada(params)

        NodeChild controller = html.depthFirst().find{ it.@id == 'controllers'}.depthFirst().find { child -> child.@class == 'controller'}
        String path = controller.depthFirst().list[0].@href.text()
        paginaArquivos(path)
    }

    public void paginaArquivos(String path){

    }

}