package con.kotlin

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class KotlinTest {

    @Test
    fun testDate() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE,+100)
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(calendar.time)
        println(date)

    }

    @Test
    fun testRandomTime() {
     val date = Calendar.getInstance()
        for (i in 1..70) {
            val random = getRandomTime()
            date.add(Calendar.SECOND,+random)
            println("$random     "+ SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date.time))
        }
    }
    fun getRandomTime(): Int {
        return ((Math.random() * 100).toInt()) + 30
    }

}