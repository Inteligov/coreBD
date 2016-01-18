package mx.inteligov.login.pojo;

import java.io.Serializable;
 
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String password;
	
	public User(){}
	
	public User(String usuario, String password) {
		super();
		this.usuario = usuario;
		this.password = password;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [usuario=" + usuario + ", password=" + password + "]";
	}
	
	

}
