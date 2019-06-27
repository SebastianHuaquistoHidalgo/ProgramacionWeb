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

import pe.edu.upc.spring.service.IHabitacionService;
import pe.edu.upc.spring.service.IReservaHabitacionService;
import pe.edu.upc.spring.service.IReservaService;
import pe.edu.upc.spring.model.ReservaHabitacion;


@Controller
@RequestMapping("/reservahabitacion")
public class ReservaHabitacionController {

	@Autowired
	private IReservaHabitacionService rhService; 
	@Autowired
	private IHabitacionService hService; 
	@Autowired
	private IReservaService rService; 
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaReservaHabitaciones", rhService.listar());
		model.put("listaHabitacion", hService.listar());
		model.put("listaReserva", rService.listar());
		
		return "listReservaHabitaciones";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("reservahabitacion", new ReservaHabitacion());
		model.addAttribute("listaHabitacion",hService.listar());
		model.addAttribute("listaReserva",rService.listar());
		
		return "reservahabitacion";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid ReservaHabitacion objReservaHabitacion, BindingResult binRes, Model  model) throws ParseException{ 
		if(binRes.hasErrors()) {
			model.addAttribute("reservahabitacion",new ReservaHabitacion());
			model.addAttribute("listaHabitacion",hService.listar());
			model.addAttribute("listaReserva",rService.listar());
			
			return "reservahabitacion";
		}
		else {
			boolean flag=rhService.insertar(objReservaHabitacion);
			if(flag) {
				return "redirect:/reservahabitacion/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/reservahabitacion/irRegistrar";
			}
		}
	
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid ReservaHabitacion objReservaHabitacion, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/reservahabitacion/listar";
		}
		else {
			boolean flag=rhService.modificar(objReservaHabitacion);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/reservahabitacion/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/reservahabitacion/listar";
			}
			
		}
	
	}
		@RequestMapping("/modificar/{id}")
		public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
			ReservaHabitacion objReservaHabitacion=rhService.listarId(id);
			 if(objReservaHabitacion==null) {
				 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				 return "redirect:/reservahabitacion/listar";
			 }
			 else {
				 model.addAttribute("listaHabitacion", hService.listar());
				 model.addAttribute("listaReserva",rService.listar());
				 model.addAttribute("reservahabitacion", objReservaHabitacion);
				 return "reservahabitacion";
			 }
		}

		@RequestMapping("/eliminar")
		public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		
				if(id !=null && id>0) {
					rhService.eliminar(id);
					model.put("listaReservaHabitaciones", rhService.listar());
				}
			 
		
			return "listReservaHabitaciones";
		}
		
		
		@RequestMapping("/listarId")
		public String listarId(Map<String, Object> model, @ModelAttribute ReservaHabitacion reservahabitacion) {
			rhService.listarId(reservahabitacion.getIdReservaHabitacion());
			return "listReservaHabitaciones";
		}
	
	}

