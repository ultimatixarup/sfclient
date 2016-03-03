package com.topera.sfclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.mysql.jdbc.StringUtils;
import com.sforce.soap.enterprise.InvalidIdFault;
import com.sforce.soap.enterprise.LoginFault;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.SforceService;
import com.sforce.soap.enterprise.Soap;
import com.sforce.soap.enterprise.UnexpectedErrorFault;
import com.sforce.soap.schemas._class.getprocedureid.GetProcedureIdPortType;
import com.sforce.soap.schemas._class.getprocedureid.GetProcedureIdService;
import com.sforce.soap.schemas._class.getprocedureid.ProcedureC;
import com.sforce.soap.schemas._class.getprocedureid.SessionHeader;
import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

public class SFClient {

	public static void main(String args[]) throws InvalidIdFault, LoginFault, UnexpectedErrorFault, JAXBException {

		SforceService service = new SforceService();

		Soap port = service.getSoap();

		LoginResult loginResult = port.login("raghu@mtsiinc.com.aep", "Abbott123hTl2uCqkXvrqqP8RZVJsRmGy0");

		System.out.println("SFClient.main()" + loginResult.getSessionId());

		List<String> workstations = DBOperation.getWorkstations();
		for (String workstation : workstations) {

			System.out.println("SFClient.main()workstation==" + workstation);
			GetProcedureIdService getprocservice = new GetProcedureIdService();

			SessionHeader sessionHeader = new SessionHeader();
			sessionHeader.setSessionId(loginResult.getSessionId());

			SessionHeader sh = new SessionHeader();
			sh.setSessionId(loginResult.getSessionId());

			GetProcedureIdPortType getprocport = getprocservice.getGetProcedureId();
			JAXBContext jc = JAXBContext.newInstance("com.sforce.soap.schemas._class.getprocedureid");

			((WSBindingProvider) getprocport).setOutboundHeaders(Headers.create((JAXBRIContext) jc, sh));
			String date = workstation.split("#")[1];
			SimpleDateFormat sfd = new SimpleDateFormat("mm/dd/yyyy");
			

			List<ProcedureC> proclist = getprocport.getprocids("", workstation.split("#")[0], "",
					workstation.split("#")[1], "");
			System.out.println("SFClient.main()"+proclist);
			if(proclist.size()>0)
			System.out.println("SFClient.main()" + proclist.get(0).getName().getValue());

			if (proclist.size() > 0) {
				System.out.println("SFClient.main()" + proclist.get(0).getName().getValue());

				DBOperation.updateWorkStation(workstation.split("#")[0], proclist.get(0).getName().getValue());
			}
			//break;

		}

	}

}
