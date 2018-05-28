import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Kalkulator extends JFrame{
    boolean wyliczone=false;

    JFrame frame=this;
    static JTextField display=new JTextField(20);
    static String wyrazenie="";
    static String liczba="";
    static List<String> liczby=new ArrayList<>();
    static Stack<String> operatory=new Stack<>();
    static float[] wynik=new float[2];
    Kalkulator(){
        super("Kalkulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setEditable(false);
        display.setSize(100,10);
        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, display);

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(4,4));
        JButton b7=new JButton("7");
        b7.addActionListener(Put);
        panel.add(b7);
        JButton b8=new JButton("8");
        b8.addActionListener(Put);
        panel.add(b8);
        JButton b9=new JButton("9");
        b9.addActionListener(Put);
        panel.add(b9);
        JButton bplus=new JButton("+");
        bplus.addActionListener(Put);
        panel.add(bplus);
        JButton b4=new JButton("4");
        b4.addActionListener(Put);
        panel.add(b4);
        JButton b5=new JButton("5");
        b5.addActionListener(Put);
        panel.add(b5);
        JButton b6=new JButton("6");
        b6.addActionListener(Put);
        panel.add(b6);
        JButton bminus=new JButton("-");
        bminus.addActionListener(Put);
        bminus.setActionCommand("~");
        panel.add(bminus);
        JButton b1=new JButton("1");
        b1.addActionListener(Put);
        panel.add(b1);
        JButton b2=new JButton("2");
        b2.addActionListener(Put);
        panel.add(b2);
        JButton b3=new JButton("3");
        b3.addActionListener(Put);
        panel.add(b3);
        JButton bmul=new JButton("*");
        bmul.addActionListener(Put);
        panel.add(bmul);
        JButton b0=new JButton("0");
        b0.addActionListener(Put);
        panel.add(b0);
        JButton bdot=new JButton(".");
        bdot.addActionListener(Put);
        panel.add(bdot);
        JButton bdiv=new JButton("/");
        bdiv.addActionListener(Put);
        panel.add(bdiv);
        JButton beq=new JButton("=");
        beq.addActionListener(Licz);
        panel.add(beq);
        add(BorderLayout.SOUTH, panel);

        setSize(300,200);
        setVisible(true);
    }

    ActionListener Put=new ActionListener(){
        public void actionPerformed(ActionEvent e){
            String znak=((JButton)e.getSource()).getActionCommand();
            if(znak.equals("+") || znak.equals("*") || znak.equals("/")){
                wyliczone=false;
                if(!liczby.isEmpty()){
                    if(!liczba.equals(""))
                        liczby.add(liczba);
                    String poprzedni=liczby.get(liczby.size()-1);
                    if(!poprzedni.equals("+") && !poprzedni.equals("-") && !poprzedni.equals("*") && !poprzedni.equals("/")){
                        liczba="";
                        liczby.add(znak);
                    }
                    else{
                        liczby.add(znak);
                    }
                    wyrazenie+=znak;
                }
                else{
                    liczby.add(liczba);
                    liczba="";
                    liczby.add(znak);
                    wyrazenie+=znak;
                }
            }
            else if(znak.equals("~")){
                wyliczone=false;
                if(liczba.equals(""))
                    liczba+="-";
                else{
                    liczby.add(liczba);
                    liczba="";
                    liczby.add("-");
                }
                wyrazenie+="-";
            }
            else if(wyliczone){
                wyliczone=false;
                liczba=znak;
                wyrazenie=znak;
            }
            else{
                liczba+=znak;
                wyrazenie+=znak;
            }
            display.setText(wyrazenie);
        }
    };
    ActionListener Licz=new ActionListener(){
        public void actionPerformed(ActionEvent e){
            wyliczone=true;
            liczby.add(liczba);
            float liczba1;
            float liczba2;
            float wynik;
            while(liczby.size()>1){
                if(liczby.contains("*")){
                    int o=liczby.indexOf("*");
                    String poprzednik=liczby.get(o-1);
                    String nastepnik=liczby.get(o+1);
                    if (errorChecker(poprzednik, nastepnik))
                        break;
                    liczba1=Float.parseFloat(poprzednik);
                    liczba2=Float.parseFloat(nastepnik);
                    wynik=liczba1*liczba2;
                    liczby.add(o-1,Float.toString(wynik));
                    liczby.remove(o+2);
                    liczby.remove(o+1);
                    liczby.remove(o);
                }
                else if(liczby.contains("/")){
                    int o=liczby.indexOf("/");
                    String poprzednik=liczby.get(o-1);
                    String nastepnik=liczby.get(o+1);
                    if (errorChecker(poprzednik, nastepnik))
                        break;
                    liczba1=Float.parseFloat(poprzednik);
                    liczba2=Float.parseFloat(nastepnik);
                    wynik=liczba1/liczba2;
                    liczby.add(o-1,Float.toString(wynik));
                    liczby.remove(o+2);
                    liczby.remove(o+1);
                    liczby.remove(o);
                }
                else if(liczby.contains("+")){
                    int o=liczby.indexOf("+");
                    String poprzednik=liczby.get(o-1);
                    String nastepnik=liczby.get(o+1);
                    if (errorChecker(poprzednik, nastepnik)) break;
                    liczba1=Float.parseFloat(poprzednik);
                    liczba2=Float.parseFloat(nastepnik);
                    wynik=liczba1+liczba2;
                    liczby.add(o-1,Float.toString(wynik));
                    liczby.remove(o+2);
                    liczby.remove(o+1);
                    liczby.remove(o);
                }
                else if(liczby.contains("-")){
                    int o=liczby.indexOf("-");
                    String poprzednik=liczby.get(o-1);
                    String nastepnik=liczby.get(o+1);
                    if (errorChecker(poprzednik, nastepnik)) break;
                    liczba1=Float.parseFloat(poprzednik);
                    liczba2=Float.parseFloat(nastepnik);
                    wynik=liczba1-liczba2;
                    liczby.add(o-1,Float.toString(wynik));
                    liczby.remove(o+2);
                    liczby.remove(o+1);
                    liczby.remove(o);
                }
            }
            if(!wyrazenie.equals("")){
                wyrazenie=liczby.get(0);
                liczba=wyrazenie;
                display.setText(wyrazenie);
                liczby.clear();
                System.out.println(wyrazenie);
            }
        }
    };

    private boolean errorChecker(String poprzednik, String nastepnik) {
        if(poprzednik.equals("+") || poprzednik.equals("-") || poprzednik.equals("/") || poprzednik.equals("*") ||
                nastepnik.equals("+") || nastepnik.equals("-") || nastepnik.equals("/") || nastepnik.equals("*")){
            wyrazenie="BLAD!\n";
            display.setText(wyrazenie);
            wyrazenie="";
            liczba="";
            liczby.clear();
            return true;
        }
        return false;
    }
}
