package master.developer.time3

import groovy.transform.CompileStatic

@CompileStatic
class ConciliacaoService {

	final Closure<Boolean> checaIgualdadeDeCodigosEDescricoes = { Item a, Item b ->
		return a.codigoItem == b.codigoItem && a.codigoTabela == b.codigoTabela && a.descricao == b.descricao
	}

	final Closure<Boolean> checaIgualdadeDeValores = { Item a, Item b ->
		return a.valorApresentado == b.valorApresentado && a.valorUnitario == b.valorUnitario
	}

	final Closure<Boolean> checaIgualdadeDataEQuantidade = { Item a, Item b ->
		return a.quantidade == b.quantidade && a.dataInicio == b.dataInicio && a.dataFim == b.dataFim
	}

	final List<Closure<Boolean>> regrasIgualdadeNecessariaParaAssociacaoDeItens = [
			checaIgualdadeDeCodigosEDescricoes,
			checaIgualdadeDeValores,
			checaIgualdadeDataEQuantidade
	]


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

	Set<Item> associeItens(final GuiaHospital guiaHospitalAssociada) {
		Set<Item> itensNaoAssociadosDoHospital
		Set<Item> itensNaoAssociadosDoConvenio

		Set<Item> itensAssociados

		final GuiaConvenio guiaConvenioAssociada = (GuiaConvenio) guiaHospitalAssociada.guiaAssociada

		itensNaoAssociadosDoHospital = guiaHospitalAssociada.itens?.findAll { !it.itemAssociado }
		itensNaoAssociadosDoConvenio = guiaConvenioAssociada.itens?.findAll { !it.itemAssociado }


		for(final Item itemNaoAssociadoHospital in itensNaoAssociadosDoHospital) {
			final Set<Item> itensSimilares = itensNaoAssociadosDoConvenio.findAll {final Item itemConvenio ->
				for(Closure<Boolean> saoIguais in  regrasIgualdadeNecessariaParaAssociacaoDeItens){
					if( saoIguais(itemNaoAssociadoHospital, itemConvenio) == false){
						return false
					}
				}
			}


			if(itensSimilares.size() == 1) {
				final Item itemConvenioAssociado = itensSimilares[0]
				itemNaoAssociadoHospital.itemAssociado = itemConvenioAssociado
				itemConvenioAssociado.itemAssociado = itemNaoAssociadoHospital
				itemNaoAssociadoHospital.save(failOnError: true)
				itemConvenioAssociado.save(failOnError: true)
				itensAssociados.add(itemConvenioAssociado)
			}
		}

		return itensAssociados
	}


}
