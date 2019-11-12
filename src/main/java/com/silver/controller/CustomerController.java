package com.silver.controller;

import com.silver.pojo.Customer;
import com.silver.pojo.PageBean;
import com.silver.service.CustomerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private Customer myCustomer;

    @RequestMapping(value = "/fream",method = RequestMethod.GET)
    public String mainFream(){
        //主界面
        return "fream";
    }

    @RequestMapping(value = "/allCustomerList/{pageNum}",method = RequestMethod.GET)
    public String allCustomerList(@PathVariable("pageNum") Integer pageNum, Model model) throws Exception {
        if (pageNum == null) {
            pageNum = 1;
        }
        int pageRecord = 8;
        PageBean<Customer> pb = customerService.allList((pageNum-1)*pageRecord,pageRecord);
        myCustomer = new Customer();
        model.addAttribute("pb",pb);
        return "list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addCustomer(){
        return "add";
    }

    @RequestMapping(value = "/addCustomerSubmit",method = RequestMethod.POST)
    public String addCustomerSuccess(Customer customer) throws Exception {
        customerService.insert(customer);
        return "redirect:allCustomerList/1";
    }

    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editCustomer(@PathVariable("id") long id,Model model) throws Exception {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "edit";
    }

    @RequestMapping(value = "/addCustomerSubmit",method = RequestMethod.POST)
    public String editCustomerSubmit(@Param("id") long id,@Param("customer") Customer customer) throws Exception {
        customerService.update(id,customer);
        return "redirect:allCustomerList/1";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public void deleteCustomer(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        customerService.delete(id);
        String url = request.getRequestURI().toString();
        String newUrl = url.substring(0,url.lastIndexOf("delete")) + "allCustomerList/1";
        response.sendRedirect(newUrl);
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String queryList(){
        // 进入模糊查询页面
        return "query";
    }

    // 由于模糊查询需要使用post提交表单，这里设置了可以使用get和post方法传递url
    @RequestMapping(value = "/list/{pageNum}", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryCustomerList(@PathVariable("pageNum") Integer pageNum,Customer customer, Model model) throws Exception{
        // 获取从前端传过来的当前页数，并进行分页操作，显示列表，具有模糊查询的功能
        if(pageNum == null){
            pageNum = 1;                 // 空则当前页面设置为1
        }
        int pageRecord = 8;             // 设置每页记录数为8
        if(!customer.isNull()){         // 如果模糊查询中有设置查询信息，则将信息保存到myCustomer对象中
            myCustomer = customer;
        }
        PageBean<Customer> pb = customerService.queryList((pageNum-1)*pageRecord, pageRecord,myCustomer);
        model.addAttribute("pb",pb);

        return "list";
    }

    @RequestMapping(value = "/DevelopDoc",method = RequestMethod.GET)
    public String developDoc(){
        // 显示开发日志页面
        return "DevelopDoc";
    }
}
