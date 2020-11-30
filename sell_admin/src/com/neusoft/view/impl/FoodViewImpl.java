package com.neusoft.view.impl;

import com.neusoft.dao.BusinessDao;
import com.neusoft.dao.FoodDao;
import com.neusoft.dao.impl.BusinessDaoImpl;
import com.neusoft.dao.impl.FoodDaoImpl;
import com.neusoft.domain.Business;
import com.neusoft.domain.Food;
import com.neusoft.view.FoodView;

import java.util.List;
import java.util.Scanner;

public class FoodViewImpl  implements FoodView {
    private Scanner input = new Scanner(System.in);
    @Override
    public List<Food> showFoodList(Integer businessId) {
        FoodDao dao = new FoodDaoImpl();
        List<Food> list = dao.listFoodByBusinessId(businessId);
        System.out.println("食品编号" + "\t" + "食品名称" + "\t" + "食品备注" + "\t" + "食品价格");
        for (Food f : list) {
            System.out.println(f.getFoodId() + "\t" + f.getFoodName() + "\t" + f.getFoodExplain() + "\t" + f.getFoodPrice());
        }
        return list;
    }

    @Override
    public void saveFood(Integer businessId) {
        System.out.println("请输入新食品的名称");
        String foodName = input.next();
        System.out.println("请输入新食品的备注");
        String foodExplain = input.next();
        System.out.println("请输入新食品的价格");
        double foodPrice = input.nextDouble();
        FoodDao dao = new FoodDaoImpl();
        Food food = new Food();
        food.setFoodName(foodName);
        food.setFoodExplain(foodExplain);
        food.setFoodPrice(foodPrice);
        food.setBusinessId(businessId);
        int foodId = dao.saveFood(food);
        // 根据id进行查询， 然后进行回显
        if (foodId >0){
            System.out.println("保存成功");
            food = dao.getFoodById(foodId);
            System.out.println(food);
        }else {
            System.out.println("新建食品失败");
        }
    }

    @Override
    public void updateFood(Integer businessId) {
        FoodDao dao = new FoodDaoImpl();
        int foodId = 0;
        System.out.println("请输入你要修改的食品编号：");
        foodId = input.nextInt();
        Food food = dao.getFoodById(foodId);
        int menu = 0;
        int res = 0;
        while (menu != 4) {
            System.out.println(">>> 二级菜单  1. 修改名称   2. 修改备注    3. 修改价格    4. 返回上一级菜单");
            System.out.println("请输入你要选择的序号：");
            menu = input.nextInt();
            System.out.println("请输入修改后的内容：");
            switch (menu) {
                case 1:
                    String text1 = input.next();
                    food.setFoodName(text1);
                    res = dao.updateFood(food);
                    break;
                case 2:
                    String text2 = input.next();
                    food.setFoodExplain(text2);
                    res = dao.updateFood(food);
                    break;
                case 3:
                    double text3 = input.nextDouble();
                    food.setFoodPrice(text3);
                    res = dao.updateFood(food);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("没有这个选项");
                    break;

            }
            if (res>0){
                System.out.println("修改成功！");
            }else {
                System.out.println("修改失败！");
            }
        }
    }

    @Override
    public void removeFood(Integer businessId) {
        System.out.println("请输入要删除的食品id");
        int id = input.nextInt();
        FoodDao dao = new FoodDaoImpl();
        System.out.println("确认要删除吗(y/n)");
        if (input.next().equals("y")){
            int i = dao.removeFood(id);
            if (i == 1){
                System.out.println("删除成功");
            }else{
                System.out.println("删除失败");
            }
        }
    }
}