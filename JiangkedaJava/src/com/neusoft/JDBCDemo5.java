package com.neusoft;

import com.neusoft.domain.Emp;

import javax.xml.crypto.Data;
import java.sql.*;

public class JDBCDemo5 {
    public static void main(String[] args) throws Exception {
        //1、导入驱动jar包, 建立libs文件夹， 右键 add as library
        //2、 注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3、获取数据库连接对象
        Connection conn=DriverManager.getConnection("jdbc:mysql://" +
                "localhost:3306/jiangkeda","root","root");
        //4、定义sql
        String sql="select  *  from emp";
        //5、获取数据库连接对象statement
        Statement stmt= conn.createStatement();
        //6、执行sql
        ResultSet rs=stmt.executeQuery(sql);
        //        7、处理结果
        rs.next();
            int empno =rs.getInt("empno");
            String ename= rs.getString("ENAME");
            String job= rs.getString("job");
            int mgr =rs.getInt("mgr");
            Date hiredate=rs.getDate("hiredate");
            int sal =rs.getInt("sal");
            int comm =rs.getInt("comm");
            int deptno =rs.getInt("deptno");
            /*System.out.println(empno+" "+ename+" "+job+" "+mgr+" "+hiredate+" "+sal+" "+comm+deptno);*/
            Emp emp= new Emp(empno,ename,job,mgr,hiredate,sal,comm,deptno);
            System.out.println(emp);

        //        8、释放资源
        stmt.close();
        conn.close();

    }
}
