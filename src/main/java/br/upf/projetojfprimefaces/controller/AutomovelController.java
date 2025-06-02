package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.AutomovelEntity;
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

@Named(value = "automovelController")
@SessionScoped
public class AutomovelController implements Serializable {

    @EJB
    private br.upf.projetojfprimefaces.facade.AutomovelFacade ejbFacade;

    private AutomovelEntity automovel = new AutomovelEntity();
    private List<AutomovelEntity> automovelList = new ArrayList<>();
    private AutomovelEntity selected;
    
    
    public List<AutomovelEntity> automovelAll() {
        return ejbFacade.buscarTodos();
    }

    public List<AutomovelEntity> getAutomovelList() {
        return ejbFacade.buscarTodos();
    }

    public void setAutomovelList(List<AutomovelEntity> automovelList) {
        this.automovelList = automovelList;
    }

    public AutomovelEntity getSelected() {
        return selected;
    }

    public void setSelected(AutomovelEntity selected) {
        this.selected = selected;
    }

    public AutomovelEntity getAutomovel() {
        return automovel;
    }

    public void setAutomovel(AutomovelEntity automovel) {
        this.automovel = automovel;
    }

    public AutomovelEntity getAutomovel(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    
    @FacesConverter(forClass = AutomovelEntity.class)
    public static class AutomovelControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AutomovelController controller
                    = (AutomovelController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "automovelController");
            return controller.getAutomovel(getKey(value));
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
            if (object instanceof AutomovelEntity) {
                AutomovelEntity o = (AutomovelEntity) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }
    }
    
    /**
     * Método utilizado para executar algumas de abrir o formulário
     * de criação de um automovel
     * @return
     */
    public AutomovelEntity prepareAdicionar() {
        automovel = new AutomovelEntity();
        return automovel;
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
                        ejbFacade.createReturn(automovel);
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
