package controllers;
import models.Aluno;
import models.Professor;
import models.SalaVirtual;
import models.Tabuleiro;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

public class Tabuleiros extends Controller {
	@Before(only = {"indexTabuleiroAlunos", "avisoTabuleiro"})
	static void rentringirSalaAoAluno() {
		if (session.get("tipoUsuario") != null && !session.get("tipoUsuario").equalsIgnoreCase("Aluno")) {
			Restricoes.restricoesProfessores();
		}
	}
	
	@Before(only = {"formTabuleiro", "indexTabuleiro"})
	static void rentringirSalaAoProfessor() {
		if (session.get("tipoUsuario") != null && !session.get("tipoUsuario").equalsIgnoreCase("Professor")) {
			Restricoes.restricoesAlunos();
		}
	}
	
	public static void indexTabuleiroAlunos(Long idSala) {
		Tabuleiro tabuleiro1 = Tabuleiro.find("idsalaVirtual = ?", idSala).first();
		if (tabuleiro1 != null) {
			render();
		} else {
			System.out.println("Esse tabuleiro não existe");
			renderTemplate("Tabuleiros/AvisoTabuleiro.html");
		}
	}
	
	public static void indexTabuleiro(Long idSala) {
		SalaVirtual sala = SalaVirtual.findById(idSala);
		//Tabuleiro tabuleiro1 = Tabuleiro.find("idSalaVirtual = ?", salaVirtual).first();
		render(sala);
	}
	
public static void formTabuleiro(Long salaVirtual, Tabuleiro tabuleiro) {
		Tabuleiro tabuleiro1 = Tabuleiro.find("idSalaVirtual = ?", salaVirtual).first();
		if(tabuleiro1 == null ) {
			System.out.println("");
			System.out.println("ESSE TABULEIRO NAO EXISTEEEE ");
			System.out.println("");
		}
		else {
			System.out.println("Esse tabuleiro existe. n crie outro!");
			indexTabuleiro(salaVirtual);
		}
		render(salaVirtual);
	}
	
	
	public static void salvarTabuleiro(Long idSala, @Valid Tabuleiro t) {
		if (validation.hasErrors()) {	
			validation.keep();
			params.flash();	
			formTabuleiro(idSala, t);
		}
		SalaVirtual sala = SalaVirtual.findById(idSala);	
		t.salaVirtual = sala;
		t.save();
		indexTabuleiro(idSala);
	}
}	
