package utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MyXMLReader {
	public void personalInfToXMLExample(PersonalInformation personInf) throws Exception {  
	    File file = new File("config.xml");
	    JAXBContext jaxbContext = JAXBContext.newInstance(PersonalInformation.class);

	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(personInf, file);
	    jaxbMarshaller.marshal(personInf, System.out);
	}
	 
	public PersonalInformation XMLtoPersonInf() throws Exception {
       File file = new File("config.xml");
       JAXBContext jaxbContext = JAXBContext.newInstance(PersonalInformation.class);
       Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
       
       return (PersonalInformation) jaxbUnmarshaller.unmarshal(file);
   }
}