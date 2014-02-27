package controllers;

import managers.GerenciadorDeCadeiras;
import models.Cadeira;
import models.PlanoDeCurso;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	static PlanoDeCurso plano;

	public static Result index() throws Exception {
		if (plano == null) {
			if (!PlanoDeCurso.find.all().isEmpty()){
				// se ja houver uma entidade salva no BD carrega ela
				plano = PlanoDeCurso.find.all().get(0);
				plano.distribuiCadeiras(Cadeira.find.all());
			} else {
				plano = new PlanoDeCurso();
				plano.save();
				plano.distribuiCadeiras(GerenciadorDeCadeiras.getListaCadeiras());
				plano.update();
			}
		}
		return ok(views.html.index.render(plano));
	}


	public static Result addPeriodo() {
		plano.addPeriodo();
		return ok(views.html.index.render(plano));
	}

	public static Result addCadeira(String cadeira, int periodo)
			throws NumberFormatException, Exception {
		plano.addCadeira(cadeira, periodo);

		return redirect(routes.Application.index());
	}

	public static Result remPeriodo(int periodo) {
		plano.removePeriodo(periodo);
		return redirect(routes.Application.index());
	}

	public static Result remCadeira(String cadeira) throws Exception {
		plano.removeCadeira(cadeira);
		return redirect(routes.Application.index());
	}
}
