package pe.edu.upc.spring.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.edu.upc.spring.service.ITipoClienteService;
import pe.edu.upc.spring.model.TipoCliente;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import groovyjarjarcommonscli.ParseException;

@Controller
@RequestMapping("/tip")

public class TipoClienteController {

	@Autowired
	private ITipoClienteService tcService;

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaTipoClientes", tcService.listar());
		return "listTipoClientes";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("tipocliente", new TipoCliente());
		return "tipocliente";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid TipoCliente objTipoCliente, BindingResult binRes, Model  model) throws ParseException{
		if(binRes.hasErrors()) {
			return "tipocliente";
		}
		else {
			boolean flag=tcService.insertar(objTipoCliente);
			if(flag) {
				return "redirect:/tip/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/tip/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid TipoCliente objTipoCliente, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/tip/listar";
		}
		else {
			boolean flag=tcService.modificar(objTipoCliente);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/tip/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/tip/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		 TipoCliente objTipoCliente=tcService.listarId(id);
		 if(objTipoCliente==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/tip/listar";
		 }
		 else {
			 model.addAttribute("tipocliente", objTipoCliente);
			 return "tipocliente";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		try {
			if(id !=null && id>0) {
				tcService.eliminar(id);
				model.put("listaTipoClientes", tcService.listar());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "no se puede eliminar");
			model.put("listaTipoClientes",tcService.listar());
		}
		return "listTipoClientes";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute TipoCliente tipocliente) {
		tcService.listarId(tipocliente.getIdTipoCliente());
		return "listTipoCliente";
	}
	
}
