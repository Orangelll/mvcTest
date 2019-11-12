package com.silver.service;

import com.silver.pojo.Customer;
import com.silver.pojo.PageBean;

public interface CustomerService {

    public PageBean<Customer> allList(int offset,int pageRecord) throws Exception;

    public Customer getById(long id) throws Exception;

    public void insert(Customer customer) throws Exception;

    public void delete(long id) throws Exception;

    public void update(long id,Customer customer) throws Exception;

    public PageBean<Customer> queryList(int offset,int pageRecord,Customer customer) throws Exception;

}
