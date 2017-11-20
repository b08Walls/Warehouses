import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

/*Esta clase es una clase general que va a englobar varios elementos usados por cada
tabla en el sistema */

public class TPanel extends JPanel
{
	//********************************************************************************
	//***CONSTANTES DE LA CLASE*******************************************************
	//********************************************************************************

	public static final String [] HEADER_LEGEND = {"#","MEANING","COLOR"};
	public static final String [] ENCABEZADO_LEYENDA = {"#","SIGNIFICADO","COLOR"};

	public static final String [][] IDIOMAS_LEGEND = {HEADER_LEGEND,ENCABEZADO_LEYENDA};
	
	public static final String [] HEADER_PRODUCTS = {"#","NAME","TOTAL QTY.","REMAINING QTY.","NO. WAREHOUSE"};	
	public static final String [] ENCABEZADO_PRODUCTOS = {"#","NOMBRE","CANTIDAD TOTAL","CANTIDAD RESTANTE","NO. ALMACEN"};

	public static final String [][] IDIOMAS_PRODUCTS = {HEADER_PRODUCTS,ENCABEZADO_PRODUCTOS};

	public static final String [] HEADER_WAREHOUSES = {"#","MIN QTY.","MAX QTY."};
	public static final String [] ENCABEZADO_ALMACENES = {"#","CANTIDAD MIN.","CANTIDAD MAX"};

	public static final String [][] IDIOMAS_ALMACENES = {HEADER_WAREHOUSES,ENCABEZADO_ALMACENES};

	public static final String [] HEADER_INVENTORY = {"PRODUCT NAME","ON STOCK","SOLD","TOTAL","WAREHOUSE"};
	public static final String [] ENCABEZADO_INVENTARIO = {"NOMBRE EL PRODUCTO","EN ALMACEN","VENDIDO","TOTAL","ALMACEN"};

	public static final String [][] IDIOMAS_INVENTARIO = {HEADER_INVENTORY,ENCABEZADO_INVENTARIO};

	public static final String [] BUTTONS_TEXT = {"REFRESH","NEW ROW","SAVE","HIDE TABLE"};
	public static final String [] TEXTO_BOTONES = {"REFRESCAR","NUEVO RENGLON","GUARDAR","OCULTAR TABLA"};

	public static final String [][] IDIOMAS_BUTTONS = {BUTTONS_TEXT,TEXTO_BOTONES};

	//********************************************************************************
	//***ATRIBUTOS DE LA CLASE********************************************************
	//********************************************************************************

	private JTable jtable;//Tabla donde se muestra la información de la base de datos
	private JScrollPane jScrollPane;//Elemento que permite tener un scroll
	private MainGUI padre;//Atributo donde se guarda el JFrame que hospeda este TPanel

	private JButton refreshButton;//Boton que baja la info de DB a GUI
	private JButton newButton;//boton para crear una nueva entrada al sistema.
	private JButton updateButton;//Boton que sube la info de GUI a DB
	private JButton hideButton;//Boton que esconde la tabla.

	private JPanel panelTable;//Panel donde se acomoda la tabla
	private JPanel panelButtons;//Panel donde se acomodan los botones

	private int idioma = 0;//Atributo que define con que idioma cargan los elementos
	private int noTabla = 0;//Atributo que guarda el número de tabla en el TPanel
	private String [][] tableHeader;//Atributo que guarda el encabezado de la tabla
	private boolean editable = true;//Atributo que define si el objeto se puede editar

	//--------------------------------------------------------------------------------
	/*Distintos atributos donde se guaradan las condiciones con las cuales se van a 
	agregar los elementos a los JPanels en GridBagLayout*/

	private GridBagConstraints tablePanelCons;//Condiciones del JPanel de la tabla
	private GridBagConstraints buttonsPanelCons;//Condiciones del JPanel de los botones
	private GridBagConstraints buttonsCons;//Condiciones de los botones 
	private GridBagConstraints tableCons;//Condiciones de la tabla

	//********************************************************************************
	//***CONSTRUCTORES DE LA CLASE****************************************************
	//********************************************************************************

	public TPanel(int idioma, MainGUI padre,String [][] tableHeader, int noTabla, boolean editable)
	{
		/*Se guardan los atributos en el construcor*/
		this.idioma = idioma;
		this.padre = padre;
		this.noTabla = noTabla;
		this.tableHeader = tableHeader;
		this.editable = editable;

		/*Se inicializan las tablas*/
		jTableInit(tableHeader,getTable(noTabla));

		/*Se inicializa el JScroll pane de cada TPanel*/
		jScrollPaneInit();

		/*Se incializan los objetos para describir las condiciones con las que se agregan
		los diferentes elementos a la GUI*/
		constraintsInit();

		/*Se inicializan los botones*/
		jButtonsInit();

		/*Finalmente se incializan los JPanels que guardan todos los elementos del TPanel*/
		jpanelsInit();
	}

	//********************************************************************************
	//***METODOS DE INICIALIZACIÓN DE LA CLASE****************************************
	//********************************************************************************

	/*Este metodo inicializa los JPanels donde se almacenan distintos elementos de la
	Tabla*/
	private void jpanelsInit()
	{
		/*En este panel se guardara la tabla*/
		panelTable = new JPanel();
		panelTable.setLayout(new GridBagLayout());//Se asigna un GridBagLayout
		panelTable.setBorder(BorderFactory.createBevelBorder(1));//se le crea borde

		/*Se agrega la table al panel de tabla*/
		panelTable.add(jScrollPane,tableCons);

		/*En este panel se agregaran los botones de las opciones de interacción con
		las tablas*/
		panelButtons = new JPanel();
		panelButtons.setLayout(new GridBagLayout());//Se asigna un GridBagLayout
		panelButtons.setBorder(BorderFactory.createBevelBorder(1));//se le crea borde

		/*Se agregan los botones en el panel de botones*/
		panelButtons.add(refreshButton,buttonsCons);
		if(editable)
		{
			panelButtons.add(newButton,buttonsCons);
			panelButtons.add(updateButton,buttonsCons);
		}
		panelButtons.add(hideButton,buttonsCons);
		

		/*Configuraciones del JPanel de la clase*/
		this.setPreferredSize(new Dimension(600,400));
		this.setLayout(new GridBagLayout());

		/*Se agregan los dos JPanels al JPanel principal*/
		this.add(panelTable,tablePanelCons);
		this.add(panelButtons,buttonsPanelCons);	
	}

	/*Este metodo inicializa las condiciones bajo las cuales algunos elementos son 
	agregados al Layout*/
	private void constraintsInit()
	{
		tablePanelCons = new GridBagConstraints();
		tablePanelCons.fill = GridBagConstraints.BOTH;
		tablePanelCons.weightx = 1;
		tablePanelCons.weighty = 5;
		tablePanelCons.gridy = 0;

		buttonsPanelCons = new GridBagConstraints();
		buttonsPanelCons.fill = GridBagConstraints.HORIZONTAL;
		buttonsPanelCons.weightx = 1;
		buttonsPanelCons.weighty = 0;
		buttonsPanelCons.gridx = 0;

		buttonsCons = new GridBagConstraints();
		buttonsCons.fill = GridBagConstraints.HORIZONTAL;
		buttonsCons.weightx = 1;
		buttonsCons.weighty = 1;
		buttonsCons.gridy = 0;

		tableCons = new GridBagConstraints();
		tableCons.fill = GridBagConstraints.BOTH;
		tableCons.weightx = 1;
		tableCons.weighty = 1;
		tableCons.gridx = 0;
	}

	/*Este metodo inicializa el JScrollPane y le asigna como objeto la tabla
	*/
	private void jScrollPaneInit()
	{
		jScrollPane = new JScrollPane(jtable);
	}

	// /*Este metodo inicializa una tabla demo para pruebas*/
	// private void jTableInit()
	// {
	// 	String[] columnNames = {"First Name",
 //                        "Last Name",
 //                        "Sport",
 //                        "# of Years",
 //                        "Vegetarian"};



	// 	Object[][] data = {
	// 	    {"testLabel", "Smith",
	// 	     "Snowboarding", new Integer(5), new Boolean(false)},
	// 	    {"John", "Doe",
	// 	     "Rowing", new Integer(3), new Boolean(true)},
	// 	    {"Sue", "Black",
	// 	     "Knitting", new Integer(2), new Boolean(false)},
	// 	    {"Jane", "White",
	// 	     "Speed reading", new Integer(20), new Boolean(true)},
	// 	    {"Joe", "Brown",
	// 	     "Pool", new Integer(10), new Boolean(false)}
	// 	};

	// 	jtable = new JTable(data, columnNames);
	// }

	/*Este metodo inicializa una tabla a partir de parámetros recibidos*/
	public void jTableInit(String [][] header, Object [][] data)
	{
		jtable = new JTable(new DefaultTableModel(data,header[idioma]));
	}

	/*Este metodo inicializa los botones y les asigna su ActionListener*/
	public void jButtonsInit()
	{
		refreshButton = new JButton(IDIOMAS_BUTTONS[idioma][0]);
		newButton = new JButton(IDIOMAS_BUTTONS[idioma][1]);
		updateButton = new JButton(IDIOMAS_BUTTONS[idioma][2]);
		hideButton = new JButton(IDIOMAS_BUTTONS[idioma][3]);

		hideButton.addActionListener(new HideTableHandler(this,padre));
		refreshButton.addActionListener(new refreshButtonHandler());
		newButton.addActionListener(new newButtonHandler(jtable));
		updateButton.addActionListener(new saveButtonHandler(jtable,noTabla));
	}

	//********************************************************************************
	//***METODOS SET'S Y GET'S********************************************************
	//********************************************************************************

	/*Metodo que ocupa a la clase DataBaseHandler la cual toma los datos de la base de
	datos y retorna un objeto apto para asignarse a la tabla*/
	private Object [][] getTable(int noTabla)
	{
		Object [][] tableData = null;

		switch(noTabla)
		{
			case 0:
				tableData = DataBaseHandler.getLegendTable();
				break;
			case 1:
				tableData = DataBaseHandler.getWarehousesTable();
				break;
			case 2:
				tableData = DataBaseHandler.getProductsTable();
				break;
			case 3:
				tableData = DataBaseHandler.getInventoryTable();
				break;
			default:
			break;
		}
		return tableData;
	}

	//********************************************************************************
	//***METODOS DE OPERACION DE LA CLASE ********************************************
	//********************************************************************************

	/*Este metodo da la instrucción al TPanel de actualizar su JTable con la base de
	datos*/
	public void refreshMe()
	{
		Object [][] temp = getTable(noTabla);
		for(int i = 0;i<temp.length;i++)
		{
			for(int j = 0;j<temp[0].length;j++)
			{
				jtable.getModel().setValueAt(temp[i][j],i,j);
			}
		}
	}


	//********************************************************************************
	//***CLASES PRIVADAS PARA EL MANEJO DE EVENTOS************************************
	//********************************************************************************

	/*Clase privada que permite actualizar la JTable del TPanel y al mismo tiempo
	actualiza la tabla de salida*/
	private class refreshButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			refreshMe();
			padre.refreshInventory();
		}
	}

	private class newButtonHandler implements ActionListener
	{
		JTable jt;

		public newButtonHandler(JTable jt)
		{
			this.jt = jt;
		}

		public void actionPerformed(ActionEvent e)
		{
			int index = jt.getModel().getColumnCount();

			DefaultTableModel model = (DefaultTableModel) jt.getModel();

			model.addRow(new Object[index]);
		}
	}

	private class saveButtonHandler implements ActionListener
	{
		JTable jt;
		int noTabla;

		public saveButtonHandler(JTable jt, int noTabla)
		{
			this.jt = jt;
			this.noTabla = noTabla;
		}

		public void actionPerformed(ActionEvent e)
		{
			DefaultTableModel model = (DefaultTableModel) jt.getModel();

			int columnas = model.getColumnCount();
			int renglones = model.getRowCount();

			System.out.println("TABLA TAMAÑO: COL: "+columnas+", REN: "+renglones);
			jt.clearSelection();

			switch(noTabla)
			{
				case 1:
					DataBaseHandler.clearWarehouse();
					for(int i = 0;i<renglones;i++)
					{
						String [] valores= new String [columnas];
						for(int j = 0;j<columnas;j++)
						{
							System.out.println("OBTENIENDO EL VALOR EN: "+i+","+j+" EL VALOR ES : "+model.getValueAt(i,j));
							valores[j] = (String) model.getValueAt(i,j);
						}
						DataBaseHandler.insertWarehouse(valores[1],valores[2]);
					}
					break;
				case 2:
					DataBaseHandler.clearProducts();
					for(int i = 0;i<renglones;i++)
					{
						String [] valores= new String [columnas];
						for(int j = 0;j<columnas;j++)
						{
							valores[j] = (String) model.getValueAt(i,j);
						}
						DataBaseHandler.insertProduct(valores[1],valores[2],valores[3],valores[4]);
					}
					break;
				default:
					break;

			}

			padre.refreshInventory();
		}

	}


	


}