package com.ipartek.servicios;

import java.util.List;

import com.ipartek.pojos.Usuario;

public interface UsuarioServicio {
	
	public String validarUsuario(String usu, String pas); //no necesita el token
	public Usuario bloquearUsuario(Usuario usu, String jwtToken);
	
	public Boolean guardarUsuario(Usuario usu, String jwtToken);
	public List<Usuario> obtenerTodosUsuarios(String jwtToken);
	public Boolean borrarUsuario(Integer id, String jwtToken);
	public Usuario obtenerUsuarioPorId(Integer id, String jwtToken) ;
	public Usuario modificarUsuario(Usuario obj_usuario, String jwtToken);
}
