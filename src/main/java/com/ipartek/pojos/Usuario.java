package com.ipartek.pojos;

import com.ipartek.auxiliar.Auxiliar;

public class Usuario {
	
	private int id;
	private String user;
	private String pass;
	private String salt;
	private Rol rol;

	public Usuario(int id, String user, String pass, String salt, Rol rol) {
		super();
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.salt = salt;
		this.rol = rol;
	}
	
	public Usuario() { 
		super();
		this.id = 0;
		this.user = "";
		this.pass = "";
		this.salt = Auxiliar.generarHexRandom(16);
		this.rol = new Rol();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", pass=" + pass + ", salt=" + salt + ", rol=" + rol + "]";
	}
	
	
	

}
