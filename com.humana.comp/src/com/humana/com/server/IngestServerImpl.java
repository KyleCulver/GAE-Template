package com.humana.com.server;

import com.humana.com.client.IngestService;
import com.humana.comp.bll.AllBizLogic;
import com.humana.comp.dal.DalServiceNoSqlImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class IngestServerImpl extends RemoteServiceServlet implements IngestService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String[] getStaticIngestTypes() {
		// TODO Auto-generated method stub
		return AllBizLogic.GetStaticIngestTypes();
	}

	@Override
	public String ingestData(String data, String ingestType, String agencyName) {
		//"Agency","Calendar","Calendar Dates","Routes","Shapes","Stops","Stop Times","Trips"
				String output = null;
				
				DalServiceNoSqlImpl dalService =  new DalServiceNoSqlImpl();
				AllBizLogic bll = new AllBizLogic(dalService);
				
				switch (ingestType) {
				case AllBizLogic.FILE_TYPE_UNO:
					output = bll.PersistCollectionOfObjs(data);
					break;

				default:
					output = "Ingest Data Failed - Ingest Type not implemented";
					break;
				}
				return output;
	}

}
