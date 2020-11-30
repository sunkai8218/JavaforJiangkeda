package com.neusoft;

import com.neusoft.dao.impl.BusinessDaoImpl;
import com.neusoft.domain.Business;
import com.neusoft.view.impl.BusinessViewImpl;
import com.neusoft.view.impl.FoodViewImpl;

import java.util.Scanner;

/**
 * 入驻商家入口程序
 */

public class BusinessAdminEntry {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Scanner input = new Scanner(System.in);
        System.out.println("-----------------------------------");
        System.out.println("---------饿了么商家管理系统----------");
        System.out.println("-----------------------------------");
        //商家登录
        BusinessViewImpl businessView = new BusinessViewImpl();
        Business business = businessView.login();
        //System.out.println("business="+business);
        int menu = 0;
        if (business != null) {
            System.out.println("商家:" + business.getBusinessName() + "欢迎回来！");
            while (menu != 5) {
                System.out.println(">>>一级菜单 1. 查看商家信息   2. 修改商家信息   3. 修改密码   4.所属商品管理   5. 退出系统");
                System.out.println("请输入你要选择的序号");
                menu = input.nextInt();
                switch (menu) {
                    case 1:
                        //System.out.println("1.查看商家信息");
                        //System.out.println("商家编号"+"\t"+"商家名称"+"\t"+"商家地址"+"\t"+"商家备注"+"\t"+"商家配送费"+"\t"+"商家起送费");
                        //System.out.println(business.getBusinessId() +"\t"+business.getBusinessName()+"\t"+business.getBusinessAddress()+"\t"+business.getBusinessExplain()+"\t"+business.getDeliveryPrice()+"\t"+business.getStartPrice());
                        System.out.println(businessView.getBusinessById(business.getBusinessId()));
                        break;
                    case 2:
                        System.out.println("2.修改商家信息");
                        businessView.updateBusiness(business.getBusinessId());
                        break;
                    case 3:
                        businessView.updatePassword(business.getBusinessId());
                        break;
                    case 4:
                        foodManger(business.getBusinessId());
                        break;
                    case 5:
                        System.out.println("欢迎下次登录");
                        break;
                    default:
                        System.out.println("没有这个选项");
                        break;

                }
            }

        } else {
            System.out.println("登录失败, 用户名密码错误");
        }


    }

    public static void foodManger(Integer businessId) {
        Scanner input = new Scanner(System.in);
        FoodViewImpl foodView = new FoodViewImpl();
        int menu = 0;
        while (menu != 5) {
            System.out.println(">>>二级菜单 1. 查看食品信息  2. 修改食品信息   3. 增加食品信息  4.删除食品信息  5. 返回上一级菜单");
            System.out.println("请输入你要选择的序号");
            menu = input.nextInt();
            switch (menu) {
                case 1:
                    //TDDO
                    foodView.showFoodList(businessId);
                    System.out.println("1.查看食品信息");
                    break;
                case 2:
                    //TDDO
                    foodView.updateFood(businessId);
                    System.out.println("2.修改食品信息");
                    break;
                case 3:
                    //TDDO
                    foodView.saveFood(businessId);
                    System.out.println("3.增加食品信息");
                    break;
                case 4:
                    //TDDO
                    foodView.removeFood(businessId);
                    System.out.println("4.删除食品信息");
                    break;
                case 5:
                    //TDDO
                    System.out.println("返回上一级菜单");
                    break;
                default:
                    //TDDO
                    System.out.println("没有这个选项");
                    break;

            }

        }
    }
}
