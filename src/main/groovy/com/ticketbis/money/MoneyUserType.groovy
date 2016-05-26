package com.ticketbis.money

import groovy.util.logging.Log4j
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.usertype.UserType

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types

@Log4j
class MoneyUserType implements UserType {

    @Override
    int[] sqlTypes() {
        [Types.DECIMAL, Types.VARCHAR] as int[]
    }

    @Override
    Class returnedClass() {
        Money.class
    }

    @Override
    boolean equals(Object x, Object y) throws HibernateException {
        x == y
    }

    @Override
    int hashCode(Object x) throws HibernateException {
        x.hashCode()
    }

    @Override
    Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

        if ( rs.wasNull() ) {
            return null
        }

        BigDecimal amount = rs.getBigDecimal(names[0])
        String currencyCode = rs.getString(names[1])
        if ( amount == null && currencyCode == null ) {
            return null
        }
        Currency currency = currencyCode ? Currency.getInstance(currencyCode) :  null
        new Money(amount, currency)

    }

    @Override
    void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {

        if (value) {
            Money money = (Money) value
            st.setBigDecimal(index, money.amount)
            st.setString(index + 1, money.currency.currencyCode)

        } else {
            st.setNull(index, null)
            st.setNull(index + 1, null)
        }
    }

    @Override
    Object deepCopy(Object value) throws HibernateException {
        Money receivedMoney = (Money) value
        new Money(receivedMoney)
    }

    @Override
    boolean isMutable() {
        true
    }

    @Override
    Serializable disassemble(Object value) throws HibernateException {
        (Serializable) value
    }

    @Override
    Object assemble(Serializable cached, Object owner) throws HibernateException {
        cached
    }

    @Override
    Object replace(Object original, Object target, Object owner) throws HibernateException {
        deepCopy(original)
    }
}
