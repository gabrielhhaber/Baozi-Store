package br.com.baozi.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.baozi.store.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByClienteId(Long clienteId);

    boolean existsByProdutoId(Long produtoId);
}
