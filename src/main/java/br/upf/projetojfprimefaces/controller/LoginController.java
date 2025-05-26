package br.upf.projetojfprimefaces.controller;

import br.upf.projetojfprimefaces.entity.FuncionarioEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    
    @EJB
    private br.upf.projetojfprimefaces.facade.FuncionarioFacade ejbFacade;


    public LoginController() {
    }

    //objeto que representa um Funcionario
    private FuncionarioEntity funcionario;

    //objeto para autenticar
    public void prepareAutenticarFuncionario() {
        funcionario = new FuncionarioEntity();
    }

    /**
     * Método utilizado para inicializar métodos ao instanciar a classe...
     */
    @PostConstruct
    public void init() {
        prepareAutenticarFuncionario();
    }

    /**
     * Método utilizado para validar login e senha.   
     * @return
     */
    public String validarLogin() {
        FuncionarioEntity funcionarioDB = ejbFacade.buscarPorEmail(funcionario.getEmail(), funcionario.getSenha());
        if ((funcionarioDB != null && funcionarioDB.getId() != null)) {
            //caso as credenciais foram válidas, então direciona para página index
            return "/funcionario.xhtml?faces-redirect=true";
        } else {
            //senão, exibe uma mensagem de falha...
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }

}
