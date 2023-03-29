package com.QuinchApp.Controladores;

import com.QuinchApp.Entidades.Usuario;
import com.QuinchApp.Servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "/registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password") String password2, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, @RequestParam("tipoUsuario") String tipo, ModelMap modelo) throws Exception {
        System.out.print(tipo);
        try {
            if (tipo.equalsIgnoreCase("cliente")) {
                usuarioServicio.registrar(nombre, nombreUsuario, email, password, password2, telefono, archivo);
            } else {
                usuarioServicio.registrarPropietario(nombre, nombreUsuario, email, password, password2, telefono, archivo);
            }
            modelo.put("exito", "El usuario fue registrado correctamente!");
        } catch (Exception exception) {
            System.out.println(exception);
            modelo.put("nombre", nombre);
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("archivo", archivo);
            modelo.put("error", "Verifique que los datos hayan sido cargado correctamente y el email no este registrado");
            return "registro";
        }
        return "registro";

    }

//    @PostMapping("/actualizar/{id}")
//    public String actualizar(@PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
//            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("telefono") long telefono,
//            @RequestParam("archivo") MultipartFile archivo) throws Exception {
//        try {
//            usuarioServicio.actualizar(id, nombre, nombreUsuario, email, password, telefono, archivo);
//            return "exito";
//        } catch (Exception exception) {
//            System.out.println(exception);
//            return "error";
//        }
//    }
//    @GetMapping("/listar")
//    public String listar(ModelMap modelo) {
//        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
//        modelo.addAttribute("usuario", usuarios);
//        return "usuarioList";
//    }
    @GetMapping("/borrar/{id}")
    public String borrarUsuario(@PathVariable Integer id, ModelMap modelo) throws Exception {
     modelo.put("usuario", usuarioServicio.getOne(id));
     return null;
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarUsuario(@PathVariable Integer id) throws Exception {
        try {
            usuarioServicio.borrar(id);
            return "Exito";
        } catch (Exception exception) {
            System.out.println(exception);
            return "Error";
        }
    }

    @GetMapping("/terminos")
    public String terminos() {
        return "terminos";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @GetMapping("/perfilModificar")
    public String perfilModificar(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "modiificarUsuario";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_ADMIN', 'ROLE_PROPIETARIO')")
    @PostMapping("/perfilModificar/{id}")
    public String actualizar(HttpSession session, @PathVariable int id, @RequestParam("nombre") String nombre, @RequestParam("nombreUsuario") String nombreUsuario,
            @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password2") String password2, @RequestParam("telefono") long telefono,
            @RequestParam("archivo") MultipartFile archivo, @RequestParam("tipoUsuario") String tipo, ModelMap modelo) {
        modelo.addAttribute("usuario", new Usuario());
        try {
            if (tipo.equalsIgnoreCase("cliente")) {
                usuarioServicio.actualizarCliente(id, nombre, nombreUsuario, email, password, password2, telefono, archivo);
            } else {
                usuarioServicio.actualizarPropietario(id, nombre, nombreUsuario, email, password, password2, telefono, archivo);
            }
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("exito", "Usuario actualizado correctamente!, vuelva a iniciar sesion para ver los cambios");
            return "modiificarUsuario";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("password", password);
            modelo.put("password2", password2);
            return "modiificarUsuario";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar")
    public String listarUsuario(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuario", usuarios);
        return "listadoUsuario";
    }
}
