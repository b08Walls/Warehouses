//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;



public class DataBaseHandler
{

   //********************************************************************************************
   //***CONSTANTES*******************************************************************************
   //********************************************************************************************

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/warehouses";

   static final String USER = "root";
   static final String PASS = "password";
   
   //********************************************************************************************
   
   public static final String [] COLUMNAS_PRODUCTOS = {"NAME","TOTAL_QTY","REMAINING_QTY","NO_WAREHOUSE"};
   public static final String [] COLUMNAS_WAREHOUSES = {"ID_WAREHOUSE,MIN_QTY","MAX_QTY"};

   //********************************************************************************************
   //***ATRIBUTOS DE LA CLASE********************************************************************
   //********************************************************************************************

   private static Connection connection = null;
   private static Statement statement = null;

   //********************************************************************************************
   //***METODOS DE LA CLASE*********************************************************************
   //********************************************************************************************

   /*Este metodo retorna un arreglo bidimencional de Strings que son los datos de la tabla leyenda*/
   public static String [][] getLegendTable()
   {
      //Inicializa el arreglo
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /*Se guarda el strin de la consulta a realizar*/
         String sql = "SELECT * FROM legend";

         /*Se ejecuta la consulta*/
         ResultSet result = statement.executeQuery(sql);

         /*Se crea una Arraylist para ir guardando los distintos resultados independientemente del
         número de renglones obtenidos*/
         ArrayList<String []> temp = new ArrayList<String []>();

         /*Dentro de este ciclo While se van guardando los renglones*/
         while(result.next())
         {
            /*Se guardan los 3 valores de la tabla*/
            String id_color  = result.getString("ID_COLOR");
            String meaning = result.getString("MEANING");
            String color = result.getString("COLOR");

            /*Se crea el arreglo*/
            String [] tempArray = {id_color,meaning,color};
            /*Finalmente se agrega el arreglo al ArrayList*/
            temp.add(tempArray);
         }
         
         /*Se cierran los elementos usados durante la consulta esto como buena práctica de programacion*/
         result.close();
         statement.close();
         connection.close();

         /*Se crea el arreglo que el metodo debe regresar a partir del ArrayList*/
         valoresTabla = temp.toArray(new String [temp.size()][3]);
      }/*Se colocan los catch necesarios para las posibles excepciones*/
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally/*Bloque finally para ejecutar siempre el cierre de las entidades en la conección a la base de datos*/
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
         return valoresTabla;
      }
   }   

   public static String [][] getProductsTable()
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "SELECT * FROM products";
         ResultSet result = statement.executeQuery(sql);
         ArrayList<String []> temp = new ArrayList<String []>();


         while(result.next())
         {
            
            String id_product  = result.getString("ID_PRODUCT");
            String name = result.getString("NAME");
            String total_qty = result.getString("TOTAL_QTY");
            String remaining_qty = result.getString("REMAINING_QTY");
            String no_warehouse = result.getString("NO_WAREHOUSE");

            String [] tempArray = {id_product,name,total_qty,remaining_qty,no_warehouse};
            temp.add(tempArray);
         }
         
         result.close();
         statement.close();
         connection.close();

         valoresTabla = temp.toArray(new String [temp.size()][3]);
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
         return valoresTabla;
      }
   }

   public static String [][] getWarehousesTable()
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "SELECT * FROM warehouses";
         ResultSet result = statement.executeQuery(sql);
         ArrayList<String []> temp = new ArrayList<String []>();


         while(result.next())
         {
            
            String id_warehouse  = result.getString("ID_WAREHOUSE");
            String min_qty = result.getString("MIN_QTY");
            String max_qty = result.getString("MAX_QTY");

            String [] tempArray = {id_warehouse,min_qty,max_qty};
            temp.add(tempArray);
         }
         
         result.close();
         statement.close();
         connection.close();

         valoresTabla = temp.toArray(new String [temp.size()][3]);
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
         return valoresTabla;
      }
   }

   public static void updateSomeValue(String table,String column,String newVal,String id_type,int id)
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "UPDATE "+table+" SET "+column+" = "+newVal+" where "+id_type+" = "+id;
         statement.executeUpdate(sql);
         
         statement.close();
         connection.close();

      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
   }

   public static void insertProduct(String producto,String total,String remaining_qty,String no_warehouse)
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "INSERT INTO PRODUCTS (NAME,TOTAL_QTY,REMAINING_QTY,NO_WAREHOUSE) VALUES (\""+producto+"\","+total+","+remaining_qty+","+no_warehouse+")";


         // INSERT INTO table_name (column1, column2, column3, ...)
         // VALUES (value1, value2, value3, ...);
         statement.executeUpdate(sql);
         
         statement.close();
         connection.close();

      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
   }

   public static void insertWarehouse(String min_qty,String max_qty)
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "INSERT INTO WAREHOUSES (MIN_QTY,MAX_QTY) VALUES ("+min_qty+","+max_qty+")";

         System.out.println(sql);


         // INSERT INTO table_name (column1, column2, column3, ...)
         // VALUES (value1, value2, value3, ...);
         statement.executeUpdate(sql);
         
         statement.close();
         connection.close();

      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
   }

   public static void clearWarehouse()
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "DELETE FROM WAREHOUSES";

         statement.executeUpdate(sql);

         sql = "ALTER TABLE WAREHOUSES AUTO_INCREMENT = 0";

         statement.executeUpdate(sql);
         
         statement.close();
         connection.close();

      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
   }

   public static void clearProducts()
   {
      String valoresTabla[][] = null;
      try
      {
         /*Intentamos iniciar la conección*/
         connect();

         /**/
         String sql;
         sql = "DELETE FROM PRODUCTS";

         statement.executeUpdate(sql);

         sql = "ALTER TABLE PRODUCTS AUTO_INCREMENT = 0";

         statement.executeUpdate(sql);
         
         statement.close();
         connection.close();

      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if(statement!=null)
               statement.close();
         }
         catch(SQLException se2)
         {
         }

         try
         {
            if(connection!=null)
               connection.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }
      }
   }


   public static void connect() throws SQLException,ClassNotFoundException 
   {
         Class.forName(JDBC_DRIVER);
         connection = DriverManager.getConnection(DB_URL,USER,PASS);
         statement = connection.createStatement();
   }

   public static String [][] getInventoryTable()
   {
      /*Para la tabla de salida es necesario mandar a llamar a las otras tres tablas y guardarlas 
      de manera local para este metodo*/
      String [][] productsTable = getProductsTable();
      String [][] warehousesTable = getWarehousesTable();
      /*Se inicializan las variables para la comparación requerida para el color*/
      int min = 0;
      int max = 0;
      int cantidad = 0;

      /*Se crea un ArrayList para guardar los renglones que se generen*/
      ArrayList<String[]> tablaAL = new ArrayList<String[]>();
      
      /*Por medio del for se recorre la tabla de productos*/
      for(int i = 0;i<productsTable.length;i++)
      {
         String [] row = new String[5];//se crea un arreglo para el renglon de esta iteración del for

         row[0] = productsTable[i][1];//Se van tomando los valores requeridos
         row[1] = productsTable[i][3];//para cada una de las columnas
         row[3] = productsTable[i][2];//esto a partir de la tabla productos
         row[4] = productsTable[i][4];//nos saltamos el dos porque esta parte debe quedar dentro del bloque try/catch

         try
         {
            /*Aquí se hace el calculo de los productos que quedan restantes*/
            row[2] = ""+(Integer.parseInt(productsTable[i][2]) - Integer.parseInt(productsTable[i][3]));

            /*Se guardan los valores mínimo y máximo del almacen correspondiente al producto, de igual
            manera se guarda la cantidad restante del producto*/
            min = Integer.parseInt(warehousesTable[Integer.parseInt(row[4])-1][1]);
            max = Integer.parseInt(warehousesTable[Integer.parseInt(row[4])-1][2]);
            cantidad = Integer.parseInt(row[1]);

            /*Finalmente se hace las comparaciones pertinentes para poder determinar cual es el color requerido 
            en la celda*/

            if(cantidad<min)
            {
               row[1]+=" (RED)";
            }
            else if(cantidad >= max)
            {
               row[1]+=" (GREEN)";
            }
            else
            {
               row[1]+=" (YELLOW)";
            }
         }
         catch(Exception e)
         {
            System.out.println(e);
         }



         tablaAL.add(row);
      }

      //Finalmente se regresa el arrayList como un arreglo bidimencional para crear las tablas en la GUI 
      return tablaAL.toArray(new String [tablaAL.size()][5]);
   }


}