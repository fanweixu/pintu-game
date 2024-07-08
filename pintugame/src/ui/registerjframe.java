package ui;

import javax.swing.*;
import java.security.PublicKey;

//用extend 继承自窗口类 就可以形成注册窗口的子类
public class registerjframe extends JFrame {

    //构造方法知识点
    //public class STudent{
    //修饰符 类名（参数）{
    //方法体；
    //}
    //}

    //对窗口进行初始化
    public registerjframe(){
        //对窗口进行初始化
        // 设置窗口标题
        this.setTitle("注册界面");
        // 设置窗口关闭时退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口初始大小和位置（这里仅设置大小，位置默认为屏幕中心）
        this.setSize(400, 300);
        this.setLocationRelativeTo(null); // 居中显示
        // 使窗口可见
        this.setVisible(true);
    }

}
