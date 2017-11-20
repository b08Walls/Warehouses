//imports
import javax.swing.JFrame;//Clase para la ventana
import java.awt.Dimension;//Clase usada para definir las dimensiones preferidas.
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.*;


/*Esta clase sera la ventana principal del sistema donde se podrán ver y modificar las dife-
**rentes tablas del sistema, la clase hereda de JFrame.
**
**Autor: Octavio R. Paredes G.*/

public class MainGUI extends JFrame
{
	//****************************************************************************************
	//***CONSTANTES DE LA CLASE***************************************************************
	//****************************************************************************************

	//***CONSTANTES DE DIMENSIONES************************************************************
	public final static int PREFERRED_HEIGTH = 600;
	public final static int PREFERRED_WIDTH = 800;
	public final static Dimension PREFERRED_DIMENSION = new Dimension(PREFERRED_WIDTH,PREFERRED_HEIGTH);

	//***CONSTANTES DEL MENU DE TABLAS********************************************************

	/*Constantes enteras que asigna un número a cada una de las tablas*/
	public final static int LEGEND_TABLE = 0;
	public final static int WAREHOUSES_TABLE = 1;
	public final static int PRODUCTS_TABLE = 2;
	public final static int INVENTORY_TABLE = 3;

	/*Constantes de arreglos de string los cuales contienen las diferentes opciones de palabras
	para los idiomas de la GUI*/
	public final static String [] TABLES_MENU_STRINGS = {"Legend Table","Warehouses Table","Products Table","Inventory Table"};
	public final static String [] TEXTOS_MENU_TABLAS = {"Tabla de leyenda","Tabla de almacenes","Tabla de productos","Tabla de inventarios"};

	public final static String [][] IDIOMAS_TABLAS = {TABLES_MENU_STRINGS,TEXTOS_MENU_TABLAS};

	public final static String [] IDIOMAS_SALIR = {"EXIT","SALIR"};
	public final static String [] IDIOMAS_LANGUAGES = {"Languages","Idiomas"};
	public final static String [] IDIOMAS_MENU_TABLAS = {"Tables","Tablas"};

	public final static String [] OPCIONES_DE_IDIOMA = {"Inglés","Español"};
	public final static String [] LANGUAGE_OPTIONS = {"English","Spanish"};

	public final static String [][] MENU_LENGUAJE = {LANGUAGE_OPTIONS,OPCIONES_DE_IDIOMA};

	/*Constantes enteras que asignan que lugar en los arreglos tienen cada uno de los idiomas*/
	public final static int ENGLISH = 0;
	public final static int SPANISH = 1;

	//****************************************************************************************
	//***ATRIBUTOS DE LA CLASE****************************************************************
	//****************************************************************************************

	/*Panel principal que se encarga de almacenar todos los elementos de la GUI*/
	//private JPanel mainPanel = new JPanel();

	/*Objetos GridBagConstraints, que tienen como objetivo describir con que caracteristicas
	se agregaran los elementos a la interfas en el GridBagLayout*/
	private GridBagConstraints tableConstraints = new GridBagConstraints();
	private GridBagConstraints labelConstraints = new GridBagConstraints();

	/*Estos son los objetos TPanel que son los que contienen las tablas y los botones de inter-
	accion con el usuario*/
	private TPanel legendTPanel;
	private TPanel warehousesTPanel;
	private TPanel productsTPanel;
	private TPanel inventoryTPanel;

	/*Estos son los botones que ocultan o muestran las diferentes tablas del sistema*/
	private JButton legendButton;
	private JButton warehouseButton;
	private JButton productosButton;
	private JButton inventoryButton;

	/*Arreglo en el que se guardan todas las tablas usadas dentro de la interfas */
	private TPanel tablas [];// = {legendTPanel,productsTPanel,productsTPanel,inventoryTPanel};

	//***ELEMENTOS DE LA BARRA DE MENU********************************************************
	
	/*Objeto principal este guarda y distribuye todos los menus*/
	private JMenuBar menuBar;

	/*Los objetos JMenu almacenan y distribuyen las opciones o items*/
	private JMenu tablesMenu;//Elemento del menu de selección de tablas
	private JMenu languageMenu;//Elemento del menu de selección de lenguaje

	/*Los objetos con la palabra item son las opciones en cuestion*/
	private JMenuItem exitFromMenu;//Opcion de salida
	/*Arreglo de RadioButtons para la selección de las tablas*/
	private JRadioButtonMenuItem [] opcionesTablas;
	//private JRadioButtonMenuItem [] opcionesIdiomas;

	//***VARIABLES DE OPERACION DE LA CLASE***************************************************

	private int idioma = 0;//Variable que guarda el idioma seleccionado, inglés predeterminado

	//****************************************************************************************
	//***CONSTRUCTORES DE LA CLASE************************************************************
	//****************************************************************************************

	public MainGUI(int idioma)
	{
		this.idioma = idioma;
		
		windowInit();
		constraintsInit();
		initComponents();
		addMainComponents();
		menuInit();
		deploy();
	}

	//****************************************************************************************
	//***METODOS SET'S Y GET'S****************************************************************
	//****************************************************************************************


	//****************************************************************************************
	//***METODOS DE INICIALIZACIÓN DE LA CLASE************************************************
	//****************************************************************************************

	/*Este metodo se encarga de configurar todo lo relacionado con la ventana, su comporta-
	miento, layout, entre algunas otras cosas.*/
	private void windowInit()
	{
		/*Se asinga el tamaño predeterminado a la venta*/
		this.setPreferredSize(PREFERRED_DIMENSION);
		/*Se asigna la operación a realizar al cierre de la ventana, en este caso se declara
		que el proceso debe detenerse al cerrar la ventana*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// /*Se asigna un BoxLayout vertical para dividir la GUI facilmente en secciones hori-
		// zontales*/.
		// this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		//this.getContentPane().setLayout(new BorderLayout(0,0));
		this.getContentPane().setLayout(new GridBagLayout());

		
	}

	/*Metodo que inicializa la barra de menu en la venta*/
	private void menuInit()
	{
		/*Crea el objeto menuBar para dar opciones al software*/
		menuBar = new JMenuBar();//Objeto principal que almacena los demas menus
		//----------------------------------------------------------------------------------------
		/*Primer menu de la barra, asignamos el nombre "Tables" y va a permitir al usuario
		seleccionar que tablas desea ver y cuales no*/
		tablesMenu = new JMenu(IDIOMAS_MENU_TABLAS[idioma]);//creación del objeto
		/*Se agrega el primer menu a la barra de menus*/
		menuBar.add(tablesMenu);

		//----------------------------------------------------------------------------------------

		/*Se crea inicializa el arreglo opcionesTablas que guarda los items del menu de opciones
		para seleccionar las ventanas visibles en la GUI*/
		opcionesTablas = new JRadioButtonMenuItem[TABLES_MENU_STRINGS.length];
		/*Una vez creado se llena por medio de un ciclo for el cual los inicializa de acuerdo al
		idioma seleccionado, las inicializa en modo seleccionado y las agrega al menu de tablas*/
		for(int i = 0,y=opcionesTablas.length;i<y;i++)
		{
			opcionesTablas[i] = new JRadioButtonMenuItem(IDIOMAS_TABLAS[idioma][i],true);
			opcionesTablas[i].addItemListener(new HideTableHandler(tablas[i],this));
			tablesMenu.add(opcionesTablas[i]);

		}

		tablesMenu.addSeparator();//Se agrega un separador al menu con fines esteticos
		//----------------------------------------------------------------------------------------

		/*Se agrega la opcion de salir de la interfaz desde el menu tablas*/
		exitFromMenu = new JMenuItem(IDIOMAS_SALIR[idioma]);//se inicializa el item segun idioma
		exitFromMenu.getAccessibleContext().setAccessibleDescription(IDIOMAS_SALIR[idioma]);
		exitFromMenu.addActionListener(new ExitHandler(this));

		tablesMenu.add(exitFromMenu);

		//----------------------------------------------------------------------------------------

		// /*Se crea el menu de lenguaje*/
		// languageMenu = new JMenu(IDIOMAS_LANGUAGES[idioma]);
		// /*Se agrega a la barra de menu*/
		// menuBar.add(languageMenu);
		/*Se inializan las opciones que JRadioButtonMenuItem*/
		//opcionesIdiomas = new JRadioButtonMenuItem[LANGUAGE_OPTIONS.length];
		/*Finalmente se inicializa todo y se agrega a languageMenu*/
		// for(int i = 0;i<opcionesIdiomas.length;i++)
		// {
		// 	opcionesIdiomas[i] = new JRadioButtonMenuItem(MENU_LENGUAJE[idioma][i],idioma==i);
		// 	languageMenu.add(opcionesIdiomas[i]);
		// }

		//----------------------------------------------------------------------------------------
		/*Se asigna el JMenuBar al JFrame*/
		this.setJMenuBar(menuBar);
	}

	/*Metodo que incializa los parametros para agregar los elementos al Layout*/
	private void constraintsInit()
	{
		/*Configuración de las carateristicas bajo las cuales los elementos seran agregados en
		el GridBagLayout*/
		tableConstraints.fill = GridBagConstraints.BOTH;
		tableConstraints.weightx=1;
		tableConstraints.weighty=5;
		tableConstraints.gridx=0;

		/*Configuración de las caracteristicas bajo las cuales las etiquetas seran agregada*/
		labelConstraints.fill = GridBagConstraints.BOTH;
		labelConstraints.weightx=1;
		labelConstraints.weighty=1;
		labelConstraints.gridx=0;
	}

	/*Metodo que incializa todos los componentes gráficos*/
	private void initComponents()
	{
		/*Se crean todos los objetos TPanel, los cuales almacenan las tablas y los botones necesarios para 
		su interacción, el constructor recibe los siguientes parametros:
			- idioma, que define el idioma con el cual se cargan los botones y los titulos de las columns
			- un objeto MainGUI el que es el contenedor al cual pertenecen, esto para ciertas modificaciones
			- Un arreglo de Strings para los titulos
			- Un entero que define que tabla almacenan
			- Un boleano que les hace saber si son editables o no
		*/
		legendTPanel = new TPanel(idioma,this,TPanel.IDIOMAS_LEGEND,LEGEND_TABLE,false);
		warehousesTPanel = new TPanel(idioma,this,TPanel.IDIOMAS_ALMACENES,WAREHOUSES_TABLE,true);
		productsTPanel = new TPanel(idioma,this,TPanel.IDIOMAS_PRODUCTS,PRODUCTS_TABLE,true);
		inventoryTPanel = new TPanel(idioma,this,TPanel.IDIOMAS_INVENTARIO,INVENTORY_TABLE,false);

		/*Se llena un arreglo con las tablas para operaciones futuras*/
		tablas = new TPanel[4];
		tablas[0] = legendTPanel;
		tablas[1] = warehousesTPanel;
		tablas[2] = productsTPanel;
		tablas[3] = inventoryTPanel;

		/*Se crean los botones para ocultar o mostrar las tablas*/
		legendButton = new JButton(IDIOMAS_TABLAS[idioma][LEGEND_TABLE]);
		warehouseButton = new JButton(IDIOMAS_TABLAS[idioma][WAREHOUSES_TABLE]);
		productosButton = new JButton(IDIOMAS_TABLAS[idioma][PRODUCTS_TABLE]);
		inventoryButton = new JButton(IDIOMAS_TABLAS[idioma][INVENTORY_TABLE]);

		/*Se asignan los bordes de los botones con fines esteticos*/
		legendButton.setBorder(BorderFactory.createBevelBorder(0));
		warehouseButton.setBorder(BorderFactory.createBevelBorder(0));
		productosButton.setBorder(BorderFactory.createBevelBorder(0));
		inventoryButton.setBorder(BorderFactory.createBevelBorder(0));

		/*Se agregan los action listener los cuales son una clase privada que
		recibe como parametro los objetos TPanel de la clase para poder interactuar
		con estos elementos*/
		legendButton.addActionListener(new TableButtonHandler(tablas[0]));
		warehouseButton.addActionListener(new TableButtonHandler(tablas[1]));
		productosButton.addActionListener(new TableButtonHandler(tablas[2]));
		inventoryButton.addActionListener(new TableButtonHandler(tablas[3]));

		/*Se le crean los bordes a los TPanels esto solo con fines esteticos*/
		legendTPanel.setBorder(BorderFactory.createBevelBorder(0));
		warehousesTPanel.setBorder(BorderFactory.createBevelBorder(0));
		productsTPanel.setBorder(BorderFactory.createBevelBorder(0));
		inventoryTPanel.setBorder(BorderFactory.createBevelBorder(0));
	}

	/*Metodo que agrega los componentes*/
	private void addMainComponents()
	{
		/*En esta clase se agregan los componentes en el orden adecuado*/
		this.add(legendButton,labelConstraints);
		this.add(legendTPanel,tableConstraints);
		this.add(warehouseButton,labelConstraints);
		this.add(warehousesTPanel,tableConstraints);
		this.add(productosButton,labelConstraints);
		this.add(productsTPanel,tableConstraints);
		this.add(inventoryButton,labelConstraints);
		this.add(inventoryTPanel,tableConstraints);

	}

	/*Metodo final*/
	private void deploy()
	{
		/*Este metodo solamente hace que todo se acomode como debe de*/
		this.pack();
		this.setVisible(true);
	}

	public void refreshInventory()
	{
		/*Este metodo es usado por los ActionListeners para aplicar los 
		cambios correctos a la table de salida.*/
		inventoryTPanel.refreshMe();
	}

	//****************************************************************************************
	//***CLASES PRIVADAS DE LOS MANEJADORES DE EVENTOS****************************************
	//****************************************************************************************
	
	/*Handler para la opción de salir en el menu de opciones*/
	private class ExitHandler implements ActionListener
	{
		private MainGUI x;//referencia a el objeto MainGUI que hospeda el menu

		/*Constructor que espera el objeto MainGUI que espera*/
		public ExitHandler(MainGUI x)
		{
			this.x = x;
		}

		/*Metodo que contiene las instrucciones que se van a realizar cuando ocurra un evento*/
		public void actionPerformed(ActionEvent e)
		{
			/*Esta linea cierra la ventana y termina el proceso de la ventana*/
			x.dispose();
		}
	}

	/*Esta clase es para el manejo de los botones que ocultan y muestran las tablas en la GUI*/
	private class TableButtonHandler implements ActionListener
	{
		private TPanel t;//Tienen como unico atributo la tabla que contiene el boton

		/*El constructor espera como unico parametro la tabla que contiene el boton al cual
		pertenece el objeto TableButtonHandler*/
		public TableButtonHandler(TPanel t)
		{
			this.t = t;
		}

		/*Metodo que va a activarse cuando ocurra un evento*/
		public void actionPerformed(ActionEvent e)
		{
			/*Esta linea de codigo niega el parametro visible de los objetos TPanel
			así es como los muestra u oculta al usuario.*/
			t.setVisible(!t.isVisible());
		}
	}


}