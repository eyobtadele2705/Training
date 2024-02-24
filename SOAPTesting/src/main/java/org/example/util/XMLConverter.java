package org.example.util;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLConverter {

    // THIS METHOD CONVERTS OBJECT TO XML FILE

    public static String jaxbObj2XML(Object o, Class aClass){
        String xmlRequest = null;
        Marshaller marshaller = Misc.getMarshaller(aClass);
        try {
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(o, stringWriter);
            xmlRequest = stringWriter.toString();
        } catch (PropertyException e) {
            e.printStackTrace();
            System.out.println(XMLConverter.class + " - jaxbObj2XML | PropertyException: "+ e.getMessage());
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println(XMLConverter.class + " - jaxbObj2XML | JAXBException: "+ e.getMessage());
        }
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xmlRequest;
    }

    // THIS METHOD CONVERTS XML TO OBJECT

    public static <T> T jaxbXML2Obj(String xmlstring, Class<T> aClass){

        Object obj = null;
        Unmarshaller unmarshaller = new Misc().getUnMarshaller(aClass);
        try {
            obj = unmarshaller.unmarshal(new StringReader(xmlstring));
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println(XMLConverter.class + " - jaxbXML2Obj | JAXBException: "+ e.getMessage());
        }
        return (T) obj;
    }
}
