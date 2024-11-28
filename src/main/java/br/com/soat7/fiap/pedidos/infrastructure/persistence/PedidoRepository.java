package br.com.soat7.fiap.pedidos.infrastructure.persistence;


import br.com.soat7.fiap.pedidos.domain.Pedido;
import br.com.soat7.fiap.pedidos.infrastructure.persistence.entity.PedidoEntity;
import jakarta.persistence.EntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Component
public class PedidoRepository {

    private final IPedidoRepository repository;
    private EntityManager entityManager;

    public PedidoRepository(IPedidoRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        try {
            PedidoEntity savedPedidoEntity = this.repository.save(new PedidoEntity(pedido));
            entityManager.flush();
            entityManager.clear();
            return this.repository.findById(savedPedidoEntity.getId()).get().toPedido();
        } catch (Exception e) {
            String message = "Erro ao salvar os valores informados. Verifique as valores informados.";
            if (e.getMessage().contains("Referential integrity constraint violation")){
                message = "Um ou mais produtos da lista não foi encontrado. Verifique os valores informados.";
            }
            throw new DataIntegrityViolationException(message);
        }
    }

    public Pedido findById(Long id) {
        PedidoEntity pedidoEntity = repository.findById(id).orElseThrow(() -> {
            String errorMessage = String.format("O Pedido [%s] não foi encontrado.", id);
            return new NoSuchElementException(errorMessage);
        });
        return pedidoEntity.toPedido();
    }

    public List<Pedido> findAll(){
        return this.repository.findNotFinalizedAndOrdered().stream().map(PedidoEntity::toPedido).toList();
    }
}
