package example.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.beans.JavaBean;


@JavaBean
public class Misc {

    public static Marshaller getMarshaller(Class aClass) {
        Marshaller m = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
            m = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println(Misc.class + " - getMarshaller | JAXBException: " + e.getMessage());
        }
        return m;
    }

    public Unmarshaller getUnMarshaller(Class aClass) {
        Unmarshaller unmarshaller = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println(Misc.class + " - getUnMarshaller | JAXBException: "+ e.getMessage());
        }
        return unmarshaller;
    }
}