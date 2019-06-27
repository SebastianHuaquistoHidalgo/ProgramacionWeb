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

import pe.edu.upc.spring.service.IAcompanianteService;
import pe.edu.upc.spring.service.IReservaAcompanianteService;
import pe.edu.upc.spring.service.IReservaService;
import pe.edu.upc.spring.model.ReservaAcompaniante;

@Controller

@RequestMapping("/reservaacom")

public class ReservaAcompanianteController {

	@Autowired
	private IReservaAcompanianteService raService; 
	@Autowired
	private IAcompanianteService aService; 
	@Autowired
	private IReservaService rService;
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaReservaAcompaniantes", raService.listar());
		model.put("listaAcompaniante", aService.listar());
		model.put("listaReservas", rService.listar());
		
		return "listReservaacompaniante";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("reservaacompaniante", new ReservaAcompaniante());
		model.addAttribute("listaAcompaniantes",aService.listar());
		model.addAttribute("listaReservas",rService.listar());
		return "reservaacompaniante";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid ReservaAcompaniante objReservaacompaniante, BindingResult binRes, Model  model) throws ParseException{ 
		if(binRes.hasErrors()) {
			model.addAttribute("reservaacompaniante", new ReservaAcompaniante());
			model.addAttribute("listaAcompaniantes",aService.listar());
			model.addAttribute("listaReservas", rService.listar());
			return "reservaacompaniante";
		}
		else {
			boolean flag=raService.insertar(objReservaacompaniante);
			if(flag) {
				return "redirect:/reservaacom/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/reservaacom/irRegistrar";
			}
		}
	
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid ReservaAcompaniante objReservaacompaniante, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/reservaacom/listar";
		}
		else {
			boolean flag=raService.modificar(objReservaacompaniante);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/reservaacom/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/reservaacom/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		ReservaAcompaniante objReservaacompaniante=raService.listarId(id);
		 if(objReservaacompaniante==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/reservaacom/listar";
		 }
		 else {
			 model.addAttribute("listaAcompaniantes", aService.listar());
			 model.addAttribute("listaReservas", rService.listar());
			 model.addAttribute("reservaacompaniante", objReservaacompaniante);
			 return "reservaacompaniante";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
	
			if(id !=null && id>0) {
				raService.eliminar(id);
				model.put("listaReservaAcompaniantes", raService.listar());
			}
		 
	
		return "listReservaacompaniante";
	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute ReservaAcompaniante reservaacompaniante) {
		raService.listarId(reservaacompaniante.getIdReservaAcompaniante());
		
		return "listReservaacompaniante";
	}	
	
}
