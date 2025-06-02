package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.OrdemEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "ordemController")
@SessionScoped
public class OrdemController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.OrdemFacade ejbFacade;

    //objeto que representa uma ordem
    private OrdemEntity ordem = new OrdemEntity();
    //objeto que representa uma lista de ordens
    private List<OrdemEntity> ordemList = new ArrayList<>();

    private OrdemEntity selected;

    //atributo que será utilizado no momento da seleção da linha na datatable
    public OrdemEntity getSelected() {
        return selected;
    }

    public void setSelected(OrdemEntity selected) {
        this.selected = selected;
    }

    public OrdemEntity getOrdem() {
        return ordem;
    }

    public void setOrdem(OrdemEntity ordem) {
        this.ordem = ordem;
    }

    public List<OrdemEntity> getOrdemList() {
        return ejbFacade.buscarTodos();
    }

    public void setOrdemList(List<OrdemEntity> ordemList) {
        this.ordemList = ordemList;
    }

    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de uma ordem
     *
     * @return
     */
    
    public OrdemEntity prepareAdicionar() {
    ordem = new OrdemEntity();
    ordem.setId(null); // <--- garante que o ID será auto-gerado
    return ordem;
}


     public void adicionarOrdem() {
        persist(OrdemController.PersistAction.CREATE, 
                "Registro incluído com sucesso!");
    }

    public void editarOrdem() {
        persist(OrdemController.PersistAction.UPDATE, 
                "Registro alterado com sucesso!");
    }

    public void deletarOrdem() {
        persist(OrdemController.PersistAction.DELETE, 
                "Registro excluído com sucesso!");
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    /**
     * Método que recebe a requisição para persistencia e executa a mesma.
     * @param persistAction
     * @param successMessage 
     */
    private void persist(PersistAction persistAction, String successMessage) {
    try {
        System.out.println(">>> Iniciando persistência com ação: " + persistAction);

        if (persistAction != null) {
            switch (persistAction) {
                case CREATE:
                    System.out.println(">>> Persistindo nova ordem: " + ordem);
                    ejbFacade.createReturn(ordem);
                    break;
                case UPDATE:
                    System.out.println(">>> Atualizando ordem: " + selected);
                    ejbFacade.edit(selected);
                    selected = null;
                    break;
                case DELETE:
                    System.out.println(">>> Deletando ordem: " + selected);
                    ejbFacade.remove(selected);
                    selected = null;
                    break;
            }
        }

        System.out.println(">>> Persistência concluída com sucesso");
        addSuccessMessage(successMessage);

    } catch (EJBException ex) {
        String msg = "";
        Throwable cause = ex.getCause();
        while (cause != null) {
            msg += cause.getClass().getName() + ": " + cause.getMessage() + " | ";
            cause = cause.getCause();
        }
        System.out.println(">>> ERRO na persistência (EJBException): " + msg);
        ex.printStackTrace();
        addErrorMessage("Erro ao persistir: " + msg);
    } catch (Exception ex) {
        System.out.println(">>> ERRO inesperado: " + ex.getMessage());
        ex.printStackTrace();
        addErrorMessage("Erro inesperado: " + ex.getLocalizedMessage());
    }
}

}
