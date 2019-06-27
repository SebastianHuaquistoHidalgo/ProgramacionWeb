package pe.edu.upc.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.spring.model.Boleta;
import pe.edu.upc.spring.model.DetalleBoleta;
import pe.edu.upc.spring.model.Servicio;
import pe.edu.upc.spring.model.Reserva;
import pe.edu.upc.spring.service.IBoletaService;
import pe.edu.upc.spring.service.IServicioService;
import pe.edu.upc.spring.service.IReservaService;

@Controller
@RequestMapping("/boleta")
@SessionAttributes("boleta")
public class BoletaController {

	@Autowired
	private IBoletaService boletaService;
	@Autowired
	private IReservaService reservaService; //Dueño
	@Autowired
	private IServicioService servicioService; //Producto
	
	@GetMapping("/form/{idreserva}")
	public String formVoucher(@PathVariable(value = "idreserva") int idReserva, Model model) {
		try {
			Reserva reserva = reservaService.buscarPorId(idReserva);
			if (reserva==null) {
				model.addAttribute("info", "Reserva no existe");
				return "redirect:/reserva/listar";
			} else {
				Boleta boleta = new Boleta();
				boleta.setIdReserva(reserva);
				model.addAttribute("boleta", boleta);
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "form";
	}
	
	@PostMapping("/save")
	public String saveVoucher(Boleta boleta, Model model, 
			 @RequestParam(name="item_id[]",required=false) Integer[] itemId,
			 @RequestParam(name="quantity[]",required=false) Integer[] quantity,
			 SessionStatus status) {
		try {
			
			if(itemId==null || itemId.length==0) {
				model.addAttribute("info","Voucher no tiene");
				return "form";
			}
			
			for (int i = 0; i < itemId.length; i++) { //Servicio
				Servicio servicio=servicioService.buscarPorId(itemId[i]);
				if(servicio!=null) {
					DetalleBoleta line=new DetalleBoleta();
					line.setCantidad(quantity[i]);
					line.setIdServicio(servicio);
					boleta.addBoletaDetalle(line);
				}
			}
			
			boletaService.insertar(boleta);
			status.setComplete();
			model.addAttribute("éxito","Boleta Generada");
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		
		return "redirect:/reserva/detalle/"+boleta.getIdReserva().getIdReserva();
	}
	
	//@Secured("ROLE_ADMIN")
	@GetMapping("/detalle/{id}")
	public String detailVoucher(@PathVariable(value = "id") int id, Model model) {

		try {
			Optional<Boleta> boleta = boletaService.fetchByVoucherIdWithPatientWhithVoucherDetailWithMedicine(id);

			if (boleta==null) {
				model.addAttribute("error", "Voucher no existe");
				return "redirect:/reserva/listar";
			}

			model.addAttribute("boleta", boleta.get());
			System.out.println(boleta);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "verBoleta";
	}
	
}







