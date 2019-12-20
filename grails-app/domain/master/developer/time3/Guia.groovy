package master.developer.time3

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
abstract class Guia {

    String nomeBeneficiario
    Long numeroCarteira
    String atendimentoRn
    String senha

    static hasMany = [itens: Item]
    static belongsTo = [convenio: Convenio]
    static hasOne = [guiaAssociada: Guia]

    static constraints = {
    }
}
