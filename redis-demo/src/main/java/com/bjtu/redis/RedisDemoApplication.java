package com.bjtu.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 *  SpringBootApplication
 * 用于代替 @SpringBootConfiguration（@Configuration）、 @EnableAutoConfiguration 、 @ComponentScan。
 * <p>
 * SpringBootConfiguration（Configuration） 注明为IoC容器的配置类，基于java config
 * EnableAutoConfiguration 借助@Import的帮助，将所有符合自动配置条件的bean定义加载到IoC容器
 * ComponentScan 自动扫描并加载符合条件的组件
 */
@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        int num=1;
        while(true) {
            System.out.println(num+ ". Please Choose Your Next Action:");
            System.out.println("\t 1. 增加count \t 2. 读出count \t 3.读取freq \t 4.读取log \t 5.获取所有count增加的非重复时间戳");
            System.out.print("choice>>");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if(!str.equals("1")&&!str.equals("2")&&!str.equals("3")&&!str.equals("4")&&!str.equals("5")){
                System.out.println("\t 输入格式错误，请输入1、2、3、4、5");
            }else{
                switch (str){
                    case "1":
                        Action action1 = new Action("pluscount");
                        Count count1 = new Count(action1);
                        count1.Counts();
                        break;
                    case "2":
                        Action action2 = new Action("readcount");
                        Count count2 = new Count(action2);
                        count2.Counts();
                        break;
                    case "3":
                        Action action3 = new Action("readfreq");
                        Count count3 = new Count(action3);
                        count3.Counts();
                        break;
                    case "4":
                        Action action4 = new Action("readlog");
                        Count count4 = new Count(action4);
                        count4.Counts();
                        break;
                    case "5":
                        Action action5 = new Action("readSet");
                        Count count5 = new Count(action5);
                        count5.Counts();
                        break;
                    default:
                        break;
                }

            }
            System.out.println("Do You Wanna Exit?");
            System.out.println("\t A. yes \t B. no");
            System.out.print("choice>>");
            String ex = scanner.nextLine();
            if(ex.equals("A")){
                break;
            }
            num++;
            System.out.println("------------------------------------");
        }
        System.out.println("YOU HAVE EXIT SUCCESSFULLY");

    }



}