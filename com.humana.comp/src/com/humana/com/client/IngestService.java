/**
 * 
 */
package com.humana.com.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author kyle
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("ingest")
public interface IngestService extends RemoteService  {
	String[] getStaticIngestTypes();
	String ingestData(String data, String ingestType, String agencyName);
	
	
}