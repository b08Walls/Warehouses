import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class DemoHide
{
    public static void main(String args[]){
        JFrame f = new JFrame();
        f.setLayout(new BorderLayout());
        


        TPanel p = new TPanel(0);
        //p.add(new JLabel("A Panel"));
        

        f.add(p, BorderLayout.CENTER);

        //create a button which will hide the panel when clicked.
        JButton b = new JButton("HIDE");
        // b.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e){
        //             p.setVisible(false);
        //     }
        // });

        b.addActionListener(new HideTableHandler(p,f));

        f.add(b,BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }    


    private static class HideHandler implements ActionListener
    {

        private TPanel p;
        private JFrame f;
        private boolean dentro = true;

        public HideHandler(TPanel p, JFrame f)
        {
            this.p = p;
            this.f = f;
        }

        public void actionPerformed(ActionEvent e)
        {
            if(dentro)
            {
                //p.setVisible(false);
                //p.setPreferredSize(new Dimension(0,0));
                f.remove(p);
                f.pack();
                dentro = false;
            }
            else
            {
                //p.setVisible(true);
                //p.setPreferredSize(new Dimension(600,800));
                f.add(p);
                f.pack();
                dentro = true;
            }
        }
    }
}

