
$(function(){
	$('[rel="tooltip"]').tooltip();
	$('.js-valor').maskMoney({decimal: ',' , thousands: '.' ,  });
});
$('#excluirCasa').on('show.bs.modal', function(event){
	var button = $(event.relatedTarget);
	
	var codigoCasa = button.data('codigo');
	
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action + codigoCasa);
});

$('#excluirShow').on('show.bs.modal', function(event){
	var button = $(event.relatedTarget);
	
	var codigoShow = button.data('codigo');
	
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')){
		action += '/';
	}
	form.attr('action', action + codigoShow);
});