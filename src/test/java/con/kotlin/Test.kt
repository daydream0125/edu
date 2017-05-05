package con.kotlin

import com.edu.dao.ClassmateDAO
import com.edu.model.*
import com.edu.service.AccountMsgService
import com.edu.service.ClazzService
import com.edu.service.CourseService
import com.edu.service.ExerciseService
import com.edu.utils.model.Answer
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.text.SimpleDateFormat
import java.util.*
import javax.annotation.Resource
import kotlin.collections.ArrayList

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:spring-test.xml", "classpath:applicationContext-hibernate.xml"))
class Test {
    @Resource
    private val accountMsgService: AccountMsgService? = null
    @Resource
    private val clazzService: ClazzService? = null
    @Resource
    private val courseService: CourseService? = null
    @Resource
    private val exerciseService: ExerciseService? = null


    /*
        为班级加入 classmate
     */
    @Test
    fun addClassmatesToClass() {
        val classId = 12
        val courseId = 1
        val teacherId = "1"
        val clazz = clazzService?.getClazzById(classId)
        val course = courseService?.getCourseByCourseId(courseId)
        accountMsgService?.getAccountById(teacherId)
        for (i in 251..310) {
            val classmate = Classmate()
            classmate.course = course
            classmate.clazz = clazz
            classmate.account = accountMsgService?.getAccountById("" + i)
            clazzService!!.saveClassMate(classmate)
        }
    }

    @Test
    fun testGet() {
        val userId = "2"
        val list = exerciseService?.getExercisesByUserId(userId)
        list?.forEach { println(it) }
    }

    @Test
    fun testCheckIsSubmit() {
        println(exerciseService?.checkIsSubmit("2", 2))
    }

    @Test
    fun testGetClassmatesByExerciseId() {
        val exerciseId = 3
        exerciseService?.getClassmatesByExerciseId(exerciseId)?.forEach { print(it) }
    }

    @Test
    fun testGetClassmateByExerciseAndUser() {
        println(exerciseService?.getClassmateByExerciseAndUser(2, "2")
        )
    }

    @Test
    fun testGetSubmitAnswers() {
        val exerciseId = 2
        val classmateId = 37
        exerciseService?.getSubjectiveAnswers(exerciseId, classmateId)?.forEach { println(it) }
    }


    @Test
    fun testAddNameToAccount() {
        for (i in 5..499) {
            val account = accountMsgService?.getAccountById("" + i)
            account?.name = "" + i
            accountMsgService?.updateAccount(account)
        }
    }

    @Test
    fun getProblemsByExercise() {
        val exerciseId = 2
        val list = exerciseService?.getProblemsByExerciseId(exerciseId)
        val problemsId = IntArray(30, { 0 })
        for (i in problemsId.indices) {
            problemsId[i] = (list?.get(i) as? Problem)?.problemId ?: 0
        }
        for (i in 4..29) {
            exerciseService?.addProbemsToExercise(problemsId, i)
        }

    }

    @Test
    fun insertAnswer(): Unit {
        val date = Calendar.getInstance()
        for (exerciseId in 8..12) {
            val list = exerciseService?.getProblemsByExerciseId(exerciseId)
            for (i in 121..180) {
                val answer = Answer()
                answer.exerciseId = exerciseId
                answer.userId = "" + i
                list?.forEach {
                    if (it is Problem) {
                        answer.problemsId.add(it.problemId)
                        val submitAnswer = SubmitAnswer()
                        submitAnswer.answer = getRandomChoose()
                        submitAnswer.startTime = date.time
                        date.add(Calendar.SECOND, +getRandomTime())
                        submitAnswer.endTime = date.time
                        answer.answers.add(submitAnswer)
                    }
                }
                exerciseService?.batchSaveAnswers(answer, "192.168.0.1")
            }
        }
    }

    fun getRandomChoose(): String {
        val random = (Math.random() * 10).toInt() % 4
        when (random) {
            0 -> return "A"
            1 -> return "B"
            2 -> return "C"
            else -> return "D"
        }
    }

    fun getRandomTime(): Int {
        return ((Math.random() * 100).toInt()) + 40
    }

    @Test
    fun testGetUnMarkClassmates() {
        val exerciseId = 2
        val list = exerciseService?.getExistUnMarkProblemClassmates(exerciseId)
        list?.forEach {
            if (it is Classmate) {
                println(it.classmateId)
            }

        }
        println(list?.size)
    }


}



