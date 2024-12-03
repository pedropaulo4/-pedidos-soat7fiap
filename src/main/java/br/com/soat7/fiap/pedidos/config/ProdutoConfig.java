package br.com.soat7.fiap.pedidos.config;

import br.com.soat7.fiap.pedidos.application.gateways.ProdutoGateway;
import br.com.soat7.fiap.pedidos.application.usecases.AdicionarProdutoUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.ConsultarProdutoCategoriaUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.EditarProdutoUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.ExcluirProdutoUsecase;
import br.com.soat7.fiap.pedidos.infrastructure.gateways.ProdutoRepositoryGateway;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoConfig {

    @Bean
    AdicionarProdutoUsecase adicionarProdutoUseCase(ProdutoGateway produtoGateway) {
        return new AdicionarProdutoUsecase(produtoGateway);
    }

    @Bean
    ConsultarProdutoCategoriaUsecase consultarProdutoUseCase(ProdutoGateway produtoGateway) {
        return new ConsultarProdutoCategoriaUsecase(produtoGateway);
    }

    @Bean
    EditarProdutoUsecase editarProdutoUseCase(ProdutoGateway produtoGateway) {
        return new EditarProdutoUsecase(produtoGateway);
    }

    @Bean
    ExcluirProdutoUsecase excluirProdutoUseCase(ProdutoGateway produtoGateway) {
        return new ExcluirProdutoUsecase(produtoGateway);
    }

    @Bean
    ProdutoGateway produtoGateway(ProdutoRepository produtoRepository) {
        return new ProdutoRepositoryGateway(produtoRepository);
    }
}
