package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Produto produto) {
		this.em.persist(produto);//para persistir no banco de dados/salvar o objeto entidade
	}

	public void atualizar(Produto produto) {
		this.em.merge(produto);//merge serve para retornar a entidade para estado MANAGED aonde o hibernate
		//está de olho em tudo que ocorre nessa entidade
	}

	public void remover(Produto produto) {
		produto = em.merge(produto);// retornando o objeto produto para um estado Managed
		this.em.remove(produto);//removendo o produto
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";//JPQL uma forma de simplicar um comando SQL
		//de forma orientada a objetos pelo java
		return em.createQuery(jpql, Produto.class).getResultList();
		//getResultList() para devolver uma lista de objetos pelo hibernate
	}
	
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		//Produto tem que estar igual no código pois ele verifica pelo nome da classe
		//e não pelo nome da entidade no banco de dados
		//
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)//esquerda nome do ojeto no JPQL direita nome do atributo no metodo
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nomesss";
		return em.createQuery(jpql, Produto.class)//explicitar na query que se trata da classe produto
				.setParameter("nomesss", nome)
				.getResultList();
	}
	
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)
				.getSingleResult();//para retorna um único resultado já que o retorno não é um arraylist
	}

}
