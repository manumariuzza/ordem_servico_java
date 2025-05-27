package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.FuncionarioEntity;
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

@Named(value = "funcionarioController")
@SessionScoped
public class FuncionarioController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.FuncionarioFacade ejbFacade;

    //objeto que representa uma pessoa mais entidade
    private FuncionarioEntity funcionario = new FuncionarioEntity();
    //objeto que representa uma lista de pessoas
    private List<FuncionarioEntity> funcionarioList = new ArrayList<>();

    private FuncionarioEntity selected;

    //atributo que será utilizado no momento da seleção da linha na datatable
    public void setSelected(FuncionarioEntity selected) {
        this.selected = selected;
    }
    public FuncionarioEntity getSelected() {
        return selected;
    }

    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setPessoaList(List<FuncionarioEntity> funcionarioList) {
        this.funcionarioList = funcionarioList;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }

    public List<FuncionarioEntity> getFuncionarioList() {
        return ejbFacade.buscarTodos();
    }


    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de uma pessoa
     *
     * @return
     */
    public FuncionarioEntity prepareAdicionar() {
        funcionario = new FuncionarioEntity();
        return funcionario;
    }

    public void adicionarFuncionario() {
        //buscando a datahoraatual do sistema.
        persist(FuncionarioController.PersistAction.CREATE, 
                "Registro incluído com sucesso!");
    }

    public void deletarFuncionario() {
        persist(FuncionarioController.PersistAction.DELETE, 
                "Registro excluído com sucesso!");
    }


    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public void editarFuncionario() {
        persist(FuncionarioController.PersistAction.UPDATE, 
                "Registro alterado com sucesso!");
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    /**
     * declaração de uma classe enum disponivel para classe
     */
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
                        ejbFacade.createReturn(funcionario);
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
