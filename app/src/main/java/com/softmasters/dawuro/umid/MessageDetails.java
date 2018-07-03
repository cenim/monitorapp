package com.softmasters.dawuro.umid;

import java.io.Serializable;
import java.util.List;

public class MessageDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	Contactinformation contactinformation;
	Comments comments;
	List<Gallery> gallery;
	List<Location> location;

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public Contactinformation getContactinformation() {
		return contactinformation;
	}

	public void setContactinformation(Contactinformation contactinformation) {
		this.contactinformation = contactinformation;
	}

	public List<Gallery> getGallery() {
		return gallery;
	}

	public void setGallery(List<Gallery> gallery) {
		this.gallery = gallery;
	}

	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}


}
