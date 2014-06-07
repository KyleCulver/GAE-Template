/**
 * 
 */
package com.humana.comp.dal;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.humana.comp.DTO.InputObj;
import com.humana.comp.DTO.RequestObj;

/**
 * @author kyle
 *
 */
public class DalServiceNoSqlImpl implements DalService {

	private static final Logger LOG = Logger.getLogger(DalServiceNoSqlImpl.class
			.getName());
	
	/* (non-Javadoc)
	 * @see com.humana.comp.dal.DalService#IngestData(javax.jdo.PersistenceManager, com.humana.comp.DTO.InputObj)
	 */
	@Override
	public String IngestData(PersistenceManager pm, InputObj obj) {
		StringBuilder sbOutput = new StringBuilder();
		String temp;
		try {
			LOG.log(Level.FINE, "Begin Persist for Agency -" + obj.toString());
			
			InputObj savedObj = GetData(pm, obj.getKey());
			if(savedObj==null){
				pm.makePersistent(obj);
				temp = "Insert "+DalServiceNoSqlImpl.class.getName()+" Successful for -" + obj.toString();
				sbOutput.append(temp);
				LOG.log(Level.INFO, temp);
			}
			else{
				temp = "Obj Already exists. Updated not implemented: -"
						+ savedObj.toString();
				sbOutput.append(temp+System.lineSeparator());
				LOG.log(Level.SEVERE,temp);
			}
			
		} catch (Exception e) {
			temp = " Exception Occurred IngestData "+InputObj.class.getName()+": -"+
					 obj.toString() + ". Exception:"+e.getMessage();
			sbOutput.append(temp+System.lineSeparator());
			
			LOG.log(Level.SEVERE, temp);
		}
		return sbOutput.toString();
	}

	/* (non-Javadoc)
	 * @see com.humana.comp.dal.DalService#GetData(javax.jdo.PersistenceManager, com.humana.comp.DTO.OutputObj)
	 */
	@Override
	public InputObj GetData(PersistenceManager pm, RequestObj request) {
		
		return GetData(pm,request.getKey());
	}
	
	@SuppressWarnings("unchecked")
	public InputObj GetData(PersistenceManager pm, String cacheKey) {
		InputObj obj = null;
		try {
			
			//check cache
			//String cacheKey = RequestObj.class.getName()+"_"+request.getKey();
		    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		    obj = (InputObj)syncCache.get(cacheKey);
		    if(obj!=null){
		    	return obj;
		    }
			
			Query qry = pm.newQuery(InputObj.class);
			qry.setFilter("key == NameParam");
			qry.declareParameters("String NameParam");

			List<InputObj> lstObj = (List<InputObj>) qry.execute(cacheKey);
			if (!lstObj.isEmpty()) {
				// check for duplicate Urls
				if (lstObj.size() == 1) {
					obj = lstObj.get(0);
				} else {
					// Duplicates
					LOG.log(Level.SEVERE,
							"Exception occurred Multiple Agencies with Same Cache Key: -"
									+ cacheKey);
				}

			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE,
					"Exception occurred in Get Data By Key for "+InputObj.class.getName()+"- GetData Error:"
							+ e.getMessage());
		} 

		return obj;
	}

}
