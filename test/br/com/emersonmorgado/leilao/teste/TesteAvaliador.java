package br.com.emersonmorgado.leilao.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.emersonmorgado.leilao.dominio.Lance;
import br.com.emersonmorgado.leilao.dominio.Leilao;
import br.com.emersonmorgado.leilao.dominio.Usuario;
import br.com.emersonmorgado.leilao.servico.Avaliador;

public class TesteAvaliador {
	
	/*
	 * Estes testes tem como ficar melhores, atraves da criação das classes de
	 *  Test Data Bulder.
	 *  Esta informação consta no treinamento de testes unitários automáticos. 
	 * 
	 * Ao contrário do @Before, métodos anotados com @After são executados após a execução do método de teste.
	 * Utilizamos métodos @After quando nossos testes consomem recursos que precisam ser finalizados. 
	 * Exemplos podem ser testes que acessam banco de dados, abrem arquivos, abrem sockets, e etc.
	 * Métodos anotados com @BeforeClass são executados apenas uma vez, antes de todos os métodos de teste.
	 * @AfterClass, por sua vez, é executado uma vez, após a execução do último método de teste da classe.
	 * 
	 */
	
	
	
	private Avaliador leiloeiro;

	@Before  // O jUnit irá chamar este método uma vez, antes de cada método de teste de forma
			 //	automaticamente e não precisa colocálo em casa teste.
	public void criaAvaliador() {
		this.leiloeiro =  new Avaliador();
		System.out.println("Cria Avaliador!!");
	}

	// para o JUnit o método não pode ser Static nem receber argumentos
	@Test
	public void deveEntederLancesEmOrdemCrescente() {
		
		//Parte 1: Senário
		Usuario	joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 novo");
		
		leilao.propoe(new Lance(joao, 250));
		leilao.propoe(new Lance(jose, 300));
		leilao.propoe(new Lance(maria, 400));
		
		//Parte 2: ação
		//criaAvaliador();
		leiloeiro.avalia(leilao);
		
		
		//Parte 3: valicação
		double maiorEsperado = 400;
		double menorEsperado = 250;
		//System.out.println(leiloeiro.getMaiorLance() == maiorEsperado);
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001); 
		//A ordem é sempre esperado e calculado
		// Apesar de não fazer diferença nenhuma (afinal, esperamos que os dois números sejam iguais), é importante manter essa ordem.
		//Quando o teste falha, o JUnit usa esses valores para exibir uma mensagem de erro amigável. Por exemplo, expected 10, but was 20. Ou seja, esperava 10, mas veio 20. Se invertêssemos os valores, a mensagem ficaria errada!
		//System.out.println(leiloeiro.getMenorLance() == menorEsperado);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void deveEntederLeilaoComApenasUmLance(){
		
		//Parte 1: Senário
		Usuario	joao = new Usuario("Joao");
		
		Leilao leilao = new Leilao("Playstation 3 novo");
		
		leilao.propoe(new Lance(joao, 1000));
		
		//Parte 2: ação
		//criaAvaliador();
		leiloeiro.avalia(leilao);
		
		
		//Parte 3: valicação
		double maiorEsperado = 1000;
		//duas formas de escrever com ou sem import statico
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001); 
		Assert.assertEquals(maiorEsperado, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		Usuario	joao = new Usuario("Joao");
		Usuario	maria = new Usuario("Maria");
		
		
		Leilao leilao = new Leilao("Playstation 3 novo");
		
		leilao.propoe(new Lance(joao, 100));
		leilao.propoe(new Lance(maria, 200));
		leilao.propoe(new Lance(joao, 300));
		leilao.propoe(new Lance(maria, 400));
		
		//criaAvaliador();	
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
		assertEquals(300.0, maiores.get(2).getValor(), 0.0001);
		assertEquals(200.0, maiores.get(3).getValor(), 0.0001);
		
	}
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        Usuario joao = new Usuario("Joao"); 
        Usuario maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,450.0));
        leilao.propoe(new Lance(joao,120.0));
        leilao.propoe(new Lance(maria,700.0));
        leilao.propoe(new Lance(joao,630.0));
        leilao.propoe(new Lance(maria,230.0));

     //   criaAvaliador();
        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }
	
	@Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        Usuario joao = new Usuario("Joao"); 
        Usuario maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,400.0));
        leilao.propoe(new Lance(maria,300.0));
        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,100.0));

       // criaAvaliador();
        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }

}
