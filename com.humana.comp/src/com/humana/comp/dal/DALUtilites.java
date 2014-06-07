/**
 * 
 */
package com.humana.comp.dal;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author kyle
 *
 */
public class DALUtilites {

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private static DALUtilites instance = null;

	protected DALUtilites() {
		// Exists only to defeat instantiation.
	}

	public static DALUtilites getInstance() {
		if (instance == null) {
			instance = new DALUtilites();
		}
		return instance;
	}

	public static final PersistenceManager GetPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
