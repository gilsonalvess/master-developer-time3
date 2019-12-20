import spock.lang.Specification

class NavegadorGlosaMaxxSpec extends Specification {
    void "teste webnavigator"(){
        setup:
        NavegadorGlosaMaxx webNavigator = new NavegadorGlosaMaxx()
        when:
        webNavigator.paginaPrincipal()

        then:
        true
    }
}
