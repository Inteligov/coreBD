package mx.inteligov.login.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.inteligov.login.pojo.ResponseLogin;
import mx.inteligov.login.pojo.User;
import mx.inteligov.login.service.LDAPConnection;
import mx.inteligov.login.service.LoginService;

@Controller
@RequestMapping("/auth")
public class AuthenticationController{

	@Autowired
	private LoginService loginService ;
	
	@Autowired
	private LDAPConnection ldapConnection;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseLogin correctLogin (@RequestBody User usuario){
		System.out.println("llama al metodo "+ usuario.toString());
		ResponseLogin _response = new ResponseLogin();
		_response = loginService.autenticacion(usuario.getUsuario(), usuario.getPassword());
		return _response;
		
	}
	
	@RequestMapping(value = "/loginLDAP", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseLogin loginLDAP (@RequestBody User usuario){
		System.out.println("llama al metodo loginLDAP "+ usuario.toString());
		ResponseLogin _response = new ResponseLogin();
		if(!ldapConnection.correctLogin(usuario.getUsuario(), usuario.getPassword())){
			_response.set_responseMsg("Login Incorrecto");
			_response.set_responseCode(ResponseLogin.BAD_RESPONSE);
		}
		else{
			_response.set_response("Login correcto");
			_response.set_responseCode(ResponseLogin.OK_RESPONSE);
		}
		return _response;
		
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody ResponseLogin test(){
		ResponseLogin _response = new ResponseLogin();
		_response.set_response("Conexion Stisfactoria");
		_response.set_responseCode(ResponseLogin.OK_RESPONSE);
		return _response;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody User getUser(){
		User usuario = new User();
		usuario.setUsuario("alopez@inteligov.mx");
		usuario.setPassword("2015");
		
		return usuario;
	}
	
	@RequestMapping(value = "/getDataLDAP", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody ResponseLogin getDataLDAP(){
		ResponseLogin _response = new ResponseLogin();
		_response.set_responseMsg(ldapConnection.getDataLdap());
		_response.set_response("Conexion Stisfactoria");
		_response.set_responseCode(ResponseLogin.OK_RESPONSE);
		return _response;
		
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/connectToLDAP", method = RequestMethod.GET,produces="application/json")
	public @ResponseBody ResponseLogin connectToLDAP(){
		ResponseLogin _response = new ResponseLogin();
		
		if(ldapConnection.connectToLDAP() ==null){
			_response.set_responseMsg("Error en la conexion LDAP");
			_response.set_responseCode(ResponseLogin.BAD_RESPONSE);
		}
		else{
			_response.set_response("Conexion Stisfactoria");
			_response.set_responseCode(ResponseLogin.OK_RESPONSE);
		}
		return _response;
		
	}
	
}
  