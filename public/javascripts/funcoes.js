//funções do DRAG e DROP!

//Funcção para quando o elemento estiver sobre a div de Periodo
function allowDrop(ev,e)
{
	$("#"+e.id).addClass("periodoDrag");
	ev.preventDefault();
}
//Função para quando eu arrasto o elemento
function drag(ev){
	$(".periodo").addClass("periodoActive");
	ev.dataTransfer.setData("Text",ev.target.id);
}
//Função para quando afastar o elemento da div de Periodo
function leave(ev, e){
	$("#"+e.id).removeClass("periodoDrag");
}
//Função para quando o evento de arrastar é cancelado
function dragEnd(){
	$(".periodo").removeClass("periodoActive");
}
//Função para quando o elemento é colocado sobre a div de Periodo
function drop(ev, e){
	$(".periodo").removeClass("periodoActive");
	$("#"+e.id).removeClass("periodoDrag");
	ev.preventDefault();
	var data=ev.dataTransfer.getData("Text");
	$.ajax({
		  type: "POST",
		  url: "/addCadeira/"+data+"/"+e.id,
		  data: "",
		  success: function(){
		        alert( "Cadeira Adicionada");
		        window.location = "/";
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
			  //parser pra achar a Excecao lancada
			  var ind = XMLHttpRequest.responseText.indexOf("Exception:")
			  var res = XMLHttpRequest.responseText.substring(ind +11 , ind + 900);
			  var ind2 = res.indexOf("]");
			  var res2 = res.substring(0, ind2);
		      alert(res2);
		  }
		});
}

$(document).ready(function(){
	$(".alocadas").hover(function(){
		  $(this).css("opacity", "0.7")
		  $(this).children("span").addClass("glyphicon glyphicon-remove");
		  $(this).children("span").css("visibility", "visible")
		  },function(){
		  $(this).css("opacity", "1")
		  $(this).children("span").removeClass("glyphicon glyphicon-remove");
		  $(this).children("span").css("visibility", "hidden")
		});

	});

function remCadeira(cadeira, flagRequisitos){
	var r = true;
	if (flagRequisitos == 'true'){
		r=confirm("A remoção dessa cadeira implicará na remoção de outra(s) cadeira(s), você realmente deseja removê-la?");
	}
	if (r==true){
	$.ajax({
		  type: "POST",
		  url: "/remCadeira/" + cadeira,
		  data: "",
		  success: function(){
		        alert( "Cadeira Removida");
		        window.location = "/";
		  },
		  error: function(XMLHttpRequest, textStatus, errorThrown) {
			  //parser pra achar a Excecao lancada
			  var ind = XMLHttpRequest.responseText.indexOf("Exception:");
			  var res = XMLHttpRequest.responseText.substring(ind +11 , ind + 500);
			  var ind2 = res.indexOf("]");
			  var res2 = res.substring(0, ind2);
		      alert(res2);
		  }
		});
	}
}
