package master.developer.time3

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class GuiaHospital extends Guia {

    Long numeroGuiaPrestadorPrestador
    Long numeroGuiaOperadoraPrestador

    static constraints = {
    }
}
