package PharmacyOrganizer;

import GUI.JFrame;

public interface Option {
	
	abstract String getOption();
	
	abstract void oper(Database database, JFrame parent, Employee e);

}
