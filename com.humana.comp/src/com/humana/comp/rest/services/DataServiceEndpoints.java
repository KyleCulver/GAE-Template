/**
 * 
 */
package com.humana.comp.rest.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.humana.comp.DTO.OutputObj;
import com.humana.comp.DTO.RequestObj;
import com.humana.comp.bll.AllBizLogic;
import com.humana.comp.dal.DalServiceNoSqlImpl;

/**
 * @author kyle
 *
 */
@Path("data")
public class DataServiceEndpoints {

	private static final Logger LOG = Logger
			.getLogger(DataServiceEndpoints.class.getName());

	        // The Java method will process HTTP GET requests
			@GET
			// The Java method will produce content identified by the MIME Media
			// type "text/plain"
			@Produces("text/plain")
			@Path("/helloworld")
			public String getClichedMessage() {
				// Return some cliched textual content and clear cache
				//com.cseg.gaej.shared.Utilities.ClearMemcache();
				LOG.log(Level.INFO, "Services - getClichedMessage Called");
				return "Hello World";
			}
			
			@GET
			@Produces({ "application/xml", "application/json" })
			@Path("/fetch/{key}")
			public OutputObj getInputObject(@PathParam("key") String key) {
				
			    LOG.log(Level.INFO, "Services - getInputObject Started");
				
			    DalServiceNoSqlImpl _dalService = new DalServiceNoSqlImpl();
				AllBizLogic bll = new AllBizLogic(_dalService);
				RequestObj req = new RequestObj();
				req.setKey(key);
				OutputObj obj = bll.GetObject(req);
				
				LOG.log(Level.INFO, "Services - getInputObject Completed");
				
				return obj;
			}
			
//			@GET
//			@Produces("text/plain")
//			@Path("/outputObjs ")
//			public String getInputObjects() {
//				
//				LOG.log(Level.INFO, "Services - getInputObjects Started");
//				//output Route Names and RouteTmIds for that agency
//				DalServiceNoSqlImpl _dalService = new DalServiceNoSqlImpl();
//				AllBizLogic bll = new AllBizLogic(_dalService);
//				RequestObj req = new RequestObj();
//				req.setKey(key);
//				OutputObj obj = bll.GetObject(req);
//				
//				LOG.log(Level.INFO, "Services - getInputObjects Completed");
//				
//				return routes;
//			}
			
}
