/**
 * 
 */
package com.humana.comp.bll;

//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.humana.comp.DTO.InputObj;
import com.humana.comp.DTO.OutputObj;
import com.humana.comp.DTO.RequestObj;
import com.humana.comp.dal.DALUtilites;
import com.humana.comp.dal.DalService;
/**
 * @author kyle
 *
 */
public class AllBizLogic {

	private static final Logger LOG = Logger.getLogger(AllBizLogic.class.getName());
	
    private DalService _dalService;
	public final static String FILE_TYPE_UNO = "Data File Uno";
	public final static String FILE_TYPE_DOS = "Data File Dos";
	public final static String FILE_TYPE_TRES = "Data File Tres";
	
	public AllBizLogic(DalService dalService){
		_dalService = dalService;
	}
	
	public static String[] GetStaticIngestTypes() {
		LOG.log(Level.FINE, "Begin AllBizLogic.GetStaticIngestTypes ");
		
		String[] arrIngestTypes = new String[]{FILE_TYPE_UNO,FILE_TYPE_DOS,FILE_TYPE_TRES};
		
		LOG.log(Level.FINE, "End AllBizLogic.GetStaticIngestTypes ");
		
		return arrIngestTypes;
	}
	
	public String PersistCollectionOfObjs(String data){
		 	StringBuilder sbOutput = new StringBuilder();
	        sbOutput.append("Ingest "+InputObj.class.getName()+" Started");
	        sbOutput.append("<BR/>");
			
	        PersistenceManager pm = null;
	        
			try {
				pm = DALUtilites.GetPersistenceManager();
				
				String[] inputLines = data.split(System.lineSeparator());
				//skip first line header
		    	for (int i = 0; i < inputLines.length; i++) {
			    	
			    	sbOutput.append("InputObj Count:"+ inputLines.length);
			    	sbOutput.append("<BR/>");
					
					String temp = inputLines[i];
			    	String[] arrAgencyAttr = temp.split(",");
			    		
		    		//All Fields are required
		    		//TODO validation?
		    		InputObj inputObj = new InputObj();
		    		inputObj.setKey(arrAgencyAttr[0]);
		    			
		    		sbOutput.append("InputObject Added:"+ inputObj.toString());
					sbOutput.append("<BR/>");
					
					sbOutput.append(_dalService.IngestData(pm, inputObj));
		
				}
		    	
		    	pm.flush();
				pm.close();
			} catch (Exception e) {
				sbOutput.append("Exception Occurred:"+ e.getMessage());
				sbOutput.append(System.lineSeparator());
				
			}
			finally{
				if(pm!=null){
					if(!pm.isClosed()){
						pm.flush();
						pm.close();
					}
				}
			}
		    
			return sbOutput.toString(); 
	}
	
	public OutputObj GetObject(RequestObj request){
		
		PersistenceManager pm = null;
		OutputObj output = null;
		
		StringBuilder sbOutput = new StringBuilder();
        sbOutput.append("Get Object");
        sbOutput.append("<BR/>");
		
		try {
			
		
			pm = DALUtilites.GetPersistenceManager();
			InputObj inputObj =  _dalService.GetData(pm, request);
			output = mapInputToOutput(inputObj);
			pm.flush();
			pm.close();
		} catch (Exception e) {
			sbOutput.append("Exception Occurred:"+ e.getMessage());
			sbOutput.append(System.lineSeparator());
			
		}
		finally{
			if(pm!=null){
				if(!pm.isClosed()){
					pm.flush();
					pm.close();
				}
			}
		}
		return output;
	}
	
	//BEGIN MAPPING FUNCTIONALITY
	
	private OutputObj mapInputToOutput(InputObj inputObj){
		
		OutputObj output = new OutputObj();
		output.setKey("Output:" + inputObj.getKey());
		
		return output;
	}
	
}
