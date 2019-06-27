package pe.edu.upc.spring.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.edu.upc.spring.service.IAcompanianteService;
import pe.edu.upc.spring.model.Acompaniante;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import groovyjarjarcommonscli.ParseException;

@Controller
@RequestMapping("/acom")

public class AcompanianteController {

	@Autowired
	private IAcompanianteService aAcompaniante;

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaAcompaniantes", aAcompaniante.listar());
		return "listAcompaniantes";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("acompaniante", new Acompaniante());{
		return "acompaniante";
		}
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Acompaniante objAcompaniante, BindingResult binRes, Model  model) throws ParseException{
		if(binRes.hasErrors()) {
			return "acompaniante";
		}
		else {
			boolean flag=aAcompaniante.insertar(objAcompaniante);
			if(flag) {
				return "redirect:/acom/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/acom/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Acompaniante objAcompaniante, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/acom/listar";
		}
		else {
			boolean flag=aAcompaniante.modificar(objAcompaniante);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/acom/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/acom/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		 Acompaniante objAcompaniante=aAcompaniante.listarId(id);
		 if(objAcompaniante==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/acom/listar";
		 }
		 else {
			 model.addAttribute("acompaniante", objAcompaniante);
			 return "acompaniante";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		try {
			if(id !=null && id>0) {
				aAcompaniante.eliminar(id);
				model.put("listaAcompaniantes", aAcompaniante.listar());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "no se puede eliminar");
			model.put("listaAcompaniantes",aAcompaniante.listar());
		}
		return "listAcompaniantes";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Acompaniante acompaniante) {
		aAcompaniante.listarId(acompaniante.getIdAcompaniante());
		return "listAcompaniante";
	}	
}













