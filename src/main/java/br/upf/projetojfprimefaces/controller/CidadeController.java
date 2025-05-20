package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.CidadeEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "cidadeController")
@SessionScoped
public class CidadeController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.CidadeFacade ejbFacade;

    private CidadeEntity cidade = new CidadeEntity();
    private List<CidadeEntity> cidadeList = new ArrayList<>();
    private CidadeEntity selected;

    public List<CidadeEntity> cidadeAll() {
        return ejbFacade.buscarTodos();
    }

    public List<CidadeEntity> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<CidadeEntity> cidadeList) {
        this.cidadeList = cidadeList;
    }

    public CidadeEntity getSelected() {
        return selected;
    }

    public void setSelected(CidadeEntity selected) {
        this.selected = selected;
    }

    public CidadeEntity getCidade() {
        return cidade;
    }

    public void setCidade(CidadeEntity cidade) {
        this.cidade = cidade;
    }

    
    public CidadeEntity getCidade(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = CidadeEntity.class)
    public static class CidadeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CidadeController controller
                    = (CidadeController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "cidadeController");
            return controller.getCidade(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CidadeEntity) {
                CidadeEntity o = (CidadeEntity) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }
    }
    
    /**
     * Método utilizado para executar algumas ações antes de abrir o formulário
     * de criação de uma cidade
     * @return
     */
    public CidadeEntity prepareAdicionar() {
        cidade = new CidadeEntity();
        return cidade;
    }    

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(cidade);
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
    
    public void adicionar() {
        persist(PersistAction.CREATE,
                "Registro incluído com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE,
                "Registro alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE,
                "Registro excluído com sucesso!");
    }   
}
