import spock.lang.Specification

class NavegadorGlosaMaxxSpec extends Specification {
    void "teste webnavigator"(){
        setup:
        NavegadorGlosaMaxx webNavigator = new NavegadorGlosaMaxx()
        when:
        String caminho = webNavigator.extraiCaminhoPaginaArquivos()
        webNavigator.extraiCaminhosPagina(caminho)

        then:
        true
    }
}
