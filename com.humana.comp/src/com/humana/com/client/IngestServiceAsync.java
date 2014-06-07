/**
 * 
 */
package com.humana.com.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author kyle
 * The async counterpart of <code>IngestService</code>.
 */
public interface IngestServiceAsync {
	void getStaticIngestTypes(AsyncCallback<String[]> callback);
	void ingestData(String data, String ingestType, String agencyName, AsyncCallback<String> callback);
}
