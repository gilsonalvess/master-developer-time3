import groovy.util.slurpersupport.NodeChild
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import com.opencsv.CSVWriter


class NavegadorGlosaMaxx {
    HTTPBuilder httpBuilder;

    NavegadorGlosaMaxx(String url){
        this.httpBuilder = new HTTPBuilder(url)
    }

    public void fluxoPagamentosGlosaMaxx(){
        Map params = [path: '/pagamento-glosamax', contentType: HTML]
        NodeChild html = httpBuilder.get(params)

        NodeChild controller = html.depthFirst().find{ it.'@id' == 'controllers'}.depthFirst().find { child -> child.@class == 'controller'}
        String path = controller.depthFirst().list[0].'@href'.text()

        paginaDownloads(path)
    }

    public void paginaDownloads(String path){
        Map params = [path: path, contentType: HTML]
        NodeChild html = httpBuilder.get(params)
        List<String> paginas = html.depthFirst().find{
            it.@id == 'list-arquivo'
        }.children().findAll {
            it.name() == "A"
        }.collect{ it.'@href'.text() }

        paginas.each {String pagina ->
            paginaDownload(pagina)
        }
    }

    public void paginaDownload(String path){
        Map params = [path: path, contentType: HTML]
        NodeChild html = httpBuilder.get(params)
        List<String> urlArquivos = html.depthFirst()
                .find{ it.@id == 'list-arquivo'}
                .depthFirst()
                .findAll{ child -> child.@class == 'controller'}
                .collect{ NodeChild nodeChild ->
                    if(nodeChild.depthFirst().find{it.name() == "A"}.text().contains('.csv'))
                        return nodeChild.depthFirst().find{it.name() == "A"}.collect{it.'@href'.text()}
                }

        urlArquivos.each {String urlArquivo ->
            if(urlArquivo)
                baixarArquivos(urlArquivo)
        }
    }

    private String montaNomeArquivo(byte[] bytes){
        String conteudo = new String(bytes, "UTF-8")
        String lote = conteudo.find('(?<=motivoGlosa\\n)\\d+')
        return "pagamento_glosamaxx_" + lote + ".csv"
    }

    public void baixarArquivos(String url){
        Map params = [uri: url]
        ByteArrayInputStream byteArrayInputStream = httpBuilder.get(params)
        int n = byteArrayInputStream.available()
        byte[] bytes = new byte[n]
        byteArrayInputStream.read(bytes, 0, n)
        String nomeArquivo = montaNomeArquivo(bytes)
        FileOutputStream fos = new FileOutputStream("/home/zeroglosa/Documents/master-developer-time3/src/main/arquivo-repositorio/arquivos-convenio/" + nomeArquivo)
        fos.write(bytes)
    }
}