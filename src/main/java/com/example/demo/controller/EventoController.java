package com.example.demo.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.example.demo.model.Compra;
import com.example.demo.model.ItensCompras;
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
	public ModelAndView novoEvento() {
		ModelAndView mv = new ModelAndView("NovoEvento");
		mv.addObject(new NovoShow());
		return mv ;
	}
	@RequestMapping("/novaCasa")
	public ModelAndView novaCasa() {
		ModelAndView mv = new ModelAndView("NovaCasa");
		mv.addObject(new CasaShow());
		return mv;
	}
	@RequestMapping("/listaShows")
	public String novaGenero() {
		return "ListaDeShows";
	}
	
	@RequestMapping("/listaCasas")
	public String listarCasas() {
		return "ListaDeCasas";
	}
	
	////////////////////////////////////////////// CARRINHO ////////////////////////////////////////////////
	
	private List<ItensCompras> itensCompra = new ArrayList<ItensCompras>();
	
	private Compra compra = new Compra();
	
	public void calcularCompra() {
		compra.setValorTotal(0.);
		for (ItensCompras it : itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}
	
	
	
	@RequestMapping("/carrinho")
	public ModelAndView carrinho() {
	ModelAndView mv = new ModelAndView("Carrinho");
	calcularCompra();
	
	mv.addObject("compra" , compra);
	mv.addObject("listaVenda" , itensCompra);
	
	return  mv ;
	}
	
	
	
	/////////////////////////////////////////// ALTERAR QUANTIDADE ////////////////////////////////////////////////////
	
	@RequestMapping("/alterarQuantidadeIngressos/{codigo}/{alteracao}")
	public String alterarQuantidadeIngressos(@PathVariable Long codigo , @PathVariable Integer alteracao) {
	
	for(ItensCompras it : itensCompra) {
		
		if(it.getShow().getCodigo().equals(codigo)) {
			
			if(alteracao == 1) {
			it.setQuantidade(it.getQuantidade()+1);
			it.setValorTotal(0.);
			it.setValorTotal(it.getValorTotal() + (it.getQuantidade()*it.getValorUnitario()));
			
		}
		else if (alteracao == 0) {
			it.setQuantidade(it.getQuantidade()-1);
			it.setValorTotal(0.);
			it.setValorTotal(it.getValorTotal() + (it.getQuantidade()*it.getValorUnitario()));
		}
			break;
		}
	}
	return  "redirect:/home/carrinho";
	}
	
	/////////////////////////////////////// REMOVER DO CARRINHO ///////////////////////////////////////////////
	
	@RequestMapping("/removerCarrinho/{codigo}")
	public String removerIngressos(@PathVariable Long codigo) {
	
	for(ItensCompras it : itensCompra) {
		if(it.getShow().getCodigo().equals(codigo)) {
			itensCompra.remove(it);
			break;
		}
		
	}
	return  "redirect:/home/carrinho";
	}
	
	/////////////////////////////////////// ADD CARRINHO ////////////////////////////////////////////////////////
	
	
	
	@RequestMapping("/carrinho/addCarrinho/{codigo}")
	public String addcarrinho(@PathVariable Long codigo) {
	Optional<NovoShow> compra = shows.findById(codigo);
	NovoShow show = compra.get();
	
	int controle = 0 ;
	for(ItensCompras it : itensCompra) {
		if(it.getShow().getCodigo().equals(show.getCodigo())) {
			it.setQuantidade(it.getQuantidade()+1);
			it.setValorTotal(0.);
			it.setValorTotal(it.getValorTotal() + (it.getQuantidade()*it.getValorUnitario()));
				controle = 1;
				break;
		}	
	}
	if(controle == 0) {
	
	ItensCompras item = new ItensCompras();
	item.setShow(show);
	item.setValorUnitario(show.getValorEvento());
	item.setQuantidade(item.getQuantidade()+1);
	
	item.setValorTotal(item.getValorTotal() + (item.getQuantidade()*item.getValorUnitario()));
	itensCompra.add(item);
	}
	
	return "redirect:/home/carrinho" ;
	}
	
	
	
	/////////////////////////////////////////////// SALVAR /////////////////////////////////////////////////
	
	
	
	
	
	@RequestMapping(value = "/novaCasa" , method = RequestMethod.POST)
	public String salvarCasa(@Validated CasaShow casashow, Errors errors , RedirectAttributes attributes) {
		
		if (errors.hasErrors()) {
			return "NovaCasa";
		}
		

		casas.save(casashow);
		attributes.addFlashAttribute("mensagem", "Casa Salva com sucesso");
		
	 return "redirect:/home/novaCasa";
	}
	
	
	@RequestMapping(value="/novoEvento" , method = RequestMethod.POST)
	public String salvarShow(@Validated NovoShow novoshows , Errors errors , RedirectAttributes attributes) {
		
		if (errors.hasErrors()) {
			return "NovoEvento";
		}
		try {
		shows.save(novoshows);
		attributes.addFlashAttribute("mensagem", "Evento salvo!!!");
		
		return "redirect:/home/novoEvento";
		}catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataEvento", null, "Formato de DATA inválido.");
			
		}
		return "redirect:/home/novoEvento";
	}
	
	////////////////////////////////////// EDITAR /////////////////////////////////////////////////////////
	
	@RequestMapping("/novaCasa/{codigo}")
	public ModelAndView editarCasa(@PathVariable ("codigo") CasaShow casa ) {
		ModelAndView mv = new ModelAndView("NovaCasa");
		mv.addObject(casa);
		return mv ;
	}
	
	@RequestMapping("/novoEvento/{codigo}")
	public ModelAndView editarShow(@PathVariable ("codigo") NovoShow showEditar) {
		ModelAndView mv = new ModelAndView("NovoEvento");
		mv.addObject(showEditar);
		return mv;
	}
	
	
	
	////////////////////////////////////// EXCLUIR //////////////////////////////////////////////////
	
	@RequestMapping(value = "/listaCasas/{codigo}" , method = RequestMethod.POST )
	public String excluirCasa(@PathVariable Long codigo) {
		casas.deleteById(codigo);
		return "redirect:/home/listaCasas";	
	}
	
	@RequestMapping(value = "/listaShows/{codigo}", method = RequestMethod.POST)
	public String excluirShow(@PathVariable Long codigo) {
		shows.deleteById(codigo);
		return "redirect:/home/listaShows";
		
	}
	////////////////////////////////////// LISTAS //////////////////////////////////////////////////
	
	@ModelAttribute("todasCasasShow")
	public List<CasaShow> todasCasasShow(){
		
		return casas.findAll();
	}
	
	@ModelAttribute("todosShows")
	public List<NovoShow> todosShows(){
		return shows.findAll();
		
	}
	
	@ModelAttribute("todosGeneros")
	public List<StatusSelecionarGenero> todosGeneros(){
		return Arrays.asList(StatusSelecionarGenero.values());
	}
}