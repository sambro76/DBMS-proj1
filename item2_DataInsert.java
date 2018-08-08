/*Student Id: 1000732321
Student name: Samnang Chay
*/

import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class item2_DataInsert{
	public static void main(String[] args) {
		try {
			Connection myConn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","myDB","Apple123");
			Statement myStmt = myConn.createStatement();
			
			File file = new File("department.txt");
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert into department values ("+ readLn+ ") ");	
			}
			scanner.close();
			
			file = new File("dept_locations.txt");
			scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert into dept_locations values ("+ readLn + ")");	
			}
			scanner.close();
			
			file = new File("employee.txt");
			scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert into employee values ("+ readLn+ ") ");	
			}
			scanner.close();

			file = new File("project.txt");
			scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert into project values("+ readLn + ")");	
			}
			scanner.close();
		
			file = new File("works_on.txt");
			scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX (works_on (essn,pno)) */ into works_on (essn,pno,hours) values ("+readLn+ ")");
			}
			scanner.close();
			
			file = new File("dependent.txt");
			scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			while (scanner.hasNext()) {
				String readLn=scanner.next();
				System.out.println(readLn);
				myStmt.executeQuery("insert into dependent values ("+ readLn + ")");	
			}
			scanner.close();
			/*Insert the remaining foreign keys */
			myStmt.executeQuery("alter table employee add constraint empdeptfk foreign key(dno) references department(dnumber)");	
			myStmt.executeQuery("alter table department add constraint deptmgrfk foreign key(mgr_ssn) references employee(ssn)");
			myStmt.executeQuery("delete from works_on "
					+ " where pno in (select w.pno from works_on w "
					+ " where not exists (select pnumber from project p where w.pno=p.pnumber))");
			myStmt.executeQuery("alter table works_on add constraint wrkson_fk2 foreign key (pno) references project(pnumber)");
			myConn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}