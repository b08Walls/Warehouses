import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

/*Clase que se encarga de interactuar con los botones del menu y de
la gui para hacer aparecer y desaparecer los distintos elementos de
las tablas*/
public class HideTableHandler implements ActionListener,ItemListener
{
    //Atributo TPanel importante para ocultar los elementos
    private TPanel t;
    //Atributo JFrame utilizado para ejecutar el comando pack()
    private JFrame f;
    //Atributo boolean que permite saber si la tabla esta visible o no 
    private boolean visible = true;

    public HideTableHandler(TPanel t, JFrame f)
    {
        this.t = t;
        this.f = f;
    }

    public void actionPerformed(ActionEvent e)
    {
        if(visible)
        {
            t.setVisible(false);
            f.pack();
            visible = !visible;
        }
        else
        {
            f.pack();
            visible = !visible;
        }
    }

    public void itemStateChanged(ItemEvent e) 
    {
        if (e.getStateChange() == ItemEvent.SELECTED) 
        {
            t.setVisible(true);
            f.pack();
            visible = true;
        } 
        else 
        {
            t.setVisible(false);
            f.pack();
            visible = false;
        }
    }

}