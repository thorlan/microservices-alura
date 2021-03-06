package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.dto.CompraDTO;
import br.com.alura.microservice.loja.model.Compra;

@Service
public class CompraService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	@Autowired
	private FornecedorClient fornecedorClient;

	public Compra realizaCompra(CompraDTO compra) {
		
		LOG.info("Buscando informações do fornecedor de {}" , compra.getEndereco().getEstado());
		var infoFornecedorDTO = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		
		LOG.info("Realizando um pedido");
		var pedido = fornecedorClient.realizaPedido(compra.getItens());
		
		var compraRealizada = new Compra(pedido.getId(), pedido.getTempoDePreparo(), infoFornecedorDTO.getEndereco().toString());
		
		return compraRealizada;
	}

}
