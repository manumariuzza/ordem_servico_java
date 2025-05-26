package br.upf.projetojfprimefaces.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ordem")//mapeando o nome da tabela no banco de dados
public class OrdemEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;


    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "datafim")
    private Date dataFim;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "datainicio")
    private Date dataInicio;
    
    @Column(name = "valor")
    private String valor;
    
    @Column(name = "problema")
    private String problema;

    @Column(name = "situacao")
    private String situacao;
    
    @Column(name = "diagnostico")
    private String diagnostico;
    
    @Column(name = "observacao")
    private String observacao;

    
    
      //mapeamento (n:1) Automovel    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_automovel", referencedColumnName = "id")
    private AutomovelEntity idAutomovel;

    public AutomovelEntity getIdAutomovel() {
        return idAutomovel;
    }
    public void setIdAutomovel(AutomovelEntity idAutomovel) {
        this.idAutomovel = idAutomovel;
    }    
    
    
      //mapeamento (n:1) Pessoa  
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private PessoaEntity idPessoa;

    public PessoaEntity getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(PessoaEntity idPessoa) {
        this.idPessoa = idPessoa;
    }    
    
    
      //mapeamento (n:1) Funcionario
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id")
    private FuncionarioEntity idFuncionario;

    public FuncionarioEntity getIdFuncionario() {
        return idFuncionario;
    }
    public void setIdFuncionario(FuncionarioEntity idFuncionario) {
        this.idFuncionario = idFuncionario;
        
    }   

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

     public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemEntity other = (OrdemEntity) obj;
        return Objects.equals(this.id, other.id);
    }

}
