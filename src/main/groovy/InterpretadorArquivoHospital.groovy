class InterpretadorArquivoHospital extends InterpretadorDeArquivoXML {

	final List<String> tagsGuia = [
	        'guiaResumoInternacao'
	]

	final List<String> tagsDadosGuia = [
	        'numeroGuiaPrestador',
			'numeroGuiaOperadora',
			'senha',
			'numeroCarteira',
			'atendimentoRN',
			'nomeBeneficiario',
			'valorTotalGeral'
	]

	List<String> tagsItens = [
	        'procedimentoExecutado'
	]

	List<String> tagsDadosItens = [
	        'dataExecucao',
			'horaInicial',
			'horaFinal',
			'codigoTabela',
			'codigoProcedimento',
			'descricaoProcedimento',
			'quantidadeExecutada',
			'valorUnitario',
			'valorTotal'
	]

	List<Map<String,String>> realizaEquivalenciaCampos(List<Map<String,String>> registros){
		List<Map<String,String>> registrosProcessados = []
		for(Map<String,String> registro in registros){

			Map<String,String> registroProcessado = [:]
			registroProcessado.put('nomeBeneficiario', registro.nomeBeneficiario)
			registroProcessado.put('numeroCarteira', registro.numeroCarteira)
			registroProcessado.put('atendimentoRn', registro.atendimentoRN)
			registroProcessado.put('senha', registro.senha)
			registroProcessado.put('numeroGuiaPrestadorPrestador', registro.numeroGuiaPrestador)
			registroProcessado.put('numeroGuiaOperadoraPrestador', registro.numeroGuiaOperadora)
			registroProcessado.put('valorTotal', registro.valorTotalGeral)

			registroProcessado.put('dataInicio', registro.dataExecucao + ' ' + registro.horaInicial)
			registroProcessado.put('dataFim', registro.dataExecucao + ' ' + registro.horaFinal)
			registroProcessado.put('codigoItem', registro.codigoProcedimento)
			registroProcessado.put('codigoTabela', registro.codigoTabela)
			registroProcessado.put('valorUnitario', registro.valorUnitario)
			registroProcessado.put('valorTotal', registro.valorTotal)
			registroProcessado.put('valorApresentado', registro.valorTotal)
			registroProcessado.put('quantidade', registro.quantidadeExecutada)
			registroProcessado.put('descricaoItem', registro.descricaoProcedimento)
			registrosProcessados.add(registroProcessado)
		}
		registrosProcessados
	}

}
