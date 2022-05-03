package ua.goit.projectmanagementsystem.model.domain;

import java.util.Objects;

public class Skill {
    private Integer skillId;
    private String specialization;
    private String level;

    public Skill() {
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
        return "SkillDto{" +
                "skillId=" + skillId +
                ", specialization='" + specialization + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillId.equals(skill.skillId) && specialization.equals(skill.specialization) && level.equals(skill.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, specialization, level);
    }
}
