import java.util.Calendar
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value) {
            field = value
            counter++
        }
}


class LazyProperty(val initializer: () -> Int) {
    var storedValue: Int? = null
    val lazy: Int
        get() {
            if (storedValue == null) {
                storedValue = initializer()
            }
            return storedValue!!
        }
}


class LazyProperty2(val initializer: () -> Int) {
    val lazyValue: Int by lazy(initializer)
}


fun MyDate.toMillis(): Long {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    return c.getTimeInMillis()
}

fun Long.toDate(): MyDate {
    val c = Calendar.getInstance()
    c.setTimeInMillis(this)
    return MyDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE))
}

class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
        val millis = timeInMillis
            ?: throw IllegalStateException("Date was not set")
        return millis.toDate()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
        timeInMillis = value.toMillis()
    }
}