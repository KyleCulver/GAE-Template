package com.humana.com.client;

import com.humana.com.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Com_humana_comp implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	//private final GreetingServiceAsync greetingService = GWT
	//		.create(GreetingService.class);
	private final IngestServiceAsync ingestService = GWT.create(IngestService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		
		final Button ingestButton = new Button("Ingest");
		final TextArea dataBox = new TextArea();
		dataBox.setText("Paste Data here");
		
		dataBox.setWidth("600px");
		dataBox.setVisibleLines(20);
		
		final Label errorLabel = new Label();
		final ListBox lstIngestTypes = new ListBox();
		
		// We can add style names to widgets
		ingestButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		VerticalPanel vpAll = new VerticalPanel();
		vpAll.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		
		
		Label lblFileLstNotify = new Label();
		lblFileLstNotify.setText("Select Data Type");
		
		HorizontalPanel hpFileTypes = new HorizontalPanel();
		hpFileTypes.add(lblFileLstNotify);
		hpFileTypes.add(lstIngestTypes);
		
		
		
		vpAll.add(hpFileTypes);
		vpAll.add(ingestButton);
		//vpAll.add(errorLabel);
		vpAll.add(dataBox);
		//RootPanel.get().add(vpAll);
		RootPanel.get("nameFieldContainer").add(vpAll);
		
		//RootPanel.get("nameFieldContainer").add(dataBox);
		//RootPanel.get("sendButtonContainer").add(ingestButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		dataBox.setFocus(true);
		dataBox.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		//dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				ingestButton.setEnabled(true);
				//ingestButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				
				String textToServer = dataBox.getText();
				int indexOfSelected =  lstIngestTypes.getSelectedIndex();
				String ingestType = lstIngestTypes.getItemText(indexOfSelected);
				
				
				
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				ingestButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				
				
				
				ingestService.ingestData(textToServer,ingestType,"Parent Not Needed",
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		ingestButton.addClickHandler(handler);
		dataBox.addKeyUpHandler(handler);
		
		
		//populate the listbox
		ingestService.getStaticIngestTypes(
				new AsyncCallback<String[]>(){
					public void onFailure(Throwable caught) {
					// Show the RPC error message to the user
					dialogBox
							.setText("Remote Procedure Call - Failure");
					serverResponseLabel
							.addStyleName("serverResponseLabelError");
					serverResponseLabel.setHTML(SERVER_ERROR);
					dialogBox.center();
					closeButton.setFocus(true);
				}

				
				@Override
				public void onSuccess(String[] result) {
					// TODO Auto-generated method stub
					for (int i = 0; i < result.length; i++) {
						lstIngestTypes.addItem(result[i]);
					}
				}
				});
		
	
	}

	
}
