package master.developer.time3

import groovy.transform.CompileStatic

@CompileStatic
class ConciliacaoService {

	void concilieGuias(final List<GuiaHospital> guiasDoHospital, final List<GuiaConvenio> guiasDoConvenio) {
		List<GuiaHospital> guiasHospitalAssociadas = obtenhaGuiasHospitalSeForemAssociadas(guiasDoHospital, guiasDoConvenio)

	}

	List<GuiaHospital> obtenhaGuiasHospitalSeForemAssociadas(final List<GuiaHospital> guiasDoHospital, final List<GuiaConvenio> guiasDoConvenio) {
		final List<GuiaHospital> guiasHospitalNaoAssociadas = guiasDoHospital.findAll { final GuiaHospital guiaHospital -> !guiaHospital.guiaAssociada }
		final List<GuiaConvenio> guiasConvenioNaoAssociadas = guiasDoConvenio.findAll { final GuiaConvenio guiaConvenio -> !guiaConvenio.guiaAssociada }
		final List<GuiaHospital> guiasHospitalAssociadas = []

		for (final GuiaHospital guiaHospitalNaoAssociada in guiasHospitalNaoAssociadas) {
			final List<GuiaConvenio> guiasAssociaveisAGuiaHospital = obtenhaGuiasConvenioAssociaveisAGuia(guiaHospitalNaoAssociada, guiasConvenioNaoAssociadas)

			if(guiasAssociaveisAGuiaHospital.size() == 1) {
				final GuiaConvenio guiaConvenioAssociada = guiasAssociaveisAGuiaHospital[0]
				guiaHospitalNaoAssociada.guiaAssociada = guiaConvenioAssociada
				guiaConvenioAssociada.guiaAssociada = guiaHospitalNaoAssociada
				guiaConvenioAssociada.save(failOnError: true)
				final GuiaHospital guiaHospitalAssociada = guiaHospitalNaoAssociada.save(failOnError: true)
				guiasHospitalAssociadas.add(guiaHospitalAssociada)
			}
		}

		return guiasHospitalAssociadas
	}

	private static List<GuiaConvenio> obtenhaGuiasConvenioAssociaveisAGuia(final GuiaHospital guiaSendoAssociada, final List<GuiaConvenio> guiasConvenioAAssociar) {
		final List<GuiaConvenio> guiasConvenioBeneficiariosSimilares = guiasConvenioAAssociar.findAll { final GuiaConvenio guiaConvenioNaoAssociada ->
			return guiaSendoAssociada.numeroCarteira == guiaConvenioNaoAssociada.numeroCarteira ||
					guiaSendoAssociada.nomeBeneficiario == guiaConvenioNaoAssociada.nomeBeneficiario
		}

		final List<GuiaConvenio> guiasComDadosDeGuiaSimilares = guiasConvenioBeneficiariosSimilares.findAll {final GuiaConvenio guiaConvenioMesmoBeneficiario ->
			final boolean numeroGuiaHospitalPresenteNasDuasGuias = guiaSendoAssociada.numeroGuiaPrestadorPrestador == guiaConvenioMesmoBeneficiario.numeroGuiaPrestadorOperadora
			final boolean numeroGuiaConvenioPresenteNasDuasGuias = guiaSendoAssociada.numeroGuiaOperadoraPrestador == guiaConvenioMesmoBeneficiario.numeroGuiaOperadoraOperadora

			if(numeroGuiaHospitalPresenteNasDuasGuias && numeroGuiaConvenioPresenteNasDuasGuias) {
				return true
			}

			return (numeroGuiaHospitalPresenteNasDuasGuias || numeroGuiaConvenioPresenteNasDuasGuias) && guiaSendoAssociada.senha == guiaConvenioMesmoBeneficiario.senha
		}

		return guiasComDadosDeGuiaSimilares
	}

	void associeItens(final Set<Item> itensHospital, final Set<Item> itensConvenio) {
		final Set<Item> itensNaoAssociadosDoHospital = itensHospital.findAll {final Item itemHospital -> !itemHospital.itemAssociado}
		final Set<Item> itensNaoAssociadosDoConvenio = itensConvenio.findAll {final Item itemConvenio -> !itemConvenio.itemAssociado}

		for(final Item itemNaoAssociadoHospital in itensNaoAssociadosDoHospital) {
			final Set<Item> itensConvenioComCodigosEDescricoesIguais = itensNaoAssociadosDoConvenio.findAll {final Item itemConvenio ->
				if(itemNaoAssociadoHospital.codigoItem == itemConvenio.codigoItem && itemNaoAssociadoHospital.codigoTabela == itemConvenio.codigoTabela) {
					return itemNaoAssociadoHospital.descricao == itemConvenio.descricao
				}
			}

			final Set<Item> itensValoresIguais = itensConvenioComCodigosEDescricoesIguais.findAll {final Item itemConvenio ->
				return itemNaoAssociadoHospital.valorApresentado == itemConvenio.valorApresentado && itemNaoAssociadoHospital.valorUnitario == itemConvenio.valorUnitario
			}

			final Set<Item> itensSimilares = itensValoresIguais.findAll {final Item itemSimilarConvenio ->
				itemNaoAssociadoHospital.quantidade == itemSimilarConvenio.quantidade &&
						itemNaoAssociadoHospital.dataInicio == itemSimilarConvenio.dataInicio &&
						itemNaoAssociadoHospital.dataFim == itemSimilarConvenio.dataFim
			}

			if(itensSimilares.size() == 1) {
				final Item itemConvenioAssociado = itensSimilares[0]
				itemNaoAssociadoHospital.itemAssociado = itemConvenioAssociado
				itemConvenioAssociado.itemAssociado = itemNaoAssociadoHospital
				itemNaoAssociadoHospital.save(failOnError: true)
				itemConvenioAssociado.save(failOnError: true)
			}
		}
	}
}
