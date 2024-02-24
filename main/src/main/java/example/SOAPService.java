package example;

import example.util.XMLConverter;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.XmlElement;

@Service
public class SOAPService {

    public String convertTOXML(){
        AmolePay amolePay = new AmolePay("251911999999", "999", "01", "123.45", "123", "Testing", "12345");
        Body body = new Body(amolePay);
        Header header = new Header("qwertyuiop", "172.10.155.12", "eyob", "eyob");

        Envelope envelope = new Envelope(header, body);
        String soap = XMLConverter.jaxbObj2XML(envelope, Envelope.class);
        System.out.println(soap);
        return soap;
    }
}
