package com.example.winoapp;

/**
 * @author Scott
 * This is a Model for wine entry
 * Contains attributes of wine and their getters and setters 
 */
public class Wine {

	private int    _id;
	private String _name;
	private String _vineyard;
	private String _varietal;
	private String _vintage;
	private String _quantity;
	private String _containerType;
	private double _rating;
	private String _storageLoc;
	private String _notes;
	private String _imgPath;
    
    public Wine( int id, String name, String vineyard, String varietal, String vintage, 
    		     String quantity, String containerType, double rating, String storageLoc, 
    		     String notes, String imgPath) {
    	this._id = id;
    	this._name = name;
    	this._vineyard = vineyard;
    	this._varietal = varietal;
    	this._vintage = vintage;
    	this._quantity = quantity;
    	this._containerType = containerType;
    	this._rating = rating;
    	this._storageLoc = storageLoc;
    	this._notes = notes;
    	this._imgPath = imgPath;
    }
    
    public String getVarietal() {
    	return _varietal;
    }
    
    public void setvarietal(String varietal) {
    	this._varietal = varietal;
    }
    
    public String getImagePath() {
    	return _imgPath;
    }
    
    public void setImagePath(String imgPath) {
    	this._imgPath = imgPath;
    }

    public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getVineyard() {
		return _vineyard;
	}

	public void setVineyard(String vineyard) {
		this._vineyard = vineyard;
	}

	public String getVintage() {
		return _vintage;
	}

	public void setVintage(String vintage) {
		this._vintage = vintage;
	}

	public String getQuantity() {
		return _quantity;
	}

	public void setQuantity(String quantity) {
		this._quantity = quantity;
	}

	public String getContainerType() {
		return _containerType;
	}

	public void setContainerType(String containerType) {
		this._containerType = containerType;
	}

	public double getRating() {
		return _rating;
	}

	public void setRating(double rating) {
		this._rating = rating;
	}

	public String getStorageLoc() {
		return _storageLoc;
	}

	public void setStorageLoc(String storageLoc) {
		this._storageLoc = storageLoc;
	}

	public void setNotes(String info) {
		this._notes = info;
	}

	public String getNotes() {
		return _notes;
	}

	public int getID() {
		return _id;
	}

	public void setID(int id) {
		this._id = id;
	}
}
