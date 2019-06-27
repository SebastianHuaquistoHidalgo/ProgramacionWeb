package pe.edu.upc.spring.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import groovyjarjarcommonscli.ParseException;
import pe.edu.upc.spring.model.TipoPago;
import pe.edu.upc.spring.service.ITipoPagoService;

@Controller
@RequestMapping("/tipopago")
public class TipoPagoController {

	@Autowired
	private ITipoPagoService tpService;

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {
		model.put("listaTipoPago", tpService.listar());
		return "listTipoPago";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
		model.addAttribute("tipopago", new TipoPago());
		return "tipopago";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid TipoPago objTipoPago, BindingResult binRes, Model  model) throws ParseException{
		if(binRes.hasErrors()) {
			return "tipocliente";
		}
		else {
			boolean flag=tpService.insertar(objTipoPago);
			if(flag) {
				return "redirect:/tipopago/listar";
			}
			else {
				model.addAttribute("mensaje","Ocurrió un error");
				return "redirect:/tipopago/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid TipoPago objTipoPago, BindingResult binRes, Model  model, RedirectAttributes objRedir)
	throws ParseException{
		if(binRes.hasErrors()) {
			return "redirect:/tipopago/listar";
		}
		else {
			boolean flag=tpService.modificar(objTipoPago);
			if(flag) {
				objRedir.addFlashAttribute("mensaje","Se actualizó correctamente");
				return "redirect:/tipopago/listar";
			}
			else {
				objRedir.addFlashAttribute("mensaje", "ocurrió un error");
				return "redirect:/tipopago/listar";
			}
			
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		 TipoPago objTipoPago=tpService.listarId(id);
		 if(objTipoPago==null) {
			 objRedir.addFlashAttribute("mensaje", "ocurrió un error");
			 return "redirect:/tipopago/listar";
		 }
		 else {
			 model.addAttribute("tipopago", objTipoPago);
			 return "tipopago";
		 }
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value="id")Integer id) {
		try {
			if(id !=null && id>0) {
				tpService.eliminar(id);
				model.put("listaTipoPago", tpService.listar());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "no se puede eliminar");
			model.put("listaTipoPago",tpService.listar());
		}
		return "listTipoPago";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute TipoPago tipopago) {
		tpService.listarId(tipopago.getIdTipoPago());
		return "listTipoPago";
	}
}
