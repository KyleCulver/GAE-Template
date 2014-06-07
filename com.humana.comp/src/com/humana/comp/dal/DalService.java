package com.humana.comp.dal;

import javax.jdo.PersistenceManager;

import com.humana.comp.DTO.InputObj;
import com.humana.comp.DTO.RequestObj;

public interface DalService {

	String IngestData(PersistenceManager pm, InputObj obj);
	InputObj GetData(PersistenceManager pm, RequestObj request);
	
	
}
