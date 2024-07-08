package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//用extend 继承自窗口类 就可以形成游戏窗口的子类
public class gamejframe extends JFrame implements KeyListener, ActionListener {

    //成员变量
    int[][] data = new int[4][4];//二维数组用来管理变量
    //用x和y来记录空白图片的坐标
    int x, y;
    //定义一个变量 记录路径
    String path = "image\\girl\\girl3\\";

    //构造方法知识点
    //public class STudent{
    //修饰符 类名（参数）{
    //方法体；
    //}
    //}
    //判断是否胜利 方法
    //新建一个二维数组 只要原来数组顺序符合123456的 即为正确
    //定义一个胜利的二维数组
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}

    };
    // 定义变量来统计步数
    int step = 0;

    // 创建选项下拉条目
    JMenuItem replayitem = new JMenuItem("重新开始游戏");
    JMenuItem reloginitem = new JMenuItem("重新登录游戏");
    JMenuItem closeitem = new JMenuItem("关闭游戏");

    JMenuItem accountitem = new JMenuItem("GitHub");

    // 对窗口进行初始化的构造方法
    public gamejframe() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据（将数组打乱）
        initDate();
        //初始化图片（根据打乱之后的结构加载图片）
        initimage();
        // 使窗口可见
        this.setVisible(true);//要放到最后执行


    }

    //初始化图片
    private void initimage() {
        //路径分为两种
        //绝对路径 从盘符开始
        //相对路径 相对当前项目而言 aaa\\bbb 在当前项目先去找aaa文件夹 再在aaa中找bbb、
        //清空已经出现的内容
        this.getContentPane().removeAll();
        //如果调用判断函数返回true 说明移动完成 展示胜利
        if (victory()) {
            //展示胜利
            JLabel win = new JLabel(new ImageIcon("image\\win.png"));
            win.setBounds(203, 283, 197, 73);
            this.getContentPane().add(win);

        }

        JLabel stepcount = new JLabel("步数" + step);
        stepcount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepcount);

        //设置图片地址
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int num = data[i][j];
                //创建一个图片imageicon对象  用”+num+“是让num作为字符串连接  只用num 就表示输入了一个num 不是变量
                ImageIcon imageIcon = new ImageIcon(path + num + ".jpg");
                //创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(imageIcon);
                //指定坐标位置 坐标看左上角点
                jLabel.setBounds(105 * j + 85, 105 * i + 135, 105, 105);
                //给图片添加边框  0让图片凸起来 1让图片凹进去
                jLabel.setBorder(new BevelBorder(1));
                //把管理容器添加到界面中调用get是为了添加在菜单下面容器中
                this.getContentPane().add(jLabel);
            }
        }
        //如果先加背景会把图片盖住
        //添加背景图片
        ImageIcon bg = new ImageIcon("image\\background.png");
        //JLabel是一个用于显示文本或图像的组件
        JLabel background = new JLabel(bg);
        //对background进行设置
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
    }

    //初始化页面的方法  用private 防止别的方法调用的时候错误改变
    private void initJFrame() {
        // 设置窗口标题
        this.setTitle("游戏界面");
        // 设置窗口关闭时退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置界面置顶
        this.setAlwaysOnTop(true);
        // 设置窗口初始大小和位置（这里仅设置大小，位置默认为屏幕中心）
        this.setSize(600, 800);
        this.setLocationRelativeTo(null); // 居中显示
        //取消默认的居中放置 为了后面添加图片的时候能自由放置 按照坐标来布局
        this.setLayout(null);

        //用this就可以在创建的时候 将创建对象的地址传送过来 然后this接受
        // eg:gamejframe game= new gamejframe（）
        //new后面是空参 所有进行空参构造 把game给this
        //添加键盘监听事件
        this.addKeyListener(this);


    }

    //初试化菜单的方法 用private 防止别的方法调用的时候错误改变
    private void initJMenuBar() {
        //创建菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        // 创建菜单选项
        JMenu functionjmenu = new JMenu("功能");
        JMenu aboutjmenu = new JMenu("所用到的知识点");

        //将下拉条目添加到选项中
        functionjmenu.add(replayitem);
        functionjmenu.add(reloginitem);
        functionjmenu.add(closeitem);

        aboutjmenu.add(accountitem);

        //给条目绑定事件
        replayitem.addActionListener(this);
        reloginitem.addActionListener(this);
        closeitem.addActionListener(this);

        accountitem.addActionListener(this);

        //将选项加入到菜单中
        jMenuBar.add(functionjmenu);
        jMenuBar.add(aboutjmenu);

        //让界面可见
        //这行代码的作用是将一个JMenuBar对象设置到当前窗口（this指的是当前对象，通常是一个JFrame的实例）的菜单栏位置上。
        this.setJMenuBar(jMenuBar);

    }

    private void initDate() {
        //创建一个一维数组
        int[] temparr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //创建一个二维数组

        //将一维数组的顺序随机打乱
        shuffleArray(temparr);
        //将打乱后的数据添加到二位数组中
        int num = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (temparr[num] == 0) {//如果是0 也要赋值给二维数组 否则下次调用没有0
                    x = i;
                    y = j;
                }
                data[i][j] = temparr[num++];
            }
        }

    }

    //Fisher-Yates洗牌算法（也称为Knuth洗牌算法）
    public static void shuffleArray(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            // 产生一个[0, i]之间的随机数
            int j = rand.nextInt(arr.length);
            // 交换arr[i]和arr[j]
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松
    //当按住空格不松手的时候 会展示完整的图片
    @Override
    public void keyPressed(KeyEvent e) {
        //空格是32
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 32) {
            //删除界面中所有图片
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon((path + "all.jpg")));
            all.setBounds(85, 135, 420, 420);
            this.getContentPane().add(all);
            //加载背景图片
            ImageIcon bg = new ImageIcon("image\\background.png");
            //JLabel是一个用于显示文本或图像的组件
            JLabel background = new JLabel(bg);
            //对background进行设置
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    //松开按键时候调用
    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利 如果胜利就跳出
        if (victory()) {
            return;
        }
        //如果越界怎么办？需要判断xy
        //对上下左右进行判断
        //获取键入的值  左 37 上 38 右 39 下 40
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("左");
            if (y == 3) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            //步数加1
            step++;
            initimage();
        } else if (code == 38) {
            System.out.println("上");
            if (x == 3) {
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //步数加1
            step++;
            initimage();
        } else if (code == 39) {
            System.out.println("右");
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //步数加1
            step++;
            initimage();
        } else if (code == 40) {
            System.out.println("下");
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //步数加1
            step++;
            initimage();
        } else if (code == 32) { //当我松开空格时候
            initimage();
        } else if (code == 87) { //等于w 就作弊码
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0},
            };
            initimage();

        }
    }

    //判断两个数组中的数据是否相同相同则胜利 相同返回true 不同返回false
    public boolean victory() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前点击对象
        Object obj = e.getSource();
        //判断
        if (obj == replayitem) {
            System.out.println("重新游戏");
            //再次打乱二维数组 重新加载 计步器清零
            //计步器清零
            step = 0;
            //再次打乱
            initDate();
            //重新加载
            initimage();

        } else if (obj == reloginitem) {
            System.out.println("重新登录");
            //关闭当前的游戏登录界面
            this.setVisible(false);
            //打开登录界面
            new loginjframe();
        } else if (obj == closeitem) {
            System.out.println("关闭游戏");
            //关闭虚拟机
            System.exit(0);
        } else if (obj == accountitem) {
            System.out.println("知识点");
            //当点击是 弹出一个图片
            //创建弹窗
            JDialog jDialog = new JDialog();
            //创建管理图片的容器Jlabel
            JLabel jLabel = new JLabel("https://github.com/fanweixu/pintu-game/upload");
            //设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);
            //把图片添加到弹框中
            jDialog.getContentPane().add(jLabel);
            //给弹窗设置大小
            jDialog.setSize(344, 344);
            //弹窗置顶
            jDialog.setAlwaysOnTop(true);
            //弹窗居中
            jDialog.setLocationRelativeTo(null);
            //弹窗不关闭不能操作
            jDialog.setModal(true);
            //弹窗显示
            jDialog.setVisible(true);

        }


    }


}
