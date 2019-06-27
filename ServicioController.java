package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pe.edu.upc.spring.service.IServicioService;
import pe.edu.upc.spring.model.Servicio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import groovyjarjarcommonscli.ParseException;

@Controller
@RequestMapping("/servicio")
@SessionAttributes("servicio")
public class ServicioController {

	@Autowired
	private IServicioService sService;

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaServicios", sService.listar());// listar(.))
		return "listServicios";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		
		model.addAttribute("servicio", new Servicio());
		
		return "servicio";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Servicio objServicio, BindingResult binRes, Model  model) throws ParseException{
		if(binRes.hasErrors()) {
			return "servicio";
		}
		else {
			boolean flag=sService.insertar(objServicio);
			if(flag) {
				return "redirect:/servicio/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/servicio/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Servicio objServicio, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/servicio/listar";
		}
		else {
			boolean flag=sService.modificar(objServicio);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/servicio/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/servicio/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		 Servicio objServicio=sService.listarId(id);
		 if(objServicio==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/servicio/listar";
		 }
		 else {
			 model.addAttribute("servicio", objServicio);
			 return "servicio";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		try {
			if(id !=null && id>0) {
				sService.eliminar(id);
				model.put("listaServicios", sService.listar());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "no se puede eliminar");
			model.put("listaServicios",sService.listar());
		}
		return "listServicios";
	}
	
	
	
	@RequestMapping("/buscar") 
	public String buscar(Map<String, Object> model, @ModelAttribute Servicio Servicio) throws ParseException {

		List<Servicio> listaServicios;

		listaServicios = sService.findByName((Servicio.getNombreServicio()));

		if (listaServicios.isEmpty()) {

			model.put("mensaje", "No se encontró");
		}

		model.put("listaservicios", listaServicios);
		return "buscarservicio";

	}

	@RequestMapping("/irBuscar") 
	public String irBuscar(Model model) {

		model.addAttribute("servicio", new Servicio());
		return "buscarservicio";

	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Servicio servicio) {
		sService.listarId(servicio.getIdServicio());
		return "listServicio";
	}	
	
	
	
	
	@GetMapping(value = "/list/{nombreServicio}", produces = { "application/json" })
	public @ResponseBody List<Servicio> fetchServicios(@PathVariable String nombreServicio, Model model) {
		List<Servicio> servicios = null;
		try {
			System.out.println(nombreServicio);
			servicios = sService.findByName(nombreServicio);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return servicios;
	}

}













