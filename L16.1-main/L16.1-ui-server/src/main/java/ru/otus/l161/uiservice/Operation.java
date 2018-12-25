package ru.otus.l161.uiservice;

public enum Operation {

	SELECT("Select"), SAVE("Save"), DELETE("Delete");

	private String name;

	private Operation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
