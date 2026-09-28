import java.util.Calendar

/*
 * Returns the following date after the given one.
 * For example, for Dec 31, 2019 the date Jan 1, 2020 is returned.
 */
fun MyDate.followingDate(): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    val millisecondsInDay = 24 * 60 * 60 * 1000L
    val timeInMillis = c.timeInMillis + millisecondsInDay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}

/*
 * Returns the date after the given time interval.
 * The interval is specified as the given amount of days, weeks of years.
 * Usages:
 * 'date.addTimeIntervals(TimeInterval.DAY, 4)'
 * 'date.addTimeIntervals(TimeInterval.WEEK, 3)'
 */
fun MyDate.addTimeIntervals(timeInterval: TimeInterval, amount: Int): MyDate {
    val c = Calendar.getInstance()
    c.set(year + if (timeInterval == TimeInterval.YEAR) amount else 0, month, dayOfMonth)
    var timeInMillis = c.timeInMillis
    val millisecondsInDay = 24 * 60 * 60 * 1000L
    timeInMillis += amount * when (timeInterval) {
        TimeInterval.DAY -> millisecondsInDay
        TimeInterval.WEEK -> 7 * millisecondsInDay
        TimeInterval.YEAR -> 0L
    }
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}