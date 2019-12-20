package master.developer.time3

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Lote {

    Long numero

    static hasMany = [guias: Guia]
    static belongsTo = [convenio: Convenio]

    static constraints = {
    }
}
