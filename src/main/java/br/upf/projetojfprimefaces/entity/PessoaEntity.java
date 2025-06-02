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
@Table(name = "pessoa")//mapeando o nome da tabela no banco de dados
public class PessoaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false) //atributo não é opcional
    @NotNull //definido como obrigatório
    @Size(min = 1, max = 500) //quantidade min e max de caracteres
    @Column(name = "nome")
    private String nome;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "datanascimento")
    private Date dataNascimento;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "email")
    private String email;

    @Column(name = "cidadania")
    private String cidadania;

    @Column(name = "fumante")
    private boolean fumante;

    @Basic(optional = false)
    @NotNull
    @Column(name = "idade")
    private Integer idade;

    @Column(name = "observacao")
    private String observacao;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorareg")
    private Date datahorareg;
    
    //mapeamento (n:1)    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cidade", referencedColumnName = "id")
    private CidadeEntity idCidade;

    public CidadeEntity getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(CidadeEntity idCidade) {
        this.idCidade = idCidade;
    }    

    public Date getDatahorareg() {
        return datahorareg;
    }

    public void setDatahorareg(Date datahorareg) {
        this.datahorareg = datahorareg;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidadania() {
        return cidadania;
    }

    public void setCidadania(String cidadania) {
        this.cidadania = cidadania;
    }

    public boolean isFumante() {
        return fumante;
    }

    public void setFumante(boolean fumante) {
        this.fumante = fumante;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
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
        final PessoaEntity other = (PessoaEntity) obj;
        return Objects.equals(this.id, other.id);
    }

}
