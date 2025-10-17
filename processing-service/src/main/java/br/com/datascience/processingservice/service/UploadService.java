package br.com.datascience.processingservice.service;

import br.com.datascience.processingservice.entity.DadosVendas;
import br.com.datascience.processingservice.entity.Dataset;
import br.com.datascience.processingservice.entity.User;
import br.com.datascience.processingservice.repository.DadosVendasRepository;
import br.com.datascience.processingservice.repository.DatasetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DadosVendasRepository dadosVendasRepository;

    @Transactional
    public Dataset processaUploadCsvVendas(MultipartFile file, User analista) {

        // 1. SALVAR METADADOS (Tabela Dataset)
        Dataset dataset = new Dataset();
        dataset.setName(file.getOriginalFilename());
        dataset.setFilePath(file.getOriginalFilename());
        dataset.setUser(analista); 

        // Salva e captura o Dataset salvo
        Dataset savedDataset = datasetRepository.save(dataset);

        // 2. PROCESSAR CSV
        List<DadosVendas> vendasParaInserir = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : csvParser) {
                DadosVendas venda = new DadosVendas();
                venda.setDataset(savedDataset);

                try {
                    venda.setDataTransacao(LocalDate.parse(record.get("data_transacao"))); 
                    venda.setValorLiquido(new BigDecimal(record.get("valor_liquido")));
                    venda.setCategoriaProduto(record.get("categoria_produto"));
                    venda.setRegiao(record.get("regiao"));
                    venda.setCanalVenda(record.get("canal_venda"));

                    vendasParaInserir.add(venda);
                } catch (DateTimeParseException | NumberFormatException e) {
                    // 💡 MUDANÇA: APENAS REGISTRE O ERRO E PULE A LINHA! 
                    // Isso evita que um erro de formato em uma única linha trave toda a transação.
                    System.err.println("ERRO DE FORMATO NA LINHA " + record.getRecordNumber() + 
                                       " DO CSV (Dados ignorados): " + e.getMessage());
                    continue; // Pula para a próxima linha do CSV
                }
            }

            // 3. INSERÇÃO EM LOTE
            dadosVendasRepository.saveAll(vendasParaInserir);

            // 4. Atualiza status do dataset
            savedDataset.setStatus(Dataset.DatasetStatus.PROCESSADO);
            datasetRepository.save(savedDataset);

        } catch (Exception e) {
            // Se algo falhar, marcar como ERRO
            savedDataset.setStatus(Dataset.DatasetStatus.ERRO);
            datasetRepository.save(savedDataset);
            throw new RuntimeException("Falha no processamento do CSV.", e);
        }
        return dataset; 
    }
}

