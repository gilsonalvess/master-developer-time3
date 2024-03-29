package master.developer.time3
import java.time.LocalDateTime

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Item {

	LocalDateTime dataInicio
	LocalDateTime dataFim
    String codigoItem
    String codigoTabela
	String descricao
    BigDecimal valorUnitario = BigDecimal.ZERO
    BigDecimal valorTotal = BigDecimal.ZERO
    BigDecimal valorApresentado = BigDecimal.ZERO
    BigDecimal valorGlosa = BigDecimal.ZERO
    String motivoGlosa
    BigDecimal quantidade
	Item itemAssociado

    static belongsTo = [guia: Guia]

    static constraints = {
    }
}
