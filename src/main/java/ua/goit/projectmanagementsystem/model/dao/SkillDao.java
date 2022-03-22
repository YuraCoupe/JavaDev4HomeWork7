package ua.goit.projectmanagementsystem.model.dao;

import java.util.Objects;

public class SkillDao {
    private Integer skillId;
    private String specialization;
    private String level;

    public SkillDao() {
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SkillDao{" +
                "skillId=" + skillId +
                ", specialization='" + specialization + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDao skillDao = (SkillDao) o;
        return skillId.equals(skillDao.skillId) && specialization.equals(skillDao.specialization) && level.equals(skillDao.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, specialization, level);
    }
}
