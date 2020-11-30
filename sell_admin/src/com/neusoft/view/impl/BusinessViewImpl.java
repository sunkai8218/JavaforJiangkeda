package com.neusoft.view.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.neusoft.dao.BusinessDao;
import com.neusoft.dao.impl.BusinessDaoImpl;
import com.neusoft.domain.Business;
import com.neusoft.utils.JDBCUtils;
import com.neusoft.view.BusinessView;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

public class BusinessViewImpl implements BusinessView {

    private Scanner input =  new Scanner(System.in);

    @Override
    public void listAllBusinesses() {
        BusinessDao dao = new BusinessDaoImpl();
        List<Business> list = dao.listBusiness(null, null);
        System.out.println("商家编号"+"\t"+"商家名称"+"\t"+"商家地址"+"\t"+"商家备注"+"\t"+"商家配送费"+"\t"+"商家起送费");
        for (Business b :list){
            System.out.println(b.getBusinessId() +"\t"+b.getBusinessName()+"\t"+b.getBusinessAddress()+"\t"+b.getBusinessExplain()+"\t"+b.getDeliveryPrice()+"\t"+b.getStartPrice());
        }

    }

    @Override
    public void selectBusinesses() {
        String businessName = "";
        String inputStr = "";
        String businessAddress = "";
        System.out.println("请输入是否输入商家名称关键词(y/n):");
        inputStr = input.next();
        if (inputStr.equals("y")){
            System.out.println("请输入商家名称关键词");
            businessName = input.next();
        }

        System.out.println("请输入是否输入商家地址关键词(y/n):");
        inputStr = input.next();
        if (inputStr.equals("y")){
            System.out.println("请输入商家地址关键词");
            businessAddress = input.next();
        }
        BusinessDaoImpl dao = new BusinessDaoImpl();
        List<Business> list = dao.listBusiness(businessName, businessAddress);
        System.out.println("商家编号"+"\t"+"商家名称"+"\t"+"商家地址"+"\t"+"商家备注"+"\t"+"商家配送费"+"\t"+"商家起送费");
        for (Business b :list){
            System.out.println(b.getBusinessId() +"\t"+b.getBusinessName()+"\t"+b.getBusinessAddress()+"\t"+b.getBusinessExplain()+"\t"+b.getDeliveryPrice()+"\t"+b.getStartPrice());
        }

    }

    @Override
    public void saveBusiness() {
        System.out.println("请输入新商家的名称");
        String businessName = input.next();
        BusinessDao dao = new BusinessDaoImpl();
        int businessId = dao.saveBusiness(businessName);
        // 根据id进行查询， 然后进行回显
        if (businessId >0){
            System.out.println("保存成功");
            Business business = dao.getBusinessById(businessId);
            System.out.println(business);
        }else {
            System.out.println("新建商家失败");
        }

    }

    @Override
    public void removeBusiness() {
        System.out.println("请输入要删除的商家id");
        int id = input.nextInt();
        BusinessDao dao = new BusinessDaoImpl();
        System.out.println("确认要删除吗(y/n)");
        if (input.next().equals("y")){
            int i = dao.removeBusiness(id);
            if (i == 1){
                System.out.println("删除成功");
            }else{
                System.out.println("删除失败");
            }
        }

    }

    @Override
    public Business login() {
        System.out.println("请输入商家编号");
       Integer businessId=input.nextInt();
        System.out.println("请输入密码");
        String password=input.next();
        BusinessDaoImpl dao = new BusinessDaoImpl();


        return dao.getBusinessByIdAndPassword(businessId, password);
    }

    @Override
    public void updatePassword(Integer businessId) {
        BusinessDaoImpl dao = new BusinessDaoImpl();
        Business business = dao.getBusinessById(businessId);
        System.out.println("请输入旧密码");
        String oldPass = input.next();
        System.out.println("请输入新密码");
        String newPass = input.next();
        System.out.println("请再次输入新密码");
        String beginNewPass = input.next();
        // 进行密码校验
        if (!business.getPassword().equals(oldPass)){
            System.out.println("你的密码蒙错了，请重新输入");
        }else if (!newPass.equals(beginNewPass)){
            System.out.println("两次密码不一致请重新输入");
        }else {
            int res = dao.updateBusinessPassword(businessId, newPass);
            if (res>0){
                System.out.println("修改密码成功！");
            }else {
                System.out.println("修改密码失败！");
         }
        }

    }

    @Override
    public Business getBusinessById(Integer businessId) {
        Business business = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * from business where businessId = ?";
        try {
            conn = JDBCUtils.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, businessId);
            rs = pst.executeQuery();
            while (rs.next()){
                business = new Business();
                business.setBusinessId(rs.getInt("businessId"));
                business.setPassword(rs.getString("password"));
                business.setBusinessName(rs.getString("businessName"));
                business.setBusinessAddress(rs.getString("businessAddress"));
                business.setBusinessExplain(rs.getString("businessExplain"));
                business.setStartPrice(rs.getDouble("starPrice"));
                business.setDeliveryPrice(rs.getDouble("deliveryPrice"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(rs, pst, conn);
        }
        return business;
    }

    @Override
    public void updateBusiness(Integer businessId) {
        BusinessDao dao = new BusinessDaoImpl();
        Business business = dao.getBusinessById(businessId);
        int menu = 0;
        int result = 0;
        while (menu != 6) {
            System.out.println(">>> 选择你需要修改的信息：1. 修改名称  2. 修改地址  3. 修改备注  4. 修改配送费  5. 修改起送费 6. 返回上一级菜单");
            System.out.println("请输入你要选择的序号：");
            menu = input.nextInt();
            System.out.println("请输入修改后的内容：");
            if (menu == 1) {
                String text1 = input.next();
                business.setBusinessName(text1);
                result = dao.updateBusiness(business);
                break;
            }
            if (menu == 2) {
                String text2 = input.next();
                business.setBusinessAddress(text2);
                result = dao.updateBusiness(business);
                break;
            }
            if (menu == 3) {
                String text3 = input.next();
                business.setBusinessExplain(text3);
                result = dao.updateBusiness(business);
                break;

            }
            if (menu == 4) {
                double text4 = input.nextDouble();
                business.setStartPrice(text4);
                result = dao.updateBusiness(business);
            }
            if (menu == 5) {
                double text5 = input.nextDouble();
                business.setDeliveryPrice(text5);
                result = dao.updateBusiness(business);
                break;
            }
            if (menu >= 6 || menu <= 0) {
                System.out.println("没有此选项");
                break;
            }
            if (result>0){
                System.out.println("修改成功");

            }else if (result<=0){
                System.out.println("修改失败");
            }
        }
    }
}















