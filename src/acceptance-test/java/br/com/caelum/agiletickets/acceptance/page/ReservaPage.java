package br.com.caelum.agiletickets.acceptance.page;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReservaPage {

	private static final String BASE_URL = "http://localhost:8080";
	private final WebDriver driver;

	public ReservaPage(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void abreListagem() {
		driver.get(BASE_URL + "/sessao/6");
	}

	public void adicioneQuantidade(String quantidade) {
		WebElement form = driver.findElement(By.cssSelector("form")); 
		form.findElement(By.id("qtde")).sendKeys(quantidade);
		form.submit();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void mensagemDeveConter(String textoAlvo) {
		assertThat(driver.findElement(By.id("message")).getText(), containsString(textoAlvo));
	}

}
