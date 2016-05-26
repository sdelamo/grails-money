**This plugin is a migration to Grails 3 plugins of the [Ticketbis Grails Money](https://github.com/ticketbis/grails-money) for Grails 2**

# grails-money

[![Build
Status](https://travis-ci.org/ticketbis/grails-money.png?branch=master)](https://travis-ci.org/ticketbis/grails-money)

Grails plugin for money and currency exchange management

## Installation

Add dependency to your BuildConfig;

```groovy
compile "com.ticketbis:money:0.1.17"
```

## Usage

```groovy
import com.ticketbis.money.*

def money = new Money(100, 'EUR')

money.amount == 100.00G
money.currency == Currency.getInstance('EUR')

// Comparing money
money == new Money('100 EUR') // true
money == new Money('100 USD') // false
money != new Money('250 EUR') // true

// Arithmetic
new Money('100 EUR') / 2 == new Money('50 EUR')
new Money('100 EUR') * 2 == new Money('200 EUR')
new Money('100 EUR') + new Money('20 EUR') == new Money('120 EUR')
```
### Use it in domain classes

```groovy
import com.ticketbis.money.Money
import com.ticketbis.money.MoneyUserType

class ProductOffer implements Serializable {

    Money price
    Money oldPrice

    static constraints = {
    }

    static mapping = {
        price type: MoneyUserType, {
            column name: "amount", sqlType: "numeric"
            column name: "currency", nullable: true
        }
        oldPrice type: MoneyUserType, {
            column name: "old_amount", sqlType: "numeric"
            column name: "old_currency", nullable: true
        }
    }
}
```
If you do not define the sqlType, the default sqlType
for the amount column will be _Types.DECIMAL_ and for
the currency column it will be _Types.VARCHAR_
