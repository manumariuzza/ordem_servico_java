package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.PessoaEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

//respondem apenas a uma chamada e logo depois podem ser 
@Stateless //utilizados para outras chamadas de qualquer cliente.            
public class PessoaFacade extends AbstractFacade<PessoaEntity> {

    /**
     * Definindo a unidade de persistencia
     */
    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Construtor que passa para superclasse a instância de PessoaEntity
     */
    public PessoaFacade() {
        super(PessoaEntity.class);
    }

    

    private List<PessoaEntity> entityList;

    public List<PessoaEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().createQuery("SELECT p FROM PessoaEntity p order by p.nome");
            //verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                entityList = (List<PessoaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    /**
     * Buscar uma pessoa por email
     * @param email
     * @param senha
     * @return 
     */
    public PessoaEntity buscarPorEmail(String email, String senha) {
        PessoaEntity pessoa = new PessoaEntity();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM PessoaEntity p WHERE p.email = :email AND p.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            //verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                pessoa = (PessoaEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return pessoa;
    }
    
    
  
}
