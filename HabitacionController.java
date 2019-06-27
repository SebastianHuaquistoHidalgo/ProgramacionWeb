package pe.edu.upc.spring.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.edu.upc.spring.service.IHabitacionService;
import pe.edu.upc.spring.model.Habitacion;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import groovyjarjarcommonscli.ParseException;


@Controller
@RequestMapping("/habitacion")
public class HabitacionController {

	@Autowired
	private IHabitacionService hService;

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaHabitaciones", hService.listar());
		return "listHabitaciones";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("habitacion", new Habitacion());
		return "habitacion";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Habitacion objHabitacion, BindingResult binRes, Model  model) throws ParseException{
		if(binRes.hasErrors()) {
			return "habitacion";
		}
		else {
			boolean flag=hService.insertar(objHabitacion);
			if(flag) {
				return "redirect:/habitacion/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/habitacion/irRegistrar";
			}
		}
	}
		
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Habitacion objHabitacion, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
			if(binRes.hasErrors()) {
				return "redirect:/habitacion/listar";
			}
			else {
				boolean flag=hService.modificar(objHabitacion);
				if(flag) {
					objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
					return "redirect:/habitacion/listar";
				}
				else {
					objRedir.addFlashAttribute("mensaje", "ocurrió un error");
					return "redirect:/habitacion/listar";
				}
				
			}	
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
			 Habitacion objHabitacion=hService.listarId(id);
				 if(objHabitacion==null) {
					 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
					 return "redirect:/habitacion/listar";
				 }
				 else {
					 model.addAttribute("habitacion", objHabitacion);
					 return "habitacion";
				 }
			}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		try {
			if(id !=null && id>0) {
				hService.eliminar(id);
				model.put("listaHabitaciones", hService.listar());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "no se puede eliminar");
			model.put("listaHabitaciones",hService.listar());
		}
		return "listHabitaciones";
	}
			
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Habitacion habitacion) {
		hService.listarId(habitacion.getIdHabitacion());
		return "listHabitacion";
	}
					
}	


