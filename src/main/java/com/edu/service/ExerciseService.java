package com.edu.service;

import com.edu.dao.*;
import com.edu.model.*;
import com.edu.utils.JudgeUtil;
import com.edu.utils.UploadFile;
import com.edu.utils.model.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ExerciseService {
    @Resource
    private ProblemDAO problemDAO;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private CourseDAO courseDAO;
    @Resource
    private CourseChapterDAO courseChapterDAO;
    @Resource
    private ExerciseDAO exerciseDAO;
    @Resource
    private SubmitAnswerDAO submitAnswerDAO;
    @Resource
    private ClazzDAO clazzDAO;
    @Resource
    private ClassmateDAO classmateDAO;
    @Resource
    private SubmitExerciseDAO submitExerciseDAO;


    public List getProblemBySectionId(int sectionId) {
        return problemDAO.getProblemBySectionId(sectionId);
    }

    public Problem getProblemById(int problemId) {
        return problemDAO.getProblemById(problemId);
    }

    public void updateProblem(Problem problem) {
        problemDAO.update(problem);
    }

    //以小节名搜索数据库，并取出对应的题目。
    public List searchChapterByChapterTitle(String chapterTitle) {
        return courseChapterDAO.searchByChapterTitle(chapterTitle);
    }

    public void deleteProblem(int problemId) {
        problemDAO.delete(problemId);
    }

    public Long getProblemCountByChapterId(int chapterId) {
        return problemDAO.getProblemCountByChapterId(chapterId);
    }

    public CourseChapter getCourseChapterById(int chapterId) {
        return courseChapterDAO.getChapterById(chapterId);
    }

    public boolean saveExercise(Exercise exercise, int courseId, int classId, String teacherId) {
        try {
            exercise.setCourse(courseDAO.getCourseById(courseId));
            exercise.setClazz(clazzDAO.get(classId));
            exercise.setTeacher(accountDAO.getByUserId(teacherId));
            exerciseDAO.save(exercise);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Exercise getExerciseById(int exerciseId) {
        return exerciseDAO.getById(exerciseId);
    }

    public List getExerciseByTeacherId(String teacherId) {
        return exerciseDAO.getExerciseByTeacherId(teacherId);
    }

    public void updateExercise(Exercise exercise) {
        exerciseDAO.update(exercise);
    }

    public void deleteExerciseById(int exerciseId) {
        exerciseDAO.delete(exerciseId);
    }

    public boolean releaseExercise(int exerciseId) {
        try {
            exerciseDAO.updateIsRelease(exerciseId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getExercisesByUserId(String userId) {
        return exerciseDAO.getExercisesByUserId(userId);
    }

    public Long getProblemCountByExerciseId(int exerciseId) {
        return exerciseDAO.getProblemCount(exerciseId);
    }

    public List getProblemsByExerciseId(int exerciseId) {
        return exerciseDAO.getProlems(exerciseId);
    }


    //批量保存用户提交的答案, Answer 为方便 springmvc 封装数据的辅助类
    //submitAnswers属于同一exercise
    public boolean batchSaveAnswers(Answer answer, String ip) {
        try {
            Exercise exercise = exerciseDAO.getById(answer.getExerciseId());
            Classmate classmate = classmateDAO.getClassmateByExerciseAndUser(answer.getExerciseId(), answer.getUserId());
            //创建 submitAnswer
            SubmitExercise submitExercise = new SubmitExercise();

            submitExercise.setSubmitTime(new Date());
            submitExercise.setSubmitIP(ip);
            submitExercise.setExercise(exercise);
            submitExercise.setClassmate(classmate);

            submitExerciseDAO.save(submitExercise);

            for (int i = 0; i < answer.getAnswers().size(); i++) {
                Problem problem = problemDAO.getProblemById(answer.getProblemsId().get(i));
                problem.setTotalSolutions(problem.getTotalSolutions() + 1);

                answer.getAnswers().get(i).setClassmate(classmate);
                answer.getAnswers().get(i).setProblem(problem);
                answer.getAnswers().get(i).setExercise(exercise);
                answer.getAnswers().get(i).setSubmitIp(ip);
                submitAnswerDAO.save(answer.getAnswers().get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveProblem(Problem problem, String createUserId, int sectionId, int courseId) {
        try {
            problem.setCreateTime(new Date());
            problem.setUpdateTime(new Date());
            problem.setCreateUser(accountDAO.getByUserId(createUserId));
            problem.setCourseChapter(courseChapterDAO.getChapterById(sectionId));
            problem.setCourse(courseDAO.getCourseById(courseId));
            problemDAO.save(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String uploadPicture(MultipartFile multipartFile) {
        //相对路径,数据库存储该字段
        String relativePath = "problem-img/" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        //绝对路径,文件存放于本地的路径
        String absolutePath = UploadFile.BASE_URL + relativePath;

        File file = new File(absolutePath);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return relativePath;
    }

    public boolean addProblemsToExercise(int[] problemsId, int exerciseId) {
        if (problemsId.length == 0) {
            return false;
        } else {
            try {
                Exercise exercise = exerciseDAO.getById(exerciseId);
                if (exercise == null) {
                    return false;
                } else {
                    for (int i : problemsId) {
                        Problem problem = problemDAO.getProblemById(i);
                        if (problem == null) {
                            continue;
                        }
                        exercise.getProblems().add(problem);
                    }
                }
                exerciseDAO.save(exercise);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public List filterProblems(int type, int keywords) {
        if (type == 1) {
            return problemDAO.getProblemsByCourseId(keywords);
        } else if (type == 2) {
            return problemDAO.getProblemsByChapterId(keywords);

        } else if (type == 3) {
            return problemDAO.getProblemBySectionId(keywords);
        } else return null;
    }

    public boolean checkIsSubmit(String userId, int exerciseId) {
        return submitExerciseDAO.checkIsSubmit(userId, exerciseId);
    }


    public Long getSubmitExerciseCount(int exerciseId) {
        return submitExerciseDAO.getSubmitCount(exerciseId);
    }

    public Long getJudgeCount(int exerciseId) {
        return submitExerciseDAO.getJudgeCount(exerciseId);
    }

    public boolean markObjectiveProblem(int exerciseId) {

        List<SubmitAnswer> answers = submitAnswerDAO.getObjectiveAnswer(exerciseId);

        if (answers == null) {
            return false;
        }
        try {

            for (SubmitAnswer answer : answers) {
                //已批阅，跳过
                if (answer.getJudge()) {
                    continue;
                }
                Problem problem = answer.getProblem();
                if (problem.getIsManualJudge()) {
                    if (answer.getAnswer().equalsIgnoreCase(answer.getProblem().getSolution())) {
                        answer.setResult(JudgeUtil.CORRECT_OBJECT_PROBLEM_SCORE);
                        //答案正确，接受+1
                        problem.setAcceptedSolutions(problem.getAcceptedSolutions() + 1);
                    } else answer.setResult(JudgeUtil.ERROR_OBJECT_PROBLEM_SCORE);
                }
                answer.setJudge(true);
                //更新成绩字段
                submitAnswerDAO.update(answer);
            }


            List<SubmitExercise> submitExercises = submitExerciseDAO.get(exerciseId);
            for (SubmitExercise exercise : submitExercises) {
                //设置客观题批改时间
                if (exercise.getJudgeObjective()) {
                    continue;
                }
                exercise.setJudgeObjective(true);
                exercise.setJudgeObjectiveTime(new Date());
                //count score
                List<Double> results = submitAnswerDAO.getObjectiveResults(exerciseId, exercise.getClassmate().getClassmateId());
                Double score = 0.0;
                for (Double d : results) {
                    score += d;
                }
                exercise.setScore(exercise.getScore() == null ? score : exercise.getScore() + score);
                submitExerciseDAO.update(exercise);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List getClassmatesByExerciseId(int exerciseId) {
        return submitExerciseDAO.getClassmatesByExerciseId(exerciseId);
    }

    public List getSubjectiveAnswers(int exerciseId, int classmateId) {
        return submitAnswerDAO.getAnswersByClassmateAndExercise(exerciseId, classmateId);
    }

    public Classmate getClassmateByExerciseAndUser(int exerciseId, String userId) {
        return classmateDAO.getClassmateByExerciseAndUser(exerciseId, userId);
    }

    public List getExistUnMarkProblemClassmates(int exerciseId) {
        return submitExerciseDAO.getExistUnMarkProblemClassmates(exerciseId);
    }

    public boolean markSubjectiveProblem(int[] scores, int[] answersId) {
        if (answersId == null) {
            return false;
        }
        try {
            int score = 0;
            SubmitExercise submitExercise = submitExerciseDAO.getByExerciseAndClassmate(submitAnswerDAO.get(answersId[0]).getExercise().getExerciseId(), submitAnswerDAO.get(answersId[0]).getClassmate().getClassmateId());
            for (int i = 0; i < scores.length; i++) {
                SubmitAnswer submitAnswer = submitAnswerDAO.get(answersId[i]);
                submitAnswer.setResult((double) scores[i]);
                submitAnswer.setJudge(true);
                score += scores[i];
                submitAnswerDAO.update(submitAnswer);
            }
            submitExercise.setJudgeSubjective(true);
            submitExercise.setJudgeSubjectiveTime(new Date());
            submitExercise.setScore(submitExercise.getScore() + score);
            submitExerciseDAO.update(submitExercise);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //计算每位同学某一练习下的最终得分，计算规则为 总分/problemCount*10（转换为百分制）
    public boolean countScore(int exerciseId) {
        try {
            Long problemCount = exerciseDAO.getProblemCount(exerciseId);
            List<SubmitExercise> submitExercises = submitExerciseDAO.get(exerciseId);
            for (SubmitExercise s : submitExercises) {
                //已经计算过，跳过
                if (s.getCountScore()) {
                    continue;
                }
                s.setScore(s.getScore() / problemCount * 10.0);
                s.setCountScore(true);
                submitExerciseDAO.update(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isExercciseFinishJudge(int exerciseId) {
        return exerciseDAO.getJudge(exerciseId);
    }


    public List getClassmates(int exerciseId) {
        return submitExerciseDAO.getClassmates(exerciseId);
    }

    public int getClassmateIdByExerciseAndUser(int exerciseId, String userId) {
        return submitExerciseDAO.getClassmateByExerciseAndUser(exerciseId,userId);
    }

    public boolean checkIsExistsAnswers(int exerciseId, String userId) {
        return submitExerciseDAO.getByExerciseAndUser(exerciseId,userId) != null;
    }

    public List getAnswers(int exerciseId,String userId) {
        return submitAnswerDAO.getAnswersByUserAndExercise(exerciseId,userId);
    }

    public List getSharpExercise(int exerciseId) {
        return exerciseDAO.getSharpExercise(exerciseId);
    }
}
