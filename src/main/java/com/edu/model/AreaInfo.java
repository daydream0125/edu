package com.edu.model;



import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class AreaInfo {
    private int areaId;
    private String areaName;
    private Integer parentId;

    @Id
    @Column(name = "areaID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "areaName", nullable = true, length = 32)
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Basic
    @Column(name = "parentID", nullable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaInfo areaInfo = (AreaInfo) o;

        if (areaId != areaInfo.areaId) return false;
        if (areaName != null ? !areaName.equals(areaInfo.areaName) : areaInfo.areaName != null) return false;
        if (parentId != null ? !parentId.equals(areaInfo.parentId) : areaInfo.parentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = areaId;
        result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
