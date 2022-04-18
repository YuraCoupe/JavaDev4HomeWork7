package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.CompanyConverter;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    public CompanyDto findByName(String name) {
        return companyConverter.daoToDto(companyRepository.findByName(name).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company %s does not exist", name))));
    }

    public boolean isCompanyExist(String name) {
        return companyRepository.findByName(name).isPresent();
    }

    public void save(CompanyDto companyDto) {
        if (companyRepository.findByName(companyDto.getCompanyName()).isEmpty()) {
            companyRepository.save(companyConverter.dtoToDao(companyDto));
        } else {
            throw new CompanyAlreadyExistException("This company already exists");
        }
    }

    public void delete(CompanyDto companyDto) {
        if (!companyRepository.findById(companyDto.getCompanyId()).isEmpty()) {
            companyRepository.delete(companyConverter.dtoToDao(companyDto));
        } else {
            throw new CompanyNotFoundException("This company doesn't exist");
        }
    }

    public void update(CompanyDto companyDto) {
            companyRepository.update(companyConverter.dtoToDao(companyDto));
    }

    public List<CompanyDto> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(companyDao -> companyConverter.daoToDto(companyDao))
                .collect(Collectors.toList());
    }

    public CompanyDto finbById(Integer id) {
        return companyConverter.daoToDto(companyRepository.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company %d does not exist", id))));
    }
}




