package com.edu.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class CourseChapter {
    private int id;
    private String title;
    private int num;
    private String desc;
    private ChapterContent chapterContent;
    private CourseChapter parentChapter;
    private Course course;
    private Set<CourseChapter> childChapters = new HashSet<>();
    private Set<Problem> problems = new HashSet<>();

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "num", nullable = true, length = 2)
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 200)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseChapter that = (CourseChapter) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (num !=that.num) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = result + num;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "courseChapter")
    @JsonIgnore
    public ChapterContent getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(ChapterContent chapterContent) {
        this.chapterContent = chapterContent;
    }

    @ManyToOne
    @JoinColumn(name = "parentId")
    @JsonIgnore
    public CourseChapter getParentChapter() {
        return parentChapter;
    }

    public void setParentChapter(CourseChapter parentChapter) {
        this.parentChapter = parentChapter;
    }


    @ManyToOne
    @JoinColumn(name = "courseId")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parentChapter")
    @OrderBy("num asc")
    public Set<CourseChapter> getChildChapters() {
        return childChapters;
    }
    public void setChildChapters(Set<CourseChapter> childChapters) {
        this.childChapters = childChapters;
    }


    @OneToMany(mappedBy = "courseChapter",cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }
}
