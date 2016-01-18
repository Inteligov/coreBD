package mx.inteligov.login.service;

import org.springframework.stereotype.Service;

import mx.inteligov.login.pojo.ResponseLogin;

@Service("loginService")
public class LoginService {
	
	public ResponseLogin autenticacion(String usuario,String password){
		ResponseLogin response = new ResponseLogin();
		if(usuario.equals("itzel@inteligov.mx")){
			if(password.equals("2015")){
				response.set_responseMsg("Autentificacion exitosa");
				response.set_responseCode(ResponseLogin.OK_RESPONSE);
				System.out.println("Autentificacion exitosa");
			}else{
				response.set_responseCode(ResponseLogin.BAD_RESPONSE);
				response.set_responseMsg("El password es incorrecto");
			}
		}else{
			response.set_responseCode(ResponseLogin.BAD_RESPONSE);
			response.set_responseMsg("El usuario es incorrecto");
		}
		return response;
	}

}
