package br.upf.projetojfprimefaces.facade;

import br.upf.projetojfprimefaces.entity.AutomovelEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless //utilizados para outras chamadas de qualquer cliente.            
public class AutomovelFacade extends AbstractFacade<AutomovelEntity> {

    @PersistenceContext(unitName = "ProjetojfprimefacesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutomovelFacade() {
        super(AutomovelEntity.class);
    }

    private List<AutomovelEntity> entityList;

    /**
     * método responsável por buscar na base de dados, todas as cidades cadastradas
     * @return 
     */
    public List<AutomovelEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().
                    createQuery("SELECT p FROM AutomovelEntity p ORDER BY p.nome");
            //verifica se existe algum resultado para não gerar excessão
            entityList = (List<AutomovelEntity>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

}
