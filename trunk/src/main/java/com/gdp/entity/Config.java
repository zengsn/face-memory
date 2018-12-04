package com.gdp.entity;

public class Config {
    private String keyes;

    private String valuees;

    private String description;

    public String getKeyes() {
        return keyes;
    }

    public void setKeyes(String keyes) {
        this.keyes = keyes == null ? null : keyes.trim();
    }

    public String getValuees() {
        return valuees;
    }

    public void setValuees(String valuees) {
        this.valuees = valuees == null ? null : valuees.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	@Override
	public String toString() {
		return "Config [keyes=" + keyes + ", valuees=" + valuees + ", description=" + description + "]";
	}
}