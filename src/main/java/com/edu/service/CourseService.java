package com.edu.service;

import com.edu.annotation.SystemServiceLog;
import com.edu.dao.*;
import com.edu.model.*;
import com.edu.utils.Page;
import com.edu.utils.UploadFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class CourseService {
    @Resource
    private UserInfoDAO userInfoDAO;
    @Resource
    private CourseDAO courseDAO;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private ClazzDAO clazzDAO;
    @Resource
    private ChapterContentDAO chapterContentDAO;
    @Resource
    private CourseChapterDAO courseChapterDAO;
    @Resource
    private ClassmateDAO classmateDAO;
    @Resource
    private SubmitExerciseDAO submitExerciseDAO;


    @SystemServiceLog("增设课程")
    //为待存取的course设置外键开课老师以及开课老师所在的学校
    public void saveCourse(Course course, String teacherId) {
        course.setTeacher(accountDAO.findByUserId(teacherId));
        courseDAO.save(course);
    }

    @SystemServiceLog("获取教师开设的课程")
    public List getCoursesByTeacherId(String teacherId) {
        return courseDAO.findCoursesByTeacherId(teacherId);
    }

    public List getAllCourse() {
        return courseDAO.getAllCourse();
    }

    @SystemServiceLog("分页获取课程")
    public List getCourseByPage(Page page) {
        return courseDAO.getCourseByPage(page);
    }
    @SystemServiceLog("新增班级")
    //为 class 设置外键 teacher 和课程 course
    public void saveClass(Clazz clazz, String teacherId, int courseId) {
        clazz.setTeacher(accountDAO.findByUserId(teacherId));
        clazz.setCourse(courseDAO.getCourseById(courseId));
        clazzDAO.save(clazz);
    }


    @SystemServiceLog("获取学生数量")
    public long getStudentsdNumByCourseId(int courseId) {
        return classmateDAO.getStudentsNumByCourseId(courseId);
    }

    @SystemServiceLog("获取课程，依据 id")
    public Course getCourseByCourseId(int Id) {
        return courseDAO.getCourseById(Id);
    }
    @SystemServiceLog("依据课程下的班级")
    public List getClazzByCourseId(int courseId) {
        return clazzDAO.getClazzByCourseId(courseId);
    }



    @SystemServiceLog("获取课程章节信息")
    public List getCourseChapterByCourseId(int courseId) {
        return courseChapterDAO.getAllChapterByCourseId(courseId);
    }

    @SystemServiceLog("增设课程章节信息")
    //保存课程章节信息，其中courseChapter为章的信息，sectionList为章下面节信息的封装
    public boolean saveCourseChapter(CourseChapter courseChapter,int courseId,String sections[]) {
        try {
            Course course = getCourseByCourseId(courseId);
            courseChapter.setCourse(course);
            int num = 1;
            for (String section : sections) {
                CourseChapter chapter = new CourseChapter();


                chapter.setTitle(section);
                chapter.setCourse(course);
                //为节编号
                chapter.setNum(num);
                num++;
                //设置双向关联，只需保存父节点（章）即可，Hibernate 自动保存章下的节信息
                chapter.setParentChapter(courseChapter);
                courseChapter.getChildChapters().add(chapter);
                //自动增加 chapterContent记录
                ChapterContent chapterContent = new ChapterContent();
                chapterContent.setCourseChapter(chapter);
                chapterContentDAO.save(chapterContent);
            }
            courseChapterDAO.save(courseChapter);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @SystemServiceLog("获取课程章节信息，依据 id")
    public CourseChapter getCourseChapterById(int id) {
        return courseChapterDAO.getChapterById(id);
    }
    public void saveChapterContent(ChapterContent chapterContent) {
        chapterContentDAO.save(chapterContent);
    }


    @SystemServiceLog("获取课程内容")
    public ChapterContent getChapterContentByChapterId(int chapterId) {
        return chapterContentDAO.getChapterContentByChapterId(chapterId);
    }


    public ChapterContent getChapterContentById(int id) {
        return chapterContentDAO.getChapterContentById(id);
    }

    public void updateChapterContent(ChapterContent chapterContent) {
        chapterContentDAO.update(chapterContent);
    }


    public List getSectionsByChapterId(int chapterId) {
        return courseChapterDAO.getSectionsByChapterId(chapterId);
    }


    @SystemServiceLog("上传课程视屏")
    public String uploadChapterVideo(MultipartFile multipartFile,int contentId) {

        //相对路径,数据库存储该字段
        String relativePath = "course-video/"+ System.currentTimeMillis() + multipartFile.getOriginalFilename();
        //绝对路径,文件存放于本地的路径
        String absolutePath = UploadFile.BASE_URL + relativePath;

        File file = new File(absolutePath);

        ChapterContent chapterContent = this.getChapterContentById(contentId);
        //文件已存在,先删除,后保存
        if (!chapterContent.getVideoPath().equals("none")) {
            File deleteFile = new File(UploadFile.BASE_URL + chapterContent.getVideoPath());
            deleteFile.delete();
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        chapterContent.setVideoPath(relativePath);
        chapterContentDAO.update(chapterContent);
        return relativePath;
    }

    @SystemServiceLog("更新课程内容")
    public boolean updateChapterContent(int sectionId,String content) {
        try {
            ChapterContent chapterContent = chapterContentDAO.getChapterContentById(sectionId);
            chapterContent.setContent(content);
            chapterContentDAO.update(chapterContent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public int getCourseIdByChapterId(int chapterId) {
        return courseChapterDAO.getCourseIdByChapterId(chapterId);
    }


    public String getCourseName(int courseId) {
        return courseDAO.getCourseName(courseId);

    }

    public List getSharpCourse(int courseId) {
        return courseDAO.getSharpCourse(courseId);
    }

    public Long getCoursesCount() {
        return courseDAO.getCoursesCount();
    }


    public void finishCourse(int courseId) {
        classmateDAO.countScore(courseId);
    }

    public List getSharpCourseByTeacherId(String teacherId) {
        return courseDAO.getSharpCourseByTeacherId(teacherId);
    }
}
