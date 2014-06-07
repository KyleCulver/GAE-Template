/**
 * 
 */
package com.humana.comp.DTO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

/**
 * @author kyle
 *
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class InputObj {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private java.lang.Long TmId;

	@Persistent
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
	
}
