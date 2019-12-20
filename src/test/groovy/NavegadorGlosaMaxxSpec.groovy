import spock.lang.Specification

class NavegadorGlosaMaxxSpec extends Specification {
    void "teste navegadorGlosaMaxx"(){
        setup:
        NavegadorGlosaMaxx navegadorGlosaMaxx = new NavegadorGlosaMaxx('http://172.22.1.108:8080')
        when:
        navegadorGlosaMaxx.fluxoPagamentosGlosaMaxx()

        then:
        true
    }
}
