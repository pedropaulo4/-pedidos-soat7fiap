package br.com.soat7.fiap.pedidos.infrastructure.persistence;

import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProdutoRepository  extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByCategoria(Categoria categoria);
}
