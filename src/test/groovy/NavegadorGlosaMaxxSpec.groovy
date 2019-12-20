import spock.lang.Specification

class NavegadorGlosaMaxxSpec extends Specification {
    void "teste navegadorGlosaMaxx"(){
        setup:
        NavegadorGlosaMaxx navegadorGlosaMaxx = new NavegadorGlosaMaxx('http://172.22.1.108:8080')
        when:
        navegadorGlosaMaxx.fluxoPagamentosGlosaMaxx()
        File file1 = new File('/home/zeroglosa/Documents/master-developer-time3/src/main/arquivo-repositorio/arquivos-convenio/pagamento_glosamaxx_8469.csv')
        File file2 = new File('/home/zeroglosa/Documents/master-developer-time3/src/main/arquivo-repositorio/arquivos-convenio/pagamento_glosamaxx_8541.csv')
        File file3 = new File('/home/zeroglosa/Documents/master-developer-time3/src/main/arquivo-repositorio/arquivos-convenio/pagamento_glosamaxx_8550.csv')
        File file4 = new File('/home/zeroglosa/Documents/master-developer-time3/src/main/arquivo-repositorio/arquivos-convenio/pagamento_glosamaxx_8551.csv')

        then:
        file1.exists()
        file2.exists()
        file3.exists()
        file4.exists()

        cleanup:
        file1.delete()
        file2.delete()
        file3.delete()
        file4.delete()
    }
}
