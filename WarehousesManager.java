import javax.swing.*;

public class WarehousesManager
{
	public static void main(String[] args) {
		
		/*Las primeras lineas de codigo crean una ventana que permite al usuario seleccionar el idioma con el cual
		quiere ejecutar el programa*/		
		String[] list = {"English", "Español"};
		JComboBox idiomas = new JComboBox(list);
		idiomas.setEditable(true);
		JOptionPane.showMessageDialog( null, idiomas, "Please select your language", JOptionPane.QUESTION_MESSAGE);

		/*Se instancia una nueva GUI a partir de aqui el resto del proceso esta dentro de las clases:
			- MainGUI.java
			- TPanel.java
			- DataBaseHandler.java
		*/
		MainGUI test = new MainGUI(idiomas.getSelectedIndex());
	}
}