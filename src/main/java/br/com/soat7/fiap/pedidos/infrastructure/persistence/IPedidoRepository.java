package br.com.soat7.fiap.pedidos.infrastructure.persistence;


import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPedidoRepository extends JpaRepository<PedidoEntity, Long> {

    @Query("SELECT p FROM PedidoEntity p JOIN FETCH p.produtoList WHERE p.status != 'FINALIZADO' " +
            "ORDER BY CASE p.status WHEN 'PRONTO' THEN 1 WHEN 'EM_PREPARACAO' THEN 2 WHEN 'RECEBIDO' THEN 3 ELSE 4 " +
            "END, p.dataCadastro ASC")
    List<PedidoEntity> findNotFinalizedAndOrdered();
}