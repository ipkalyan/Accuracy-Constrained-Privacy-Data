package lucene.external;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import web.Resource;
import web.dbfunction;

public class EmployeeDatabase {

    private static Employee[] EMPS=null; //= { 			new Employee("1", "Gandhinagar", "Dramil", "Lucene Developer"),
    //			new Employee("2", "Pune", "Karamveer", "J2ee Developer"),
    //			new Employee("3", "Redmond", "Nikunj", "Spring Developer"),
    //			new Employee("4", "Hydrabad", "Jigar", "EJB Developer"),
    //			new Employee("5", "New Jersey", "Vijay", "Project Manager")};

    public boolean formDatabase() {
        dbfunction dbfunc = new dbfunction();
        try {
            dbfunc.createConnection();


            ResultSet rs = dbfunc.queryRecord("select count(*) from users where userId != 'admin'");
            int users = 0;
            if (rs.next()) {
                users = Integer.parseInt(rs.getString(1));
            }
            
            EMPS = new Employee[users];
            
            rs = dbfunc.queryRecord("select userId,password,name,address,country,zip,email,salary,sex,language,about,status from users where userId != 'admin'");
            
            int g=0;
            while (rs.next()) {
                EMPS[g]=new Employee(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12));
                g++;
            }
            
            dbfunc.closeConnection();
            
                        
            return true;

        } catch (Exception ex) {
            System.out.println("<p><b>" + ex.toString() + "</b></p>");
            JOptionPane.showMessageDialog(null, "Failed to create emp database : "+ex.toString());
            return false;
        }
    }
    
    

    public static Employee[] getEmployee() {
        return EMPS;
    }

    public static Employee getEmployee(String id) {
        for (Employee employee : EMPS) {
            if (id.equals(employee.getUserId())) {
                return employee;
            }
        }
        return null;
    }

    public void createDatabase() {
        dbfunction dbfunc2 = new dbfunction();
        try {
            dbfunc2.createConnection();

            File f = new File(web.Resource.getDataSet());

            if (f.exists()) {
                try {
                    // Open the file that is the first 
                    // command line parameter
                    FileInputStream fstream = new FileInputStream(f);
                    // Get the object of DataInputStream
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    int count = 0;
                    //Read File Line By Line                    
                    while ((strLine = br.readLine()) != null) {
                        count++;
                        if (count > 1) {
                            String[] tokens = strLine.split("[,]");
                            String query = "INSERT INTO users(userId,password,name,address,country,zip,email,salary,sex,language,about,status,accesslevel) VALUES ('" + tokens[0] + "','" + tokens[1] + "','" + tokens[2] + "','" + tokens[3] + "','" + tokens[4] + "','" + tokens[5] + "','" + tokens[6] + "','" + tokens[7] + "','" + tokens[8] + "','" + tokens[9] + "','" + tokens[10] + "','" + tokens[11] + "','" + tokens[12] + "')";
                            System.out.println(query);
                            if (dbfunc2.updateRecord(query)) {
                                System.out.println("<p><b>Inserting record success!</b></p>");
                            } else {
                                System.out.println("<p><b>Inserting record failed!</b></p>");
                            }

                        }
                    }
                    dbfunc2.closeConnection();
                    //Close the input stream
                    in.close();
                } catch (Exception e) {//Catch exception if any
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println("<p><b>" + ex.toString() + "</b></p>");
        }
    }

    public static void main(String[] args) {
        // uncomment & use first time only
        EmployeeDatabase e = new EmployeeDatabase();
        e.createDatabase();
        e.createDatabase2();
        e.formDatabase();
    }

    private void createDatabase2() {
        DBase db = new DBase();
        Connection conn = db.connect(Resource.getDbUrl(), Resource.getDbUser(), Resource.getDbPass());
        File f1 = new File("clients.csv");     
        String table1 = "clients";        
        String columns = "firstName,lastName,address,city,email,phone,business,dob,salary,loanamount";
        
        if(f1.exists())
        {    
            String path = f1.getAbsolutePath();
            path=path.replace("\\", "/");
            System.out.println(path);
            db.importData(conn,path,table1,columns);
            System.out.println("Genuine Data Created");
            JOptionPane.showMessageDialog(null, "Genuine Data Created");
        }    
    }
}

class DBase {

    public DBase() {
    }

    public Connection connect(String db_connect_str,String db_userid, String db_password) {
        Connection conn;
        try {
            Class.forName(Resource.getdbDriver()).newInstance();

            conn = DriverManager.getConnection(db_connect_str,db_userid, db_password);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception 1:"+e.toString());
            e.printStackTrace();
            conn = null;
        }

        return conn;
    }

    public void importData(Connection conn, String filename,String table,String columns) {
        Statement stmt;
        String query;

        try {
            stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //query = "LOAD DATA INFILE '" + filename+ "' INTO TABLE "+table+" ("+columns+")";
            query = "LOAD DATA INFILE '"+filename+"' INTO TABLE "+table+"  FIELDS TERMINATED BY ',' ("+columns+")";
            //System.out.println(query);
            
            stmt.executeUpdate(query);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception 2:"+e.toString());
            e.printStackTrace();
            stmt = null;
        }
    }
}