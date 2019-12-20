

class CriadorDeGuia {

	InterpretadorDeArquivoAbstrata interpretadordeArquivo

	void criaGuias(String caminhoArquivo){
		File file = new File(caminhoArquivo)
		interpretadordeArquivo = InterpretadorDeArquivoAbstrata.getInstancia(file.name.find('(\\.).*'))
		Map<String,List<Map<String, String>>> registrosAgrupados = interpretadordeArquivo.obtenhaRegistrosArquivo(caminhoArquivo)
		for(registrosGuia in registrosAgrupados){
			criaGuia(registrosGuia.getValue().get(0))
			for (registroGuia in registrosGuia){

			}
		}
	}

	void criaGuia(Map<String, String> registroGuia){
		Guia guia = new Guia()
		guia.
	}
}
