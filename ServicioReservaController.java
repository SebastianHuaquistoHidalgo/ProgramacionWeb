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

import pe.edu.upc.spring.service.IReservaService;
import pe.edu.upc.spring.service.IServicioReservaService;
import pe.edu.upc.spring.service.IServicioService;
import pe.edu.upc.spring.model.ServicioReserva;

@Controller
@RequestMapping("/servicioreserva")

public class ServicioReservaController {

	@Autowired
	private IServicioReservaService srService; //cService
	@Autowired
	private IServicioService sService; //peService
	@Autowired
	private IReservaService rService;
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaServicioReservas", srService.listar());
		model.put("listaReservas", rService.listar());
		model.put("listaServicios",sService.listar());
		
		return "listServicioReservas";
	}

	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("servicioreserva", new ServicioReserva());
		model.addAttribute("listaServicios",sService.listar());
		model.addAttribute("listaReservas",rService.listar());
		
		return "servicioreserva";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid ServicioReserva objServicioReserva, BindingResult binRes, Model  model) throws ParseException{ 
		if(binRes.hasErrors()) {
			model.addAttribute("servicioreserva",new ServicioReserva());
			model.addAttribute("listaServicios",sService.listar());
			model.addAttribute("listaReservas", rService.listar());
			
			return "servicioreserva";
		}
		else {
			boolean flag=srService.insertar(objServicioReserva);
			if(flag) {
				return "redirect:/servicioreserva/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/servicioreserva/irRegistrar";
			}
		}
	
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid ServicioReserva objServicioReserva, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/servicioreserva/listar";
		}
		else {
			boolean flag=srService.modificar(objServicioReserva);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/servicioreserva/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/servicioreserva/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		ServicioReserva objServicioReserva=srService.listarId(id);
		 if(objServicioReserva==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/servicioreserva/listar";
		 }
		 else {
			 model.addAttribute("listaServicios", sService.listar());
			 model.addAttribute("listaReservas", rService.listar());
			 model.addAttribute("servicioreserva", objServicioReserva);
			 return "servicioreserva";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
	
			if(id !=null && id>0) {
				srService.eliminar(id);
				model.put("listaServicioReservas", srService.listar());
			}
		 
	
		return "listServicioReservas";
	}
	
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute ServicioReserva servicioreserva) {
		srService.listarId(servicioreserva.getIdServicioReserva());
		return "listServicioReservas";
	}
	
}
