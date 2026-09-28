import TimeInterval.*

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) return year.compareTo(other.year)
        if (month != other.month) return month.compareTo(other.month)
        return dayOfMonth.compareTo(other.dayOfMonth)
    }
}
fun test(date1: MyDate, date2: MyDate) {
    println(date1 < date2)
}


fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}


class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate>{
    override fun iterator() : Iterator<MyDate> =
        object : Iterator<MyDate> {
            var current = start
            override fun hasNext() : Boolean = current <= end
            override fun next() : MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                current = current.followingDate()
                return result
            }
        }
}
fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}


enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate =
    addTimeIntervals(timeInterval, 1)

class RepeatedTimeInterval(
    val interval: TimeInterval, val count : Int
)

operator fun TimeInterval.times(count: Int): RepeatedTimeInterval =
    RepeatedTimeInterval(this, count)
operator fun MyDate.plus(repeated : RepeatedTimeInterval) : MyDate =
    addTimeIntervals(repeated.interval, repeated.count)

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}
fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}


class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        numberOfInvocations++
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()