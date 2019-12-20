package master.developer.time3


import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month;


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

	def 'Associa itens não associados, de guias associadas'(){

		given: 'Que sejam fornecidas guias associadas com itens não associados'
		final ConciliacaoService conciliacaoService = new ConciliacaoService()
		final GuiaHospital guiaAssociavelHospital = new GuiaHospital()
		final GuiaConvenio guiaAssociavelConvenio = new GuiaConvenio()

		guiaAssociavelHospital.convenio = Mock(Convenio)
		guiaAssociavelHospital.nomeBeneficiario = 'beneficiário'
		guiaAssociavelHospital.numeroCarteira = 123456
		guiaAssociavelHospital.atendimentoRn = 'S'
		guiaAssociavelHospital.senha = 'SENHASENHA'
		guiaAssociavelHospital.guiaAssociada = guiaAssociavelConvenio
		guiaAssociavelHospital.numeroGuiaPrestadorPrestador = 99999
		guiaAssociavelHospital.numeroGuiaOperadoraPrestador = 88888
		guiaAssociavelHospital.itens = []

		guiaAssociavelConvenio.convenio = Mock(Convenio)
		guiaAssociavelConvenio.nomeBeneficiario = 'beneficiário'
		guiaAssociavelConvenio.numeroCarteira = 123456
		guiaAssociavelConvenio.atendimentoRn = 'S'
		guiaAssociavelConvenio.senha = 'SENHASENHA'
		guiaAssociavelConvenio.guiaAssociada = guiaAssociavelHospital
		guiaAssociavelConvenio.numeroGuiaPrestadorOperadora = 99999
		guiaAssociavelConvenio.numeroGuiaOperadoraOperadora = 88888
		guiaAssociavelConvenio.itens = []


		final List<Item> itensBase = [
				new Item(codigoItem: 'codigoItem1', codigoTabela: 'codigoTabela1', descricao: 'descricao1',
						valorApresentado: 5.0, valorUnitario: 5.0,
						quantidade: 1, dataInicio: LocalDateTime.of(2016, Month.JULY, 19, 15, 55, 15), dataFim: LocalDateTime.of(2016, Month.JULY, 23, 15, 40, 10)),
				new Item(codigoItem: 'codigoItem2', codigoTabela: 'codigoTabela2', descricao: 'descricao2',
						valorApresentado: 15.6, valorUnitario: 5.2,
						quantidade: 3, dataInicio: LocalDateTime.of(2016, Month.JULY, 15, 15, 55, 59), dataFim: LocalDateTime.of(2016, Month.JULY, 23, 15, 55, 3)),
				new Item(codigoItem: 'codigoItem3', codigoTabela: 'codigoTabela3', descricao: 'descricao3',
						valorApresentado: 15.6, valorUnitario: 7.8,
						quantidade: 2, dataInicio: LocalDateTime.of(2016, Month.JULY, 26, 15, 2, 50), dataFim: LocalDateTime.of(2016, Month.JULY, 26, 15, 55, 22)),
		]

		guiaAssociavelConvenio.itens.addAll(itensBase)

		List<Item> itensHospital = itensBase.collect()
		itensHospital[2].quantidade = 1
		itensHospital[2].valorApresentado = 7.8
		guiaAssociavelHospital.itens.addAll(itensHospital)

		when: 'Eu quiser associar os itens de duas guias associadas'
		Set<Item> itensAssociados = conciliacaoService.associeItens(guiaAssociavelHospital)

		then: 'Os itens serão associados e o campo itemAssociado de cada item estará preenchido'
		itensAssociados.size() == 2
		itensAssociados.each {it.itemAssociado != null}
	}
}
