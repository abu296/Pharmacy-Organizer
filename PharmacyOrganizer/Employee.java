package PharmacyOrganizer;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import GUI.JButton;
import GUI.JFrame;
import GUI.JPanel;

public abstract class Employee {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private double salary;
	private LocalDate dateOfBirth;
	private int workHours;
	private LocalTime startTime;
	private LocalTime endTime;
	protected Option[] options;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	public Employee() {}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public String getDateOfBirth() {
		return dateFormatter.format(dateOfBirth);
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = LocalDate.parse(dateOfBirth, dateFormatter);
	}
	
	public int getWorkHours() {
		return workHours;
	}
	
	public void setWorkHours(int workHours) {
		this.workHours = workHours;
	}
	
	public String getStartTime() {
		return timeFormatter.format(startTime);
	}
	
	public void setStartTime(String startTime) {
		this.startTime = LocalTime.parse(startTime, timeFormatter);
	}
	
	public String getEndTime() {
		return timeFormatter.format(endTime);
	}
	
	public void setEndTime(String endTime) {
		this.endTime = LocalTime.parse(endTime, timeFormatter);
	}
	
	public String getDateFormat() {
		return "yyyy-dd-MM";
	}
	
	public String getTimeFormat() {
		return "HH:mm";
	}
	
	abstract String getJob();
	
	public void showOptions(Database database, JFrame parent) {
		
		int rows = options.length;
		int columns = 1;
		
		while (rows>10) {
			if (rows%2==1) rows++;
			rows = rows/2;
			columns++;
		}
		
		int height = 200+45*rows;
		int width = 400*columns;
		JFrame frame = new JFrame(width, height, parent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridLayout(rows, columns, 20, 20), 20, 20, 20, 20);
		for (int i=0;i<options.length;i++) {
			JButton btn = new JButton(options[i].getOption(), 20);
			int index = i;
			btn.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					options[index].oper(database, frame, Employee.this);
				}
			});
			panel.add(btn);
		}
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	public String getName() {
		return firstName+" "+lastName;
	}
	
	public void print() {
		System.out.println("ID:\t\t"+getID());
		System.out.println("Name:\t\t"+getFirstName()+" "+getLastName());
		System.out.println("Email:\t\t"+getEmail());
		System.out.println("Phone Number:\t"+getPhoneNumber());
		System.out.println("Salary:\t\t"+getSalary()+" $");
		System.out.println("Date of Birth:\t"+getDateOfBirth());
		System.out.println("Work Hours:\t"+getWorkHours());
		System.out.println("Start Time:\t"+getStartTime());
		System.out.println("End Time:\t"+getEndTime());
		System.out.println("Job:\t\t"+getJob());
		System.out.println("---------------------------------");
	}

}
