package com.silver.service.impl;

import com.silver.dao.CustomerDao;
import com.silver.pojo.Customer;
import com.silver.pojo.PageBean;
import com.silver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public PageBean<Customer> allList(int offset, int pageRecord) throws Exception {
        List<Customer> customers = customerDao.findAll(offset, pageRecord);
        Customer customer = new Customer();
        int totalRecord=customerDao.countCustomers(customer);
        int pageNum = offset/pageRecord + 1;
        int totalPage;
        if (totalRecord % pageRecord == 0){
            totalPage = totalRecord/pageRecord;
        } else {
            totalPage = totalRecord/pageRecord + 1;
        }
        PageBean<Customer> pageBean = new PageBean<>();
        pageBean.setBeanList(customers);
        pageBean.setPageNum(pageNum);
        pageBean.setPageRecord(pageRecord);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Customer getById(long id) throws Exception {
        Customer customer = customerDao.findById(id);
        return customer;
    }

    @Override
    public void insert(Customer customer) throws Exception {
        customerDao.addCustomer(customer);
    }

    @Override
    public void delete(long id) throws Exception {
        customerDao.deleteCustomer(id);
    }

    @Override
    public void update(long id, Customer customer) throws Exception {
        customerDao.editCustomer(id,customer);
    }

    @Override
    public PageBean<Customer> queryList(int offset, int pageRecord, Customer customer) throws Exception {
        List<Customer> customers = customerDao.queryAll(offset,pageRecord,customer);    // 获取每页的所有记录
        int totalRecord = customerDao.countCustomers(customer);                         // 获取模糊查询总记录数
        int pageNum = offset/pageRecord + 1;                                            // 当前页数
        // 计算总页数
        int totalPage;
        if (totalRecord % pageRecord == 0){
            totalPage = totalRecord/pageRecord;
        } else {
            totalPage = totalRecord/pageRecord + 1;
        }
        PageBean<Customer> pageBean = new PageBean<>();   // 将分页数据封装到PageBean中
        pageBean.setBeanList(customers);
        pageBean.setPageNum(pageNum);
        pageBean.setPageRecord(pageRecord);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }
}
