import com.edu.model.*;
import com.edu.service.AccountMsgService;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml", "classpath:applicationContext-hibernate.xml"})
public class DB extends AbstractJUnit4SpringContextTests {
    @Resource
    private AccountMsgService accountMsgService;
    @Resource
    private ClazzService clazzService;
    @Resource
    private CourseService courseService;

    @Test
    public void insertAccount() {
        for (int i = 5; i < 500; i++) {
            accountMsgService.saveAccount("" + i, "" + i);
        }
    }

    @Test
    public void updateAccount() {
        for (int i = 5; i < 500; i++) {
            Account account = accountMsgService.getAccountById("" + i);
            String prefix = "E21314";
            if (i < 10) {
                prefix += "00";
            }
            if (i > 10 && i < 100) {
                prefix += "0";
            }
            account.setCardNumber(prefix + i);
            accountMsgService.updateAccount(account);
        }
    }

    @Test
    public void setRoleStudent() {
        for (int i = 5; i < 500; i++) {
            Account account = accountMsgService.getAccountById("" + i);
            accountMsgService.setRoleStudent(account);
        }
    }

    @Test
    public void addClassmatesToClass() {
        int classId = 5;
        int courseId = 1;
        String teacherId = "1";
        Clazz clazz = clazzService.getClazzById(classId);
        Course course = courseService.getCourseByCourseId(courseId);
        accountMsgService.getAccountById(teacherId);
        for (int i = 5;i < 70; i++) {
            Classmate classmate = new Classmate();
            classmate.setCourse(course);
            classmate.setClazz(clazz);
            classmate.setAccount(accountMsgService.getAccountById(""+i));
            clazzService.saveClassMate(classmate);
        }
    }
}
