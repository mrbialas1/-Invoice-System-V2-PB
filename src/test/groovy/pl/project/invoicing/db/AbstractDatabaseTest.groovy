package pl.project.invoicing.db


import pl.project.invoicing.model.Invoice
import spock.lang.Specification

import static pl.project.invoicing.helpers.TestHelpers.invoice

abstract class AbstractDatabaseTest extends Specification {

    List<Invoice> invoices = (1..12).collect { invoice(it) }
    Database database = getDatabaseInstance()

    abstract Database getDatabaseInstance()

    def "should save invoices returning sequential id, invoice should have id set to correct value, get by id returns saved invoice"() {
        when:
        def ids = invoices.collect({ database.save(it) })

        then:
        (1..invoices.size() - 1).forEach { assert ids[it] == ids[0] + it }
    }

    def "get by id returns empty optional when there is no invoice with given id"() {
        expect:
        !database.getById(1).isPresent()
    }

    def "get all returns empty collection if there were no invoices"() {
        expect:
        database.getAll().isEmpty()
    }

    def "get all returns all invoices in the database, deleted invoice is not returned"() {
        given:
        invoices.forEach({ database.save(it) })

        expect:
        database.getAll().size() == invoices.size()
        database.getAll().forEach({ assert it == invoices.get(it.getId() - 1) })

        when:
        //database.delete(1)
        def firstInvoiceId = database.getAll().get(0).getId()
        database.delete(firstInvoiceId)
        then:
        database.getAll().size() == invoices.size() - 1
        database.getAll().forEach({ assert it == invoices.get(it.getId() - 1) })
        database.getAll().forEach({ assert it.getId() != 1 })

    }

    def "can delete all invoices"() {
        given:
        database.getAll().isEmpty()
        //invoices.forEach({ database.save(it) })

        when:
        invoices.forEach({ database.delete(it.getId()) })

        then:
        database.getAll().isEmpty()
    }

    def "deleting not existing invoice returns optional empty"() {
        expect:
        database.delete(123) == Optional.empty()
    }

    def "it's possible to update the invoice, original invoice is returned"() {
        given:
        def originalInvoice = invoices.get(0)
        int id = database.save(originalInvoice)


        when:
        def result = database.update(id, invoices.get(1))


        then:
        database.getById(id).get() == invoices.get(1)
        result == Optional.of(originalInvoice)
    }

    def "updating not existing invoice returns Optional.empty()"() {
        expect:
        database.update(213, invoices.get(1)) == Optional.empty()
    }

}
