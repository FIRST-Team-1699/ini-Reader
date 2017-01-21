/**
 * FIRST Team 1699
 * 
 * A class that represents a section of a configuration file.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.exception.NotFoundException;

import java.util.ArrayList;

/**
 * Represents a section of a configuration file. It stores multiple ConfigLine objects and has methods to access and process them. 
 */
public class ConfigSection {

	private String name;
	private ArrayList<ConfigLine<?>> lines = new ArrayList<>();

	/**
	 * Creates a ConfigSection with the name provided
	 * 
	 * @param name the name of the ConfiSection
	 */
	public ConfigSection(String name) {
		this.name = name;
	}
	
	/**
	 * Creates a copy of the given ConfigSection
	 */
	public ConfigSection(ConfigSection section) {
		this.name = section.getName();
		this.lines = (ArrayList<ConfigLine<?>>) section.getLines();
	}

	/**
	 * Gets the name of the ConfigSection
	 * 
	 * @return name of the ConfigSection
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add the ConfigLine to the ConfigSection
	 * 
	 * @param line the ConfigLine to be added
	 */
	public void add(ConfigLine<?> line) {
		lines.add(line);
	}

	/**
	 * Create a ConfigLine and add it to the ConfigSection
	 * 
	 * @param <Type> the type of the new ConfigLine
	 * 
	 * @param name  the name for the ConfigLine to be added
	 * @param value  the value for the ConfigLine to be added
	 */
	public <Type> void add(String name, Type value) {
		ConfigLine<Type> line = new ConfigLine<Type>(name, value);
		this.add(line);
	}
	
	/**
	 * Get a ConfigLine based on the provided name. Returns the first ConfigLine with that name.
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @return the ConfigLine that matches the specified name, null if it does not exist
	 * @throws NotFoundException if the given name is not found
	 */
	public ConfigLine<?> getLine(String name) throws NotFoundException {
		for (ConfigLine<?> cl : lines) {
			if (cl.getName().trim().equals(name.trim())) {
				return new ConfigLine<>(cl);
			}
		}
		throw new NotFoundException("Line not found " + name + ".");
	}
	
	/**
	 * Get the contents of this ConfigSection. Useful for autonomous. 
	 * 
	 * @return an ArrayList of ConfigLines that make up this ConfigSection
	 */
	public List<ConfigLine<?>> getLines() {
		if (this.lines == null) {
			return null;
		}
		ArrayList<ConfigLine<?>> output = new ArrayList<>();
		for (ConfigLine<?> cl : this.lines) {
			output.add(new ConfigLine<>(cl));
		}
		return output;
	}
	
	
	/**
	 * Gets the value of a ConfigLine without using a dot operator! Cool!
	 * 
	 * @param <Check_Type> the type to check against
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @param class_type the Class of the type
	 * @return the the value of this ConfigLine or null if the types do not match
	 * @throws NotFoundException if the given name is not found
	 */
	@SuppressWarnings("unchecked") // it is checked so...
	public <Check_Type> Check_Type getLineValue(String name, Class<Check_Type> class_type) throws NotFoundException {
		ConfigLine<?> out = this.getLine(name);
		if (out.getRawValue().getClass().equals(class_type)) {
			return (Check_Type) out.getRawValue(); 
		} 
		return null;
	}
	
	/**
	 * Get the contents of this ConfigSection as a List of Strings. 
	 * 
	 * @return an ArrayList of Strings made from the ConfigLines in the ConfigSection
	 */
	public List<String> getStringValues() {
		if (this.lines == null) {
			return null;
		} else {
			ArrayList<String> output = new ArrayList<>();
			for (ConfigLine<?> cl : this.lines) {
				output.add(cl.getRawValue().toString());
			}
			return output;
		}
	}
	
	/**
	 * Get the number of lines in this ConfigSection
	 * 
	 * @return the number of lines in this ConfigSection
	 */
	public int size() {
		return this.lines.size();
	}
	
	/**
	 * Attempts to generate the code that made this ConfigSection.
	 * 
	 * @return a String with the code that made this ConfigSection
	 */
	public String generateCode() {
		String output = "";
		output += "[" + this.name + "]\n";
		for (ConfigLine<?> cl : this.lines) {
			output += cl.generateCode();
		}
		return output;
	}
	
	/**
	 * @inheritDoc 
	 */
	@Override
	public String toString() {
		String output = "";
		output = output + "Section: " + this.name + "\n";
		for (ConfigLine<?> cl : lines) {
			output += cl.toString() + "\n";
		}
		/*if (output.charAt(output.length() - 1) == '\n') {
			output = output.substring(0, output.length() - 1);
		}*/
		return output;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigSection) {
			ConfigSection cs_test = (ConfigSection) obj;
			return (cs_test.getLines().equals(this.lines) && cs_test.getName().equals(this.name));
		}
		return false;
	}
	
	
}
