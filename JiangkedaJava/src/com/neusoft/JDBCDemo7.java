package com.neusoft;
/*
模拟用户登录程序
* * */

import com.neusoft.utils.JDBCUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCDemo7 {
    public  static  void  main(String[] arges){
        Scanner scanner= new Scanner(System.in);
        System.out.println("欢迎来到王者峡谷");
        System.out.println("请输入你的用户名");
        String username=scanner.next();
        System.out.println("请输入你的密码");
        String password=scanner.next();

        boolean flag =login(username,password);
        if(flag){
            System.out.println("欢迎回来，即将进入游戏");
        }
        else{
            System.out.println("用户名或者密码错误，请重新输入");
        }


    }
    /*
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public  static boolean login(String username ,String password){
        //参数校验
        if (username ==null || password==null){
            return  false;
        }
        Connection conn= null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
             conn= JDBCUtils.getConnection();
             String sql="SELECT * FROM USER WHERE username='"+username+"' and PASSWORD='"+password+"'";
            //System.out.println(sql);
           stmt=conn.createStatement();
             rs=stmt.executeQuery(sql);
            return  rs.next();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
