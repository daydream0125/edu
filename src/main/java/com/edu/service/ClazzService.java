package com.edu.service;

import com.edu.dao.AccountDAO;
import com.edu.dao.ClassmateDAO;
import com.edu.dao.ClazzDAO;
import com.edu.dao.CourseDAO;
import com.edu.model.Account;
import com.edu.model.Classmate;
import com.edu.model.Clazz;
import com.edu.utils.OfficeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClazzService {
    @Resource
    private ClassmateDAO classmateDAO;
    @Resource
    private ClazzDAO clazzDAO;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private CourseDAO courseDAO;

    public Clazz getClazzById(int clazzId) {
        return clazzDAO.get(clazzId);
    }

    public void saveClassMate(Classmate classmate) {
        classmateDAO.save(classmate);
    }


    public boolean isRegister(int classId, String userId) {
        return classmateDAO.isRegister(classId, userId);
    }

    public List getClazzByUserId(String userId) {
        return classmateDAO.getClazzByUserId(userId);
    }

    public long getClassmatesCount(int classId) {
        return classmateDAO.getClassmatesCount(classId);
    }

    public List getClassmatesByClassId(int classId) {
        return classmateDAO.getClassmates(classId);
    }

    public boolean register(String userId, int classId, int courseId) {
        if (classmateDAO.isRegister(classId, userId)) {
            return false;
        }
        try {
            Classmate classmate = new Classmate();
            classmate.setAccount(accountDAO.getByUserId(userId));
            classmate.setClazz(clazzDAO.get(classId));
            classmate.setCourse(courseDAO.getCourseById(courseId));
            classmateDAO.save(classmate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void openRegister(int classId) {
        clazzDAO.updateIsPublicRegister(classId, true);
    }

    public boolean addClass(Clazz clazz, String teacherId, int courseId) {
        try {
            clazz.setTeacher(accountDAO.getByUserId(teacherId));
            clazz.setCourse(courseDAO.getCourseById(courseId));
            clazz.setCreateTime(new Date(System.currentTimeMillis()));
            clazzDAO.save(clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getRegisters(int classId) {
        return classmateDAO.getRegisters(classId);
    }

    public boolean approveRegisters(int[] approveRegisters) {
        try {
            classmateDAO.approveRegisters(approveRegisters);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //导入学生
    public List<String> importStudent(MultipartFile file, int classId) {
        List<String> errorList = new ArrayList<>();
        List<String> errorInfo = new ArrayList<>();
        errorList.add("success");
        Clazz clazz = clazzDAO.get(classId);
        if (clazz == null) {
            errorInfo.add("error");
            errorInfo.add("上传出错,请检查选择班级是否有误");
            return errorInfo;
        }
        String filename = file.getOriginalFilename();
        String subfix = filename.lastIndexOf(".") == -1 ? "" : filename.substring(filename.lastIndexOf(".") + 1);
        try {
            List<String> students = OfficeUtil.readExcel(file.getInputStream(), subfix);
            for (String s:students) {
                System.out.println(s);
            }
            for (String cardNumber : students) {
                Account account = accountDAO.getByCardNumber(cardNumber);
                //学生已注册,加入班级
                if (account != null) {
                    //已注册,跳过
                    if (classmateDAO.isRegister(classId, account.getUserId())) {
                        continue;
                    }
                    Classmate classmate = new Classmate();
                    classmate.setAccount(account);
                    classmate.setClazz(clazz);
                    classmate.setCourse(clazz.getCourse());
                    classmateDAO.save(classmate);
                } else {
                    //学生未注册,加入导入错误列表
                    errorList.add(cardNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorInfo.add("error");
            errorInfo.add("上传出错,请检查文件格式是否符合规定");
            return errorInfo;
        }
        return errorList;
    }



}
