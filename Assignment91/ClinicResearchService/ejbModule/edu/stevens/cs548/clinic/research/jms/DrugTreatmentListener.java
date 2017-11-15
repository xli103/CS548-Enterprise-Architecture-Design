package edu.stevens.cs548.clinic.research.jms;

import javax.ejb.ActivationConfigProperty;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Message-Driven Bean implementation class for: DrugTreatmentListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "treatmentType='Drug'")
		})
public class DrugTreatmentListener implements MessageListener {

    /**
     * Default constructor. 
     */
    public DrugTreatmentListener() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
    	ObjectMessage objMessage = (ObjectMessage)message;
    	try{
    		TreatmentDto treatment = (TreatmentDto)objMessage.getObject();
    		if(treatment.getDrugTreatmentType()==null){
    			//Log the error
    		}
    		else{
    			DrugTreatmentRecord dtr = new DrugTreatmentRecord();
    			DrugTreatmentType dtt = new DrugTreatmentType();
    			dtt = treatment.getDrugTreatmentType();
    			dtr.setId(treatment.getId());
    			dtr.setDrugName(dtt.getName());
    			dtr.setDosage(dtt.getDosage());
    		}
    		
    		
    	} catch(JMSException e){
    		//Log the error
    	}
        
    }

}
