package pl.project.invoicing.utils

import pl.project.invoicing.helpers.TestHelpers
import pl.project.invoicing.model.Invoice
import spock.lang.Specification

class JsonServiceTest extends Specification {

    def "can convert object to json and read it back"() {
        given:
        def jsonService = new JsonService()
        def invoice = TestHelpers.invoice(12)

        when:
        def invoiceAsString = jsonService.toJson(invoice)

        and:
        def invoiceFromJson = jsonService.toObject(invoiceAsString, Invoice)

        then:
        invoice == invoiceFromJson
    }

}
