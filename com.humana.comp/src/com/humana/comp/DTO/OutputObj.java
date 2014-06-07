package com.humana.comp.DTO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author kyle
 *
 */
@XmlRootElement(name = "item")
public class OutputObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	
	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
