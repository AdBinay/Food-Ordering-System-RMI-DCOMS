package app.models;

import java.io.Serializable;

public class OrderItem implements Serializable{
    private Integer fid;
    private String food;
    private String price;
    private Integer cid;
    private Integer oid;
    private String timestamp;
    private Integer quantity;
   
    public OrderItem() {
    }

	public String getFood() {
		return food;
	}

	public void setFood(String fName) {
		this.food= fName;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}
