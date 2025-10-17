package br.com.datascience.processingservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "dados_vendas")
public class DadosVendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Chave Estrangeira para o Dataset de Origem ---
    @ManyToOne 
    @JoinColumn(name = "dataset_id", nullable = false)
    private Dataset dataset;

    // Colunas do CSV Padrão
    @Column(name = "data_transacao", nullable = false)
    private LocalDate dataTransacao;

    @Column(name = "valor_liquido", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorLiquido;

    @Column(name = "categoria_produto", length = 100)
    private String categoriaProduto;

    @Column(name = "regiao", length = 50)
    private String regiao;

    @Column(name = "canal_venda", length = 50)
    private String canalVenda;

    // --- Construtor vazio obrigatório ---
    public DadosVendas() {}

    // --- Construtor com argumentos ---
    public DadosVendas(Dataset dataset, LocalDate dataTransacao, BigDecimal valorLiquido,
                       String categoriaProduto, String regiao, String canalVenda) {
        this.dataset = dataset;
        this.dataTransacao = dataTransacao;
        this.valorLiquido = valorLiquido;
        this.categoriaProduto = categoriaProduto;
        this.regiao = regiao;
        this.canalVenda = canalVenda;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public Dataset getDataset() { return dataset; }
    public LocalDate getDataTransacao() { return dataTransacao; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public String getCategoriaProduto() { return categoriaProduto; }
    public String getRegiao() { return regiao; }
    public String getCanalVenda() { return canalVenda; }

    // --- Setters ---
    public void setDataset(Dataset dataset) { this.dataset = dataset; }
    public void setDataTransacao(LocalDate dataTransacao) { this.dataTransacao = dataTransacao; }
    public void setValorLiquido(BigDecimal valorLiquido) { this.valorLiquido = valorLiquido; }
    public void setCategoriaProduto(String categoriaProduto) { this.categoriaProduto = categoriaProduto; }
    public void setRegiao(String regiao) { this.regiao = regiao; }
    public void setCanalVenda(String canalVenda) { this.canalVenda = canalVenda; }
}
