package edu.stevens.cs548.clinic.billing.jms;

import java.util.Random;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.billing.domain.BillingRecordDAO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "jms/clinic/Treatment"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		}, 
		mappedName = "jms/clinic/Treatment")
public class TreatmentListener implements MessageListener {

    /**
     * Default constructor. 
     */
    public TreatmentListener() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext(unitName="ClinicDomain")
    private EntityManager em;
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        ObjectMessage objMessage = (ObjectMessage)message;
        try{
        	TreatmentDto treatment = (TreatmentDto)objMessage.getObject();	
        	BillingRecord br = new BillingRecord();
        	Random generator = new Random();
        	float amount = generator.nextFloat() * 500;
        	br.setId(treatment.getId());
        	br.setAmount(amount);
        	br.setDescription(treatment.getDiagnosis());
        	BillingRecordDAO tbd = new BillingRecordDAO(em);
        	tbd.addBillingRecord(br);
        }catch(JMSException e){
        	//Log the exception
        }
        
    }

}
