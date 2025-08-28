package PharmacyOrganizer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.JFrame;
import GUI.JGridFrame;
import GUI.JLabel;

public class ReadAllEmployees implements Option {

	@Override
	public void oper(Database database, JFrame parent, Employee e) {
		String select = "SELECT `ID`, `FirstName`, `LastName`, `Email`, `PhoneNumber`,"
				+ " `Salary`, `DateOfBirth`, `WorkHours`, `StartTime`, `EndTime`,"
				+ " `Job` FROM `employees`;";
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				Employee emp;
				int job = rs.getInt("Job");
				switch(job) {
				case 1:
					emp = new Manager();
					break;
				case 2:
					emp = new Pharmacist();
					break;
				case 3:
					emp = new Cashier();
					break;
					default:
						emp = new Employee() {
							@Override
							String getJob() {
								return null;
							}
						};
						break;
				}
				emp.setID(rs.getInt("ID"));
				emp.setFirstName(rs.getString("FirstName"));
				emp.setLastName(rs.getString("LastName"));
				emp.setEmail(rs.getString("Email"));
				emp.setPhoneNumber(rs.getString("PhoneNumber"));
				emp.setSalary(rs.getDouble("Salary"));
				emp.setDateOfBirth(rs.getString("DateOfBirth"));
				emp.setWorkHours(rs.getInt("WorkHours"));
				emp.setStartTime(rs.getString("StartTime"));
				emp.setEndTime(rs.getString("EndTime"));
				employees.add(emp);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JGridFrame frame = new JGridFrame(parent, employees.size()+1, 9, getOption(), true);
//		System.out.println("Name:\t\t"+getFirstName()+" "+getLastName());
//		System.out.println("Email:\t\t"+getEmail());
//		System.out.println("Phone Number:\t"+getPhoneNumber());
//		System.out.println("Salary:\t\t"+getSalary()+" $");
//		System.out.println("Date of Birth:\t"+getDateOfBirth());
//		System.out.println("Work Hours:\t"+getWorkHours());
//		System.out.println("Start Time:\t"+getStartTime());
//		System.out.println("End Time:\t"+getEndTime());
//		System.out.println("Job:\t\t"+getJob());
		
		frame.addToGrid(new JLabel("Name", 15));
		frame.addToGrid(new JLabel("Email", 15));
		frame.addToGrid(new JLabel("Phone Number", 15));
		frame.addToGrid(new JLabel("Salary", 15));
		frame.addToGrid(new JLabel("Date of Birth", 15));
		frame.addToGrid(new JLabel("Work Hours", 15));
		frame.addToGrid(new JLabel("Start Time", 15));
		frame.addToGrid(new JLabel("End Time", 15));
		frame.addToGrid(new JLabel("Job", 15));
		
		
		for (Employee emp : employees) {
			frame.addToGrid(new JLabel(emp.getName(), 15));
			frame.addToGrid(new JLabel(emp.getEmail(), 15));
			frame.addToGrid(new JLabel(emp.getPhoneNumber(), 15));
			frame.addToGrid(new JLabel("Rs."+emp.getSalary(), 15));
			frame.addToGrid(new JLabel(emp.getDateOfBirth(), 15));
			frame.addToGrid(new JLabel(String.valueOf(emp.getWorkHours()), 15));
			frame.addToGrid(new JLabel(emp.getStartTime(), 15));
			frame.addToGrid(new JLabel(emp.getEndTime(), 15));
			frame.addToGrid(new JLabel(emp.getJob(), 15));
		}
	}
	
	@Override
	public String getOption() {
		return "View all Employees";
	}

}
