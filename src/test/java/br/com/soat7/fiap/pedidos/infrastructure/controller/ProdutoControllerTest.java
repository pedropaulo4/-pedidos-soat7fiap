package br.com.soat7.fiap.pedidos.infrastructure.controller;

import br.com.soat7.fiap.pedidos.application.usecases.AdicionarProdutoUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.ConsultarProdutoCategoriaUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.EditarProdutoUsecase;
import br.com.soat7.fiap.pedidos.application.usecases.ExcluirProdutoUsecase;
import br.com.soat7.fiap.pedidos.domain.Produto;
import br.com.soat7.fiap.pedidos.domain.types.Categoria;
import br.com.soat7.fiap.pedidos.infrastructure.controller.request.CategoriaRequest;
import br.com.soat7.fiap.pedidos.infrastructure.controller.request.ProdutoRequest;
import br.com.soat7.fiap.pedidos.infrastructure.controller.response.ProdutoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdicionarProdutoUsecase adicionarProdutoUsecase;

    @MockBean
    private ConsultarProdutoCategoriaUsecase consultarProdutoCategoriaUsecase;

    @MockBean
    private ExcluirProdutoUsecase excluirProdutoUsecase;

    @MockBean
    private EditarProdutoUsecase editarProdutoUsecase;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void adicionarProduto_deveRetornar201Created() throws Exception {
        // Arrange
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("Produto Teste");
        ProdutoResponse produtoResponse = new ProdutoResponse();
        produtoResponse.setId(1L);
        produtoResponse.setNome("Produto Teste");

        when(modelMapper.map(any(ProdutoRequest.class), any(Class.class))).thenReturn(produto);
        when(adicionarProdutoUsecase.adicionarProduto(any(Produto.class))).thenReturn(produto);
        when(modelMapper.map(any(Produto.class), any(Class.class))).thenReturn(produtoResponse);

        // Act
        ResultActions result = mockMvc.perform(post("/rest/api/v1/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoRequest)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Produto Teste")));

    }


    @Test
    void atualizar_deveRetornar200OK() throws Exception {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Produto Teste Atualizado");
        ProdutoRequest produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("Produto Teste Atualizado");
        ProdutoResponse produtoResponse = new ProdutoResponse();
        produtoResponse.setId(id);
        produtoResponse.setNome("Produto Teste Atualizado");


        when(modelMapper.map(any(ProdutoRequest.class), any(Class.class))).thenReturn(produto);
        when(editarProdutoUsecase.editarProduto(anyLong(), any(Produto.class))).thenReturn(produto);
        when(modelMapper.map(any(Produto.class), any(Class.class))).thenReturn(produtoResponse);

        // Act
        ResultActions result = mockMvc.perform(put("/rest/api/v1/produtos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoRequest)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.nome", is("Produto Teste Atualizado")));
    }

    @Test
    void deletar_deveRetornar204NoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/rest/api/v1/produtos/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}