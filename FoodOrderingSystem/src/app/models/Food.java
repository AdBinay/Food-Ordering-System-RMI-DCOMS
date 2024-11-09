package app.models;

import java.io.Serializable;

public class Food implements Serializable{
    private Integer id;
    private String fname;
    private String price;
    private String category;
    private String quantity="1";

    public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Food() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String food) {
        this.fname = food;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
