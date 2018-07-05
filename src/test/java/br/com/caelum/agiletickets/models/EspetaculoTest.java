package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveCriarApenasUmaSessaoQuandoOInicioForIgualAoFim() {
		//ARRANGE
		LocalDate inicio = new LocalDate(2018, 7, 5);
		LocalDate fim = inicio;
		LocalTime horario = new LocalTime(20, 0);
		Periodicidade diaria = Periodicidade.DIARIA;
		Espetaculo espetaculo = new Espetaculo();

		//ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, diaria);

		//ASSERT
		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
		Assert.assertEquals(1,sessoes.size());
		Sessao sessao = sessoes.get(0);
		Assert.assertEquals("05/07/18",sessao.getDia());
	}
	
	@Test
	public void deveCriarUmaSessaoParaCadaDiaQuandoOFimForMaiorQueInicio() {
		//ARRANGE
		LocalDate inicio = new LocalDate(2018, 7, 5);
		LocalDate fim = new LocalDate(2018, 7, 8);
		LocalTime horario = new LocalTime(21, 0);
		Periodicidade diaria = Periodicidade.DIARIA;
		Espetaculo espetaculo = new Espetaculo();

		//ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, diaria);

		//ASSERT
		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
		Assert.assertEquals(4,sessoes.size());

		Assert.assertEquals("05/07/18",sessoes.get(0).getDia());
		Assert.assertEquals("06/07/18",sessoes.get(1).getDia());
		Assert.assertEquals("07/07/18",sessoes.get(2).getDia());
		Assert.assertEquals(fim.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))), sessoes.get(3).getDia());
		
	}
	@Test
	public void deveCriarSessoesSemanaisQuandoOFimForMaiorQueInicio() {
		//ARRANGE
		LocalDate inicio = new LocalDate(2018, 7, 5);
		LocalDate fim = new LocalDate(2018, 7, 31);
		LocalTime horario = new LocalTime(19, 0);
		Periodicidade diaria = Periodicidade.SEMANAL;
		Espetaculo espetaculo = new Espetaculo();

		//ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, diaria);

		//ASSERT
		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
		Assert.assertEquals(4,sessoes.size());

		Assert.assertEquals("05/07/18",sessoes.get(0).getDia());
		Assert.assertEquals("12/07/18",sessoes.get(1).getDia());
		Assert.assertEquals("19/07/18",sessoes.get(2).getDia());
		Assert.assertEquals("26/07/18",sessoes.get(3).getDia());
		//Assert.assertEquals(fim.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))), sessoes.get(3).getDia());
		
	}
	
	@Test
	public void deveRetornarListaVaziaQuandoInicioForMaiorQueFim() {
		
		//ARRANGE
		LocalDate inicio = new LocalDate(2018, 7, 9);
		LocalDate fim = new LocalDate(2018, 7, 8);
		LocalTime horario = new LocalTime(21, 0);
		Periodicidade diaria = Periodicidade.DIARIA;
		Espetaculo espetaculo = new Espetaculo();

		//ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, diaria);

		Assert.assertEquals(0,sessoes.size());
		
	}
	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
}
