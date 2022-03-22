package ua.goit.projectmanagementsystem.model.dao;

import java.util.Objects;

public class CustomerDao {
    private Integer customerId;
    private String customerName;
    private String industry;

    public CustomerDao() {
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
        CustomerDao that = (CustomerDao) o;
        return customerId.equals(that.customerId) && customerName.equals(that.customerName) && industry.equals(that.industry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, industry);
    }
}
