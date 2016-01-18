package mx.inteligov.login.pojo;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "_response", "_responseMsg", "_responseCode" })
public class ResponseLogin {
	public final static String OK_RESPONSE  = "OK";
	public final  static String BAD_RESPONSE = "BAD"; 
	
	private String _response;
	private String _responseMsg;
	private String _responseCode;
	
	public ResponseLogin(){}
	
	public ResponseLogin(String _response, String _responseMsg, String _responseCode) {
		super();
		this._response = _response;
		this._responseMsg = _responseMsg;
		this._responseCode = _responseCode;
	}
	public String get_response() {
		return _response;
	}
	public void set_response(String _response) {
		this._response = _response;
	}
	public String get_responseMsg() {
		return _responseMsg;
	}
	public void set_responseMsg(String _responseMsg) {
		this._responseMsg = _responseMsg;
	}
	public String get_responseCode() {
		return _responseCode;
	}
	public void set_responseCode(String _responseCode) {
		this._responseCode = _responseCode;
	}
	
	
}
