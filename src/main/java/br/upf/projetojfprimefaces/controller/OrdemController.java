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

    public void setOrdem(OrdemEntity ordem) {
        this.ordem = ordem;
    }

    public OrdemEntity getOrdem() {
        return ordem;
    }

    public void setOrdemList(List<OrdemEntity> ordemList) {
        this.ordemList = ordemList;
    }
    public List<OrdemEntity> getOrdemList() {
        return ejbFacade.buscarTodos();
    }

    public void setSelected(OrdemEntity selected) {
        this.selected = selected;
    }

    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de uma ordem
     *
     * @return
     */
    public OrdemEntity prepareAdicionar() {
        ordem = new OrdemEntity();
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

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    
    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
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
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(ordem);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

}
