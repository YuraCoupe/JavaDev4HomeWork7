package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperShortDto;

public class DeveloperShortConverter implements Converter<DeveloperDao, DeveloperShortDto> {

    @Override
    public DeveloperShortDto daoToDto(DeveloperDao developerDao) {
        DeveloperShortDto developerShortDto = new DeveloperShortDto();
        developerShortDto.setFirstname(developerDao.getFirstname());
        developerShortDto.setLastname(developerDao.getLastname());
        developerShortDto.setSalary(developerDao.getSalary());

        return developerShortDto;
    }

    @Override
    public DeveloperDao dtoToDao(DeveloperShortDto developerShortDto) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setFirstname(developerShortDto.getFirstname());
        developerDao.setLastname(developerShortDto.getLastname());
        developerDao.setSalary(developerShortDto.getSalary());

        return developerDao;
    }
}
