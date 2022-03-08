package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;

public class CustomerDto {
    private Integer customerId;
    private String customerName;
    private String industry;

    public CustomerDto() {
    }

    public CustomerDto(String customerName, String industry) {
        this.customerName = customerName;
        this.industry = industry;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "CustomerDao{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return customerId.equals(that.customerId) && customerName.equals(that.customerName) && industry.equals(that.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, industry);
    }

}
