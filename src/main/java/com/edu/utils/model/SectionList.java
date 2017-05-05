package com.edu.utils.model;

import com.edu.model.CourseChapter;

import java.util.ArrayList;
import java.util.List;

/*
    辅助类，用于前端向后台传递章下的节，为 list 集合，
    springMVC 可自动进行封装
 */
public class SectionList {
    private List<CourseChapter> sections = new ArrayList<>();


    public List<CourseChapter> getSections() {
        return sections;
    }

    public void setSections(List<CourseChapter> sections) {
        this.sections = sections;
    }
}
