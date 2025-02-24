package com.example.mod3.repository;

import com.example.mod3.Contact;
import com.example.mod3.exeption.ContactNotFoundExeption;
import com.example.mod3.jooq.db.Tables;
import com.example.mod3.jooq.db.tables.records.ContactRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Primary
public class JooqContactRepository implements ContactRepository{

    private final DSLContext dslContext;
    @Override
    public List<Contact> findAll() {
        log.debug("JooqContactRepository->findAll");
        return dslContext.selectFrom(Tables.CONTACT)
                .fetchInto(Contact.class);
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("JooqContactRepository->findById");
        return dslContext.selectFrom(Tables.CONTACT)
                .where(Tables.CONTACT.ID.eq(id))
                .fetchOptional()
                .map(tableRecord -> tableRecord.into(Contact.class));
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("JooqContactRepository->save");
        contact.setId(System.currentTimeMillis());
        ContactRecord contactRecord = dslContext.newRecord(Tables.CONTACT, contact);
        contactRecord.store();
        return contactRecord.into(Contact.class);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("JooqContactRepository->update");
        var mayBeUpdateRecord = dslContext.update(Tables.CONTACT)
                .set(dslContext.newRecord(Tables.CONTACT, contact))
                .where(Tables.CONTACT.ID.eq(contact.getId()))
                .returning()
                .fetchOptional();
        return mayBeUpdateRecord.map(contactRecord -> contactRecord.into(Contact.class))
                .orElseThrow(() -> new ContactNotFoundExeption("Contact not found" + contact.getId()));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("JooqContactRepository->deleteById");

        dslContext.deleteFrom(Tables.CONTACT)
                .where(Tables.CONTACT.ID.eq(id))
                .execute();
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("JooqContactRepository->batchInsert");

        List<Query> insertQueries = new ArrayList<>();
        for(Contact contact : contacts) {
            insertQueries.add(
                    dslContext.insertInto(
                            Tables.CONTACT,
                            Tables.CONTACT.ID,
                            Tables.CONTACT.FIRSTNAME,
                            Tables.CONTACT.LASTNAME,
                            Tables.CONTACT.EMAIL,
                            Tables.CONTACT.PHONENUMBER
                    ).values(
                            contact.getId(),
                            contact.getFirstName(),
                            contact.getLastName(),
                            contact.getEmail(),
                            contact.getPhoneNumber()
                    )
            );
        }
        dslContext.batch(insertQueries).execute();
    }
}
