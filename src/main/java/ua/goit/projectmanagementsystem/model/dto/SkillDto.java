package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;

public class SkillDto {
    private Integer skillId;
    private String specialization;
    private String level;

    public SkillDto() {
    }

    public SkillDto(String specialization, String level) {
        this.specialization = specialization;
        this.level = level;
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
        SkillDto skillDao = (SkillDto) o;
        return skillId.equals(skillDao.skillId) && specialization.equals(skillDao.specialization) && level.equals(skillDao.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, specialization, level);
    }
}
