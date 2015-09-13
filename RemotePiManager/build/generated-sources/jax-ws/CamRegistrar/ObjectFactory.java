
package CamRegistrar;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CamRegistrar package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetOnlineList_QNAME = new QName("http://RPITechRegistrar/", "getOnlineList");
    private final static QName _GetRegisteredPiList_QNAME = new QName("http://RPITechRegistrar/", "getRegisteredPiList");
    private final static QName _LoginResponse_QNAME = new QName("http://RPITechRegistrar/", "loginResponse");
    private final static QName _GetOnlineListResponse_QNAME = new QName("http://RPITechRegistrar/", "getOnlineListResponse");
    private final static QName _Retire_QNAME = new QName("http://RPITechRegistrar/", "retire");
    private final static QName _PingAliveResponse_QNAME = new QName("http://RPITechRegistrar/", "pingAliveResponse");
    private final static QName _RegisterResponse_QNAME = new QName("http://RPITechRegistrar/", "registerResponse");
    private final static QName _PingAlive_QNAME = new QName("http://RPITechRegistrar/", "pingAlive");
    private final static QName _RetireResponse_QNAME = new QName("http://RPITechRegistrar/", "retireResponse");
    private final static QName _Login_QNAME = new QName("http://RPITechRegistrar/", "login");
    private final static QName _GetRegisteredPiListResponse_QNAME = new QName("http://RPITechRegistrar/", "getRegisteredPiListResponse");
    private final static QName _Register_QNAME = new QName("http://RPITechRegistrar/", "register");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CamRegistrar
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOnlineList }
     * 
     */
    public GetOnlineList createGetOnlineList() {
        return new GetOnlineList();
    }

    /**
     * Create an instance of {@link GetRegisteredPiList }
     * 
     */
    public GetRegisteredPiList createGetRegisteredPiList() {
        return new GetRegisteredPiList();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link PingAliveResponse }
     * 
     */
    public PingAliveResponse createPingAliveResponse() {
        return new PingAliveResponse();
    }

    /**
     * Create an instance of {@link GetOnlineListResponse }
     * 
     */
    public GetOnlineListResponse createGetOnlineListResponse() {
        return new GetOnlineListResponse();
    }

    /**
     * Create an instance of {@link Retire }
     * 
     */
    public Retire createRetire() {
        return new Retire();
    }

    /**
     * Create an instance of {@link RegisterResponse }
     * 
     */
    public RegisterResponse createRegisterResponse() {
        return new RegisterResponse();
    }

    /**
     * Create an instance of {@link PingAlive }
     * 
     */
    public PingAlive createPingAlive() {
        return new PingAlive();
    }

    /**
     * Create an instance of {@link RetireResponse }
     * 
     */
    public RetireResponse createRetireResponse() {
        return new RetireResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetRegisteredPiListResponse }
     * 
     */
    public GetRegisteredPiListResponse createGetRegisteredPiListResponse() {
        return new GetRegisteredPiListResponse();
    }

    /**
     * Create an instance of {@link Register }
     * 
     */
    public Register createRegister() {
        return new Register();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOnlineList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "getOnlineList")
    public JAXBElement<GetOnlineList> createGetOnlineList(GetOnlineList value) {
        return new JAXBElement<GetOnlineList>(_GetOnlineList_QNAME, GetOnlineList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRegisteredPiList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "getRegisteredPiList")
    public JAXBElement<GetRegisteredPiList> createGetRegisteredPiList(GetRegisteredPiList value) {
        return new JAXBElement<GetRegisteredPiList>(_GetRegisteredPiList_QNAME, GetRegisteredPiList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOnlineListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "getOnlineListResponse")
    public JAXBElement<GetOnlineListResponse> createGetOnlineListResponse(GetOnlineListResponse value) {
        return new JAXBElement<GetOnlineListResponse>(_GetOnlineListResponse_QNAME, GetOnlineListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Retire }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "retire")
    public JAXBElement<Retire> createRetire(Retire value) {
        return new JAXBElement<Retire>(_Retire_QNAME, Retire.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingAliveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "pingAliveResponse")
    public JAXBElement<PingAliveResponse> createPingAliveResponse(PingAliveResponse value) {
        return new JAXBElement<PingAliveResponse>(_PingAliveResponse_QNAME, PingAliveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "registerResponse")
    public JAXBElement<RegisterResponse> createRegisterResponse(RegisterResponse value) {
        return new JAXBElement<RegisterResponse>(_RegisterResponse_QNAME, RegisterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingAlive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "pingAlive")
    public JAXBElement<PingAlive> createPingAlive(PingAlive value) {
        return new JAXBElement<PingAlive>(_PingAlive_QNAME, PingAlive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetireResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "retireResponse")
    public JAXBElement<RetireResponse> createRetireResponse(RetireResponse value) {
        return new JAXBElement<RetireResponse>(_RetireResponse_QNAME, RetireResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRegisteredPiListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "getRegisteredPiListResponse")
    public JAXBElement<GetRegisteredPiListResponse> createGetRegisteredPiListResponse(GetRegisteredPiListResponse value) {
        return new JAXBElement<GetRegisteredPiListResponse>(_GetRegisteredPiListResponse_QNAME, GetRegisteredPiListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Register }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://RPITechRegistrar/", name = "register")
    public JAXBElement<Register> createRegister(Register value) {
        return new JAXBElement<Register>(_Register_QNAME, Register.class, null, value);
    }

}
