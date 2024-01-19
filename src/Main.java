/**
 * 手搓计算器
 */

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Main extends JFrame{
    JTextField text;
    /*
     * 使用状态记录
     * bl初始值为0
     * 使用等号后bl=1
     * 此时你点击运算符的按钮将只保留运算结果
     * 点击数字将清除输出
     * 重置bl为0
     * */
    public Main() {
        final int[] bl = {0};//状态
        final Object[] allResult = new Object[1];
        setBounds(400,400,300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c=getContentPane();
        setLayout(null);

        //文本框  获取输入
        text=new JTextField();
        text.setColumns(20);
        text.setBounds(0,10,300,65);//据窗口左侧距离0 上边距10 长300 宽30
        c.add(text);
        //按钮布局
        JPanel jp=new JPanel(new GridLayout(4,3,5,5));//网格布局 3行3列 间距均为5px
        //AC清除键
        JButton btn_AC=new JButton("  AC ");
        btn_AC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                text.setText("");
            }
        });
        jp.add(btn_AC);
        //乘号
        JButton btn_c=new JButton("  × ");
        btn_c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 0)
                    text.setText(text.getText()+"×");
                if(bl[0] == 1)
                    text.setText(allResult[0] +"×");
                bl[0] = 0;
            }
        });
        jp.add(btn_c);
        //除号
        JButton btn_d=new JButton("  ÷ ");
        btn_d.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 0)
                    text.setText(text.getText()+"÷");
                if(bl[0] == 1)
                    text.setText(allResult[0] +"÷");
                bl[0] = 0;
            }
        });
        jp.add(btn_d);
        JButton btn[]=new JButton[10];
        for( int i=1;i<10;i++) {
            btn[i]=new JButton();
            btn[i].setText(""+i+"");
            int k=i;
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(bl[0] == 1)
                        text.setText("");
                    //1~9数字键监听并执行动作
                    text.setText(text.getText()+k);
                    bl[0] = 0;
                }
            });
            jp.add(btn[i]);
        }
        jp.setBounds(5,100,200,180);//数字1~9面板据窗口左5 上边距100 宽200 高180
        //按钮0 单独放大
        btn[0]=new JButton(" 0 ");
        //按钮0 添加事件
        btn[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 1)
                    text.setText("");
                text.setText(text.getText()+0);
                bl[0] = 0;
            }
        });
        btn[0].setBounds(5,285,100,50);//按钮 左距5 上边距285 长100 高50
        c.add(btn[0]);

        //小数点按钮
        JButton btn_po=new JButton("  . ");
        btn_po.setBounds(110, 285, 95, 50);
        btn_po.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 1)
                    text.setText("");
                bl[0] = 0;
                text.setText(text.getText()+".");
            }
        });
        c.add(btn_po);
        c.add(jp);

        //加号
        JButton btn_a=new JButton("  + ");
        btn_a.setBounds(210,100,70,55);
        btn_a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 0)
                    text.setText(text.getText()+"+");
                if(bl[0] == 1)
                    text.setText(allResult[0]+"+");
                bl[0] = 0;
            }
        });
        c.add(btn_a);
        //减号
        JButton btn_b=new JButton("  - ");
        btn_b.setBounds(210,160,70,65);
        btn_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bl[0] == 0)
                    text.setText(text.getText()+"-");
                if(bl[0] == 1)
                    text.setText(allResult[0]+"-");
                bl[0] = 0;
            }
        });
        c.add(btn_b);

        //等号
        JButton btn_e=new JButton("  = ");
        btn_e.setBounds(210,230,70,105);
        btn_e.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bl[0] = 1;
                String str=text.getText();
                //替换掉str中的×为*
                String newstr1=str.replace("×","*");
                //替换÷为/
                String newstr2=newstr1.replace("÷","/");
                System.out.println(newstr2);


                //字符串内进行算术运算
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("JavaScript");

                try {
                    Object result = engine.eval(newstr2);
                    System.out.println(result); //计算结果存在result
                    System.out.println(result.getClass());
                    text.setText(text.getText()+"="+result);
                    allResult[0] = result;
                } catch (ScriptException ev) {
                    ev.printStackTrace();
                }


            }
        });
        c.add(btn_e);
        //添加键盘事件 监听键盘 使键盘也能输入
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int code=e.getKeyCode();
                switch (code){
                    case KeyEvent.VK_0:
                        text.setText(text.getText()+0);
                        break;
                    case KeyEvent.VK_1:
                        text.setText(text.getText()+1);
                        break;
                    case KeyEvent.VK_2:
                        text.setText(text.getText()+2);
                        break;
                    case KeyEvent.VK_3:
                        text.setText(text.getText()+3);
                        break;
                    case KeyEvent.VK_4:
                        text.setText(text.getText()+4);
                        break;
                    case KeyEvent.VK_5:
                        text.setText(text.getText()+5);
                        break;
                    case KeyEvent.VK_6:
                        text.setText(text.getText()+6);
                        break;
                    case KeyEvent.VK_7:
                        text.setText(text.getText()+7);
                        break;
                    case KeyEvent.VK_8:
                        text.setText(text.getText()+8);
                        break;
                    case KeyEvent.VK_9:
                        text.setText(text.getText()+9);
                        break;
                    case KeyEvent.VK_MULTIPLY:
                        text.setText(text.getText().replace("*","×"));
                        break;
                    case KeyEvent.VK_DIVIDE:
                        text.setText(text.getText().replace("/","÷"));
                        break;
                    case KeyEvent.VK_ENTER:
                        String str=text.getText();
                      
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }

}