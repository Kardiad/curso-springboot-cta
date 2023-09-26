package edu.cta.academy.DTO;

public class Chiquitada {
	
	private int id;
	private String quote;
	
	public Chiquitada() {}
	
	public Chiquitada(int id, String quote) {		
		this.id = id;
		this.quote = quote;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	@Override
	public String toString() {
		return "Chiquitada [id=" + id + ", quote=" + quote + "]";
	}
		
}
