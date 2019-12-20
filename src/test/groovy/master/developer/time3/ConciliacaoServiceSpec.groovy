package master.developer.time3

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock([GuiaHospital, GuiaConvenio])
@TestFor(ConciliacaoService)
class ConciliacaoServiceSpec extends Specification{

	def 'Associa guias quando elas não estão associadas, os dados do beneficiário são iguais e quando os números das guias são referenciados entre elas'() {
		given: 'Que sejam apresentadas guias não associadas com os mesmos beneficiários e que seus números sejam referenciados na guia associada'
		final GuiaHospital guiaAssociavelHospital = new GuiaHospital()
		guiaAssociavelHospital.convenio = Mock(Convenio)
		guiaAssociavelHospital.nomeBeneficiario = 'beneficiário'
		guiaAssociavelHospital.numeroCarteira = 123456
		guiaAssociavelHospital.atendimentoRn = 'S'
		guiaAssociavelHospital.senha = 'SENHASENHA'
		guiaAssociavelHospital.guiaAssociada = null
		guiaAssociavelHospital.numeroGuiaPrestadorPrestador = 99999
		guiaAssociavelHospital.numeroGuiaOperadoraPrestador = 88888


		final GuiaConvenio guiaAssociavelConvenio = new GuiaConvenio()
		guiaAssociavelConvenio.convenio = Mock(Convenio)
		guiaAssociavelConvenio.nomeBeneficiario = 'beneficiário'
		guiaAssociavelConvenio.numeroCarteira = 123456
		guiaAssociavelConvenio.atendimentoRn = 'S'
		guiaAssociavelConvenio.senha = 'SENHASENHA'
		guiaAssociavelConvenio.guiaAssociada = null
		guiaAssociavelConvenio.numeroGuiaPrestadorOperadora = 99999
		guiaAssociavelConvenio.numeroGuiaOperadoraOperadora = 88888

		when: 'Necessário obter as guias associadas a partir da apresentação das guias do hospital e do convênio'
		final List<GuiaHospital> guiasAssociadas = service.obtenhaGuiasHospitalSeForemAssociadas([guiaAssociavelHospital], [guiaAssociavelConvenio])

		then: 'As guias serão associadas e o campo guiaAssociada estará preenchido'
		guiasAssociadas.size() == 1
		guiasAssociadas.first().guiaAssociada != null
	}
}
