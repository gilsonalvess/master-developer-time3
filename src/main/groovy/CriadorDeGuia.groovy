import master.developer.time3.Guia
import master.developer.time3.GuiaHospital

class CriadorDeGuia {

	InterpretadorDeArquivoAbstrata interpretadordeArquivo

	void criaGuias(String caminhoArquivo){
		File file = new File(caminhoArquivo)
		interpretadordeArquivo = InterpretadorDeArquivoAbstrata.getInstancia(file.name.find('(\\.).*'))
		Map<String,List<Map<String, String>>> registrosAgrupados = interpretadordeArquivo.obtenhaRegistrosArquivo(caminhoArquivo)
		for(registrosGuia in registrosAgrupados){
			criaGuia(registrosGuia.getValue().get(0))
			for (registroGuia in registrosGuia){
				criaItens()
			}
		}
	}

	void criaGuia(Map<String, String> registroGuia){
		if(registroGuia.Origem == 'Hospital'){
			GuiaHospital guia = new GuiaHospital()
			guia.nomeBeneficiario = registroGuia.nomeBeneficiario
			guia.senha = registroGuia.senha
			guia.numeroCarteira = registroGuia.numeroCarteira as long
			guia.numeroGuiaOperadoraPrestador = registroGuia.numeroGuiaOperadoraPrestador as long
			guia.numeroGuiaPrestadorPrestador = registroGuia.numeroGuiaPrestadorPrestador as long
			guia.valorTotal = new BigDecimal(registroGuia.valorTotal)
			guia.origem = registroGuia.origem
			guia.atendimentoRn = registroGuia.atendimentoRn
			guia.save()
		}
	}

	void criaItens(){

	}
}
