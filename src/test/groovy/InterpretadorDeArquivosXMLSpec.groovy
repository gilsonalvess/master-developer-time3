import spock.lang.Specification

class InterpretadorDeArquivosXMLSpec extends Specification {

	def "Deve conseguir obeter o arquivo corretamente"(){
		when:
		InterpretadorDeArquivoXML arquivoXML = new InterpretadorDeArquivoXML()
		File arquivo = arquivoXML.obtenhaArquivoResource('arquivos-hopital/faturamento_glosamaxx_2016-02-01_161526_3.02.00.xml')
		then:
		arquivo
	}

	def "Deve conseguir obeter registros sem erro"(){
		when:
		InterpretadorDeArquivoXML arquivoXML = new InterpretadorDeArquivoXML()
		def registros = arquivoXML.obtenhaRegistrosArquivo('arquivos-hopital/faturamento_glosamaxx_2016-02-01_161526_3.02.00.xml')
		then:
		registros.toList().size() == 0
	}

	def "Deve conseguir obeter conteudo das tags buscadas"(){
		when:
		InterpretadorDeArquivoXML arquivoXML = new InterpretadorDeArquivoXML()
		String conteudoXML = arquivoXML.obtenhaArquivoResource('arquivos-hopital/faturamento_glosamaxx_2016-02-01_161526_3.02.00.xml').text
		def conteudoTag = arquivoXML.obtenhaConteudoTags(conteudoXML,['guiaResumoInternacao'])
		then:
		conteudoTag.toList().size() == 4
	}

	def "Deve conseguir obeter registros sem"(){
		when:
		InterpretadorArquivoHospital arquivoXML = new InterpretadorArquivoHospital()
		def registros = arquivoXML.obtenhaRegistrosArquivo('arquivos-hopital/faturamento_glosamaxx_2016-02-01_161526_3.02.00.xml')
		then:
		registros.toList().size() == 4
		registros.get(0).size() == 6
	}
}
