package pe.edu.upc.spring.controller;

import java.util.List;
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

import pe.edu.upc.spring.service.ITipoPagoService;
import pe.edu.upc.spring.service.IClienteService;
import pe.edu.upc.spring.service.IReservaService;
import pe.edu.upc.spring.model.Reserva;

@Controller
@RequestMapping("/reserva")

public class ReservaController {

	@Autowired
	private IClienteService cService; 
	@Autowired
	private IReservaService rService;
	
	@Autowired
	private ITipoPagoService tpService; 
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaReservas", rService.listar());
		model.put("listaCliente", cService.listar());
		model.put("listaTipoPago", tpService.listar());
		
		return "listReserva";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("reserva", new Reserva());
		model.addAttribute("listaCliente",cService.listar());
		model.addAttribute("listaTipoPago",tpService.listar());
		
		return "reserva";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Reserva objReserva, BindingResult binRes, Model  model) throws ParseException{ 
		if(binRes.hasErrors()) {
			model.addAttribute("reserva", new Reserva());
			model.addAttribute("listaCliente",cService.listar());
			model.addAttribute("listaTipoPago",tpService.listar());
			return "reserva";
		}
		else {
			boolean flag=rService.insertar(objReserva);
			if(flag) {
				return "redirect:/reserva/listar"; 
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/reserva/irRegistrar";
			}
		}
	
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Reserva objReserva, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/reserva/listar";
		}
		else {
			boolean flag=rService.modificar(objReserva);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/reserva/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/reserva/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Reserva objReserva=rService.listarId(id);
		 if(objReserva==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/reserva/listar";
		 }
		 else {
			 model.addAttribute("listaCliente", cService.listar());
			 model.addAttribute("listaTipoPago", tpService.listar());
			 model.addAttribute("reserva", objReserva);
			 return "reserva";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
	
			if(id !=null && id>0) {
				rService.eliminar(id);
				model.put("listaReservas", rService.listar());
			}
		 
	
		return "listReserva";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Reserva reserva) {
		rService.listarId(reserva.getIdReserva());
		return "listReserva";
	}
	
	@RequestMapping("/buscar") 
	public String buscar(Map<String, Object> model, @ModelAttribute Reserva reserva) throws ParseException {

		List<Reserva> listareservas;

		listareservas = rService.findByReserva(reserva.getNombreReserva());

		if (listareservas.isEmpty()) {

			model.put("mensaje", "No se encontró");
		}

		model.put("listareservas", listareservas);
		return "buscarreserva";

	}

	@RequestMapping("/irBuscar") 
	public String irBuscar(Model model) {

		model.addAttribute("reserva", new Reserva());
		return "buscarreserva";

	}
		
	@RequestMapping("/detalle/{id}") 
	public String buscar(@PathVariable(value="id") int id, Model model) {

		try {
			Reserva reserva = rService.buscarPorId(id);
			if(reserva==null) {
				model.addAttribute("info","Reserva no existe no existe");
				return "redirect:/reserva/listar";
			}else {
				model.addAttribute("reserva",reserva);
			}
			
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "verReserva";
	}

}













