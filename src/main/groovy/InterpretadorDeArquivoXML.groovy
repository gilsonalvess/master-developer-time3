class InterpretadorDeArquivoXML extends InterpretadorDeArquivoAbstrata {

	List<String> tagsGuia = []
	List<String> tagsDadosGuia = []

	List<Map<String,String>> obtenhaRegistrosArquivo(String nomeArquivo){
		File arquivo = obtenhaArquivoResource(nomeArquivo)
		criaRegistros(arquivo)
	}

	List<Map<String,String>> criaRegistros(File arquivo){
		String conteudoArquivo = arquivo.text
		List<Map<String,String>> registros = obtenhaRegistros(conteudoArquivo, getTagsGuia())
		return registros
	}

	List<Map<String,String>> obtenhaRegistros(String conteudoXML, List<String> tagsBuscadas){
		List<String> guias = obtenhaConteudoTags(conteudoXML, tagsBuscadas)
		List<Map<String,String>> dadosGuias = obtenhaDadosGuias(guias, getTagsDadosGuia())
		dadosGuias
	}

	List<Map<String,String>> obtenhaDadosGuias(List<String> registrosGuia, List<String> tagsDadosGuia){
		List<Map<String,String>> dadosGuias = []
		for (registroGuia in registrosGuia) {
			dadosGuias.add(obtenhaDadosGuia(registroGuia,tagsDadosGuia))
		}
		dadosGuias
	}

	Map<String,String> obtenhaDadosGuia(String registroGuia, List<String> tagsDadosGuia){
		Map<String,String> dadosGuia = [:]
		for (String tagAtual in tagsDadosGuia) {
			String conteudoTag = obtenhaConteudoTag(registroGuia, tagAtual)
			dadosGuia.put(tagAtual, conteudoTag)
		}
		dadosGuia
	}

	List<String> obtenhaConteudoTags(String conteudoXML, List<String> tagsBuscadas){
		List<String> todasTags = []
		for (tagAlvo in tagsBuscadas) {
			List<String> tags = conteudoXML.findAll("(?s)(?<=<${tagAlvo}>).*?(?=<\\/${tagAlvo}>)")
			todasTags.addAll(tags)
		}
		return todasTags
	}

	String obtenhaConteudoTag(String conteudoXML, String tagBuscada){
		conteudoXML.find("(?s)(?<=<${tagBuscada}>).*?(?=<\\/${tagBuscada}>)")?:''
	}
}
