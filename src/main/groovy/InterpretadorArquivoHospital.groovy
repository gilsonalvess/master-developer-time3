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
			'nomeBeneficiario'
	]

}
