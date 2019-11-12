package com.silver.dao;

import com.silver.pojo.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDao {

    public List<Customer> findAll (@Param("offset") int offset,@Param("pageRecord") int PageRecord);

    public Customer findById(long id) throws Exception;

    public void addCustomer(Customer customer) throws Exception;

    public void deleteCustomer(long id) throws Exception;

    public void editCustomer(@Param("id") long id,@Param("customer") Customer customer) throws Exception ;

    public int countCustomers(@Param("customer") Customer customer) throws Exception;

    public List<Customer> queryAll(@Param("offset") int offset,@Param("pageRecord")
                                   int pageRecord,@Param("customer") Customer customer);

}

