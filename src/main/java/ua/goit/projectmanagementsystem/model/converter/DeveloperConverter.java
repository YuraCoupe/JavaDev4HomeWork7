package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dao.SkillDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectDto;
import ua.goit.projectmanagementsystem.model.dto.SkillDto;

import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperConverter implements Converter<DeveloperDao, DeveloperDto> {
        private final SkillConverter skillConverter;

    public DeveloperConverter(SkillConverter skillConverter) {
        this.skillConverter = skillConverter;
    }

    @Override
    public DeveloperDto daoToDto(DeveloperDao developerDao) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloperId(developerDao.getDeveloperId());
        developerDto.setFirstname(developerDao.getFirstname());
        developerDto.setLastname(developerDao.getLastname());
        developerDto.setAge(developerDao.getAge());
        developerDto.setSalary(developerDao.getSalary());
        developerDto.setCompanyId(developerDao.getCompanyId());
        Set<SkillDto> skills = developerDao.getSkills().stream()
                .map(skillDao -> skillConverter.daoToDto(skillDao))
                .collect(Collectors.toSet());
        developerDto.setSkills(skills);
        return developerDto;
    }

    @Override
    public DeveloperDao dtoToDao(DeveloperDto developerDto) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloperId(developerDto.getDeveloperId());
        developerDao.setFirstname(developerDto.getFirstname());
        developerDao.setLastname(developerDto.getLastname());
        developerDao.setAge(developerDto.getAge());
        developerDao.setSalary(developerDto.getSalary());
        developerDao.setCompanyId(developerDto.getCompanyId());
        Set<SkillDao> skills = developerDto.getSkills().stream()
                .map(skillDto -> skillConverter.dtoToDao(skillDto))
                .collect(Collectors.toSet());
        developerDao.setSkills(skills);
        return developerDao;
    }
}
