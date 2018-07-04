package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos implements Preco {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		TipoDeEspetaculo tipoEspetaculo = sessao.getEspetaculo().getTipo();

		if (tipoEspetaculo.equals(TipoDeEspetaculo.CINEMA)
				|| tipoEspetaculo.equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoCInemaShow(sessao);
		} else if (tipoEspetaculo.equals(TipoDeEspetaculo.BALLET)) {
			preco = calculaPrecoBalletOrquestra(sessao);
		} else if (tipoEspetaculo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPrecoBalletOrquestra(sessao);
		} else {
			// nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoCInemaShow(Sessao sessao) {
		BigDecimal preco = calculaAcrescimo(sessao, 0.05, 0.10);
		return preco;
	}
	
	private static BigDecimal calculaPrecoBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		preco = calculaAcrescimo(sessao, 0.50, 0.20);

		if (sessao.getDuracaoEmMinutos() > 60) {
			preco = preco.add(sessao.getPreco().multiply(
					BigDecimal.valueOf(0.10)));
		}
		return preco;
	}

	private static BigDecimal calculaAcrescimo(Sessao sessao,
			double percentualIngressosRestantes, double percentualACrescimo) {
		BigDecimal preco;
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= percentualIngressosRestantes) {
			preco = sessao.getPreco().add(
					sessao.getPreco().multiply(
							BigDecimal.valueOf(percentualACrescimo)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}



}