package com.example.demo.controller;


import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.CasaShow;
import com.example.demo.model.NovoShow;
import com.example.demo.model.StatusSelecionarGenero;
import com.example.demo.repository.Casas;
import com.example.demo.repository.Shows;


@Controller
@RequestMapping("/home")
public class EventoController {
	
	@Autowired
	private Casas casas;
	
	@Autowired
	private Shows shows;
	
	
	////////////////////////////////////////////// PAGINAS /////////////////////////////////////////
	
	@RequestMapping
	public String home() {		
		return "Home";
	}
	
	@RequestMapping("/novoEvento")
	public String novoEvento() {
		return "NovoEvento";
	}
	@RequestMapping("/novaCasa")
	public ModelAndView novaCasa() {
		ModelAndView mv = new ModelAndView("NovaCasa");
		mv.addObject(new CasaShow());
		return mv;
	}
	@RequestMapping("/novoGenero")
	public String novaGenero() {
		return "Genero";
	}
	
	@RequestMapping("/listaCasas")
	public String listarCasas() {
		return "ListaDeCasas";
	}
	
	
	/////////////////////////////// SALVAR ///////////////////////////////////////////////////////
	
	@RequestMapping(value = "/novaCasa" , method = RequestMethod.POST)
	public String salvarCasa(@Validated CasaShow casashow, Errors errors , RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("NovaCasa");
		
		if (errors.hasErrors()) {
			return "NovaCasa";
		}
		
		mv.addObject("mensagem", "Casa salva com sucesso.");
		casas.save(casashow);
		attributes.addFlashAttribute("mensagem", "Casa Salva com sucesso");
		
		
	 return "redirect:/home/novaCasa";
	}
	
	
	@RequestMapping(value="/novoEvento" , method = RequestMethod.POST)
	public String salvarShow(NovoShow novoshows) {
		shows.save(novoshows);
		return "Home";
	}
	
	////////////////////////////////////// EDITAR ///////////////////////////////////////////////////
	
	@RequestMapping("/novaCasa/{codigo}")
	public ModelAndView editarCasa(@PathVariable ("codigo") CasaShow casa ) {
		ModelAndView mv = new ModelAndView("NovaCasa");
		mv.addObject(casa);
		return mv ;
	}
	
	
	
	////////////////////////////////////// EXCLUIR //////////////////////////////////////////////////
	
	@RequestMapping(value = "/listaCasas/{codigo}" , method = RequestMethod.POST )
	public String excluirCasa(@PathVariable Long codigo) {
		casas.deleteById(codigo);
		return "redirect:/home/listaCasas";
		
		
	}
	////////////////////////////////////// LISTAS //////////////////////////////////////////////////
	
	@ModelAttribute("todasCasasShow")
	public List<CasaShow> todasCasasShow(){
		
		return casas.findAll();
	}
	
	@ModelAttribute("todosGeneros")
	public List<StatusSelecionarGenero> todosGeneros(){
		return Arrays.asList(StatusSelecionarGenero.values());
	}
}