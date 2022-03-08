package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.CompanyDao;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;

public class CompanyConverter implements Converter<CompanyDao, CompanyDto> {
    @Override
    public CompanyDao dtoToDao(CompanyDto companyDto) {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setCompanyId(companyDto.getCompanyId());
        companyDao.setCompanyName(companyDto.getCompanyName());
        companyDao.setCompanyLocation(companyDto.getCompanyLocation());
        return companyDao;
    }

    @Override
    public CompanyDto daoToDto(CompanyDao companyDao) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyId(companyDao.getCompanyId());
        companyDto.setCompanyName(companyDao.getCompanyName());
        companyDto.setCompanyLocation(companyDao.getCompanyLocation());
        return companyDto;
    }
}
