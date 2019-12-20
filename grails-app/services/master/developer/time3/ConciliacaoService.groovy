package master.developer.time3

import groovy.transform.CompileStatic

@CompileStatic
class ConciliacaoService {

	void concilieGuias(final List<GuiaHospital> guiasDoHospital, final List<GuiaConvenio> guiasDoConvenio) {
		final List<GuiaHospital> guiasHospitalNaoAssociadas = guiasDoHospital.findAll { final GuiaHospital guiaHospital -> !guiaHospital.guiaAssociada }
		final List<GuiaConvenio> guiasConvenioNaoAssociadas = guiasDoConvenio.findAll { final GuiaConvenio guiaConvenio -> !guiaConvenio.guiaAssociada }

		for (final GuiaHospital guiaHospitalNaoAssociada in guiasHospitalNaoAssociadas) {
			final List<GuiaConvenio> guiasConvenioBeneficiariosSimilares = guiasConvenioNaoAssociadas.findAll { final GuiaConvenio guiaConvenioNaoAssociada ->
				return guiaHospitalNaoAssociada.numeroCarteira == guiaConvenioNaoAssociada.numeroCarteira ||
						guiaHospitalNaoAssociada.nomeBeneficiario == guiaConvenioNaoAssociada.nomeBeneficiario
			}

			final List<GuiaConvenio> guiasComDadosDeGuiaSimilares = guiasConvenioBeneficiariosSimilares.findAll {final GuiaConvenio guiaConvenioMesmoBeneficiario ->
				final boolean numeroGuiaHospitalPresenteNasDuasGuias = guiaHospitalNaoAssociada.numeroGuiaPrestadorPrestador == guiaConvenioMesmoBeneficiario.numeroGuiaPrestadorOperadora
				final boolean numeroGuiaConvenioPresenteNasDuasGuias = guiaHospitalNaoAssociada.numeroGuiaOperadoraPrestador == guiaConvenioMesmoBeneficiario.numeroGuiaOperadoraOperadora

				if(numeroGuiaHospitalPresenteNasDuasGuias && numeroGuiaConvenioPresenteNasDuasGuias) {
					return true
				}

				return (numeroGuiaHospitalPresenteNasDuasGuias || numeroGuiaConvenioPresenteNasDuasGuias) && guiaHospitalNaoAssociada.senha == guiaConvenioMesmoBeneficiario.senha
			}

			if(guiasComDadosDeGuiaSimilares.size() == 1) {
				final GuiaConvenio guiaConvenioAssociada = guiasComDadosDeGuiaSimilares.get(0)
				guiaHospitalNaoAssociada.guiaAssociada = guiaConvenioAssociada
				guiaConvenioAssociada.guiaAssociada = guiaHospitalNaoAssociada
				guiaHospitalNaoAssociada.save(failOnError: true)
				guiaConvenioAssociada.save(failOnError: true)
				concilieItens(guiaHospitalNaoAssociada.itens, guiaConvenioAssociada.itens)
			}
		}
	}

	void concilieItens(final Set<Item> itensHospital, final Set<Item> itensConvenio) {

	}
}
