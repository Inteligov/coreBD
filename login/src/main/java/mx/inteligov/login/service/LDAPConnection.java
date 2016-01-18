package mx.inteligov.login.service;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service;

/*
 * Servicio que conecta LDAP
 * Ejemplo 
 * search("DC=dominio_general,OU=org", "userType=Admin", {"username", "mail"});
 * 
 *
 */
@Service("ldapConnection")
public class LDAPConnection {

	//@Value("${ldap.url}")
	private static  String URL = "ldap://172.16.6.1:389" ;
	
	//@Value("${ldap.admin}")
	private static  String ADMIN = "CN=Acceso Gestión Documental,OU=Usuarios de Servicios,OU=Insurgentes,DC=netadm,DC=gob,DC=mx";
	
	//@Value("${ldap.password}")
	private static  String PASS ="C0nacyt1970";
	
	private static String DOMINIO ="@netadm.gob.mx";
	
	
	public String getDataLdap(){
		
		return "LDAP URL= " + URL+ ", ADMIN= "+ ADMIN + " PASS = " +PASS;
	}
	
	
	public boolean correctLogin(String username, String password) { 
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, URL);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL,username+DOMINIO);
			env.put(Context.SECURITY_CREDENTIALS, password);

			// Conseguimos contexto de conexion
			DirContext ctx = new InitialDirContext(env);
			System.out.println("LA AUTENTICACION SE REALIZAO CORRECTAMENTE ANTE EL LDAP!");
			ctx.close();
			return true;
		} catch (NamingException e) {
			System.out.println("Error login " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public static DirContext connectToLDAP() {
		
		System.out.println("LDAP URL= " + URL+ ", Admin= "+ ADMIN + " Pass = " +PASS );
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(LdapContext.CONTROL_FACTORIES,"com.sun.jndi.ldap.ControlFactory");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, URL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ADMIN);
		env.put(Context.SECURITY_CREDENTIALS, PASS);
		env.put(Context.STATE_FACTORIES, "PersonStateFactory");
		env.put(Context.OBJECT_FACTORIES,"PersonObjectFactory");
		// Conseguimos contexto de conexion
		DirContext ctx;
		try {
			ctx = new InitialDirContext(env);
			System.out.println("Conexion LDAP exitosa");
			return ctx;
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("Conexion LDAP error: "+ e.getMessage());
			return null;
		}
		
	}

	

	@SuppressWarnings("rawtypes")
	public void search(String username) throws NamingException {
		// Recordar que el username tiene que tener toda la ruta de OUs/DCs/CNs
		DirContext context = connectToLDAP();
		SearchControls ctls = new SearchControls();
		ctls.setReturningObjFlag(true); // Para que devuelva los elementos que
										// buscamos

		String returning[] = new String[2];
		returning[0] = "field1";
		returning[1] = "fields2";
		// Asignamos los atributos a devolver
		ctls.setReturningAttributes(returning);
		ctls.setSearchScope(SearchControls.OBJECT_SCOPE);

		String search = username; // Search es "en donde buscar" de los
									// directorios del servidor
		String filter = "name=" + username; // filtro trivial

		NamingEnumeration answer = context.search(search, filter, ctls);
		SearchResult result = (SearchResult) answer.next(); // Sabemos que habra
															// un solo resultado
		String field1 = result.getAttributes().get("field1").get().toString();
		String field2 = result.getAttributes().get("field2").get().toString();
		System.out.println(username + ": " + field1 + ", " + field2);
	}

	public void search(String search, String filter, String[] returningAtts) throws NamingException {
		DirContext context = connectToLDAP();
		SearchControls ctls = new SearchControls();
		ctls.setReturningObjFlag(true); // Para que devuelva los elementos que
										// buscamos

		// Asignamos los atributos a devolver
		ctls.setReturningAttributes(returningAtts);
		ctls.setSearchScope(SearchControls.OBJECT_SCOPE);

		NamingEnumeration<SearchResult> answer = context.search(search, filter, ctls);
		
		 while (answer.hasMore()){ 
		 
		SearchResult entry = (SearchResult) answer.next();
	      System.out.println(entry.getName());
	      }
		 
	}

	public static void closeConnectionToLDAP(DirContext ctx) {
		try {
			ctx.close();
		} catch (NamingException e) {
			// No se habia podido conectar, ya se habia cerrado la conexion,
			// etc..
			e.printStackTrace();
		}
	}

	
	@Override
	public String toString(){
		return "LDAP URL= " + URL+ ", Admin= "+ ADMIN + " Pass = " +PASS ;
		
	}
}
