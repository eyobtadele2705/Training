package example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "soapenv:Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/, urn:examples:helloservice")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@NoArgsConstructor
public class Envelope {

    @XmlElement(name = "soapenv:Header")
    private Header header;

    @XmlElement(name = "soapenv:Body")
    private Body body;

    public Envelope(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

// Getters and setters
}

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
class Header {

    @XmlElement(name = "HDR_Signature")
    private String signature;

    @XmlElement(name = "HDR_IPAddress")
    private String ipAddress;

    @XmlElement(name = "HDR_UserName")
    private String userName;

    @XmlElement(name = "HDR_Password")
    private String password;

    public Header(String signature, String ipAddress, String userName, String password) {
        this.signature = signature;
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
    }
// Getters and setters
}

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@RequiredArgsConstructor
class Body {

    @XmlElement(name = "amolePay")
    private AmolePay amolePay;

    public Body(AmolePay amolePay) {
        this.amolePay = amolePay;
    }
    // Getters and setters
}

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
class AmolePay {

    @XmlElement(name = "BODY_CardNumber")
    private String cardNumber;

    @XmlElement(name = "BODY_ExpirationDate")
    private String expirationDate;

    @XmlElement(name = "BODY_PIN")
    private String pin;

    @XmlElement(name = "BODY_PaymentAction")
    private String paymentAction;

    @XmlElement(name = "BODY_AmountX")
    private String amountX;

    @XmlElement(name = "BODY_MerchantID")
    private String merchantID;

    @XmlElement(name = "BODY_OrderDescription")
    private String orderDescription;

    @XmlElement(name = "BODY_SourceTransID")
    private String sourceTransID;

    @XmlElement(name = "BODY_VendorAccount")
    private String vendorAccount;

    @XmlElement(name = "BODY_AdditionalInfo1")
    private String additionalInfo1;

    @XmlElement(name = "BODY_AdditionalInfo2")
    private String additionalInfo2;

    @XmlElement(name = "BODY_AdditionalInfo3")
    private String additionalInfo3;

    @XmlElement(name = "BODY_AdditionalInfo4")
    private String additionalInfo4;

    @XmlElement(name = "BODY_AdditionalInfo5")
    private String additionalInfo5;

    public AmolePay(String cardNumber, String pin,String paymentAction, String amountX, String merchantID, String orderDescription, String sourceTransID) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.paymentAction = paymentAction;
        this.amountX = amountX;
        this.merchantID = merchantID;
        this.orderDescription = orderDescription;
        this.sourceTransID = sourceTransID;
    }

    // Getters and setters
}
