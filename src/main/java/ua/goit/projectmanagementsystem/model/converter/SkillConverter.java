package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.SkillDao;
import ua.goit.projectmanagementsystem.model.dto.SkillDto;

public class SkillConverter implements Converter<SkillDao, SkillDto> {
    @Override
    public SkillDto daoToDto(SkillDao skillDao) {
        SkillDto skillDto = new SkillDto();
        skillDto.setSkillId(skillDao.getSkillId());
        skillDto.setLevel(skillDao.getLevel());
        skillDto.setSpecialization(skillDao.getSpecialization());
        return skillDto;
    }

    @Override
    public SkillDao dtoToDao(SkillDto skillDto) {
        SkillDao skillDao = new SkillDao();
        skillDao.setSkillId(skillDto.getSkillId());
        skillDao.setLevel(skillDto.getLevel());
        skillDao.setSpecialization(skillDto.getSpecialization());
        return skillDao;
    }
}
