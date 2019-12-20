class InterpretadorDeArquivoXML extends InterpretadorDeArquivoAbstrata {

	List<String> tagsGuia = []
	List<String> tagsDadosGuia = []
	List<String> tagsDadosItens = []
	List<String> tagsItens = []

	Map<GString,List<Map<String,String>>> obtenhaRegistrosArquivo(String nomeArquivo){
		File arquivo = obtenhaArquivoResource(nomeArquivo)
		if(!arquivo.name.find('.xml')){
			return null
		}
		List<Map<String,String>> registros = criaRegistros(arquivo)
		registros = realizaEquivalenciaCampos(registros)
		registros = agrupeItens(registros)
		return registros.groupBy {"${it.numeroGuiaPrestadorPrestador}.${it.nomeBeneficiario}.${it.numeroCarteira}"}
	}

	List<Map<String,String>> realizaEquivalenciaCampos(List<Map<String,String>> registros){
		registros
	}

	List<Map<String,String>> agrupeItens(List<Map<String,String>> registros){
		registros
	}

	List<Map<String,String>> criaRegistros(File arquivo){
		String conteudoArquivo = arquivo.text
		List<Map<String,String>> registros = obtenhaRegistros(conteudoArquivo, getTagsGuia())
		return registros
	}

	List<Map<String,String>> obtenhaRegistros(String conteudoXML, List<String> tagsBuscadas){
		List<String> guias = obtenhaConteudoTags(conteudoXML, tagsBuscadas)
		List<Map<String,String>> dadosGuias = obtenhaDadosGuias(guias,)
		dadosGuias
	}

	List<Map<String,String>> obtenhaDadosGuias(List<String> conteudoGuias){
		List<Map<String,String>> dadosGuias = []
		for (conteudoGuia in conteudoGuias) {
			Map<String,String> dadoGuia = obtenhaDadosGuia(conteudoGuia)
			List<Map<String,String>> dadosItens = obtenhaDadosItens(conteudoGuia, dadoGuia)
			dadosGuias.addAll(dadosItens)
		}
		dadosGuias
	}

	Map<String,String> obtenhaDadosGuia(String conteudoGuia, List<String> tagsDadosGuia = getTagsDadosGuia()){
		Map<String,String> dadosGuia = [:]
		for (String tagAtual in tagsDadosGuia) {
			String conteudoTag = obtenhaConteudoTag(conteudoGuia, tagAtual)
			dadosGuia.put(tagAtual, conteudoTag)
		}
		dadosGuia
	}

	List<Map<String,String>> obtenhaDadosItens(String conteudoGuia, List<String> tagsItens = getTagsItens(), Map<String,String> dadoGuia){
		List<Map<String,String>> itens = []
		List<String> conteudoItens =  obtenhaConteudoTags(conteudoGuia,tagsItens)
		for (String conteudoItem in conteudoItens) {
			Map<String,String> dadosItens = obtenhaDadosItem(conteudoItem)
			dadosItens.putAll(dadoGuia)
			itens.add(dadosItens)
		}
		return itens
	}

	Map<String,String> obtenhaDadosItem(String conteudoGuia, List<String> tagsDadosGuia = getTagsDadosItens()){
		Map<String,String> dadosItem = [:]
		for (String tagAtual in tagsDadosGuia) {
			String conteudoTag = obtenhaConteudoTag(conteudoGuia, tagAtual)
			dadosItem.put(tagAtual, conteudoTag)
		}
		dadosItem
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
