package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.CustomerDao;
import ua.goit.projectmanagementsystem.model.dto.CustomerDto;

public class CustomerConverter implements Converter<CustomerDao, CustomerDto>{

    @Override
    public CustomerDto daoToDto(CustomerDao customerDao) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customerDao.getCustomerId());
        customerDto.setCustomerName(customerDao.getCustomerName());
        customerDto.setIndustry(customerDao.getIndustry());
        return customerDto;
    }

    @Override
    public CustomerDao dtoToDao(CustomerDto customerDto) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setCustomerId(customerDto.getCustomerId());
        customerDao.setCustomerName(customerDto.getCustomerName());
        customerDao.setIndustry(customerDto.getIndustry());
        return customerDao;
    }
}
