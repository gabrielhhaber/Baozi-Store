package br.com.baozi.store.dto;

import java.math.BigDecimal;

import br.com.baozi.store.model.Pedido;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long produtoId,
        String produtoNome,
        BigDecimal precoUnitario,
        Integer quantidade,
        BigDecimal valorTotal) {

    public static PedidoResponse de(Pedido pedido) {
        BigDecimal preco = pedido.getProduto().getPreco();
        BigDecimal total = preco.multiply(BigDecimal.valueOf(pedido.getQuantidade()));
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getProduto().getId(),
                pedido.getProduto().getNome(),
                preco,
                pedido.getQuantidade(),
                total);
    }
}
