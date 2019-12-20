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

}
