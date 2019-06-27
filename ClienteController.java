package pe.edu.upc.spring.controller;

import java.util.Map;

import javax.validation.Valid;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import pe.edu.upc.spring.service.ITipoClienteService;
import pe.edu.upc.spring.service.IClienteService;
import pe.edu.upc.spring.model.Cliente;

@Controller
@RequestMapping("/cliente")

public class ClienteController {

	@Autowired
	private IClienteService cService; 
	@Autowired
	private ITipoClienteService tService; 

	@RequestMapping("/")
	public String irTipoCliente(Map<String,Object>model) {
		
		model.put("listaTipoCliente", tService.listar());
		return "listTipoClientes";
		
	}
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("listaTipoClientes",tService.listar());
		return "cliente";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Cliente objCliente, BindingResult binRes, Model  model) throws ParseException{ 
		if(binRes.hasErrors()) {
			model.addAttribute("listaTipoClientes",tService.listar());
			return "cliente";
		}
		else {
			boolean flag=cService.insertar(objCliente);
			if(flag) {
				return "redirect:/cliente/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/cliente/irRegistrar";
			}
		}
	
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Cliente objCliente, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/cliente/listar";
		}
		else {
			boolean flag=cService.modificar(objCliente);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/cliente/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/cliente/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Cliente objCliente=cService.listarId(id);
		 if(objCliente==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/cliente/listar";
		 }
		 else {
			 model.addAttribute("listaTipoClientes", tService.listar());
			 model.addAttribute("cliente", objCliente);
			 return "cliente";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
	
			if(id !=null && id>0) {
				cService.eliminar(id);
				model.put("listaClientes", cService.listar());
			}
		 
	
		return "listClientes";
	}
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaClientes", cService.listar()); // size());
		return "listClientes";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Cliente cliente) {
		cService.listarId(cliente.getIdCliente());
		return "listClientes";
	}
	
}
