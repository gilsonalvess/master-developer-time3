package master.developer.time3

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
abstract class Guia {

    String nomeBeneficiario
    Long numeroCarteira
    String atendimentoRn
    String senha
    Guia guiaAssociada
    BigDecimal valorTotal = BigDecimal.ZERO

    static hasMany = [itens: Item]
    static belongsTo = [convenio: Convenio]

    static constraints = {
    }
}
